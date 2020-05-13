package edu.vt.kathylu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class TextFileWriter {
    public void generateGroupTextFile(String prefix, List<Set<Triangle>> groups, String path) throws IOException {
        for(int i=0;i<groups.size();i++){
            FileWriter fw=new FileWriter(path+"/"+prefix+(i+1)+".stl");
            fw.write("solid "+prefix.toUpperCase()+"_"+(i+1)+"\n");
            for(Triangle triangle: groups.get(i)){
                for(String line: triangle.getText()){
                    fw.write(line+"\n");
                }
            }
            fw.write("endsolid "+prefix.toUpperCase()+"_"+i);
            fw.close();
        }

    }

}
