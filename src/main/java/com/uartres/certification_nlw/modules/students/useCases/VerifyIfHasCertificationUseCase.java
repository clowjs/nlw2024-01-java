package com.uartres.certification_nlw.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.uartres.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;

@Service
public class VerifyIfHasCertificationUseCase {

    public boolean execute(VerifyHasCertificationDTO dto) {
        if (dto.getEmail().equals("hagare@gmail.com") && dto.getTechnology().equals("Java")) {
            return true;
        }

        return false;
    }
}
