package com.jewel.ergon.jobs.model.demandStatus;


import com.jewel.ergon.jobs.model.Demand;

//Status design pattern
public interface DemandStatus {
    //OFFER ,SENT, INPROGRESS, ACCEPTED, REJECTED, REPORTED, CLOSED
    void handleRequest(Demand demand);

    String getStatus();



}
