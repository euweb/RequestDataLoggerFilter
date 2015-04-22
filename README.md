# request data logger filter for logging the http request body in a servlet container e.g. tomcat

## Building

gradle jar

## Installing

 - copy build/libs/RequestDataLoggerFilter.jar to $CATALINA_HOME/lib
 - edit $CATALINA_HOME/conf/logging.properties:
 handlers = 6request-dumper.org.apache.juli.FileHandler
 
 6request-dumper.org.apache.juli.FileHandler.level = FINEST 
 6request-dumper.org.apache.juli.FileHandler.directory = ${catalina.base}/logs
 6request-dumper.org.apache.juli.FileHandler.prefix = .
 6request-dumper.org.apache.juli.FileHandler.formatter = org.apache.juli.VerbatimFormatter
 com.infodesire.log.RequestDataLoggerFilter.level = FINEST
 com.infodesire.log.RequestDataLoggerFilter.handlers = 6request-dumper.org.apache.juli.FileHandler
 
 - edit web.xml of the web app
 
 <filter>
    <filter-name>requestdatafilter</filter-name>
    <filter-class>
       com.infodesire.log.RequestDataLoggerFilter 
    </filter-class>
 </filter>
 <filter-mapping>
    <filter-name>requestdatafilter</filter-name>
    <url-pattern>/rest/*</url-pattern>
 </filter-mapping>
 