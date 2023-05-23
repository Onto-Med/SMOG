package de.imise.excel_api.owl_export;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.semanticweb.owlapi.apibinding.OWLManager;
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
  private Config config;

  public SimpleOWLExport(Config config) {
    this.config = config;
    treeTabs = ExcelReader.getDynamicTreeTables(config.getInputFile());
    man = OWLManager.createOWLOntologyManager();
    fac = man.getOWLDataFactory();
    try {
      ont = man.createOntology(config.getOntologyIRI());
    } catch (OWLOntologyCreationException e) {
      e.printStackTrace();
    }
  }

  public void export() {
    for (DynamicTreeTable tt : treeTabs) addDynamicTreeTable(tt);
    addOntoAnnotation(
        fac.getOWLAnnotationProperty("http://purl.org/dc/terms/created"),
        fac.getOWLLiteral(
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            OWL2Datatype.XSD_DATE_TIME));
    if (config.getVersion() != null)
      addOntoAnnotation(fac.getOWLVersionInfo(), fac.getOWLLiteral(config.getVersion()));
    for (String prop : config.getMetadata().keySet())
      addMetadata(fac.getOWLAnnotationProperty(prop), config.getMetadata().get(prop));
    save();
  }

  private void addMetadata(OWLAnnotationProperty prop, List<String> values) {
    for (String val : values) {
      if (val.contains("|")) {
        String[] valAr = val.split("\\|");
        addOntoAnnotation(prop, checkIRI(valAr[0].trim(), valAr[1].trim()));
      } else addOntoAnnotation(prop, checkIRI(val.trim(), null));
    }
  }

  public OWLDataFactory getFactory() {
    return fac;
  }

  private void save() {
    try {
      man.saveOntology(ont, config.getOutputFormat(), config.getOutputFileIRI());
    } catch (OWLOntologyStorageException e) {
      e.printStackTrace();
    }
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
    return fac.getOWLClass(IRI.create(config.getNamespace(), cleanCls(clsName)));
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
    String pref = config.getPropertyPrefix(propName);
    if (pref == null) return checkIRI(propSpec.getValue(), null);
    return checkIRI(pref + propSpec.getValue(), null);
  }

  private OWLAnnotationValue checkIRI(String val, String lang) {
    if (val.startsWith("http")) return IRI.create(val);
    if (lang == null) return fac.getOWLLiteral(val);
    return fac.getOWLLiteral(val, lang);
  }

  private OWLAnnotationProperty getAnnotationProperty(String propName) {
    propName = cleanProp(propName);
    String configPropUri = config.getAnnotationProperty(propName.toLowerCase());
    if (configPropUri != null) return fac.getOWLAnnotationProperty(configPropUri);
    return fac.getOWLAnnotationProperty(IRI.create(config.getNamespace(), propName));
  }

  private OWLObjectProperty getObjectProperty(String propName) {
    propName = cleanProp(propName);
    String configPropUri = config.getObjectProperty(propName.toLowerCase());
    if (configPropUri != null) return fac.getOWLObjectProperty(configPropUri);
    return fac.getOWLObjectProperty(IRI.create(config.getNamespace(), propName));
  }

  private void addAnnotation(OWLClass sbj, OWLAnnotation ann, List<OWLAnnotation> annOfAnns) {
    if (annOfAnns.isEmpty()) ont.add(fac.getOWLAnnotationAssertionAxiom(sbj.getIRI(), ann));
    else ont.add(fac.getOWLAnnotationAssertionAxiom(sbj.getIRI(), ann, annOfAnns));
  }

  private void addRestriction(OWLClass cls, OWLObjectProperty prop, OWLClass valCls) {
    ont.add(fac.getOWLSubClassOfAxiom(cls, fac.getOWLObjectSomeValuesFrom(prop, valCls)));
  }

  private String clean(String str) {
    String[] parts = str.trim().split("[^A-Za-z0-9]+");
    String res = "";
    for (String part : parts) if (!part.isBlank()) res += firstLetterToUpper(part);
    if (res.isBlank()) return str;
    return res;
  }

  private String cleanCls(String str) {
    return firstLetterToUpper(clean(str));
  }

  private String cleanProp(String str) {
    return firstLetterToLower(clean(str));
  }

  private String firstLetterToUpper(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private String firstLetterToLower(String str) {
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  public void addOntoVersion(String value, OWL2Datatype datatype) {
    addOntoAnnotation(fac.getOWLVersionInfo(), fac.getOWLLiteral(value, datatype));
  }

  private void addOntoAnnotation(OWLAnnotationProperty prop, OWLAnnotationValue val) {
    ont.addAxiom(
        fac.getOWLAnnotationAssertionAxiom(prop, ont.getOntologyID().getOntologyIRI().get(), val));
  }

  public static void main(String[] args) {
    SimpleOWLExport exp = new SimpleOWLExport(Config.get("config.yaml"));
    exp.export();
    //    exp.addOntoVersion(
    //        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    //        OWL2Datatype.XSD_DATE_TIME);
    //    exp.save();
  }
}
