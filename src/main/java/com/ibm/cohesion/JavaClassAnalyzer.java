package com.ibm.cohesion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;

import com.ibm.cohesion.asm.MethodRecordingVisitor;
import com.ibm.cohesion.data.Method;

public class JavaClassAnalyzer {

    private final File classToAnalyse;
    private final OutputGenerator output;

    public JavaClassAnalyzer(File classToAnalyse, OutputGenerator output) {
        this.classToAnalyse = classToAnalyse;
        this.output = output;
    }

    public void analyseClass() throws FileNotFoundException, IOException {
        List<Method> methods = new ArrayList<>();
        ClassReader reader = new ClassReader(new FileInputStream(classToAnalyse));
        reader.accept(new MethodRecordingVisitor(methods), 0);
        output.generateOutput(methods);
    }

}
