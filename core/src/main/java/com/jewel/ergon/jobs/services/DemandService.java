package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.dto.ChartProjection;
import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.repo.DemandRepository;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandService extends CrudServiceImpl<Demand, Long> {

    @Autowired
    DemandRepository demandRepository;

    public DemandService(ApplicationContext applicationContext, SpecificationBuilder<Demand> specificationBuilder, JpaRepository<Demand, Long> jpaRepository, JpaSpecificationExecutor<Demand> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }


    public Optional<List<ChartProjection>> findDemandsPercentage() {
        return demandRepository.findDemandsPercentage();
    }


    public Optional<List<ChartProjection>> findDemandsForLas7days() {
        return demandRepository.findDemandsCountForLastWeek();
    }

    public Optional<Long> findTotalDemands() {
       return Optional.of(demandRepository.countWhereStatusIsSent());
    }
}


