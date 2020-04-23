package edu.vt.kathylu;

import java.util.LinkedList;
import java.util.List;

public class Sheet {
    List<Triangle> triangles;
    public Sheet(){
        triangles = new LinkedList<>();
    }

    public void add(Triangle triangle){
        triangles.add(triangle);
    }

    public List<Triangle> getTriangles(){
        return triangles;
    }
}
