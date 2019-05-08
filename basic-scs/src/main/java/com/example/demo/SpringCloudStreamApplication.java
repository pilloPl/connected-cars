package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBinding(Sink.class)
@EnableScheduling
public class SpringCloudStreamApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void msg(String msg) {
        System.out.println(msg);
        //source.output().send(new GenericMessage<>("message"));
    }

}
