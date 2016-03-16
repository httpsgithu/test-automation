#!/usr/bin/env python

import sys
sys.path.append('./lib')

import os
import argparse
from upload import upload
import local_android
import testdroid_android


DEFAULT_APK_PATH = 'lantern-debug.apk'

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Testdroid test script')
    parser.add_argument('-l', '--local', action='store_true',
                        help='Test on local device instead of Testdroid cloud')
    parser.add_argument('-n', '--no-upload', action='store_true',
                        help='Not upload APK to testdroid before running test (uses the latest uploaded)')
    parser.add_argument('-g', '--group', type=int,
                        help='The device group to run tests on. If neither group nor device supplied, will pick whichever free device')
    parser.add_argument('-d', '--device', type=str,
                        help='The specific device to run tests on. If neither group nor device supplied, will pick whichever free device')
    parser.add_argument('apk', type=str, nargs='?', help='The path of APK file')

    args = parser.parse_args()
    apk_path = args.apk
    if apk_path is None:
        apk_path = DEFAULT_APK_PATH
    if args.local:
        local_android.test()
    else:
        testdroid_api_key = os.environ.get('TESTDROID_APIKEY')
        if testdroid_api_key is None:
            print "TESTDROID_APIKEY environment variable is not set!"
            sys.exit(1)
        cloud_path = "latest"
        if args.no_upload is not True:
            cloud_path = upload(testdroid_api_key, apk_path)

        testdroid_android.executeTests(testdroid_api_key,
                                       cloud_path,
                                       args.group,
                                       args.device)
