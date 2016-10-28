package com.ibm.cohesion;

import java.io.IOException;
import java.util.List;

import com.ibm.cohesion.data.Method;

public interface OutputGenerator {

    public void generateOutput(List<Method> methods) throws IOException;

}
