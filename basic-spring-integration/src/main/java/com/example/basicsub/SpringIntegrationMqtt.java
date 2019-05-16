package com.example.basicsub;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

@SpringBootApplication
public class SpringIntegrationMqtt {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationMqtt.class, args);
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1883"});
        options.setUserName("user");
        options.setPassword("pass".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    MessageProducerSupport messageProducerSupport() {
        return new MqttPahoMessageDrivenChannelAdapter("consumerId", mqttClientFactory(), "/cars/marcin");
    }

    @Bean
    IntegrationFlow inbound() {
        return IntegrationFlows.from(messageProducerSupport())
                .transform(m -> "Got " + m)
                .handle(m -> System.out.println(m.getPayload()))
                .get();
    }



}