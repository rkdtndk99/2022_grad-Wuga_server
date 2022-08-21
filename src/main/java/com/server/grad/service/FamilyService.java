package com.server.grad.service;

import com.server.grad.domain.Family;
import com.server.grad.domain.FamilyRepository;

import com.server.grad.domain.User;
import com.server.grad.dto.FamilyResponseDto;
import com.server.grad.dto.FamilyUpdateRequestDto;
import com.server.grad.dto.user.UserResponseDto;
import com.server.grad.dto.user.UserUpdateRequestDto;

import com.server.grad.dto.family.FamilySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;

    public FamilyResponseDto findById(Long id) {
        Family entity = familyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 가족 정보 없음 = " + id));

        return new FamilyResponseDto(entity);
    }

    @Transactional
    public Long save (FamilySaveRequestDto requestDto){
        return familyRepository.save(requestDto.toEntity()).getId();
    }

    public String createCode() {
        Random random = new Random();
        String generatedString;
        do {
            generatedString = random.ints(48, 123)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(6)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

        } while(familyRepository.findByFamilycode(generatedString).isPresent());

        return generatedString;

    }
}
