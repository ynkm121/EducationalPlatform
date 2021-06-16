package com.liu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherMessage {
    private Integer teacherId;

    private Integer courseId;

    private String message;
}
