package com.soulware.user_service_back.domain.auction.controller;

import com.soulware.user_service_back.domain.auction.dto.request.AuctionCreateRequestDto;
import com.soulware.user_service_back.domain.auction.dto.response.AuctionCreateResponseDto;
import com.soulware.user_service_back.domain.auction.dto.response.AuctionDetailResponseDto;
import com.soulware.user_service_back.domain.auction.dto.response.AuctionsResponseDto;
import com.soulware.user_service_back.domain.auction.service.AuctionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auctions")
@RestController
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDetailResponseDto> getAuctionById(
        @PathVariable("auctionId") UUID auctionId
    ) {
        AuctionDetailResponseDto auctionDetailResponseDto = auctionService.getAuctionById(
            auctionId
        );
        return ResponseEntity.ok(auctionDetailResponseDto);
    }

    @GetMapping
    public ResponseEntity<AuctionsResponseDto> getAuctions(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit,
        Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(
            page,
            limit,
            Sort.by(Direction.DESC, "createdAt")
        );
        AuctionsResponseDto auctionsResponseDto = auctionService.getAuctions(
            pageable,
            authentication.getName()
        );
        return ResponseEntity.ok(auctionsResponseDto);
    }

    @PostMapping
    public ResponseEntity<AuctionCreateResponseDto> createAuction(
        @RequestBody AuctionCreateRequestDto auctionCreateRequestDto,
        Authentication authentication
    ) {
        AuctionCreateResponseDto auctionCreateResponseDto = auctionService.createAuction(
            auctionCreateRequestDto, authentication.getName()
        );

        return ResponseEntity.ok(auctionCreateResponseDto);
    }

}
