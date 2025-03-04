package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.AppConfig;
import com.jewel.ergon.jobs.repo.AppConfigRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class AppConfigService {

    private final AppConfigRepository appConfigRepository;
    private static final String TIMEZONE_KEY = "time_zone";

    public AppConfigService(AppConfigRepository appConfigRepository) {
        this.appConfigRepository = appConfigRepository;
    }


    /**
     * Get the currently configured timezone.
     *
     * @return Configured timezone ID or the system default timezone if not set.
     */
    public Optional<String> getCurrentTimeZone() {


        String timeZoneConfig = appConfigRepository.findByKey(TIMEZONE_KEY)
                .map(AppConfig::getValue)
                .orElseGet(() -> TimeZone.getDefault().getID());
        return Optional.of(timeZoneConfig);

    }

    /**
     * Update the application's timezone and persist the value.
     *
     * @param timeZoneId The timezone ID to set.
     */
    @Transactional
    public void updateTimeZone(String timeZoneId) {
        if (!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZoneId))
            throw new IllegalArgumentException("Invalid timezone ID: " + timeZoneId);

        AppConfig app = appConfigRepository.findByKey(TIMEZONE_KEY)
                .orElse(AppConfig.builder()
                        .key(TIMEZONE_KEY)
                        .build());
       app.setValue(timeZoneId);

        // Set the timezone for the application
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(timeZoneId)));
    }
}

