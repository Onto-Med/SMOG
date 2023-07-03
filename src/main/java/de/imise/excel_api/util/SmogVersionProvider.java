package de.imise.excel_api.util;

import picocli.CommandLine.IVersionProvider;

public class SmogVersionProvider implements IVersionProvider {

  @Override
  public String[] getVersion() throws Exception {
    String version = SmogVersionProvider.class.getPackage().getImplementationVersion();
    return new String[] {"${COMMAND-FULL-NAME} version " + version};
  }
}
