#!/usr/bin/env sh

SELENDROID_VERSION=0.17.0
SELENDROID_JAR=selendroid-standalone-${SELENDROID_VERSION}-with-dependencies.jar
DEFAULT_APK_PATH=lantern-debug.apk

mkdir -p tmp

if [ ! -f tmp/${SELENDROID_JAR} ]; then
  URL=https://github.com/selendroid/selendroid/releases/download/${SELENDROID_VERSION}/${SELENDROID_JAR}
  echo Downloading ${URL}
  curl -L ${URL} > tmp/${SELENDROID_JAR}
  if [ $? -ne 0 ]; then
    exit 1
  fi
fi

apk_path=$DEFAULT_APK_PATH
if [ "$1" ]; then
  echo $1
  apk_path=$1
fi

cp ${apk_path} tmp/aut.apk
echo Copied ${apk_path} to tmp/aut.apk
if [ $? -ne 0 ]; then
  exit 1
fi

java -jar tmp/${SELENDROID_JAR} -app tmp/aut.apk
