package com.hoxcloud.capstone;

import com.hoxcloud.capstone.interfaces.Frosting;
import com.hoxcloud.capstone.interfaces.Syrup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

    private final Frosting frosting;
    private final Syrup syrup;


    public CakeBaker(@Qualifier("chocolateFrosting") Frosting frosting, @Qualifier("chocolateSyrup") Syrup syrup) {
        this.frosting = frosting;
        this.syrup = syrup;
    }

    public void getFrosting() {
        frosting.getFrostingType("Chocolate");
    }
    public void getSyrup() {
       syrup.getSyrupType("Chocolate");
    }
}
