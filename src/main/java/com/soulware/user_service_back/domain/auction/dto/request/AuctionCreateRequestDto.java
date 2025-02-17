package com.soulware.user_service_back.domain.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionCreateRequestDto {

    private String title;
    private String content;

}
