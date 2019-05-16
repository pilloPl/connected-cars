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
@EnableBinding(CarChannels.class)
public class AnomalyDetector {

    private final CarChannels carChannels;

    public AnomalyDetector(CarChannels carChannels) {
        this.carChannels = carChannels;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnomalyDetector.class, args);
    }

    @StreamListener("breaking")
    public void handle(int decelaration) {

        //complex logic
        //db
        //ml
        //connect to sth

        if(decelaration > 30) {
            System.out.println("WOT?");
            carChannels.anomalies().send(new GenericMessage<>("What is going on?"));
        }
    }



}


interface CarChannels {

    @Input("breaking")
    SubscribableChannel breaking();

    @Output("anomalies")
    MessageChannel anomalies();
}