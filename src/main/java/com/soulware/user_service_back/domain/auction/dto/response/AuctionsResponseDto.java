package com.soulware.user_service_back.domain.auction.dto.response;

import com.soulware.user_service_back.domain.auction.entity.Auction;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Slice;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionsResponseDto {

    private List<AuctionDetailResponseDto> auctions;
    private boolean isLast;

    public AuctionsResponseDto(Slice<Auction> auctions) {
        this.auctions = auctions.stream()
            .map(AuctionDetailResponseDto::new)
            .toList();

        this.isLast = auctions.isLast();
    }

}
