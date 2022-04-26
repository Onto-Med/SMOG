package de.imise.excel_api.owl_export;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
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
      String clsName = clean(root.getName());
      addClass(clsName);
      addProperties(clsName, root.getProperties());
      for (DynamicTreeTableNode node : root.getChildren()) addClass(node, clsName);
    }
  }

  private void addClass(DynamicTreeTableNode node, String superClsName) {
    String clsName = clean(node.getName());
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
    return fac.getOWLClass(IRI.create(ontoNS, clsName));
  }

  private void addProperties(String clsName, Map<String, DynamicTableField> props) {
    if (props == null) return;
    for (Entry<String, DynamicTableField> prop : props.entrySet()) {
      Optional<String> val = prop.getValue().getValue();
      if (val.isPresent()) addProperties(clsName, prop.getKey().trim(), val.get().trim());
    }
  }

  private void addProperties(String clsName, String propName, String propValues) {
    if (propValues.contains("|")) {
      String[] propVals = propValues.split("\\s*\\Q|\\E\\s*");
      for (String propVal : propVals) addProperty(clsName, propName, propVal);
    } else addProperty(clsName, propName, propValues);
  }

  private void addProperty(String clsName, String propName, String propValue) {
    OWLClass cls = getClass(clsName);
    if (propName.contains(":")) {
      String[] propNameAttr = propName.split("\\s*:\\s*");
      addComplexProperty(cls, propNameAttr[0], propNameAttr[1].toLowerCase(), propValue);
    } else addAnnotation(cls, getAnnotationProperty(propName), fac.getOWLLiteral(propValue));
  }

  private void addComplexProperty(
      OWLClass cls, String propName, String propAttr, String propValue) {
    if (propAttr.startsWith("ref")) {
      OWLClass valCls = getClass(clean(propValue));
      if ("ref-a".equals(propAttr))
        addAnnotation(cls, getAnnotationProperty(propName), valCls.getIRI());
      else if ("ref-r".equals(propAttr)) addRestriction(cls, getObjectProperty(propName), valCls);
    } else
      addAnnotation(cls, getAnnotationProperty(propName), fac.getOWLLiteral(propValue, propAttr));
  }

  private OWLAnnotationProperty getAnnotationProperty(String propName) {
    propName = clean(propName).toLowerCase();
    if ("comment".equals(propName)) return fac.getRDFSComment();
    if ("label".equals(propName)) return fac.getRDFSLabel();
    return fac.getOWLAnnotationProperty(IRI.create(ontoNS, propName));
  }

  private OWLObjectProperty getObjectProperty(String propName) {
    return fac.getOWLObjectProperty(IRI.create(ontoNS, clean(propName).toLowerCase()));
  }

  private void addAnnotation(OWLClass sbj, OWLAnnotationProperty prop, OWLAnnotationValue obj) {
    ont.add(fac.getOWLAnnotationAssertionAxiom(prop, sbj.getIRI(), obj));
  }

  private void addRestriction(OWLClass cls, OWLObjectProperty prop, OWLClass valCls) {
    ont.add(fac.getOWLSubClassOfAxiom(cls, fac.getOWLObjectSomeValuesFrom(prop, valCls)));
  }

  private String clean(String str) {
    return str.trim().replaceAll("[^A-Za-z0-9_]+", "_");
  }
}
