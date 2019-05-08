package com.example.basicsub;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
public class BasicSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicSubApplication.class, args);
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

    // consumer

    @Bean
    public IntegrationFlow mqttInFlow() {
        return IntegrationFlows.from(mqttInbound())
                .transform(p -> p + ", received from MQTT")
                .handle(msg -> System.out.println(msg.getPayload()))
                .get();
    }


    @Bean
    public MessageProducerSupport mqttInbound() {
        return new MqttPahoMessageDrivenChannelAdapter("siSampleConsumer",
                mqttClientFactory(), "/my/topic");
    }

    // publisher

    @Bean
    public IntegrationFlow mqttOutFlow() {
        return IntegrationFlows.from(() -> "source",
                e -> e.poller(Pollers.fixedDelay(1000)))
                .transform(p -> p + " sent to MQTT")
                .handle(mqttOutbound())
                .get();
    }

    @Bean
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("siSamplePublisher", mqttClientFactory());
        messageHandler.setDefaultTopic("/my/topic");
        return messageHandler;
    }
}