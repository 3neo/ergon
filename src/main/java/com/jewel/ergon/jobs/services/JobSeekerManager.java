package com.jewel.ergon.jobs.services;

import com.jewel.ergon.cv.CvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerManager {
    //TODO fix this

    CvManager cvManager;

    public JobSeekerManager(CvManager cvManager) {
        this.cvManager = cvManager;
    }
}
