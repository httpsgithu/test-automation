# -*- coding: utf-8 -*-

import requests
import os
import sys

upload_url = 'http://appium.testdroid.com/upload'


def upload(api_key, path):
    print "Uploading %s to %s" % (path, upload_url)
    files = {'file': (os.path.basename(path),
                      open(path, 'rb'),
                      'application/octet-stream')}
    r = requests.post(upload_url,
                      files=files,
                      headers={'Accept': 'application/json'},
                      auth=(api_key, ''))
    if "successful" in r.json()['value']['message']:
        apk_path = r.json()['value']['uploads']['file']
        print "Filename to use in testdroid capabilities in test: {}".format(apk_path)
        return apk_path
    else:
        print "Upload response: \n{}".format(r.json())
        sys.exit(-1)
