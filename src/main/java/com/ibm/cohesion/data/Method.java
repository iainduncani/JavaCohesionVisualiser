package com.ibm.cohesion.data;

import java.util.List;

public class Method {

    public final String name;
    public final String id;
    public final List<String> fieldsUsed;
    public final List<String> methodsCalled;
    public Method(String name, String id, List<String> fieldsUsed, List<String> methodsCalled) {
        super();
        this.name = name;
        this.id = id;
        this.fieldsUsed = fieldsUsed;
        this.methodsCalled = methodsCalled;
    }

}
