package de.imise.excel_api.owl_export;

import de.imise.excel_api.excel_reader.DynamicTableField;
import de.imise.excel_api.excel_reader.DynamicTreeTable;
import de.imise.excel_api.excel_reader.DynamicTreeTableNode;
import de.imise.excel_api.excel_reader.ExcelReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleOWLExport {

  private static final Logger log = LoggerFactory.getLogger(SimpleOWLExport.class);
  private final List<DynamicTreeTable> treeTabs;
  private final OWLOntologyManager man;
  private final OWLDataFactory fac;
  private final Config config;
  private OWLOntology ont;

  public SimpleOWLExport(Config config) {
    this.config = config;
    treeTabs = ExcelReader.getDynamicTreeTables(config.getInputFile());
    man = OWLManager.createOWLOntologyManager();
    fac = man.getOWLDataFactory();
    try {
      ont = man.createOntology(config.getOntologyIRI());
    } catch (OWLOntologyCreationException e) {
      log.error(e.getLocalizedMessage());
    }
  }

  public static void main(String[] args) {
    SimpleOWLExport exp = new SimpleOWLExport(Config.get("config.yaml"));
    exp.export();
  }

  public void report() {
    var classes = ont.getClassesInSignature();
    log.info("Exported " + classes.size() + " classes.");
    var unannotated =
        classes.stream()
            .filter(
                cls ->
                    !cls.isTopEntity()
                        && !cls.getIRI().toString().endsWith("Class") // metaclass
                        && ont.getAnnotationAssertionAxioms(cls.getIRI()).isEmpty())
            .toList();
    if (!unannotated.isEmpty())
      log.warn(
          "The following "
              + unannotated.size()
              + " classes don't have any annotations: "
              + unannotated.stream()
                  .map(cls -> cls.toStringID().replaceAll(".*/", ""))
                  .reduce((s, t) -> s + ", " + t)
                  .get());
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
    addMetaClasses();
    save();
    report();
  }

  public void addOntoVersion(String value, OWL2Datatype datatype) {
    addOntoAnnotation(fac.getOWLVersionInfo(), fac.getOWLLiteral(value, datatype));
  }

  public OWLDataFactory getFactory() {
    return fac;
  }

  private void addMetadata(OWLAnnotationProperty prop, List<String> values) {
    for (String val : values) {
      if (val.contains("|")) {
        String[] valAr = val.split("\\|");
        addOntoAnnotation(prop, checkIRI(valAr[0].trim(), valAr[1].trim()));
      } else addOntoAnnotation(prop, checkIRI(val.trim(), null));
    }
  }

  private void save() {
    try {
      man.saveOntology(ont, config.getOutputFormat(), config.getOutputFileIRI());
    } catch (OWLOntologyStorageException e) {
      log.error(e.getLocalizedMessage());
    }
  }

  private void addDynamicTreeTable(DynamicTreeTable tt) {
    for (DynamicTreeTableNode root : tt.getRootNodes()) {
      String clsName = root.name();
      addClass(clsName);

      addProperties(clsName, root.getProperties());
      for (DynamicTreeTableNode node : root.getChildren()) addClass(node, clsName);
    }
  }

  private void addClass(DynamicTreeTableNode node, String superClsName) {
    String clsName = node.name();
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

  private void addMetaClasses(OWLClass meta, OWLClass c) {
    ont.getSubClassAxiomsForSuperClass(c).stream()
        .map(axiom -> axiom.getSubClass())
        .filter(OWLClassExpression::isOWLClass)
        .map(OWLClassExpression::asOWLClass)
        .forEach(
            subClass -> {
              var ind = fac.getOWLNamedIndividual(subClass.getIRI());
              ont.add(fac.getOWLClassAssertionAxiom(meta, ind));
              // endless loop if there are subclass cycles, which shouldn't exist
              addMetaClasses(meta, subClass);
            });
  }

  private void addMetaClasses() {
    for (var cls : config.getMetaClasses()) {
      var base = fac.getOWLClass(cls);
      var meta = fac.getOWLClass(base.getIRI().getIRIString() + "Class");
      addMetaClasses(meta, base);
    }
  }

  private OWLClass getClass(String clsName) {
    try {
      return fac.getOWLClass(IRI.create(config.getNamespace(), cleanCls(clsName)));
    } catch (Exception e) {
      throw new RuntimeException("Cannot get class with name '" + clsName + "'");
    }
  }

  private void addProperties(String clsName, Map<String, DynamicTableField> props) {
    if (props == null) return;
    for (var prop : props.entrySet()) {
      Optional<String> val = prop.getValue().value();
      val.ifPresent(s -> addProperties(clsName, prop.getKey().trim(), s.trim()));
    }
  }

  private void addProperties(String clsName, String propSpec, String propValues) {
    try {
      for (PropertySpec prop : PropertyReader.getProperties(propSpec, propValues))
        addProperty(clsName, prop);
    } catch (Exception e) {
      throw new RuntimeException(
          "Class '"
              + clsName
              + "', property '"
              + propSpec
              + "': cannot add values '"
              + propValues
              + "'",
          e);
    }
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
    try {
      if (propSpec.hasReference()) return getClass(propSpec.getValue()).getIRI();
      if (propSpec.hasLanguage())
        return fac.getOWLLiteral(propSpec.getValue(), propSpec.getLanguage());
      String pref = config.getPropertyPrefix(propName);
      if (pref == null) return checkIRI(propSpec.getValue(), null);
      return checkIRI(pref + propSpec.getValue(), null);
    } catch (Exception e) {
      throw new RuntimeException(
          "Cannot get annotation value for property spec value '"
              + propSpec.getValue()
              + "' with property name '"
              + propName
              + "'",
          e);
    }
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
    StringBuilder res = new StringBuilder();
    for (String part : parts) if (!part.isBlank()) res.append(firstLetterToUpper(part));
    if (res.toString().isBlank()) return str;
    return res.toString();
  }

  private String cleanCls(String str) {
    return firstLetterToUpper(clean(str));
  }

  private String cleanProp(String str) {
    return firstLetterToLower(clean(str));
  }

  private String firstLetterToUpper(String str) {
    if (str.isEmpty())
      throw new IllegalArgumentException("can't convert first letter to uppercase: input is empty");
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private String firstLetterToLower(String str) {
    if (str.isEmpty())
      throw new IllegalArgumentException("can't convert first letter to lowercase: input is empty");
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  private void addOntoAnnotation(OWLAnnotationProperty prop, OWLAnnotationValue val) {
    ont.addAxiom(
        fac.getOWLAnnotationAssertionAxiom(prop, ont.getOntologyID().getOntologyIRI().get(), val));
  }
}
