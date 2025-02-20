package com.soulware.user_service_back.domain.town.repository;

import com.soulware.user_service_back.domain.town.entity.Town;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    Optional<Town> getTownByAddress(String address);

    @Query(value = """
              SELECT *
              FROM towns t1
              WHERE SQRT(POW(t1.latitude - :latitude, 2) + POW(t1.longitude - :longitude, 2)) <= :defaultDistance
        """, nativeQuery = true)
    List<Town> getNearTowns(double latitude, double longitude, double defaultDistance);

}
