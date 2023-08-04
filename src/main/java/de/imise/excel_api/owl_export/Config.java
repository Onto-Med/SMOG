package de.imise.excel_api.owl_export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

public class Config {

  private String namespace;
  private String version;
  private String inputFile;
  private String outputFile;
  private Map<String, String> propertyPrefixes = new HashMap<>();
  private List<String> annotationProperties = new ArrayList<>();
  private List<String> objectProperties = new ArrayList<>();
  private Map<String, List<String>> metadata = new HashMap<>();

  public static Config get(String yamlFilePath) {
    return get(new File(yamlFilePath));
  }

  public static Config get(File yamlFile) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    Config config = null;
    try {
      config = mapper.readValue(yamlFile, Config.class);
    } catch (IOException e) {
      System.out.println(
          "The file 'config.yaml' with the following content must be located in the root directory:");
      System.out.println(
          """
          namespace: ...
          version: ...
          inputFile: ...
          outputFile: ...
          propertyPrefixes:
            <name>: <prefix>
          annotationProperties:
            - <uri>
          objectProperties:
            - <uri>
          metadata:
            <property-uri>: [value1, value2, ...]""");
      e.printStackTrace();
    }
    if (config.getNamespace() == null
        || (!config.getNamespace().endsWith("/") && !config.getNamespace().endsWith("#")))
      throw new IllegalArgumentException(
          "The namespace must not be empty and must end with '#' oder '/'!");
    if (config.getInputFile() == null || config.getOutputFile() == null)
      throw new IllegalArgumentException("The input and the output files must not be empty!");
    return config;
  }

  public static void main(String[] args) {
    System.out.println(Config.get("config.yaml"));
  }

  public String getAnnotationProperty(String propName) {
    for (String ap : annotationProperties) if (propName.equals(getName(ap))) return ap;
    return null;
  }

  public String getObjectProperty(String propName) {
    for (String op : objectProperties) if (propName.equals(getName(op))) return op;
    return null;
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

  public void setObjectProperties(List<String> objectProperties) {
    this.objectProperties = objectProperties;
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

  private String getName(String uri) {
    int ind = uri.lastIndexOf('#');
    if (ind > -1) return uri.substring(ind + 1).toLowerCase();
    return uri.substring(uri.lastIndexOf('/') + 1).toLowerCase();
  }
}
