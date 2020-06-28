package edu.vt.klugroup.processor;

import edu.vt.klugroup.models.Triangle;
import edu.vt.klugroup.models.Vertex;

import java.util.*;

public class Connector {
    public Connector(){}
    public List<Set<Triangle>> getConnectedGroups(List<Triangle> triangles, float gap){

        Collections.sort(triangles, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle o1, Triangle o2) {
                Vertex centroid1 = o1.getCentroid(), centroid2 = o2.getCentroid();
                if(centroid1.getX() != centroid2.getX()){
                    return Float.compare(centroid1.getX(), centroid2.getX());
                }else if(centroid1.getY() != centroid2.getY()){
                    return Float.compare(centroid1.getY(), centroid2.getY());
                }else{
                    return Float.compare(centroid1.getZ(), centroid2.getZ());
                }
            }
        });

        Set<Integer>[] graph = build(triangles, gap);

        List<Set<Triangle>> res = new LinkedList<>();
        unionFindConnect(graph, triangles, res);

        return res;
    }

    private void unionFindConnect(Set<Integer>[] graph, List<Triangle> trianges, List<Set<Triangle>> res) {
        int[] parent = new int[graph.length];
        for(int i=0;i<parent.length;i++){
            parent[i] = i;
        }

        for(int i=0;i<graph.length;i++){
            for(int j: graph[i]){
                int pi = find(parent, i), pj = find(parent, j);
                if(pi != pj){
                    parent[pi] = pj;
                }
            }
        }

        Map<Integer, Set<Triangle>> groups = new HashMap<>();
        for(int i=0;i<parent.length;i++){
            int p = find(parent, i);
            if(!groups.containsKey(p)){
                groups.put(p, new HashSet<>());
            }
            groups.get(p).add(trianges.get(i));
        }

        for(Set<Triangle> group: groups.values()){
            res.add(group);
        }
    }

    private int find(int[] parent, int j) {
        while(parent[j] != j){
            j = parent[j];
        }
        return j;
    }

    private Set<Integer>[] build(List<Triangle> triangles, float gap) {
        Set<Integer>[] graph = new Set[triangles.size()];
        for(int i=0;i<graph.length;i++){
            graph[i] = new HashSet<>();
        }
        for(int i=0;i<triangles.size();i++){
            for(int j=i+1;j<triangles.size();j++){
                Vertex centroid1 = triangles.get(i).getCentroid(), centroid2 = triangles.get(j).getCentroid();
                if(Math.abs(centroid1.getX() - centroid2.getX()) > gap){
                    break;
                }

                if(isNeighbour(triangles.get(i).getCentroid(), triangles.get(j).getCentroid(), gap)){
                    graph[i].add(j);
                }
            }
        }
        return graph;
    }

    private boolean isNeighbour(Vertex v1, Vertex v2, float gap) {
        return getDistanceBetweenVertex(v1, v2) < gap;
    }

    private float getDistanceBetweenVertex(Vertex v1, Vertex v2){
        return (float) Math.sqrt(
                Math.pow(v1.getX()-v2.getX(), 2)
                        + Math.pow(v1.getY()-v2.getY(), 2)
                        + Math.pow(v1.getZ()-v2.getZ(), 2));
    }

}
