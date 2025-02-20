package com.soulware.user_service_back.domain.auction.service;

import com.soulware.user_service_back.domain.auction.dto.request.AuctionCreateRequestDto;
import com.soulware.user_service_back.domain.auction.dto.response.AuctionCreateResponseDto;
import com.soulware.user_service_back.domain.auction.dto.response.AuctionDetailResponseDto;
import com.soulware.user_service_back.domain.auction.dto.response.AuctionsResponseDto;
import com.soulware.user_service_back.domain.auction.entity.Auction;
import com.soulware.user_service_back.domain.auction.repository.AuctionRepository;
import com.soulware.user_service_back.domain.user.entity.User;
import com.soulware.user_service_back.domain.user.service.UserService;
import com.soulware.user_service_back.global.exception.CustomIllegalArgumentException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserService userService;

    public AuctionDetailResponseDto getAuctionById(UUID auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
            () -> new CustomIllegalArgumentException("Invalid auction id: " + auctionId)
        );

        return new AuctionDetailResponseDto(auction);
    }

    public AuctionsResponseDto getAuctions(Pageable pageable, String email) {
//        todo. 근처에 있는 경매만 조회 구현
        Slice<Auction> auctions = auctionRepository.findAll(pageable);
        return new AuctionsResponseDto(auctions);
    }

    @Transactional
    public AuctionCreateResponseDto createAuction(
        AuctionCreateRequestDto auctionCreateRequestDto,
        String email
    ) {
        User user = userService.getUserByEmail(email);
        Auction auction = new Auction(auctionCreateRequestDto, user);
        auctionRepository.save(auction);
        return new AuctionCreateResponseDto(auction.getId());
    }

}
