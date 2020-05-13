package edu.vt.kathylu;

import java.util.*;

public class Connector {
    public Connector(){}
    private int processed=0;
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

        /*
        for(Triangle triangle: triangles){
            Vertex centroid = triangle.getCentroid();
            System.out.println(centroid.getX()+", "+centroid.getY()+", "+centroid.getZ());
        }

         */

        int size = triangles.size();
        boolean[] visited = new boolean[triangles.size()];
        Set<Integer>[] graph = build(triangles, gap);
        System.out.println("graph is built");
        List<Set<Triangle>> res = new LinkedList<>();
        unionFindConnect(graph, triangles, res);
        /*
        for(int i = 0;i < triangles.size();i++){
            if(visited[i]){
                continue;
            }
            List<Triangle> subres = new LinkedList<>(), cur = new LinkedList<>();
            cur.add(triangles.get(i));
            subres.add(triangles.get(i));
            visited[i] = true;
            connect(triangles, visited, cur, subres, gap);
            System.out.println("group counts: " + subres.size());
            res.add(subres);
        }
        processed = 0;
         */
        return res;
    }

    private void unionFindConnect(Set<Integer>[] graph, List<Triangle> trianges, List<Set<Triangle>> res) {
        int[] parent = new int[graph.length];
        for(int i=0;i<parent.length;i++){
            parent[i] = i;
        }
        for(int i=0;i<graph.length;i++){
            System.out.println("union find: " + i);
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
            System.out.println("group size: " + group.size());
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
                //System.out.println("Distance from "+i+" to "+j+": "+getDistanceBetweenVertex(centroid1, centroid2));
                if(Math.abs(centroid1.getX() - centroid2.getX()) > gap){
                    break;
                }

                if(isNeighbour(triangles.get(i).getCentroid(), triangles.get(j).getCentroid(), gap)){
                    graph[i].add(j);
                    //graph[j].add(i);
                }
            }
            System.out.println("Node: "+i+",  neighor: " +graph[i].size());
        }
        return graph;
    }

    private void connect(List<Triangle> triangles, boolean[] visited, List<Triangle> cur,
                         List<Triangle> subres, float gap){
        System.out.println("level counts: " + cur.size());
        List<Triangle> nextCur = new LinkedList<>();
        boolean foundnew = false;
        for(Triangle triangle: cur){
            for(int i = 0;i < triangles.size();i++){
                if(visited[i]){
                    continue;
                }
                if(isNeighbour(triangle.getCentroid(), triangles.get(i).getCentroid(), gap)){
                    //System.out.print(i+" ");
                    foundnew = true;
                    visited[i] = true;
                    nextCur.add(triangles.get(i));
                    processed++;
                    //System.out.println(processed + " : " + visited.length);
                }
            }
        }
        subres.addAll(nextCur);
        if(!foundnew){
            return;
        }

        connect(triangles, visited, nextCur, subres, gap);
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
