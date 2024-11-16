package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.model.Status;
import com.jewel.ergon.jobs.repo.CompanyRepository;
import com.jewel.ergon.jobs.repo.DemandRepository;
import com.jewel.ergon.jobs.utilis.MappingData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.jewel.ergon.jobs.utilis.MappingData.getAndSet;

@Service
public class DemandService extends CrudServiceImpl<Demand, Long> {


    private final DemandRepository demandRepository;

    @PersistenceContext
    EntityManager em;


    @Autowired
    public DemandService(DemandRepository demandRepository) {
        this.demandRepository = demandRepository;
    }

    @Override
    protected DemandRepository getRepository() {
        return this.demandRepository; // Provide the specific repository for Company entity
    }


    @Transactional
    public Demand updateDemand(long id, Demand source) throws IllegalAccessException, NoSuchElementException {
        Demand target = this.findById(id).orElseThrow();
        getAndSet(source, target);
        save(target);
        return target;
    }


    /**
     * Method to fetch paginated demands based on status.
     *
     * @param status The status of the demand.
     * @return A list of demands that match the given status.
     */

    public Page<Demand> getDemandsByStatus(Status status, int page, int size) {
//        // Native SQL query to select from the demand table based on status
//        String sql = "SELECT * FROM demand WHERE status = :status";
//
//        // Create the native query
//        Query query = em.createNativeQuery(sql, Demand.class); // Native SQL query, expecting a Demand entity result
//
//        // Set the parameter for status
//        query.setParameter("status", status.name()); // Assuming Status is an enum and we use its name
//
//        // Execute the query and return the list of Demand entities
//        List<Demand> demands =query.getResultList();
//        return demands;


        // Create Pageable object with page number and page size
        Pageable pageable = PageRequest.of(page, size);
        return demandRepository.findDemandsByStatusPaged(status, pageable);

    }
}


