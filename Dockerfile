FROM tomcat:9.0.19-jre8

# Application should not create log files or anything in container, as it creates unnecessary load and performance
# problems, instead everything should go to standard out. So this is one way to divert logs to standard out via creating
# symbolic links
RUN ln -s /dev/stdout ./logs/einwohner.log \
     && ln -s /dev/stdout ./logs/catalina.log \
     && ln -s /dev/stdout ./logs/catalina.out \
     && ln -s /dev/stdout ./logs/access.log

# Commenting it for the time being, as rest packages need debian os
# Installing curl as alpine does not have it by default, And also removing the cache in same step. Since removing
# cache is different step won't remove it actually from image, instead it will just write information
# RUN apk add --update curl \
#    && rm -rf /var/cache/apk/*

# Download and install filebeat, and then removing the downloaded file
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.7.1-amd64.deb \
     && dpkg -i filebeat-6.7.1-amd64.deb \
     && rm filebeat-6.7.1-amd64.deb

# Copy filebeat.yml containing configuration into config path
# Please check directory layout at this URL: https://www.elastic.co/guide/en/beats/filebeat/master/directory-layout.html
COPY einwohner-docker/filebeat/filebeat.yml /etc/filebeat

# Download and install metricbeat
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-6.7.1-amd64.deb \
     && dpkg -i metricbeat-6.7.1-amd64.deb \
     && rm metricbeat-6.7.1-amd64.deb

RUN apt-get update && apt-get -y install procps

# Copy metricbeat.yml to configuration
COPY einwohner-docker/metricbeat/metricbeat.yml /etc/metricbeat

# Copy startup script, it will start all the applications, and make tomat as the entrypoint
COPY einwohner-docker/main-process.sh $CATALINA_HOME/bin

CMD ["main-process.sh"]
# Command to start tomcat
#CMD ["catalina.sh", "run"]

COPY einwohner-docker/application.properties /tmp/application.properties

# Most changing file, so keeping it at last, so that it does not impact cache.
COPY einwohner-web/build/libs/einwohner-web*.war $CATALINA_HOME/webapps/einwohner.war

###############################Commands to build and push Image################################
#sudo docker build --tag=ramansharma/einwohnertomcat:v0.0.1 .
#sudo docker run --rm ramansharma/einwohnertomcat:v0.0.1 env
#sudo docker push ramansharma/einwohnertomcat:v0.0.1
