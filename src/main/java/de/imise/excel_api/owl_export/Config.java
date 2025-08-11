package de.imise.excel_api.owl_export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

/** Read and provide configuration options for the Simple OWL Export from a given YAML file. */
public class Config {

  private String namespace;
  private String version;
  private String inputFile;
  private String outputFile;
  private Map<String, String> propertyPrefixes = new HashMap<>();
  private List<String> annotationProperties = new ArrayList<>();
  private List<String> objectProperties = new ArrayList<>();
  private List<String> metaClasses = new ArrayList<>();
  private Set<String> individualClasses = new HashSet<>();
  private Map<String, List<String>> metadata = new HashMap<>();

  /**
   * Convenience wrapper for {@link Config#get(File)}.
   *
   * @param yamlFilePath A YAML file in the OWL export format as specified in README.md.
   * @return config The config read from the file.
   * @throws IOException if the file cannot be read or is not in the correct format.
   * @throws IllegalArgumentException if the namespace is not set correctly or the input/output
   *     files are not set.
   */
  public static Config get(String yamlFilePath) throws IOException {
    return get(new File(yamlFilePath));
  }

  /**
   * @param yamlFile A YAML file in the OWL export format as specified in README.md.
   * @return config The config read from the file.
   * @throws IOException if the file cannot be read or is not in the correct format.
   * @throws IllegalArgumentException if the namespace is not set correctly or the input/output
   *     files are not set.
   */
  public static Config get(File yamlFile) throws IOException {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    Config config = null;
    try {
      config = mapper.readValue(yamlFile, Config.class);
    } catch (IOException e) {
      System.err.println(
          "Error reading the configuration file '"
              + yamlFile.getAbsolutePath()
              + "'. Please check that the file is readable and has the following content:");
      System.out.println(
          "namespace: ..."
              + System.lineSeparator()
              + "version: ..."
              + System.lineSeparator()
              + "inputFile: ..."
              + System.lineSeparator()
              + "outputFile: ..."
              + System.lineSeparator()
              + "propertyPrefixes:"
              + System.lineSeparator()
              + "  <name>: <prefix>"
              + System.lineSeparator()
              + "annotationProperties:"
              + System.lineSeparator()
              + "  - <uri>"
              + System.lineSeparator()
              + "objectProperties:"
              + System.lineSeparator()
              + "  - <uri>"
              + System.lineSeparator()
              + "metadata:"
              + System.lineSeparator()
              + "  <property-uri>: [value1, value2, ...]");
      throw e;
    }
    if (config.getNamespace() == null
        || (!config.getNamespace().endsWith("/") && !config.getNamespace().endsWith("#")))
      throw new IllegalArgumentException(
          "The namespace must not be empty and must end with '#' oder '/'!");
    if (config.getInputFile() == null || config.getOutputFile() == null)
      throw new IllegalArgumentException("The input and the output files must not be empty!");
    return config;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getInputFile() {
    return inputFile;
  }

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }

  public String getOutputFile() {
    return outputFile;
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  public Map<String, String> getPropertyPrefixes() {
    return propertyPrefixes;
  }

  public void setPropertyPrefixes(Map<String, String> propertyPrefixes) {
    this.propertyPrefixes = propertyPrefixes;
  }

  public List<String> getAnnotationProperties() {
    return annotationProperties;
  }

  public void setAnnotationProperties(List<String> annotationProperties) {
    this.annotationProperties = annotationProperties;
  }

  public List<String> getObjectProperties() {
    return objectProperties;
  }

  public List<String> getMetaClasses() {
    return metaClasses;
  }

  public Set<String> getIndividualClasses() {
    return individualClasses;
  }

  public void setObjectProperties(List<String> objectProperties) {
    this.objectProperties = objectProperties;
  }

  public void setMetaClasses(List<String> metaClasses) {
    this.metaClasses = metaClasses;
  }

  public void setIndividualClasses(Set<String> individualClasses) {
    this.individualClasses = individualClasses;
  }

  public Map<String, List<String>> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, List<String>> metadata) {
    this.metadata = metadata;
  }

  public IRI getOntologyIRI() {
    return IRI.create(namespace.substring(0, namespace.length() - 1));
  }

  public OWLDocumentFormat getOutputFormat() {
    return (outputFile.endsWith("json"))
        ? new RDFJsonLDDocumentFormat()
        : new RDFXMLDocumentFormat();
  }

  public IRI getOutputFileIRI() {
    return IRI.create(new File(outputFile).toURI());
  }

  public String getAnnotationProperty(String propName) {
    for (String ap : annotationProperties) if (propName.equals(getName(ap))) return ap;
    return null;
  }

  public String getObjectProperty(String propName) {
    for (String op : objectProperties) if (propName.equals(getName(op))) return op;
    return null;
  }

  private String getName(String uri) {
    int ind = uri.lastIndexOf('#');
    if (ind > -1) return uri.substring(ind + 1).toLowerCase();
    return uri.substring(uri.lastIndexOf('/') + 1).toLowerCase();
  }

  public String getPropertyPrefix(String propName) {
    return propertyPrefixes.get(propName);
  }

  @Override
  public String toString() {
    return "Config [namespace="
        + namespace
        + ", version="
        + version
        + ", inputFile="
        + inputFile
        + ", outputFile="
        + outputFile
        + ", propertyPrefixes="
        + propertyPrefixes
        + ", annotationProperties="
        + annotationProperties
        + ", objectProperties="
        + objectProperties
        + ", metadata="
        + metadata
        + "]";
  }

  public static void main(String[] args) throws IOException {
    System.out.println(Config.get("config.yaml"));
  }
}
