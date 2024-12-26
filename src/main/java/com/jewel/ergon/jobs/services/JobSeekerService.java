package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.JobSeeker;
import com.jewel.ergon.jobs.repo.JobseekerRepository;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerService extends CrudServiceImpl<JobSeeker, Long> {


    private final JobseekerRepository jobseekerRepository;

    public JobSeekerService(JobseekerRepository jobseekerRepository) {
        this.jobseekerRepository = jobseekerRepository;
    }


    @Override
    protected JobseekerRepository getRepository() {
        return this.jobseekerRepository; // Provide the specific repository for Company entity
    }
}
