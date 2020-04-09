package edu.vt.kathylu;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static TextFileReader textFileReader;
    public static void main(String[] args) throws IOException {
	    textFileReader = new TextFileReader("C:\\Users\\hongf\\Google Drive\\Career\\Kathy Lu\\raw data\\Mxene001_100_Mxene001_100_Phase2.txt");
        List<Triangle> triangles = textFileReader.getTrianlges();
        System.out.println("number of triangles: " + triangles.size());
        Processor processor = new Processor();
        List<Vertex> centroids = new LinkedList<>();
        List<Double> lengths = new LinkedList<>();
        double min=0.05, max=0, sum=0;
        for(Triangle triangle: triangles){
            centroids.add(processor.getCentroid(triangle));
            for(double length: processor.getTriangleEdgeLengths(triangle)){
                lengths.add(length);
                sum+=length;
                max=Math.max(max, length);
                min=Math.min(min, length);
            }
        }
        System.out.println("number of centroids: " + centroids.size());
        System.out.println("average length: " + sum/lengths.size() + "    min length" + min + "    max length: " + max);
    }
}
