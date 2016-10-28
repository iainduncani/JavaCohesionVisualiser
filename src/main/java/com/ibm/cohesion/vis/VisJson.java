package com.ibm.cohesion.vis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.cohesion.data.Method;

public class VisJson {

    public final JSONArray nodes = new JSONArray();
    public final JSONArray edges = new JSONArray();

    public VisJson(List<Method> methods) {
        convertToJson(methods);
    }

    private void convertToJson(List<Method> methods) {
        Set<String> fieldsAdded = new HashSet<>();
        for (Method method : methods) {
            addMethodNode(method);
            addFieldsUsed(fieldsAdded, method);
            addMethodsCalled(method);
        }
    }

    private void addMethodNode(Method method) {
        addNode(method.id, method.name, "ellipse");
    }

    private void addFieldsUsed(Set<String> fieldsAdded, Method method) {
        for (String field : method.fieldsUsed) {
            addFieldNode(fieldsAdded, field);
            addEdge(method.id, field);
        }
    }

    private void addFieldNode(Set<String> fieldsAdded, String field) {
        if (!fieldsAdded.contains(field)) {
            fieldsAdded.add(field);
            addNode(field, field, "square");
        }
    }

    private void addMethodsCalled(Method method) {
        for (String toMethod : method.methodsCalled) {
            addEdge(method.id, toMethod);
        }
    }

    
    private void addNode(String nodeId, String nodeLabel, String shape) {
        JSONObject node = new JSONObject();
        node.put("id", nodeId);
        node.put("label", nodeLabel);
        node.put("shape", shape);
        nodes.put(node);
    }

    private void addEdge(String from, String to) {
        JSONObject edge = new JSONObject();
        edge.put("from", from);
        edge.put("to", to);
        edge.put("arrows", "to");
        edges.put(edge);
    }

}
