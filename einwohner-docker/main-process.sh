#!/bin/bash
source build-application-environment.sh
# To trap the signals e.g. ctrl + c or SIGTERM initiated by docker container stop command, It will interrupt the
# current waiting process, and then stop will be invoked at last.
trap 'true' SIGINT SIGTERM

stop() {
 echo "Stopping the processes gracefully..."
 $CATALINA_HOME/bin/catalina.sh stop
 sudo /etc/init.d/filebeat stop
 sudo /etc/init.d/metricbeat stop
}

start() {
 echo "Starting Filebeat"
 sudo /etc/init.d/filebeat start

 echo "Starting MetricBeat"
 sudo metricbeat setup -e
 sudo /etc/init.d/metricbeat start

 echo "Starting Tomcat"
 #Start Tomcat as background process, then main-process.sh will be waiting for tomcat to exit
 $CATALINA_HOME/bin/catalina.sh run -config $CATALINA_HOME/app-conf/server-einwohner.xml &
}
build_application_properties
start

# "${@}", In case if we need to execute the commands passed

# $! means Most recent background process, Wait for most recently background process, i.e. Tomcat process, and when
# trap signal will come, it will be interrupted and control will go to next instruction i.e stop
wait $!

# Stopping all the processes...
stop
