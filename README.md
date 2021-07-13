# thingworx-kinesis-connector

Connector implementation for sending messages arriving at Kinesis stream to ThingWorx platform. Connector listens to Kinesis Data stream using 'Spring Cloud Stream Binder Kinesis' library, provides them to a pool of connectors through JMS, which then publishes the messages via WebSocket channel.

## Pre-launch Configuration

Modify the 'application.yml' file before you launch the application. The below properties need to be specified:

### Cloud and Kinesis configurations
 - **cloud.aws.credentials** : Specify the AWS access-key and secret-key required for accessing the Kinesis Data stream resource.
 - **cloud.aws.region.static** : Region for the Kinesis Data stream resource.
 - **spring.cloud.stream.bindings.input.destination** : Name of the Kinesis Data stream for consumption.

### ThingWorx configurations
 - **connectorCount** : The number of connectors require to process the incoming telemetry messages. Identify a count according to your requiremnts. Can be set as low as 1 for low volume streaming data and as high as 1000 for high volume streaming data.
 - **host** : Host name of Thingworx Foundation server
 - **port** : Port of Thingworx Foundation server
 - **secureConnection** : Provide this as 'true' if using a secure (https) connection, else 'false'
 - **appKey** : Thingworx application key that provides service execution permissions for the telemetry and health services
 - **telemetryThing** : The Thing containing the service for telemetry processing
 - **telemetryService** : The ThingWorx service that processes the telemetry message.
 - **healthThing** : The Thing containing the service for health check
 - **healthService** : The ThingWorx service that performs health check.

## Installation
Use maven to compile and build project:
```bash
mvn clean install 
```
Run the spring-boot application:
```bash
mvn spring-boot:run
```
