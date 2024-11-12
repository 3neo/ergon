package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Connection;
import com.jewel.ergon.jobs.repo.CompanyRepository;
import com.jewel.ergon.jobs.repo.ConnectionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class ConnectionService extends  CrudServiceImpl<Connection, Long> {
    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }


    @Override
    protected ConnectionRepository getRepository() {
        return this.connectionRepository ;
    }
}
