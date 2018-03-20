#!/usr/bin/env bash

cd /Users/admin/education/otus-java-2017-10-bryukhanov/hwork13
mvn compile package
cp target/hw-13-00.war /Applications/jetty/base/webapps/root.war
cd /Applications/jetty/base
java -jar ../start.jar

# windows
# copy target/L13.1.3-war.war $JETTY_HOME/webapps/root.war
