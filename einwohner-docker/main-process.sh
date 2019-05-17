#!/bin/bash

# To trap the signals e.g. ctrl + c or SIGTERM initiated by docker container stop command, It will interrupt the
# current waiting process, and then stop will be invoked at last.
trap 'true' SIGINT SIGTERM

stop() {
 echo "Stopping the processes gracefully..."
 $CATALINA_HOME/bin/catalina.sh stop
 service filebeat stop
 service metricbeat stop
}

start() {
 echo "Starting Filebeat"
 service filebeat start

 echo "Starting MetricBeat"
 metricbeat setup -e
 service metricbeat start

 echo "Starting Tomcat"
 #Start Tomcat as background process, then main-process.sh will be waiting for tomcat to exit
 $CATALINA_HOME/bin/catalina.sh run &
}

start

# "${@}", In case if we need to execute the commands passed

# $! means Most recent background process, Wait for most recently background process, i.e. Tomcat process, and when
# trap signal will come, it will be interrupted and control will go to next instruction i.e stop
wait $!

# Stopping all the processes...
stop
