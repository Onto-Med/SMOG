package de.imise.excel_api;

import de.imise.excel_api.model_generator.ModelGenerator;
import de.imise.excel_api.util.SmogVersionProvider;
import java.io.File;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
    name = "generate",
    mixinStandardHelpOptions = true,
    versionProvider = SmogVersionProvider.class,
    description = "Generates Java class files in a directory with a desired package name")
public class GenerateJavaFiles implements Callable<Integer> {

  @Parameters(
      index = "0",
      description = "Microsoft Excel file for which Java files are to be generated.")
  private File inputFile;

  @Parameters(
      index = "1",
      description = "The output directory to which the Java files will be written.")
  private File outputDir;

  @Option(
      names = {"-p", "--package"},
      defaultValue = "com.example",
      description = "Package name of the generated Java classes. (defaults to 'com.example')")
  private String packageName;

  public static void main(String... args) {
    int exitCode = new CommandLine(new GenerateJavaFiles()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    try {
      ModelGenerator gen = new ModelGenerator(inputFile);
      gen.generate(outputDir, packageName);
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }
    System.out.printf(
        "Resulting Java files have been written to '%s'.", outputDir.getAbsolutePath());
    return 0;
  }
}
