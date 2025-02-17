package com.soulware.user_service_back.domain.auction.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionsResponseDto {

    private List<AuctionDetailResponseDto> auctions;

}
