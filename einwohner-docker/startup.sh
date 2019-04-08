#!/bin/bash

# Start Filebeat
service filebeat start

#Start Metricbeat
metricbeat setup -e
service metricbeat start

# Start Tomcat
$CATALINA_HOME/bin/catalina.sh run