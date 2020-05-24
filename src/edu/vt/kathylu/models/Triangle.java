package edu.vt.kathylu.models;

import edu.vt.kathylu.models.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Triangle {
    private Vertex v1, v2, v3;
    private Vertex centroid = null;
    private List<String> text = null;
    public Triangle(){
        text = new ArrayList<>();
    }

    public Vertex getV1() {
        return v1;
    }

    public void setV1(Vertex v1) {
        this.v1 = v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setV2(Vertex v2) {
        this.v2 = v2;
    }

    public Vertex getV3() {
        return v3;
    }

    public void setV3(Vertex v3) {
        this.v3 = v3;
    }

    public void setCentroid(Vertex centroid){
        this.centroid = centroid;
    }

    public Vertex getCentroid(){
        return centroid;
    }

    public void addText(String line){
        this.text.add(line);
    }

    public List<String> getText(){
        return this.text;
    }
}
