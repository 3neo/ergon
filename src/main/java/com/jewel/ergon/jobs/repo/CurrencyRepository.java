package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}