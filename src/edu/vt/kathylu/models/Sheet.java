package edu.vt.kathylu.models;

import edu.vt.kathylu.models.Triangle;

import java.util.ArrayList;
import java.util.List;

public class Sheet {
    List<Triangle> triangles;
    public Sheet(){
        triangles = new ArrayList<>();
    }

    public void add(Triangle triangle){
        triangles.add(triangle);
    }

    public List<Triangle> getTriangles(){
        return triangles;
    }
}
