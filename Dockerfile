FROM tomcat:latest

COPY einwohner-web/build/libs/einwohner-web*.war $CATALINA_HOME/webapps/einwohner.war

VOLUME