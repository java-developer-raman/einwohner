FROM tomcat:latest
COPY wait-for.sh $CATALINA_HOME/bin
COPY einwohner-web/build/libs/einwohner-web*.war $CATALINA_HOME/webapps/einwohner.war