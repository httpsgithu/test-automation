# Blackbox test Lantern Android on Testdroid and local device

## Perquisites

* Have the Lantern Android debug APK

You can build it from the [Lantern project](https://github.com/getlantern/lantern).

```
make android-debug
```

* Install virtualenv if not present

```
curl -O https://pypi.python.org/packages/source/v/virtualenv/virtualenv-15.0.0.tar.gz
tar zxvf virtualenv-15.0.0.tar.gz
cd virtualenv-15.0.0
[sudo] python setup.py install
```


* Install required python packages

```
virtualenv venv
. venv/bin/activate
pip install -r requirements.txt
```

## Run tests

Run `. venv/bin/activate` before executing any python scripts.

The screenshots took in each run will be in a separate directory under `./screenshots`.

Modify `lib/suite.py` to add more tests.

### Test on Testdroid cloud

Make sure you properly set `TESTDROID_APIKEY` environment variable. You may want to add it to user profile.

```
echo "export TESTDROID_APIKEY=xxx" >> ~/.bash_profile
```

There are several options to run tests on Testdroid cloud.

* Run on any available free cloud device.

```
./start_test.py [apk-path]
```

If `apk-path` is not supplied, it will use `lantern-debug.apk` under current directory. Same afterwards.

* Run on specific cloud device.

```
./start_test.py -d "Xiaomi MI 1S"
```

* Run on all devices in specific device group on cloud.

```
./start_test.py -g 14
```

Latter two options can be combined.

The script will upload the debug APK before running any test. Supply `-n` or `--no-upload` option to skip uploading and use the latest uploaded APK.

### Test on locally connected device

* Uninstall Lantern from target device.

Selendroid will install the APK with a different signature. Installation will fail if there's an existing APK with different signature is installed.

* Connect device and keep screen unlocked.

* Start Selendroid standalone server (will download it at first time).

```
./selendroid.sh [apk-path]
```

If `apk-path` is not supplied, it will use `lantern-debug.apk` under current directory.

* Run test script

```
./start_test.py --local
```

Check for Selendroid output if the error message is not clear enough.
