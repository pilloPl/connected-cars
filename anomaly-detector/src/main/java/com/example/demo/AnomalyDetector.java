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

    private final Channels channels;

    public AnomalyDetector(Channels channels) {
        this.channels = channels;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnomalyDetector.class, args);
    }


    @StreamListener("breaking")
    public void anomalyDetector(int decelaration) {
        //complex logic
        //stateful processor
        //taling to internal db
        //etc

        if(decelaration > 30) {
            System.out.println("WOT?");
            channels.anomalies().send(new GenericMessage<>("WOOT?"));
        }

    }


}


interface Channels {

    @Input("breaking")
    SubscribableChannel breaking();

    @Output("anomalies")
    MessageChannel anomalies();
}