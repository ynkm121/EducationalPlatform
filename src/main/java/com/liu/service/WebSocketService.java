package com.liu.service;

import com.liu.model.TeacherMessage;

public interface WebSocketService {

    public void sendMessage(TeacherMessage message);
}
