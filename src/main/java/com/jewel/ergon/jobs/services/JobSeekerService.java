package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.JobSeeker;
import com.jewel.ergon.jobs.repo.JobSeekerRepository;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerService extends CrudServiceImpl<JobSeeker, Long> {


    private final JobSeekerRepository jobseekerRepository;

    public JobSeekerService(JobSeekerRepository jobseekerRepository) {
        this.jobseekerRepository = jobseekerRepository;
    }


    @Override
    protected JobSeekerRepository getRepository() {
        return this.jobseekerRepository; // Provide the specific repository for Company entity
    }
}
