package Utils;

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
        options.addRequiredOption("p", "path", true, "Path to the IOR file.");
        options.addRequiredOption("n", "name", true, "Name of the center.");
        options.addOption("l", "load", false, "Load center data from file");
    }

    /**
     * Parse command line arguments
     *
     * @return parsed app settings
     */
    public Settings parse() {
        CommandLineParser parser = new DefaultParser();
        Settings result = Settings.getInstance();
        //try to parse and show hel if we fail
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h"))
                help();
            //set all the settings parameters
            result.setName(cmd.getOptionValue("n"));
            result.setIORPath(cmd.getOptionValue("p"));
            if (cmd.hasOption("l")) result.load();
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

