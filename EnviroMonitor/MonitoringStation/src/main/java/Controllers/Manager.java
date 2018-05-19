package Controllers;

import Environment.MonitoringStationPOA;
import Interfaces.IIORProvider;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.IOException;

/**
 * Central manager for managing initialization and shutdown
 */
public class Manager {
    private ORB orb;
    private NamingContextExt namingService;
    private Monitor monitor;
    private OrbRunner orbRunner;

    /**
     * Constructor. Also acts as initializer.
     * @param args - program arguments
     * @param station - implementation of StationPOA
     * @param provider - implementation of IOR provider to retrieve IOR from
     * @throws IOException
     */
    public Manager(String[] args, MonitoringStationPOA station, IIORProvider provider) throws IOException {
        orb = ORB.init(args, null);
        //get reference to naming service
        org.omg.CORBA.Object nameServiceObj =
                orb.string_to_object(provider.GetIOR());
        if (nameServiceObj == null) {
            System.out.println("nameServiceObj = null");
            return;
        }
        namingService = NamingContextExtHelper.narrow(nameServiceObj);

        monitor = new Monitor(namingService, station);
        monitor.start();

        orbRunner = new OrbRunner(orb, namingService, station);
        orbRunner.start();
    }

    /**
     * Manages application shutdown
     */
    public void shutdown() {
        monitor.stopThis();
        orb.shutdown(false);
    }
}
