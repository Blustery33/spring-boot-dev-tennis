package com.moutmout.tennis.service;

import com.moutmout.tennis.ApplicationStatus;
import com.moutmout.tennis.HealthCheck;
import com.moutmout.tennis.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    public HealthCheck healthCheck() {
        Long activeSessions = healthCheckRepository.countApplicationConnections();
        if(activeSessions > 0){
            return new HealthCheck(ApplicationStatus.OK, "Welcome to Moutmout Tennis !");
        }else {
            return new HealthCheck(ApplicationStatus.KO, "Moutmout Tennis is not fully functional, please check your configuration.");
        }
    }
}
