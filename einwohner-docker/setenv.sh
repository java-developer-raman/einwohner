#!/usr/bin/env bash
export JAVA_OPTS="$JAVA_OPTS -Dlogback.configurationFile=$CATALINA_HOME/conf/logback.xml -Dlog.dir=$CATALINA_HOME/app-logs -Dspring.config.location=$CATALINA_HOME/conf/ -Dspring.config.name=einwohner-application-basic.properties"