package de.imise.excel_api.model_generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.imise.excel_api.excel_reader.DynamicTreeTable;
import de.imise.excel_api.excel_reader.ExcelReader;

public class DynamicTreeTableTest {

  @Test
  public void test() {
    DynamicTreeTable treeTab =
        ExcelReader.getDynamicTreeTable("DynamicTreeTable.xlsx", "DynamicTreeTable");

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
}
