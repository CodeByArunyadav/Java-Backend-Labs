package com.hoxcloud.capstone.Impl;

import com.hoxcloud.capstone.interfaces.Syrup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("chocolate")
public class ChocolateSyrup implements Syrup {
    @Override
    public void getSyrupType(String message) {
        System.out.println("Hello friend i ate Syrup type "+message);
    }
}
