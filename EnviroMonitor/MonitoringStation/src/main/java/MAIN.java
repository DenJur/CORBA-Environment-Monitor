import Controllers.CLI;
import Interfaces.ISensor;
import SharedImplemintations.IORFileProvider;
import Controllers.Manager;
import Controllers.MonitoringStationImpl;
import Models.Settings;
import ViewControllers.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MAIN extends Application {
    private static ISensor controller;
    private static Manager manager;
    private static final Logger logger = LogManager.getLogger(MAIN.class.getName());

    public static void main(String[] args) {
        //parse program arguments
        //will initialize Settings singleton
        new CLI(args).parse();
        //Initialize sensor and monitoring station implementations
        controller = new MainViewController();
        MonitoringStationImpl station = new MonitoringStationImpl(controller);
        try {
            //try to initialize manager that starts all other threads
            manager = new Manager(args, station, new IORFileProvider(Settings.getInstance().getIORPath()));
            launch(args);
        }
        catch(Exception e){
            logger.fatal("Fatal error while initializing.",e);
            System.exit(1);
        }
    }

    @Override
    public void stop() throws Exception {
        Settings.getInstance().setActive(false);
        manager.shutdown();
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //load javafx GUI
            primaryStage.setTitle("Monitoring Station");
            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setController(controller);
            rootLoader.setLocation(MAIN.class.getResource("Views/MainView.fxml"));
            Parent rootLayout = rootLoader.load();
            Scene mainScene = new Scene(rootLayout);
            primaryStage.setResizable(false);
            primaryStage.setScene(mainScene);
            primaryStage.show();
            Settings.getInstance().setActive(true);
        }
        catch (IOException e){
            logger.fatal("Error initialising GUI",e);
            System.exit(2);
        }
    }
}
