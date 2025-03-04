package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Currency;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;


@Service
public class CurrencyService extends CrudServiceImpl<Currency, Long>{


    public CurrencyService(ApplicationContext applicationContext, SpecificationBuilder<Currency> specificationBuilder, JpaRepository<Currency, Long> jpaRepository, JpaSpecificationExecutor<Currency> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}
