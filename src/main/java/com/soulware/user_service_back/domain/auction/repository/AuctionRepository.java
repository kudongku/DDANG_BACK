package com.soulware.user_service_back.domain.auction.repository;

import com.soulware.user_service_back.domain.auction.entity.Auction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, UUID> {

    @Query(value = """
        SELECT *
        FROM auctions a
        WHERE a.town_id IN :nearTownsIds
        """, nativeQuery = true)
    Slice<Auction> findAuctionsInNearAuctions(
        Pageable pageable,
        @Param("nearTownsIds") List<Long> nearTownsIds
    );

}
