package edu.vt.kathylu;

import java.util.LinkedList;
import java.util.List;

public class Processor {
    public Vertex getCentroid(Triangle triangle){
        Vertex vertex = new Vertex();
        vertex.setX((triangle.getV1().getX() + triangle.getV2().getX() + triangle.getV3().getX())/3);
        vertex.setY((triangle.getV1().getY() + triangle.getV2().getY() + triangle.getV3().getY())/3);
        vertex.setZ((triangle.getV1().getZ() + triangle.getV2().getZ() + triangle.getV3().getZ())/3);
        return vertex;
    }

    public List<Double> getTriangleEdgeLengths(Triangle triangle){
        List<Double> lengths = new LinkedList<>();
        lengths.add(getEdgeLength(triangle.getV1(), triangle.getV2()));
        lengths.add(getEdgeLength(triangle.getV2(), triangle.getV3()));
        lengths.add(getEdgeLength(triangle.getV3(), triangle.getV1()));
        return lengths;
    }

    public double getEdgeLength(Vertex v1, Vertex v2){
        return Math.sqrt(
                Math.pow(v1.getX()-v2.getX(), 2)
                        + Math.pow(v1.getY()-v2.getY(), 2)
                        + Math.pow(v1.getZ()-v2.getZ(), 2));
    }

}