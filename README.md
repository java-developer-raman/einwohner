# einwohner
## Adding Logging Framework (Log-Back)
1. Include following dependencies
```
    implementation group: 'ch.qos.logback', name: 'logback-classic'
    implementation group: 'ch.qos.logback', name: 'logback-core'
    implementation group: 'ch.qos.logback', name: 'logback-access'
    implementation group: 'org.slf4j', name: 'jcl-over-slf4j'
```
2. Exclude  following dependency to avoid conflicts
```
       group: 'commons-logging', module: 'commons-logging'
```
3. Copy logback.xml in classpath

