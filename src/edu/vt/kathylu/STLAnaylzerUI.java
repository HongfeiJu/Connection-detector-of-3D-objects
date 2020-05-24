package edu.vt.kathylu;

import edu.vt.kathylu.io.TextFileReader;
import edu.vt.kathylu.io.TextFileWriter;
import edu.vt.kathylu.models.Triangle;
import edu.vt.kathylu.models.Vertex;
import edu.vt.kathylu.processor.Connector;
import edu.vt.kathylu.processor.TriangleProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class STLAnaylzerUI extends JFrame{
    private static TextFileReader textFileReader;
    private static String
            DATA_FILE_PATH = "",
            RESULT_FILE_PATH = "",
            RESULT_FILE_PREFIX = "";
    private static int
            CRITICAL_VALUE = 0,
            SKIP = 0;

    private JTextField dataPathInputField;
    private JButton readDataButton;

    private JTextField criticalValueInputField;
    private JTextField skipNumInputField;
    private JButton connectingButton;

    private JTextField outputPathInputField;
    private JTextField prefixInputField;
    private JButton saveDataButton;

    private STLAnaylzerUI(){
        initUI();
    }

    private void initUI() {
        setSize(new Dimension(800, 800));
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new STLAnaylzerUI();
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\sample1.txt
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\sheet_1_100_100.txt
        //C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\small_packing_0_0005volper_cube.stl
        //‪C:\Users\hongf\Google Drive\Career\Kathy Lu\raw data\mxene01_100_200430.stl (multiple sheets)


        /*
        DATA_FILE_PATH = args[0];
        RESULT_FILE_PATH = args[1];
        RESULT_FILE_PATH = args[2];
        CRITICAL_VALUE = Integer.parseInt(args[3]);
        SKIP = Integer.parseInt(args[4]);
	    textFileReader = new TextFileReader(SKIP, DATA_FILE_PATH);
	    List<Triangle> triangles = textFileReader.getTrianlges();

        System.out.println("number of triangles: " + triangles.size());
        TriangleProcessor processor = new TriangleProcessor();
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
        Scanner input = new Scanner(System.in);
        System.out.println("press y to continue, any other input to exit: ");
        String choice = input.nextLine();
        if(!choice.equals("y")){
            return;
        }

        List<Set<Triangle>> groups = (new Connector()).getConnectedGroups(triangles, CRITICAL_VALUE);
        System.out.println("group count: " + groups.size());
        TextFileWriter textFileWriter = new TextFileWriter();
        textFileWriter.generateGroupTextFile(RESULT_FILE_PREFIX, groups, RESULT_FILE_PATH);
         */
    }


}
