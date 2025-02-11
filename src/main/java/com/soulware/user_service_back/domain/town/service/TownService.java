package com.soulware.user_service_back.domain.town.service;

import com.soulware.user_service_back.domain.town.entity.Town;
import com.soulware.user_service_back.domain.town.repository.TownRepository;
import com.soulware.user_service_back.domain.user.dto.request.UserLocationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TownService {

    private final TownRepository townRepository;

    @Transactional
    public Town getTownByUserLocationRequestDto(UserLocationRequestDto userLocationRequestDto) {
        return townRepository.getTownByAddress(
            userLocationRequestDto.getAddress()
        ).orElseGet(() -> {
            Town newTown = new Town(
                userLocationRequestDto.getAddress(),
                userLocationRequestDto.getLatitude(),
                userLocationRequestDto.getLongitude()
            );
            return townRepository.save(newTown);
        });
    }

}
