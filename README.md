# einwohner
## Folgende Libraries verwandet
1. Lombok
2. orika

## Adding Logging Framework (Log-Back)
1. Include following dependencies (Es enthält folgenden dependencies)
```
    implementation group: 'ch.qos.logback', name: 'logback-classic'
    implementation group: 'ch.qos.logback', name: 'logback-core'
    implementation group: 'ch.qos.logback', name: 'logback-access'
    implementation group: 'org.slf4j', name: 'jcl-over-slf4j'
```
2. Exclude following dependency to avoid conflicts
```
       group: 'commons-logging', module: 'commons-logging'
```
3. Copy logback.xml in classpath

## Getting properties from META-INF
1. Look into ApplicationInformationController

## Gradle Task for Application deployment, And how to include other gradle files.
1. Look into gradle\deployment.gradle and einwohner-web\build.gradle
