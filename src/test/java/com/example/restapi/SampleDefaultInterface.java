package com.example.restapi;

public interface SampleDefaultInterface {

    String name = "default Name";

    default String hello() {
        return name + "Default Hello";
    }

    static void print() {
        System.out.println("Static Method in Interface");
    }

}
