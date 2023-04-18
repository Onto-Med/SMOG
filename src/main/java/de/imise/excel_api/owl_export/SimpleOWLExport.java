package de.imise.excel_api.owl_export;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import de.imise.excel_api.excel_reader.DynamicTableField;
import de.imise.excel_api.excel_reader.DynamicTreeTable;
import de.imise.excel_api.excel_reader.DynamicTreeTableNode;
import de.imise.excel_api.excel_reader.ExcelReader;

public class SimpleOWLExport {

  private List<DynamicTreeTable> treeTabs;
  private OWLOntologyManager man;
  private OWLDataFactory fac;
  private OWLOntology ont;
  private String ns;
  private Map<String, String> propertyPrefixes = new HashMap<>();

  public SimpleOWLExport(String xlsxFile, String namespace) throws OWLOntologyCreationException {
    this.ns = namespace.trim();
    if (!ns.endsWith("/") && !ns.endsWith("#"))
      throw new IllegalArgumentException("The namespace must end with '#' oder '/'!");
    treeTabs = ExcelReader.getDynamicTreeTables(xlsxFile);
    man = OWLManager.createOWLOntologyManager();
    fac = man.getOWLDataFactory();
    ont = man.createOntology(IRI.create(ns.substring(0, ns.length() - 1)));
  }

  public void addPropertyPrefix(String prop, String pref) {
    propertyPrefixes.put(prop, pref);
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

  public void save(String outputFile, OWLDocumentFormat format) throws OWLOntologyStorageException {
    man.saveOntology(ont, format, IRI.create(new File(outputFile).toURI()));
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
    return fac.getOWLClass(IRI.create(ns, clean(clsName)));
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
    OWLAnnotationValue val = getAnnotationValue(propSpec, prop.getIRI().getShortForm());
    return fac.getOWLAnnotation(prop, val);
  }

  private OWLAnnotationValue getAnnotationValue(Property propSpec, String propName) {
    if (propSpec.hasReference()) return getClass(propSpec.getValue()).getIRI();
    if (propSpec.hasLanguage())
      return fac.getOWLLiteral(propSpec.getValue(), propSpec.getLanguage());
    String pref = propertyPrefixes.get(propName);
    if (pref == null) return fac.getOWLLiteral(propSpec.getValue());
    return fac.getOWLLiteral(pref + propSpec.getValue());
  }

  private OWLAnnotationProperty getAnnotationProperty(String propName) {
    propName = cleanProp(propName);
    if ("comment".equals(propName)) return fac.getRDFSComment();
    if ("label".equals(propName)) return fac.getRDFSLabel();
    if ("alt_label".equals(propName) || "altlabel".equals(propName))
      return fac.getOWLAnnotationProperty("http://www.w3.org/2004/02/skos/core#altLabel");
    return fac.getOWLAnnotationProperty(IRI.create(ns, propName));
  }

  private OWLObjectProperty getObjectProperty(String propName) {
    return fac.getOWLObjectProperty(IRI.create(ns, cleanProp(propName)));
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

  public void addOntoVersion(String value, OWL2Datatype datatype) {
    addOntoAnnotation(fac.getOWLVersionInfo(), fac.getOWLLiteral(value, datatype));
  }

  public void addOntoAnnotation(OWLAnnotationProperty prop, OWLAnnotationValue val) {
    ont.addAxiom(
        fac.getOWLAnnotationAssertionAxiom(prop, ont.getOntologyID().getOntologyIRI().get(), val));
  }

  public static void main(String[] args)
      throws OWLOntologyCreationException, OWLOntologyStorageException {
    if (args.length < 3) {
      System.out.println(
          "arguments: <ontology namespace> <input.xlsx> <output.owl|.json> [<property> <prefix> ...]");
      System.exit(0);
    }

    String ns = args[0];
    String in = args[1];

    String out = args[2];
    OWLDocumentFormat format =
        (out.endsWith("json")) ? new RDFJsonLDDocumentFormat() : new RDFXMLDocumentFormat();

    SimpleOWLExport exp = new SimpleOWLExport(in, ns);

    if (args.length >= 5)
      for (int i = 3; i < args.length; i += 2) exp.addPropertyPrefix(args[i], args[i + 1]);

    exp.export();
    exp.addOntoVersion(
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        OWL2Datatype.XSD_DATE_TIME);
    exp.save(out, format);
  }
}
