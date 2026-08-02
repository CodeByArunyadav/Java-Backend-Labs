package com.hoxcloud.capstone.Impl;

import com.hoxcloud.capstone.interfaces.Syrup;
import org.springframework.stereotype.Component;

@Component
public class StrawberrySyrup implements Syrup {
    @Override
    public void getSyrupType(String message) {
        System.out.println("Hello friend i ate Syrup type "+message);
    }
}
