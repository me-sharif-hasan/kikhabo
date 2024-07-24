package com.iishanto.kikhabo.infrastructure.services.health;

import com.iishanto.kikhabo.domain.entities.people.User;
import org.springframework.stereotype.Component;

@Component
public class StanderHealthServiceImpl implements HealthServices{
    public double getBMI(User user){
        var height=user.getHeightInFt()*0.3084;
        var weight=user.getWeightInKg();
        return weight/(height*height);
    }
}
