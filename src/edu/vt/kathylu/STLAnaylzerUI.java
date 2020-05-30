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
            SKIP = 0,
            FRAME_WIDTH = 800,
            FRAME_HEIGHT = 600;

    private List<Triangle> triangles;
    private List<Set<Triangle>> groups;

    private JPanel mainPanel;
    private JPanel readPanel;
    private JPanel analyzePanel;
    private JPanel writePanel;

    private JTextField dataPathInputField;
    private JTextField skipNumInputField;
    private JButton readDataButton;

    private JTextField criticalValueInputField;
    private JButton connectingButton;

    private JTextField outputPathInputField;
    private JTextField prefixInputField;
    private JButton saveDataButton;

    private JTextArea logArea;

    private STLAnaylzerUI(){
        initUI();
        assembleUI();
        initActions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        logArea.append("Welcome!\n");
    }

    private void initUI() {
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        //mainPanel = new JPanel(new GridLayout(10,1,0,5));
        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        dataPathInputField = new JTextField();
        dataPathInputField.setText("raw data file path");
        dataPathInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 8, FRAME_HEIGHT / 15));

        skipNumInputField = new JTextField();
        skipNumInputField.setText("skip number");
        skipNumInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 1, FRAME_HEIGHT / 15));

        readDataButton = new JButton("read data");
        readDataButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15));

        criticalValueInputField = new JTextField();
        criticalValueInputField.setText("critical value");
        criticalValueInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 1, FRAME_HEIGHT / 15));

        connectingButton = new JButton("connect");
        connectingButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15));

        outputPathInputField = new JTextField();
        outputPathInputField.setText("output file path");
        outputPathInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 8, FRAME_HEIGHT / 15));

        prefixInputField = new JTextField();
        prefixInputField.setText("prefix");
        prefixInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 1, FRAME_HEIGHT / 15));
        saveDataButton = new JButton("save");
        saveDataButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15));

        logArea = new JTextArea();
        logArea.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15 * 4));
    }

    private void assembleUI() {

        getContentPane().add(mainPanel);

        mainPanel.add(dataPathInputField);
        mainPanel.add(skipNumInputField);
        mainPanel.add(readDataButton);

        mainPanel.add(criticalValueInputField);
        mainPanel.add(connectingButton);

        mainPanel.add(outputPathInputField);
        mainPanel.add(prefixInputField);
        mainPanel.add(saveDataButton);

        mainPanel.add(logArea);
    }

    private void initActions() {
        readDataButton.addActionListener(e -> {
            String path = dataPathInputField.getText();
            int skipNumber = Integer.parseInt(skipNumInputField.getText());
            if (path.length() == 0) {
                System.out.println("empty");
            } else {
                try {
                    readData(path, skipNumber);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    logArea.append(ex + "\n");
                }
            }
            getTriangleInfo();
        });

        connectingButton.addActionListener(e -> {
            float criticalValue = Float.parseFloat(criticalValueInputField.getText());
            try {
                connect(criticalValue);
            } catch (Exception ex) {
                ex.printStackTrace();
                logArea.append(ex + "\n");
            }

        });

        saveDataButton.addActionListener(e -> {
            String outputPath = outputPathInputField.getText(), prefix = prefixInputField.getText();
            try {
                saveData(outputPath, prefix);
            } catch (IOException ex) {
                ex.printStackTrace();
                logArea.append(ex + "\n");
            }
        });

    }

    private void saveData(String outputPath, String prefix) throws IOException {
        TextFileWriter textFileWriter = new TextFileWriter();
        logArea.append("saving data ...\n");
        textFileWriter.generateGroupTextFile(prefix, groups, outputPath);
        logArea.append("saving completed!\n");
    }

    private void connect(float criticalValue) {
        logArea.append("start analyzing\n");
        groups = (new Connector()).getConnectedGroups(triangles, criticalValue);
        System.out.println("group count: " + groups.size());
        logArea.append("get " + groups.size() + " groups\n");
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

    private void readData(String path, int skipNumber) throws IOException {
        textFileReader = new TextFileReader(skipNumber, path);
        logArea.append("start reading ...\n");
        triangles = textFileReader.getTrianlges();
        System.out.println("got " + triangles.size() + " triangles");
        logArea.append("got " + triangles.size() + " triangles\n");
    }

    private void getTriangleInfo(){
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
        logArea.append("average edge length: " + sum/count + "    min edge length: " + min + "    max edge length: " + max);
        System.out.println("average edge length: " + sum/count + "    min edge length: " + min + "    max edge length: " + max);
    }

}
