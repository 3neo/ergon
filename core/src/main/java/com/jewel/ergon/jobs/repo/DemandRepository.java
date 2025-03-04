package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.dto.ChartProjection;
import com.jewel.ergon.jobs.model.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DemandRepository extends JpaRepository<Demand, Long>, JpaSpecificationExecutor<Demand> {

//    @Query(
//            value = "SELECT status s , count(s) FROM demand d group by status",
//            countQuery = "SELECT COUNT(d.id) FROM demand d group by status",
//            nativeQuery = true
//    )
//    Page<Demand> findDemandsPercentage(@Param("status") Status status, Pageable pageable);

    @Query(
            value = "SELECT d.status as key , count(d.status) as value FROM demand d group by d.status",
            countQuery = "SELECT COUNT(d.id) FROM demand d group by status",
            nativeQuery = true
    )
    Optional<List<ChartProjection>> findDemandsPercentage();


    @Query(
            value = "SELECT to_char(gs.day, 'YYYY-MM-DD') as key, COALESCE(count(d.id), 0) as value\n" +
                    "FROM generate_series(current_date - interval '29 days', current_date, interval '1 day') as gs(day)\n" +
                    "LEFT JOIN demand d ON gs.day = date(d.date_applied) and d.status = 'SENT'\n" +
                    "GROUP BY gs.day\n" +
                    "ORDER BY gs.day",
            nativeQuery = true
    )
    Optional<List<ChartProjection>> findDemandsCountForLastWeek();


    @Query("SELECT COUNT(d) FROM Demand d WHERE d.status != 'OFFER'")
    Long countWhereStatusIsSent();
}