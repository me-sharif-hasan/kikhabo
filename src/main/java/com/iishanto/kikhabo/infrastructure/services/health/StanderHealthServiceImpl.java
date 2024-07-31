package com.iishanto.kikhabo.infrastructure.services.health;

import com.iishanto.kikhabo.domain.entities.people.User;
import org.springframework.stereotype.Component;

@Component
public class StanderHealthServiceImpl implements HealthServices{
    public double getBMI(User user){
        float userHeight= user.getHeightInFt();
        float userWeight=user.getWeightInKg();
        var height=userHeight*0.3084;
        return userWeight/(height*height);
    }
}
