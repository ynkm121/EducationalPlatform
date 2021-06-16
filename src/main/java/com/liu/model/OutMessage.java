package com.liu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutMessage {
    public String teacherName;

    public String courseName;

    private String content;
}
