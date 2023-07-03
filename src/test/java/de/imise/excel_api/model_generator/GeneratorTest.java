package de.imise.excel_api.model_generator;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GeneratorTest {

  private static final File testFile =
      new File(
          Objects.requireNonNull(
                  GeneratorTest.class.getClassLoader().getResource("test_workbook.xlsx"))
              .getPath());
  private static final String OUTPUT_DIR = "test";

  @BeforeAll
  public static void setup() {
    new File(OUTPUT_DIR).mkdirs();
  }

  @AfterAll
  public static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File(OUTPUT_DIR));
  }

  @Test
  public void generate() {
    ModelGenerator gen = new ModelGenerator(testFile);
    gen.generate(new File(OUTPUT_DIR), "test.model");
  }
}
