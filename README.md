# Spreadsheet Model Generator (SMOG): A Lightweight Tool for Object-Spreadsheet Mapping

## Description
The SMOG generates Java programming models from annotated Excel templates to read and write spreadsheet data. Following spreadsheet entities are inter alia supported: Fields, Tables and Trees. The entities must be annotated with entity identifiers "Field:", "Table:", "TreeTable:" followed by the entity name (see example.xlsx). Additionally, datatypes of fields or table values can be specified in the corresponding title or head cells (e.g., [Integer] or [StringL] for list types). For list types, an additional separator character can be defined (e.g., [StringL] <;>). The default separator character is "|".

## How to use

The following example demonstrates how to start the SMOG:

```java
// Build a ModelGenerator object from Microsoft Excel file: example.xlsx
ModelGenerator gen = new ModelGenerator(new File("example.xlsx")); 

// Use the ModelGenerator object to generate Java class files in the directory "test/model" with package name "test.model"
gen.generate(new File("test/model"), "test.model");
```
