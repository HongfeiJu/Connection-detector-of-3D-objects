package edu.vt.kathylu;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileReader {
    private List<Triangle> triangles = new ArrayList<>();
    public TextFileReader(int skip, String filepath) throws IOException {
        File file = new File(filepath);

        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNextLine()){
            Triangle triangle = new Triangle();
            int count = 7 * skip;
            while(count>0 && sc.hasNextLine()){
                //System.out.println();
                count--;
                String line = sc.nextLine();
                triangle.addText(line);
                //System.out.println(line);
            }
            if(count>0){
                break;
            }
            List<String> text = triangle.getText();
            Vertex v1 = extractVertex(text.get(2)),
                    v2 = extractVertex(text.get(3)),
                    v3 = extractVertex(text.get(4));

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

    private float parseExpoData(String str){
        String[] parts = str.split("e");
        float num=Float.valueOf(parts[0]), e=Float.valueOf(parts[1]);
        return (float) (num*Math.pow(10, e));
    }

    public List<Triangle> getTrianlges(){
        return triangles;
    }


}
