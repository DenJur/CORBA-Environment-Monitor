 package Controllers;

 import Models.Settings;
 import org.apache.commons.cli.*;

 /**
  * Used to parse program arguments into Settings object.
  */
 public class CLI {
    private String[] args;
    private Options options = new Options();

    public CLI(String[] args) {
        this.args = args;
        options.addOption("h", "help", false, "Show help.");
        options.addRequiredOption("p","path",true, "Path to the IOR file.");
        options.addRequiredOption("s","station", true, "Name of the station.");
        options.addRequiredOption("g","group", true, "Name of the group that station belongs to.");
        options.addRequiredOption("l","location", true, "Location where station is located.");
        options.addRequiredOption("a","alarm", true, "Initial alarm threshold. Should be a float number.");
        options.addRequiredOption("r", "regStation", true, "Regional station that this monitor is attached to.");
    }

     /**
      * Parse command line arguments
      * @return parsed app settings
      */
    public Settings parse() {
        CommandLineParser parser = new DefaultParser();
        Settings result= Settings.getInstance();
        //try to parse and show hel if we fail
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h"))
                help();
            //set all the settings parameters
            result.setAlarmLevel(Float.valueOf(cmd.getOptionValue("a")));
            result.setIORPath(cmd.getOptionValue("p"));
            result.setLocation(cmd.getOptionValue("l"));
            result.setStationGroup(cmd.getOptionValue("g"));
            result.setStationName(cmd.getOptionValue("s"));
            result.setRegStationName(cmd.getOptionValue("r"));
        } catch (Exception e) {
            help();
        }
        return result;
    }

     /**
      * Displays some help information about program arguments
      */
    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
        System.exit(0);
    }

}
