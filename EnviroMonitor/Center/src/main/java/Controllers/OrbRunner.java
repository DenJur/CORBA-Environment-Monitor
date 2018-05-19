package Controllers;

import Environment.*;
import Models.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 * Thread that listens to network calls from CORBA.
 */
public class OrbRunner extends Thread {

    private ORB orb;
    private Settings settings;
    private NamingContextExt namingService;
    private EnvironmentalCentrePOA center;
    private static final Logger logger = LogManager.getLogger(OrbRunner.class.getName());

    public OrbRunner(ORB orb, NamingContextExt namingService, EnvironmentalCentrePOA center) {
        this.orb = orb;
        this.namingService = namingService;
        this.settings = Settings.getInstance();
        this.center = center;
    }

    @Override
    public void run() {
        try {
            //register with the naming service
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(center);
            EnvironmentalCentre cref = EnvironmentalCentreHelper.narrow(ref);
            String name = settings.getName();
            NameComponent[] countName = namingService.to_name(name);
            namingService.rebind(countName, cref);
            orb.run();
        } catch (Exception e) {
            logger.fatal("Error initialising orb runner",e);
            System.exit(3);
        }
    }
}

