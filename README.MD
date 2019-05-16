1. Start RabbitMQ with MQTT plugin:

  `killall mosquitto`
  
 ` docker run -p 5672:5672 -p 15672:15672 -p 1883:1883 rabbitmq:latest`
 
 
2. Inside of the container:

  `rabbitmq-plugins enable rabbitmq_mqtt`
 
  `rabbitmq-plugins enable rabbitmq_management`
  
3. Create user in the RabbitMQ management
  Localhost:15672 -> guest/guest 
 
4. Mosquitto publish:

 ` mosquitto_pub -t /my/topic -m "Hello World" -u user -P pass`
 
5. Mosquitto subscribe:

 ` mosquitto_sub -t “/my/topic" -u user -P pass`
 
  
6. Start Spring Cloud Data Flow Server, Skipper and datashell

 `java -jar spring-cloud-skipper-server-2.0.0.RELEASE.jar`
 
 `java -jar spring-cloud-dataflow-server-2.0.1.RELEASE.jar`
 
 `java -jar spring-cloud-dataflow-shell-2.0.0.RELEASE.jar`
 

7. Register anomaly detector:

  `app register --name anomaly --type processor --uri file://Users/jphilimon/talks/connected-cars/apps/anomaly-detector/target/anomaly-detector-0.0.1-SNAPSHOT.jar`
  
8. Register apps in batch:

  `app import --uri file:/Users/jphilimon/talks/connected-cars/apps/apps-rabbit`
