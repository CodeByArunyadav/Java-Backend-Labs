package com.hoxcloud.capstone.Impl;

import com.hoxcloud.capstone.interfaces.Frosting;
import org.springframework.stereotype.Component;

@Component
public class StrawberryFrosting implements Frosting {
    @Override
    public void getFrostingType(String message) {
        System.out.println("Hello Friends i am eating Foresting Type "+message);
    }
}
