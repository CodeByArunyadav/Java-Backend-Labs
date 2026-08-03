package com.hoxcloud.capstone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CapstoneApplication implements CommandLineRunner {

	private final CakeBaker cakeBaker;


    public CapstoneApplication(CakeBaker cakeBaker){
			this.cakeBaker = cakeBaker;
		}


		public static void main(String[] args) { SpringApplication.run( CapstoneApplication.class,args);
		}


		@Override
		public void run(String... args) {

			cakeBaker.getFrosting();
			cakeBaker.getSyrup();

		}

}
