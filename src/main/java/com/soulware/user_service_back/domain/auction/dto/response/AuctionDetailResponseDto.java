package com.soulware.user_service_back.domain.auction.dto.response;

import com.soulware.user_service_back.domain.auction.entity.Auction;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDetailResponseDto {

    private String title;
    private String content;
    private String writerEmail;
    private String townName;
    private LocalDateTime createdAt;

    public AuctionDetailResponseDto(Auction auction) {
        this.title = auction.getTitle();
        this.content = auction.getContent();
        this.writerEmail = auction.getUser().getEmail();
        this.townName = auction.getTown().getAddress();
        this.createdAt = auction.getCreatedAt();
    }

}
