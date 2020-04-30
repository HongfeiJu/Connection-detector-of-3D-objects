package edu.vt.kathylu;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {
    private static TextFileReader textFileReader;
    public static void main(String[] args) throws IOException {
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\sample1.txt
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\sheet_1_100_100.txt
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\small_packing_0_0005volper_cube.stl
	    textFileReader = new TextFileReader("C:\\Users\\hongf\\Google Drive\\Career\\Kathy Lu\\raw data\\small_packing_0_0005volper_cube.stl");
        List<Triangle> triangles = textFileReader.getTrianlges();
        List<Triangle> tempTriangles=new LinkedList<>();
        for(int i=0;i<triangles.size();i+=2){
            tempTriangles.add(triangles.get(i));
        }
        triangles=tempTriangles;

        System.out.println("number of triangles: " + triangles.size());
        Processor processor = new Processor();
        List<Vertex> centroids = new LinkedList<>();
        int count = 0;
        double min=Double.MAX_VALUE, max=Double.MIN_VALUE, sum=0;
        for(Triangle triangle: triangles){
            centroids.add(processor.getCentroid(triangle));
            triangle.setCentroid(processor.getCentroid(triangle));
            count += 3;

            for(double length: processor.getTriangleEdgeLengths(triangle)){
                sum+=length;
                max=Math.max(max, length);
                min=Math.min(min, length);
            }
        }
        System.out.println("number of centroids: " + centroids.size());
        System.out.println("average edge length: " + sum/count + "    min edge length: " + min + "    max edge length: " + max);

        List<Set<Triangle>> groups = (new Connector()).getConnectedGroups(triangles, 200);
        //System.out.println(groups.size());
    }
}
