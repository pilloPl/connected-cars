package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@EnableBinding(Channels.class)
public class AnomalyDetector {

    public AnomalyDetector(Channels channels) {
        this.channels = channels;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnomalyDetector.class, args);
    }

    private final Channels channels;

    @StreamListener("breaking")
    public void msg2(int decelaration) {
        if(decelaration > 30) {
            System.out.println("CRASH");
            channels.anomalies().send(new GenericMessage<>("anomaly"));
        }
    }

}


interface Channels {

    @Output("anomalies")
    MessageChannel anomalies();

    @Input("breaking")
    SubscribableChannel breaking();

}