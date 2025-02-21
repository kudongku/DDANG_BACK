package com.soulware.user_service_back.domain.auction.service;

import com.soulware.user_service_back.domain.auction.dto.request.AuctionCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuctionServiceTest {

    @Autowired
    private AuctionService auctionService;

    @Test
    void createAuction() {
        for (int i = 0; i < 100; i++) {
            AuctionCreateRequestDto auctionCreateRequestDto = new AuctionCreateRequestDto(
                String.valueOf(i),
                String.valueOf(i)
            );
            auctionService.createAuction(auctionCreateRequestDto, "test@test.com");
        }
    }
}