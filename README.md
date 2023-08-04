# Spreadsheet Model Generator (SMOG): A Lightweight Tool for Object-Spreadsheet Mapping

[![Maven Test](https://github.com/Onto-Med/SMOG/actions/workflows/maven-test.yml/badge.svg)](https://github.com/Onto-Med/SMOG/actions/workflows/maven-test.yml)

## Description

The SMOG generates Java programming models from annotated Excel templates to read and write spreadsheet data.
Following spreadsheet entities are inter alia supported: Fields, Tables and Trees.
The entities must be annotated with entity identifiers "Field:", "Table:", "TreeTable:" followed by the entity
name (see example.xlsx). Additionally, data types of fields or table values can be specified in the corresponding
title or head cells (e.g., [Integer] or [StringL] for list types). For list types, an additional separator character
can be defined (e.g., [StringL] <;>). The default separator character is "|".

String fields of a *table 1* can be declared as foreign keys to fields of another *table 2*.
This results into methods being generated for the Java class of entries in *table 1* to access all referenced entries in
*table 2*.

## How to use

### Executable JAR

Download one of our [JAR releases](https://github.com/Onto-Med/SMOG/releases/latest) and run it as shown below
(replace 'x.x.x' with the actual SMOG version):

```sh
# generate Java files in directory 'src/main/java'
java -jar smog-x.x.x-shaded.jar generate --group-id test.model --artifact-id test_artifact --version 0.1.0 example.xlsx src/main/java

# generate Maven package in directory 'lib', default values are used for --group-id, --artifact-id, and --version
java -jar smog-x.x.x-shaded.jar generate --mvn example.xlsx lib

# specify maven home
java -jar "-Dmaven.home=/path/to/maven/home" smog-x.x.x-shaded.jar generate --mvn example.xlsx lib
```

```sh
# perform an OWL export as described in the config.yaml file
java -jar smog-x.x.x-shaded.jar export config.yaml
```

### Maven Dependency

Add SMOG as dependency to your project (e.g. as maven dependency in `pom.xml`). SMOG is available as maven package
on https://maven.pkg.github.com.

```xml
<dependency>
    <groupId>de.imise.excel_api</groupId>
    <artifactId>smog</artifactId>
    <version><!-- smog version --></version>
</dependency>
```

The following example demonstrates how to start the SMOG:

```java
// Build a ModelGenerator object from Microsoft Excel file: example.xlsx
ModelGenerator gen = new ModelGenerator(new File("example.xlsx")); 

// Use the ModelGenerator object to generate Java class files
// in the directory "test/model" with package name "test.model"
gen.generate(new File("test/model"), "test.model");
```

```java
// Build a SimpleOWLExport object from a YAML configuration file.
SimpleOWLExport export = new SimpleOWLExport(Config.get("config.yaml"));

// Perform the export, as specified in the configuration file.
export.export();
```

### OWL Export Configuration

| property       | description                                                                   |
|----------------|-------------------------------------------------------------------------------|
| namespace      | namespace to be used for this ontology                                        |
| version        | version of the ontology, see <https://www.w3.org/TR/owl-ref/#versionInfo-def> |
| inputFile      | Microsoft Excel input file                                                    |
| outputFile     | destination of the output file                                                |
| metadata (map) | additional axioms provided as `<property-uri>: [value1, value2, ...]`         |

Example `config.yaml`:

```yaml
---
namespace: http://example.com/ex#
version: 1.0.0
inputFile: example.xlsx
outputFile: example.owl
metadata:
  http://purl.org/dc/terms/title:
    - Example Ontology
```

## Development

The code in this repository, and in contributions provided via pull requests, should conform to
[Google Java Style](https://google.github.io/styleguide/javaguide.html).

We use the flag `--skip-reflowing-long-strings` for [google-java-format](https://github.com/google/google-java-format),
as it is currently not supported by all IDEs.

If you are running Linux and have `google-java-format` installed, you can run the [format](format) script to reformat
all repository files.

## Reference

> Uciteli, A.; Beger, C.; Kropf, S.; Herre, H. (2019): Spreadsheet Model Generator (SMOG): A Lightweight Tool for
> Object-Spreadsheet Mapping. In: *Studies in health technology and informatics* 267, pp. 110–117. DOI:
> [10.3233/SHTI190814](https://doi.org/10.3233/SHTI190814).

## License

The code in this repository and the package `de.imise.excel_api:smog` is licensed under [MIT](LICENSE).
