package com.example.springAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApiApplication.class, args);

        List<String> empl = List.of("Arun", "Ravi", "Avika", "Shyam");
        //for(String e :empl)
        //System.out.println(e);
        //empl.forEach(e->System.out.println(e));

        String result= empl.stream().filter(e -> e.length() > 2).sorted(Comparator.reverseOrder()).findFirst().get();
        long count=empl.stream().filter(e->e.length()>2).sorted(Comparator.reverseOrder()).count();

        System.out.println(result+""+count);

    }


}
