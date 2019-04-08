FROM tomcat:latest
# Copy application into tomcat webapps/
COPY einwohner-web/build/libs/einwohner-web*.war $CATALINA_HOME/webapps/einwohner.war

# Download and install filebeat
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.7.1-amd64.deb \
     && dpkg -i filebeat-6.7.1-amd64.deb

# Copy filebeat.yml containing configuration into config path
# Please check directory layout at this URL: https://www.elastic.co/guide/en/beats/filebeat/master/directory-layout.html
COPY einwohner-docker/filebeat/filebeat.yml /etc/filebeat

# Download and install metricbeat
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-6.7.1-amd64.deb \
     && dpkg -i metricbeat-6.7.1-amd64.deb

# Copy metricbeat.yml to configuration
COPY einwohner-docker/metricbeat/metricbeat.yml /etc/metricbeat

# Copy startup script, it will start all the applications, and make tomat as the entrypoint
COPY einwohner-docker/startup.sh $CATALINA_HOME/bin

CMD ["startup.sh"]

###############################Commands to build and push Image################################
#sudo docker build --tag=ramansharma/einwohnertomcat:v0.0.1 .
#sudo docker run --rm ramansharma/einwohnertomcat:v0.0.1 env
#sudo docker push ramansharma/einwohnertomcat:v0.0.1