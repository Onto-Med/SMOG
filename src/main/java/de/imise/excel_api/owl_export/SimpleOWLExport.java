package de.imise.excel_api.owl_export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import de.imise.excel_api.excel_reader.DynamicTableField;
import de.imise.excel_api.excel_reader.DynamicTreeTable;
import de.imise.excel_api.excel_reader.DynamicTreeTableNode;
import de.imise.excel_api.excel_reader.ExcelReader;

public class SimpleOWLExport {

  private List<DynamicTreeTable> treeTabs;
  private OWLOntologyManager man;
  private OWLDataFactory fac;
  private OWLOntology ont;
  private String ontoNS;

  public SimpleOWLExport(String xlsxFile, String ontoIRI) throws OWLOntologyCreationException {
    treeTabs = ExcelReader.getDynamicTreeTables(xlsxFile);
    man = OWLManager.createOWLOntologyManager();
    fac = man.getOWLDataFactory();
    ont = man.createOntology(IRI.create(ontoIRI));
    this.ontoNS = ontoIRI + "#";
  }

  public OWLOntology export() {
    for (DynamicTreeTable tt : treeTabs) addDynamicTreeTable(tt);
    return ont;
  }

  public OWLDataFactory getFactory() {
    return fac;
  }

  public void save(String owlFile) throws OWLOntologyStorageException {
    man.saveOntology(ont, new RDFXMLDocumentFormat(), IRI.create(new File(owlFile).toURI()));
  }

  private void addDynamicTreeTable(DynamicTreeTable tt) {
    for (DynamicTreeTableNode root : tt.getRootNodes()) {
      String clsName = root.getName();
      addClass(clsName);
      addProperties(clsName, root.getProperties());
      for (DynamicTreeTableNode node : root.getChildren()) addClass(node, clsName);
    }
  }

  private void addClass(DynamicTreeTableNode node, String superClsName) {
    String clsName = node.getName();
    addClass(clsName, superClsName);
    addProperties(clsName, node.getProperties());
    for (DynamicTreeTableNode child : node.getChildren()) addClass(child, clsName);
  }

  private void addClass(String clsName) {
    addClass(getClass(clsName), fac.getOWLThing());
  }

  private void addClass(String clsName, String superClsName) {
    addClass(getClass(clsName), getClass(superClsName));
  }

  private void addClass(OWLClass cls, OWLClass superCls) {
    ont.add(fac.getOWLSubClassOfAxiom(cls, superCls));
  }

  private OWLClass getClass(String clsName) {
    return fac.getOWLClass(IRI.create(ontoNS, clean(clsName)));
  }

  private void addProperties(String clsName, Map<String, DynamicTableField> props) {
    if (props == null) return;
    for (Entry<String, DynamicTableField> prop : props.entrySet()) {
      Optional<String> val = prop.getValue().getValue();
      if (val.isPresent()) addProperties(clsName, prop.getKey().trim(), val.get().trim());
    }
  }

  private void addProperties(String clsName, String propSpec, String propValues) {
    for (PropertySpec prop : PropertyReader.getProperties(propSpec, propValues))
      addProperty(clsName, prop);
  }

  private void addProperty(String clsName, PropertySpec propSpec) {
    OWLClass cls = getClass(clsName);
    Property mainProp = propSpec.getMainProperty();
    if (mainProp.hasReferenceRestriction())
      addRestriction(cls, getObjectProperty(mainProp.getProperty()), getClass(mainProp.getValue()));
    else {
      OWLAnnotation ann = getAnnotation(mainProp);
      List<OWLAnnotation> annOfAnns = new ArrayList<>();
      for (Property p : propSpec.getAdditionalProperties()) annOfAnns.add(getAnnotation(p));
      for (Property p : propSpec.getValueProperties()) annOfAnns.add(getAnnotation(p));
      addAnnotation(cls, ann, annOfAnns);
    }
  }

  private OWLAnnotation getAnnotation(Property propSpec) {
    OWLAnnotationProperty prop = getAnnotationProperty(propSpec.getProperty());
    OWLAnnotationValue val = getAnnotationValue(propSpec);
    return fac.getOWLAnnotation(prop, val);
  }

  private OWLAnnotationValue getAnnotationValue(Property propSpec) {
    if (propSpec.hasReference()) return getClass(propSpec.getValue()).getIRI();
    if (propSpec.hasLanguage())
      return fac.getOWLLiteral(propSpec.getValue(), propSpec.getLanguage());
    return fac.getOWLLiteral(propSpec.getValue());
  }

  private OWLAnnotationProperty getAnnotationProperty(String propName) {
    propName = cleanProp(propName);
    if ("comment".equals(propName)) return fac.getRDFSComment();
    if ("label".equals(propName)) return fac.getRDFSLabel();
    if ("alt_label".equals(propName) || "altlabel".equals(propName))
      return fac.getOWLAnnotationProperty("http://www.w3.org/2004/02/skos/core#altLabel");
    return fac.getOWLAnnotationProperty(IRI.create(ontoNS, propName));
  }

  private OWLObjectProperty getObjectProperty(String propName) {
    return fac.getOWLObjectProperty(IRI.create(ontoNS, cleanProp(propName)));
  }

  private void addAnnotation(OWLClass sbj, OWLAnnotation ann, List<OWLAnnotation> annOfAnns) {
    if (annOfAnns.isEmpty()) ont.add(fac.getOWLAnnotationAssertionAxiom(sbj.getIRI(), ann));
    else ont.add(fac.getOWLAnnotationAssertionAxiom(sbj.getIRI(), ann, annOfAnns));
  }

  private void addRestriction(OWLClass cls, OWLObjectProperty prop, OWLClass valCls) {
    ont.add(fac.getOWLSubClassOfAxiom(cls, fac.getOWLObjectSomeValuesFrom(prop, valCls)));
  }

  private String clean(String str) {
    return str.trim().replaceAll("[^A-Za-z0-9_]+", "_");
  }

  private String cleanProp(String str) {
    return clean(str).toLowerCase();
  }
}
