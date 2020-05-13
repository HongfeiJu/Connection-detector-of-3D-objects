package edu.vt.kathylu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    private static TextFileReader textFileReader;
    private static final String
            DATA_FILE_PATH = "C:\\Users\\hongf\\Google Drive\\Career\\Kathy Lu\\raw data\\mxene01_100_200430_part1.stl",
            RESULT_FILE_PATH = "C:\\Users\\hongf\\Google Drive\\Career\\Kathy Lu\\raw data",
            RESULT_FILE_PREFIX = "group";
    private static int
            CRITICAL_VALUE = 1000,
            SKIP = 1;


    public static void main(String[] args) throws IOException {
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\sample1.txt
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\sheet_1_100_100.txt
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\small_packing_0_0005volper_cube.stl
        //‪C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\mxene01_100_200430.stl (multiple sheets)
	    textFileReader = new TextFileReader(SKIP, DATA_FILE_PATH);
	    List<Triangle> triangles = textFileReader.getTrianlges();

	    /*
        List<Triangle> tempTriangles=new ArrayList<>();
        for(int i=0;i<triangles.size();i++){
            tempTriangles.add(triangles.get(i));
        }
        triangles=tempTriangles;
	     */

        /*
        Decomposer decomposer = new Decomposer(0.01);
        triangles = decomposer.decompose(triangles);
         */

        System.out.println("number of triangles: " + triangles.size());
        Processor processor = new Processor();
        List<Vertex> centroids = new ArrayList<>();
        int count = 0;
        float min=Float.MAX_VALUE, max=Float.MIN_VALUE, sum=0;
        for(Triangle triangle: triangles){
            centroids.add(processor.getCentroid(triangle));
            triangle.setCentroid(processor.getCentroid(triangle));
            count += 3;

            for(float length: processor.getTriangleEdgeLengths(triangle)){
                sum+=length;
                max=Math.max(max, length);
                min=Math.min(min, length);
            }
        }
        System.out.println("number of centroids: " + centroids.size());
        System.out.println("average edge length: " + sum/count + "    min edge length: " + min + "    max edge length: " + max);
        /*
        Vertex v0=new Vertex();
        float minCD = float.MAX_VALUE, maxCD = float.MIN_VALUE, sumCD=0;
        for(int i=0;i<centroids.size();i++){
            float curCD = processor.getEdgeLength(centroids.get(i), v0);
            minCD=Math.min(minCD, curCD);
            maxCD=Math.max(maxCD, curCD);
            sumCD+=curCD;

            for(int j=i+1;j<centroids.size();j++){
                float curCD = processor.getEdgeLength(centroids.get(i), centroids.get(j));
                minCD=Math.min(minCD, curCD);
                maxCD=Math.max(maxCD, curCD);
                sumCD+=curCD;
            }
        }
        System.out.println("average CD: " + sumCD/centroids.size() + "    min CD: " + minCD + "    max CD: " + maxCD);
        */

        List<Set<Triangle>> groups = (new Connector()).getConnectedGroups(triangles, CRITICAL_VALUE);
        System.out.println("group count: " + groups.size());
        TextFileWriter textFileWriter = new TextFileWriter();
        textFileWriter.generateGroupTextFile(RESULT_FILE_PREFIX, groups, RESULT_FILE_PATH);
    }
}
