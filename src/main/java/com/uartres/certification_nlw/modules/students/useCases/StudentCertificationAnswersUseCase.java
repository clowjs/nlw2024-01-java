package com.uartres.certification_nlw.modules.students.useCases;

import com.uartres.certification_nlw.modules.questions.entities.QuestionEntity;
import com.uartres.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.uartres.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.uartres.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.uartres.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.uartres.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.uartres.certification_nlw.modules.students.entities.StudentEntity;
import com.uartres.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.uartres.certification_nlw.modules.students.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

        var hasCertification = this.verifyIfHasCertificationUseCase
                .execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Você ja tirou sua certificacao!");
        }

        List<QuestionEntity> questionEntities = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionsAnswers()
                .stream()
                .forEach(questionAnswer -> {
                    var question = questionEntities.stream()
                            .filter(q -> q.getId().equals(questionAnswer.getQuestionId()));

                    var findCorrectAlternative = question.findFirst().get().getAlternatives().stream()
                            .filter(alternative -> alternative.isCorrect())
                            .findFirst().get();

                    if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeId())) {
                        questionAnswer.setCorrect(true);
                        correctAnswers.incrementAndGet();
                    } else {
                        questionAnswer.setCorrect(false);
                    }

                    var answersCertficiationsEntity = AnswersCertificationsEntity.builder()
                            .answerId(questionAnswer.getAlternativeId())
                            .questionId(questionAnswer.getQuestionId())
                            .isCorrect(questionAnswer.isCorrect())
                            .build();

                    answersCertifications.add(answersCertficiationsEntity);

                });

        // Verificar se existe student pelo email
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentId;

        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentId = studentCreated.getId();
        } else {
            studentId = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentId(studentId)
                .grade(correctAnswers.get())
                .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.forEach(answersCertificationsEntity -> {
            answersCertificationsEntity.setCertificationId(certificationStudentCreated.getId());
        });

        certificationStudentEntity.setAnswersCertificationsEntity(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;

    }
}
