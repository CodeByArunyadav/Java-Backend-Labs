package com.hoxcloud.capstone.serialisation;

import java.io.*;

public class Student implements Serializable {

    private static final long serialVersionUID=3L;
    private String name;
    private int age;

    public Student(String name,int age)
    {
        this.name=name;
        this.age=age;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student=new Student("Arun",25);

        ObjectOutputStream objectOutputStream= new ObjectOutputStream(new FileOutputStream("hello.txt"));
        objectOutputStream.writeObject(student);

        objectOutputStream.close();

        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("hello.txt")) ;
        Student s2=(Student) objectInputStream.readObject();

        System.out.println(s2.name + " " + s2.age);

    }
}
