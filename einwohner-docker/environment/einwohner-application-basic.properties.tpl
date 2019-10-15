spring.application.name = einwohner

server.ssl.key-store = /usr/local/tomcat/app-conf/einwohner-tls.jks
#######################Sensitive data#######################
server.ssl.key-store-password=vault:v1:zTvY3JafvAe6ZUdMLu1jj12l0PUXmxL7OqQBB0XnFLLl9Qi4h/M=
server.ssl.key-alias = einwohner
server.ssl.key-store-type = pkcs12

server.ssl.trust-store = /usr/local/tomcat/app-conf/einwohner-trust-store.jks
#######################Sensitive data#######################
server.ssl.trust-store-password=vault:v1:zTvY3JafvAe6ZUdMLu1jj12l0PUXmxL7OqQBB0XnFLLl9Qi4h/M=
server.ssl.trust-store-type=pkcs12

# Spring Hibernate properties
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
