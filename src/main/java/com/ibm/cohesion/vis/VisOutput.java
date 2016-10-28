package com.ibm.cohesion.vis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.cohesion.OutputGenerator;
import com.ibm.cohesion.data.Method;

public class VisOutput implements OutputGenerator {

    private final File outputFile;

    public VisOutput(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void generateOutput(List<Method> methods) throws IOException {
        VisJson json = new VisJson(methods);
        String nodesJson = json.nodes.toString();
        String edgesJson = json.edges.toString();
        
        InputStream templateInputStream = this.getClass().getResourceAsStream("visTemplate.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(templateInputStream));
        String line = null;
        try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile))) {
            while ((line = reader.readLine()) != null) {
                line = replaceToken(line, "%NODES%", nodesJson);
                line = replaceToken(line, "%EDGES%", edgesJson);
                outputWriter.write(line);
                outputWriter.newLine();
            }
        }
    }

    private String replaceToken(String line, String tokenToReplace, String replacement) {
        if (line.contains(tokenToReplace)) {
            return line.replace(tokenToReplace, replacement);
        }
        return line;
    }

}
