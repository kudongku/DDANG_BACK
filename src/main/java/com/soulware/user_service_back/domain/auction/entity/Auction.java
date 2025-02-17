package com.soulware.user_service_back.domain.auction.entity;

import com.soulware.user_service_back.domain.auction.dto.request.AuctionCreateRequestDto;
import com.soulware.user_service_back.domain.common.entity.TimeStamp;
import com.soulware.user_service_back.domain.town.entity.Town;
import com.soulware.user_service_back.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Getter
@NoArgsConstructor
@Table(name = "auctions")
@Entity
public class Auction extends TimeStamp {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Town town;

    @ManyToOne
    private User user;

    public Auction(AuctionCreateRequestDto auctionCreateRequestDto, User user) {
        this.title = auctionCreateRequestDto.getTitle();
        this.content = auctionCreateRequestDto.getContent();
        this.user = user;
        this.town = user.getTown();
    }

}
