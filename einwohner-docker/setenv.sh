#!/usr/bin/env bash
export JAVA_OPTS="$JAVA_OPTS -Dlogback.configurationFile=$HOME/conf/logback.xml -Dlog.dir=$CATALINA_HOME/logs -Dapp.conf.dir=$HOME/conf"