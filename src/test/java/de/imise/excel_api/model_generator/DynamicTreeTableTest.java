package de.imise.excel_api.model_generator;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.search.EntitySearcher;

import com.google.common.collect.Sets;

import de.imise.excel_api.excel_reader.DynamicTreeTable;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.owl_export.SimpleOWLExport;

public class DynamicTreeTableTest {

  @Test
  public void test() {
    DynamicTreeTable treeTab =
        ExcelReader.getDynamicTreeTable("DynamicTreeTable1.xlsx", "DynamicTreeTable");

    String exp =
        "C1 :: {P1=DynamicTableField [Optional[v1]], P2=DynamicTableField [Optional[v21]], P3=DynamicTableField [Optional[v41]]}"
            + System.lineSeparator()
            + "  C11 :: {P1=DynamicTableField [Optional[v2]], P2=DynamicTableField [Optional[v22]], P3=DynamicTableField [Optional[v42]]}"
            + System.lineSeparator()
            + "    C111 :: {P1=DynamicTableField [Optional[v3]], P2=DynamicTableField [Optional[v23]], P3=DynamicTableField [Optional[v43]]}"
            + System.lineSeparator()
            + "    C112 :: {P1=DynamicTableField [Optional[v4]], P2=DynamicTableField [Optional[v24]], P3=DynamicTableField [Optional[v44]]}"
            + System.lineSeparator()
            + "    C113 :: {P1=DynamicTableField [Optional[v5]], P2=DynamicTableField [Optional[v25]], P3=DynamicTableField [Optional[v45]]}"
            + System.lineSeparator()
            + "  C12 :: {P1=DynamicTableField [Optional[v6]], P2=DynamicTableField [Optional[v26]], P3=DynamicTableField [Optional[v46]]}"
            + System.lineSeparator()
            + "    C121 :: {P1=DynamicTableField [Optional[v7]], P2=DynamicTableField [Optional[v27]], P3=DynamicTableField [Optional[v47]]}"
            + System.lineSeparator()
            + "    C122 :: {P1=DynamicTableField [Optional[v8]], P2=DynamicTableField [Optional[v28]], P3=DynamicTableField [Optional[v48]]}"
            + System.lineSeparator()
            + "    C123 :: {P1=DynamicTableField [Optional[v9]], P2=DynamicTableField [Optional[v29]], P3=DynamicTableField [Optional[v49]]}"
            + System.lineSeparator()
            + "C2 :: {P1=DynamicTableField [Optional[v10]], P2=DynamicTableField [Optional[v30]], P3=DynamicTableField [Optional[v50]]}"
            + System.lineSeparator()
            + "  C21 :: {P1=DynamicTableField [Optional[v11]], P2=DynamicTableField [Optional[v31]], P3=DynamicTableField [Optional[v51]]}"
            + System.lineSeparator()
            + "    C211 :: {P1=DynamicTableField [Optional[v12]], P2=DynamicTableField [Optional[v32]], P3=DynamicTableField [Optional[v52]]}"
            + System.lineSeparator()
            + "    C212 :: {P1=DynamicTableField [Optional[v13]], P2=DynamicTableField [Optional[v33]], P3=DynamicTableField [Optional[v53]]}"
            + System.lineSeparator()
            + "    C213 :: {P1=DynamicTableField [Optional[v14]], P2=DynamicTableField [Optional[v34]], P3=DynamicTableField [Optional[v54]]}"
            + System.lineSeparator()
            + "  C22 :: {P1=DynamicTableField [Optional[v15]], P2=DynamicTableField [Optional[v35]], P3=DynamicTableField [Optional[v55]]}"
            + System.lineSeparator()
            + "    C221 :: {P1=DynamicTableField [Optional[v16]], P2=DynamicTableField [Optional[v36]], P3=DynamicTableField [Optional[v56]]}"
            + System.lineSeparator()
            + "    C222 :: {P1=DynamicTableField [Optional[v17]], P2=DynamicTableField [Optional[v37]], P3=DynamicTableField [Optional[v57]]}"
            + System.lineSeparator()
            + "    C223 :: {P1=DynamicTableField [Optional[v18]], P2=DynamicTableField [Optional[v38]], P3=DynamicTableField [Optional[v58]]}"
            + System.lineSeparator();

    System.out.println(treeTab);

    assertEquals(exp, treeTab.toString());
  }

  @Test
  public void testOWLExport() throws OWLOntologyCreationException, OWLOntologyStorageException {
    SimpleOWLExport exp =
        new SimpleOWLExport("DynamicTreeTable2.xlsx", "https://www.test.de/ontologies/ont");
    OWLOntology ont = exp.export();

    OWLClass c11 = exp.getFactory().getOWLClass("https://www.test.de/ontologies/ont#C11");
    Set<String> subClasses =
        EntitySearcher.getSubClasses(c11, ont)
            .map(c -> c.asOWLClass().getIRI().getShortForm())
            .collect(Collectors.toSet());

    assertEquals(Sets.newHashSet("C111", "C113", "C_1_1_2"), subClasses);

    OWLClass c112 = exp.getFactory().getOWLClass("https://www.test.de/ontologies/ont#C_1_1_2");
    Set<OWLAnnotationValue> labels =
        EntitySearcher.getAnnotationObjects(c112, ont, exp.getFactory().getRDFSLabel())
            .map(a -> a.getValue())
            .collect(Collectors.toSet());

    assertEquals(
        Sets.newHashSet(
            exp.getFactory().getOWLLiteral("A", "de"),
            exp.getFactory().getOWLLiteral("B", "de"),
            exp.getFactory().getOWLLiteral("C", "de")),
        labels);

    Set<OWLAnnotationValue> comments =
        EntitySearcher.getAnnotationObjects(c112, ont, exp.getFactory().getRDFSComment())
            .map(a -> a.getValue())
            .collect(Collectors.toSet());

    assertEquals(
        Sets.newHashSet(
            exp.getFactory().getOWLLiteral("D", "en"),
            exp.getFactory().getOWLLiteral("E", "en"),
            exp.getFactory().getOWLLiteral("F", "en")),
        comments);

    Set<OWLAnnotationValue> p1 =
        EntitySearcher.getAnnotationObjects(
                c112,
                ont,
                exp.getFactory().getOWLAnnotationProperty("https://www.test.de/ontologies/ont#P1"))
            .map(a -> a.getValue())
            .collect(Collectors.toSet());

    assertEquals(Sets.newHashSet(exp.getFactory().getOWLLiteral("v4")), p1);

    Set<OWLAnnotationValue> p2 =
        EntitySearcher.getAnnotationObjects(
                c112,
                ont,
                exp.getFactory().getOWLAnnotationProperty("https://www.test.de/ontologies/ont#P2"))
            .map(a -> a.getValue())
            .collect(Collectors.toSet());

    assertEquals(Sets.newHashSet(exp.getFactory().getOWLLiteral("v24")), p2);

    Set<OWLAnnotationValue> p3 =
        EntitySearcher.getAnnotationObjects(
                c112,
                ont,
                exp.getFactory().getOWLAnnotationProperty("https://www.test.de/ontologies/ont#P3"))
            .map(a -> a.getValue())
            .collect(Collectors.toSet());

    assertEquals(Sets.newHashSet(exp.getFactory().getOWLLiteral("v44")), p3);
  }
}
