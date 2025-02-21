package com.soulware.user_service_back.domain.auction.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionCreateResponseDto {

    private UUID auctionId;

}
