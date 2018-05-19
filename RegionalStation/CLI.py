import argparse

from Models.Settings import Settings


# parses program arguments for settings parameters
def parse_args():
    parser = argparse.ArgumentParser(description='Regional station for environment monitoring.')
    parser.add_argument('--name', "-n", required=True, type=str, nargs='?',
                        help='Name that station will use to register in the name server.')
    parser.add_argument('--step', "-s", required=True, type=int, nargs='?',
                        help='Number of confirmations from a station needed to raise an alarm')
    parser.add_argument('--time', "-t", required=True, type=int, nargs='?',
                        help='Number of milliseconds between readings that are considered confirmations')
    parser.add_argument('--centre', "-c", required=True, type=str, nargs='?',
                        help='Name of the environment centre the regional centre will connect to')
    parser.add_argument('--load', "-l", required=False, action='store_true',
                        help='Load saved state of the regional station')
    parser.add_argument('-ORBInitRef', required=False, type=str, nargs='?',
                        help='IOR for naming service')
    args = parser.parse_args()
    return Settings(name=args.name, steps=args.step, time=args.time, centre=args.centre, load=args.load)