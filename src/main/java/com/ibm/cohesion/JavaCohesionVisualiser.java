package com.ibm.cohesion;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.ibm.cohesion.vis.VisOutput;

public class JavaCohesionVisualiser {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length < 1) {
            System.err.println("You must supply a class file");
            System.exit(1);
        }
        File classFile = new File(args[0]);
        String outputFileLocation;
        if (args.length == 1) {
            outputFileLocation = classFile.getName() + ".html";
        } else {
            outputFileLocation = args[1];
        }
        File outputFile = new File(outputFileLocation);
        
        OutputGenerator output = new VisOutput(outputFile);
        JavaClassAnalyzer analyzer = new JavaClassAnalyzer(classFile, output);
        analyzer.analyseClass();
        
        Desktop.getDesktop().open(outputFile);
    }

}
