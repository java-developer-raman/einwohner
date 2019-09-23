# An ARG declared before a FROM is outside of a build stage, so it can’t be used in any instruction after a FROM, And also it should be the first instruction
ARG TOMCAT_VERSION=9.0.19-jre8
FROM tomcat:${TOMCAT_VERSION}

# Use docker inspect <container id> to view labels
LABEL "author"="Raman Sharma"
LABEL "App Name"="einwohner"
EXPOSE 8443

# Commenting it for the time being, as rest packages need debian os
# Installing curl as alpine does not have it by default, And also removing the cache in same step. Since removing
# cache is different step won't remove it actually from image, instead it will just write information
# RUN apk add --update curl \
#    && rm -rf /var/cache/apk/*

# Download and install filebeat, and then removing the downloaded file
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.7.1-amd64.deb \
     && dpkg -i filebeat-6.7.1-amd64.deb \
     && rm filebeat-6.7.1-amd64.deb

# Download and install metricbeat
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-6.7.1-amd64.deb \
     && dpkg -i metricbeat-6.7.1-amd64.deb \
     && rm metricbeat-6.7.1-amd64.deb

RUN apt-get update && apt-get -y install procps sudo

# Create user einwohner, and assigning write permissions of /usr/local/tomcat folder to user
RUN adduser --disabled-password einwohner && chown -R einwohner: $CATALINA_HOME && chmod u+w $CATALINA_HOME

# Application should not create log files or anything in container, as it creates unnecessary load and performance
# problems, instead everything should go to standard out. So this is one way to divert logs to standard out via creating
# symbolic links
RUN ln -s /dev/stdout ./logs/einwohner.log \
     && ln -s /dev/stdout ./logs/catalina.log \
     && ln -s /dev/stdout ./logs/catalina.out \
     && ln -s /dev/stdout ./logs/access.log \
     && ln -s /dev/stdout ./logs/localhost*.log \
     && ln -s /dev/stdout ./logs/localhost_access_log*.txt

#######################################Setup Metricbeat##########################################
#  Metricbeat needs a directory under /var/lib
RUN mkdir /var/lib/metricbeat && chown -R einwohner: /var/lib/metricbeat && chmod u+rw /var/lib/metricbeat
# Copy metricbeat.yml to configuration
COPY einwohner-docker/metricbeat/metricbeat.yml /etc/metricbeat

######################################Setup Filebeat#############################################
# Copy filebeat.yml containing configuration into config path
# Please check directory layout at this URL: https://www.elastic.co/guide/en/beats/filebeat/master/directory-layout.html
COPY einwohner-docker/filebeat/filebeat.yml /etc/filebeat

#######################################Give Sudo power to User to run services##################
COPY einwohner-docker/sudo-power-for-user /etc/sudoers.d/

USER einwohner

# Copy startup script, it will start all the applications, and make tomat as the entrypoint
COPY einwohner-docker/main-process.sh $CATALINA_HOME/bin

# Copy script to set environment variables for application
COPY einwohner-docker/setenv.sh $CATALINA_HOME/bin

CMD ["main-process.sh"]

# Most changing file, so keeping it at last, so that it does not impact cache.
COPY einwohner-web/build/libs/einwohner-web*.war $CATALINA_HOME/webapps/einwohner.war

###############################Commands to build and push Image################################
#sudo docker build --tag=ramansharma/einwohnertomcat:v0.0.1 .
#sudo docker run --rm ramansharma/einwohnertomcat:v0.0.1 env
#sudo docker push ramansharma/einwohnertomcat:v0.0.1
