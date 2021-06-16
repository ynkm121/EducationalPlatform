package com.liu.MQ;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue("hello"))
public class messageReceiver{

    @RabbitHandler
    public void onMessage(String message){
        System.out.println("message is " + message);
    }
}
