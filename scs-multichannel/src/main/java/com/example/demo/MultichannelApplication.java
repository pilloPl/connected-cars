package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding(CarChannels.class)
public class MultichannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultichannelApplication.class, args);
    }



    @StreamListener("temperature")
    public void handleTemp(String temp) {
        System.out.println("Temp is " + temp);
    }

    @StreamListener("breaking")
    public void handleBreaking(int decelaration) {
        System.out.println("Decl. is " + decelaration);
    }




}


interface CarChannels {

    @Input("temperature")
    SubscribableChannel temperature();

    @Input("breaking")
    SubscribableChannel breaking();

}