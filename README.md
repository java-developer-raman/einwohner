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

## integrieren Sonar qube, PMD, Checkstyle, Findbugs 
1. Sehen Sie einwohner/build.gradle fuer weitere informationen.
Bitte achten Sie, **gradle tasks** command wird nicht alle Tasks schauen, sondern verwenden Sie **gradle tasks --all**  

## Notes / Troubleshooting

1. __gradle dependencies__ to look into the dependencies of a gradle project
1. __gradle tasks__ to get list of all tasks
1. You can create your custom configurtaions. And Then add jars into that configutation. And can you them later
   e.g.
   ```
   configurations {
      myRuntimeJars
   }
   dependencies {
      myRuntimeJars project(':project')
      myRuntimeJars 'org.spring:spring-web'
   }
   //Using these librarries to create on jar file
   jar {
      //This will add extracted jar files into the jar created by closure
      from configurations.myRuntimeJars.collect (zipTree it) 
   }
   // If you want to add jars created into your configurations
   artifacts {
      myRuntimeJars jar   
   }
   ```
   1. Plugins declared through closure {} and "apply" is almost same.
      Normally script plugins are declared using __apply from: 'other.gradle'__
      And binary plugins are declared using __plugins {id 'java'}__ . But if the used plugin is not part of gradle distribution. Then
      you need to add dependency to that jar to use the pluging.
      URL to refer: https://docs.gradle.org/current/userguide/plugins.html#sec:subprojects_plugins_dsl
      __Applying plugins with the plugins DSL__
      The plugins DSL provides a succinct and convenient way to declare plugin dependencies. It works with the Gradle plugin portal to 
      provide easy access to both core and community plugins.
      ```
      //Aplying a core plugin
      plugins {
        id 'java'
      }
      //Applying a community plugin
      plugins {
        id 'com.jfrog.bintray' version '0.4.1'
      }
      ```
      The plugins {} block must also be a top level statement in the buildscript. It cannot be nested inside another construct (e.g. an 
      if-statement or for-loop).
      
      
   
   
