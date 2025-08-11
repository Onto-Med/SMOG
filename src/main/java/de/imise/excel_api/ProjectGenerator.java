package de.imise.excel_api;

import com.x5.template.Chunk;
import com.x5.template.Theme;
import de.imise.excel_api.model_generator.ModelGenerator;
import de.imise.excel_api.owl_export.Config;
import de.imise.excel_api.owl_export.SimpleOWLExport;
import de.imise.excel_api.util.SmogVersionProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.*;
import org.apache.maven.shared.utils.cli.CommandLineException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;

@Command(
    name = "smog",
    mixinStandardHelpOptions = true,
    versionProvider = SmogVersionProvider.class,
    description =
        "Spreadsheet Model Generator (SMOG): A lightweight tool for Object-Spreadsheet mapping.",
    synopsisSubcommandLabel = "COMMAND")
public class ProjectGenerator implements Callable<Integer> {

  private static final String PROPERTIES_FILE = "smog.properties";

  public static void main(String... args) {
    int exitCode = new CommandLine(new ProjectGenerator()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() {
    throw new ParameterException(
        new CommandLine(new ProjectGenerator()), "Missing required subcommand");
  }

  @Command(showDefaultValues = true)
  int generate(
      @Option(
              names = {"-g", "--group-id"},
              paramLabel = "<group-id>",
              defaultValue = "com.example",
              description = "Group ID of the generated Java classes.")
          String groupId,
      @Option(
              names = {"-a", "--artifact-id"},
              paramLabel = "<artifact-id>",
              defaultValue = "excel_api",
              description = "Artifact ID of the generated Java classes.")
          String artifactId,
      @Option(
              names = {"-v", "--version"},
              paramLabel = "<version>",
              defaultValue = "1.0.0",
              description = "Version of the generated Maven package.")
          String version,
      @Option(
              names = {"-m", "--mvn"},
              description =
                  "Set this option to enable packaging of the generated Java source files via Maven"
                      + " (Maven must be installed and set as env variable)")
          boolean maven,
      @Parameters(
              index = "0",
              paramLabel = "<input Excel>",
              description = "Microsoft Excel file for which Java files are to be generated.")
          File inputFile,
      @Parameters(
              index = "1",
              paramLabel = "<output dir>",
              description = "The output directory to which the Java files will be written.")
          File outputDir) {
    try {
      ModelGenerator gen = new ModelGenerator(inputFile);
      gen.generate(outputDir, groupId + "." + artifactId);

      if (maven) {
        addPomFile(outputDir, groupId, artifactId, version);
        toJar(outputDir);
        System.out.printf(
            "Resulting Maven package has been written to '%s'.", outputDir.getAbsolutePath());
      } else {
        System.out.printf(
            "Resulting Java files have been written to '%s'.", outputDir.getAbsolutePath());
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }

    return 0;
  }

  @Command(showDefaultValues = true)
  int export(
      @Parameters(
              index = "0",
              paramLabel = "<config-file>",
              description = "YAML configuration file for OWL export.")
          File config) {
    try {
      SimpleOWLExport exp = new SimpleOWLExport(Config.get(config));
      exp.export();
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }

    return 0;
  }

  private void addPomFile(File dir, String groupId, String artifactId, String version)
      throws IOException {
    final Properties properties = new Properties();
    properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));

    Theme theme = new Theme();
    Chunk chunk = theme.makeChunk("pom", "xml");
    chunk.set("group_id", groupId);
    chunk.set("artifact_id", artifactId);
    chunk.set("version", version);
    chunk.set("poi_version", properties.getProperty("poi_version"));
    chunk.set("smog_version", properties.getProperty("smog_version"));

    File pomFile = new File(dir, "pom.xml");
    FileWriter out = new FileWriter(pomFile);
    chunk.render(out);
    out.flush();
    out.close();
  }

  private void toJar(File dir) throws MavenInvocationException, IOException, CommandLineException {
    InvocationRequest request =
        new DefaultInvocationRequest()
            .setPomFile(new File(dir, "pom.xml"))
            .addArg("package")
            .setBatchMode(true);

    InvocationResult result = new DefaultInvoker().execute(request);
    if (result.getExitCode() != 0)
      throw new CommandLineException("Maven build finished with ExitCode != 0");

    FileUtils.deleteDirectory(new File(dir, "target"));
  }
}
