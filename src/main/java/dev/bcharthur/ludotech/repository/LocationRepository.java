package dev.bcharthur.ludotech.repository;

import dev.bcharthur.ludotech.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l JOIN l.exemplaires e WHERE e.id = :exemplaireId " +
            "AND (l.dateDebut <= :endDate AND l.dateRetour >= :startDate)")
    List<Location> findByExemplaireAndDateRange(@Param("exemplaireId") Integer exemplaireId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

}
