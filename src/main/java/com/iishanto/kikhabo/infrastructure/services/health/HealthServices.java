package com.iishanto.kikhabo.infrastructure.services.health;

import com.iishanto.kikhabo.domain.entities.people.User;
import org.springframework.stereotype.Service;

@Service
public interface HealthServices {
    double getBMI(User user);
}
