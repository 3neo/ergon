package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Connection;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;


@Service
public class ConnectionService extends CrudServiceImpl<Connection, Long> {


    public ConnectionService(ApplicationContext applicationContext, SpecificationBuilder<Connection> specificationBuilder, JpaRepository<Connection, Long> jpaRepository, JpaSpecificationExecutor<Connection> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }


//    /**
//     * @param entity
//     * @return
//     */
//    @Transactional
//    @Override
//    public Connection save(final Connection entity) {
//        if (entity == null) throw new IllegalArgumentException("Connection must not be null");
//        if (entity.getCompany() == null || entity.getCompany().getId() == null) {
//            throw new IllegalArgumentException("Company must be provided with a valid ID");
//        }
//        Company company = companyRepository.findById(entity.getCompany().getId())
//                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
//        entity.setCompany(company);
//        return connectionRepository.save(entity);
//    }
}
