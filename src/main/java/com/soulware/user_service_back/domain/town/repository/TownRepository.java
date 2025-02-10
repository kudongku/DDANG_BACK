package com.soulware.user_service_back.domain.town.repository;

import com.soulware.user_service_back.domain.town.entity.Town;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    Optional<Town> getTownByAddress(String address);

}
