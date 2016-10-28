package com.ibm.cohesion.vis;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;

import org.json.JSONObject;
import org.junit.Test;

import com.ibm.cohesion.data.Method;

public class VisJsonTest {

    @Test
    public void methodAddedAsNodeToJson() {
        Method method = new Method("testMethod", "testId", Collections.emptyList(), Collections.emptyList());
        VisJson testObject = new VisJson(Collections.singletonList(method));

        assertThat(testObject.nodes.length(), is(1));
        assertThat(testObject.nodes.get(0), instanceOf(JSONObject.class));
        JSONObject methodJson = (JSONObject) testObject.nodes.get(0);
        assertThat(methodJson.get("id"), is(method.id));
        assertThat(methodJson.get("label"), is(method.name));
        assertThat(methodJson.get("shape"), is("ellipse"));
    }

    @Test
    public void methodCallAddedAsEdgeToJson() {
        final String calleeId = "calledId";
        Method methodCallee = new Method("testMethodCallee", calleeId, Collections.emptyList(), Collections.emptyList());
        final String calledId = "callerId";
        Method methodCaller = new Method("testMethodCaller", calledId, Collections.emptyList(), Collections.singletonList(methodCallee.id));
        VisJson testObject = new VisJson(Arrays.asList(new Method[] { methodCaller, methodCallee }));

        assertThat(testObject.edges.length(), is(1));
        assertThat(testObject.edges.get(0), instanceOf(JSONObject.class));
        JSONObject edgeJson = (JSONObject) testObject.edges.get(0);
        assertThat(edgeJson.get("from"), is(methodCaller.id));
        assertThat(edgeJson.get("to"), is(methodCallee.id));
        assertThat(edgeJson.get("arrows"), is("to"));
        assertThat(testObject.nodes.length(), is(2));
        for (Object jsonNode : testObject.nodes) {
            assertThat(jsonNode, instanceOf(JSONObject.class));
            JSONObject methodJson = (JSONObject) testObject.nodes.get(0);
            String id = (String) methodJson.get("id");
            switch (id) {
                case calleeId:
                    assertThat(methodJson.get("label"), is(methodCallee.name));
                    assertThat(methodJson.get("shape"), is("ellipse"));
                    break;
                case calledId:
                    assertThat(methodJson.get("label"), is(methodCaller.name));
                    assertThat(methodJson.get("shape"), is("ellipse"));
                    break;
                default:
                    fail("Unknown ID on json node " + id);
            }
        }
    }
    
    @Test
    public void fieldAddedAsNodeAndEdge() {
        final String methodId = "testId";
        final String fieldId = "testField";
        Method method = new Method("testMethod", methodId, Collections.singletonList(fieldId), Collections.emptyList());
        VisJson testObject = new VisJson(Collections.singletonList(method));

        assertThat(testObject.edges.length(), is(1));
        assertThat(testObject.edges.get(0), instanceOf(JSONObject.class));
        JSONObject edgeJson = (JSONObject) testObject.edges.get(0);
        assertThat(edgeJson.get("from"), is(methodId));
        assertThat(edgeJson.get("to"), is(fieldId));
        assertThat(edgeJson.get("arrows"), is("to"));
        assertThat(testObject.nodes.length(), is(2));
        for (Object jsonNode : testObject.nodes) {
            assertThat(jsonNode, instanceOf(JSONObject.class));
            JSONObject methodJson = (JSONObject) testObject.nodes.get(0);
            String id = (String) methodJson.get("id");
            switch (id) {
                case fieldId:
                    assertThat(methodJson.get("label"), is(fieldId));
                    assertThat(methodJson.get("shape"), is("square"));
                    break;
                case methodId:
                    assertThat(methodJson.get("label"), is(method.name));
                    assertThat(methodJson.get("shape"), is("ellipse"));
                    break;
                default:
                    fail("Unknown ID on json node " + id);
            }
        }
    }
    
    @Test
    public void fieldUsedTwiceOnlyAddedAsSingleNode() {
        final String fieldId = "testField";
        Method methodCallee = new Method("testMethod1", "id1", Collections.singletonList(fieldId), Collections.emptyList());
        Method methodCaller = new Method("testMethod2", "id2", Collections.singletonList(fieldId), Collections.emptyList());
        VisJson testObject = new VisJson(Arrays.asList(new Method[] { methodCaller, methodCallee }));
        
        assertThat(testObject.nodes.length(), is(3));
    }

}
