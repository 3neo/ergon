package com.jewel.ergon.jobs.model.demandStatus;

import com.jewel.ergon.jobs.model.Demand;

public class OfferDemandStatus implements DemandStatus {
    public void handleRequest(Demand demand) {
      //  demand.setDemandStatus(new SentDemandStatus());
    }

    public String getStatus() {
        return "OFFER";
    }
}
