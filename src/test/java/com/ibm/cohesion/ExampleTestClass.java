package com.ibm.cohesion;

public class ExampleTestClass {

    private String c = "c";

    private void a() {
        b();
    }

    private void b() {
        String d = c ;
    }
    
}
