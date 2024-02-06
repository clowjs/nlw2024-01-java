package com.uartres.certification_nlw.modules.students.entities;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificationStudentEntity {
    private UUID studentId;
    private UUID id;
    private String technology;
    private int grade;
    private List<AnswersCertificationsEntity> answersCertificationsEntity;

}
