package edu.vt.klugroup.processor;

import edu.vt.klugroup.models.Triangle;
import edu.vt.klugroup.models.Vertex;

import java.util.LinkedList;
import java.util.List;

public class Decomposer {
    private float limit = 1;
    private TriangleProcessor processor = null;
    public Decomposer(float _limit){
        limit = _limit;
        processor = new TriangleProcessor();
    }
    public List<Triangle> decompose(List<Triangle> triangles){
        List<Triangle> result = new LinkedList<>();
        int count = 0;
        for(Triangle triangle: triangles){
            count++;
            System.out.println(count*1.00/1092596*100+"%");
            result.addAll(decompose(triangle));
        }
        return result;
    }

    public List<Triangle> decompose(Triangle triangle){
        List<Float> lengths = processor.getTriangleEdgeLengths(triangle);
        List<Triangle> result = new LinkedList<>();
        float len1 = lengths.get(0), len2 = lengths.get(1), len3 = lengths.get(2);
        //System.out.println("len1: " + len1 + ", len2: " + len2 + ", len3: " +len3);
        if( len1<= limit && len2 <= limit && len3 <= limit ){
            result.add(triangle);
        }else if(len1 >= len2 && len1 >= len3){
            Vertex v1 = triangle.getV1(), v2 = triangle.getV2(), v3 = triangle.getV3();
            Vertex newV = getMidVertex(v1, v2);
            Triangle triangle1 = new Triangle(), triangle2 = new Triangle();
            triangle1.setV1(v1);
            triangle1.setV2(newV);
            triangle1.setV3(v3);
            triangle2.setV1(newV);
            triangle2.setV2(v2);
            triangle2.setV3(v3);
            result.addAll(decompose(triangle1));
            result.addAll(decompose(triangle2));
        }else if(len2 >= len1 && len2 >= len3){
            Vertex v1 = triangle.getV1(), v2 = triangle.getV2(), v3 = triangle.getV3();
            Vertex newV = getMidVertex(v2, v3);
            Triangle triangle1 = new Triangle(), triangle2 = new Triangle();
            triangle1.setV1(v1);
            triangle1.setV2(v2);
            triangle1.setV3(newV);
            triangle2.setV1(v1);
            triangle2.setV2(newV);
            triangle2.setV3(v3);
            result.addAll(decompose(triangle1));
            result.addAll(decompose(triangle2));
        }else if(len3 >= len1 && len3 >= len2){
            Vertex v1 = triangle.getV1(), v2 = triangle.getV2(), v3 = triangle.getV3();
            Vertex newV = getMidVertex(v3, v1);
            Triangle triangle1 = new Triangle(), triangle2 = new Triangle();
            triangle1.setV1(v1);
            triangle1.setV2(v2);
            triangle1.setV3(newV);
            triangle2.setV1(newV);
            triangle2.setV2(v2);
            triangle2.setV3(v3);
            result.addAll(decompose(triangle1));
            result.addAll(decompose(triangle2));
        }
        return result;
    }

    public Vertex getMidVertex(Vertex v1, Vertex v2){
        Vertex newV = new Vertex();
        newV.setX(v1.getX() + (v2.getX() - v1.getX()) / 2);
        newV.setY(v1.getY() + (v2.getY() - v1.getY()) / 2);
        newV.setZ(v1.getZ() + (v2.getZ() - v1.getZ()) / 2);
        return newV;
    }
}
