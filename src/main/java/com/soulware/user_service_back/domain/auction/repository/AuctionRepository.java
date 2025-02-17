package com.soulware.user_service_back.domain.auction.repository;

import com.soulware.user_service_back.domain.auction.entity.Auction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, UUID> {

}
