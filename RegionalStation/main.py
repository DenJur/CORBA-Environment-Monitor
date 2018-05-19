import sys
import traceback

from omniORB import CORBA
import CosNaming as CosNaming
import logging
from CLI import parse_args
from RegionalCenterImpl import RegionalCenterImpl


def main(argv):
    logging.basicConfig(filename='station.log', level=logging.WARNING)
    settings = parse_args()
    try:
        # Initialise the ORB.
        orb = CORBA.ORB_init(argv, CORBA.ORB_ID)

        # Get a reference to the Naming service
        ns = orb.resolve_initial_references("NameService")
        rootContext = ns._narrow(CosNaming.NamingContext)
        if rootContext is None:
            logging.critical("Failed to narrow the root naming context")
            sys.exit(1)

        # Initialise the POA.
        poa = orb.resolve_initial_references("RootPOA")

        # Create an instance of the server and get its reference
        # this will also implicitly activate the object in the POA
        ei = RegionalCenterImpl(settings=settings, center=None, root=rootContext)
        eo = ei._this()

        # Initialise and activate the POA Manager
        poaManager = poa._get_the_POAManager()
        poaManager.activate()

        # bind the Count object in the Naming service
        countName = [CosNaming.NameComponent(settings.name, "")]
        rootContext.rebind(countName, eo)

        centre_name = [CosNaming.NameComponent(settings.centre, "")]
        centre_remote = rootContext.resolve(centre_name)
        centre_remote.register_regional_centre(settings.name)

        print('Server created and accepting requests...')

        # Start the event loop.
        orb.run()
    except Exception as inst:
        logging.exception("Error initializing application.")
        print("Error initializing application. ", inst)
        traceback.print_exc()



if __name__ == '__main__':
    sys.exit(main(sys.argv))

