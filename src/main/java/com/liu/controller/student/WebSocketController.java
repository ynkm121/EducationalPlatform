package com.liu.controller.student;

import com.liu.model.TeacherMessage;
import com.liu.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    WebSocketService socketService;

    @SubscribeMapping("/message/student/19152010824")
    public String subscribe(){
        return "欢迎订阅";
    }

    @MessageMapping("/message/teacher/topic")
    public void topic(TeacherMessage message){
        System.out.println(message);
        socketService.sendMessage(message);
    }
}
