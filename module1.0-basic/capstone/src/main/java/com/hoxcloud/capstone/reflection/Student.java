package com.hoxcloud.capstone.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Student {
    private Student()
    {
        System.out.println("Hello i am constructor of student class");

    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clasz= Student.class;

        System.out.println(clasz.getName());

        for (Method method :  clasz.getDeclaredMethods())
        {
            System.out.println(method.getName());
        }

        // Create object and invoke method
        Object obj = clasz.getDeclaredConstructor().newInstance();

        System.out.println(obj);
    }
}
