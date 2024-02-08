package com.uartres.certification_nlw.modules.students.controllers;

import com.uartres.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.uartres.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uartres.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.uartres.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.uartres.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Preciso usar meu Use Case
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {

        var result = this.verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO);

        if (result) {
            return "Usuario já realizou a prova";
        }
        return "Usuario pode realizar a prova";

    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(
            @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        try {
            var result = studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
