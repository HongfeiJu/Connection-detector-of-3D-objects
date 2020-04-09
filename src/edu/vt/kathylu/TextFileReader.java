package edu.vt.kathylu;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TextFileReader {
    List<Triangle> triangles = new LinkedList<>();

    public TextFileReader(String filepath) throws IOException {
        File file = new File(filepath);

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){

            String line = sc.nextLine();
            //System.out.println(line);
            if(line.indexOf("outer") ==-1){
                continue;
            }
            Vertex v1 = extractVertex(sc.nextLine()),
                    v2 = extractVertex(sc.nextLine()),
                    v3 = extractVertex(sc.nextLine());
            Triangle triangle = new Triangle();
            triangle.setV1(v1);
            triangle.setV2(v2);
            triangle.setV3(v3);
            triangles.add(triangle);
        }
    }

    private Vertex extractVertex(String line){
        String[] segs = line.split("\\s+");
        Vertex v = new Vertex();
        v.setX(parseExpoData(segs[2]));
        v.setY(parseExpoData(segs[3]));
        v.setZ(parseExpoData(segs[4]));
        return v;
    }

    private double parseExpoData(String str){
        String[] parts = str.split("e");
        double num=Double.valueOf(parts[0]), e=Double.valueOf(parts[1]);
        return num*Math.pow(10, e);
    }

    public List<Triangle> getTrianlges(){
        return triangles;
    }


}
