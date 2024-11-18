package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.model.Currency;
import com.jewel.ergon.jobs.repo.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class CurrencyService extends CrudServiceImpl<Currency, Long>{

     private final  CurrencyRepository currencyRepository;


     @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * @return CurrencyRepository
     */

    @Override
    protected JpaRepository<Currency, Long> getRepository() {
        return this.currencyRepository;
    }
}
