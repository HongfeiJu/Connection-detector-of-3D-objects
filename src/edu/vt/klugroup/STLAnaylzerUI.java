package edu.vt.klugroup;

import edu.vt.klugroup.io.TextFileReader;
import edu.vt.klugroup.io.TextFileWriter;
import edu.vt.klugroup.models.Triangle;
import edu.vt.klugroup.models.Vertex;
import edu.vt.klugroup.processor.Connector;
import edu.vt.klugroup.processor.TriangleProcessor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Main class for launching the application
 * @author Hongfei Ju
 * @version 1.0
 *
 */

public class STLAnaylzerUI extends JFrame{
    private static TextFileReader textFileReader;

    private static int
            FRAME_WIDTH = 800,
            FRAME_HEIGHT = 600;

    private List<Triangle> triangles;
    private List<Set<Triangle>> groups;

    private Container mainContainer;

    private JLabel skipNumLabel;
    private JTextField skipNumInputField;
    private JButton readDataButton;

    private JLabel criticalValueLabel;
    private JTextField criticalValueInputField;
    private JButton connectingButton;

    private JButton saveDataButton;

    private JTextArea logArea;

    private STLAnaylzerUI(){
        initUI();
        assembleUI();
        initActions();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea.append("Welcome!\n");
    }

    private void initUI() {
        setTitle("3D object connector");
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        //mainContainer = new JPanel(new GridLayout(10,1,0,5));
        mainContainer = new Container();

        skipNumLabel = new JLabel("skip number", SwingConstants.CENTER);
        skipNumLabel.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 2, FRAME_HEIGHT / 15));
        skipNumLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

        skipNumInputField = new JTextField();
        skipNumInputField.setText("1");
        skipNumInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 2, FRAME_HEIGHT / 15));
        skipNumInputField.setFont(new Font("Verdana", Font.PLAIN, 20));

        readDataButton = new JButton("read data");
        readDataButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 5, FRAME_HEIGHT / 15));
        readDataButton.setFont(new Font("Verdana", Font.PLAIN, 20));

        criticalValueLabel = new JLabel("critical value", SwingConstants.CENTER);
        criticalValueLabel.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 2, FRAME_HEIGHT / 15));
        criticalValueLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

        criticalValueInputField = new JTextField();
        criticalValueInputField.setText("1");
        criticalValueInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 2, FRAME_HEIGHT / 15));
        criticalValueInputField.setFont(new Font("Verdana", Font.PLAIN, 20));

        connectingButton = new JButton("connect");
        connectingButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 5, FRAME_HEIGHT / 15));
        connectingButton.setFont(new Font("Verdana", Font.PLAIN, 20));

        saveDataButton = new JButton("save");
        saveDataButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15));
        saveDataButton.setFont(new Font("Verdana", Font.PLAIN, 20));

        logArea = new JTextArea();
        logArea.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15 * 10));
        logArea.setFont(new Font("Verdana", Font.PLAIN, 15));
    }

    private void assembleUI() {

        getContentPane().add(mainContainer);
        mainContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainContainer.add(skipNumLabel);
        mainContainer.add(skipNumInputField);
        mainContainer.add(readDataButton);

        mainContainer.add(criticalValueLabel);
        mainContainer.add(criticalValueInputField);
        mainContainer.add(connectingButton);

        mainContainer.add(saveDataButton);

        mainContainer.add(logArea);
    }

    private void initActions() {
        readDataButton.addActionListener(e -> {
            try {
                String path = getRawDataFilePath();
                int skipNumber = Integer.parseInt(skipNumInputField.getText());
                readData(path, skipNumber);
            } catch (IOException ex) {
                ex.printStackTrace();
                logArea.append(ex + "\n");
            }
            getTriangleInfo();
        });

        connectingButton.addActionListener(e -> {
            try {
                float criticalValue = Float.parseFloat(criticalValueInputField.getText());
                connect(criticalValue);
            } catch (Exception ex) {
                ex.printStackTrace();
                logArea.append(ex + "\n");
            }

        });

        saveDataButton.addActionListener(e -> {
            try {
                String[] outputInfo = getOutputPathAndFilename();
                saveData(outputInfo[0], outputInfo[1]);
            } catch (IOException ex) {
                ex.printStackTrace();
                logArea.append(ex + "\n");
            }
        });

    }


    private void readData(String path, int skipNumber) throws IOException {
        textFileReader = new TextFileReader(skipNumber, path);
        logArea.append("\nstart reading ...\n");
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


    private String[] getOutputPathAndFilename() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int r = jFileChooser.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION){
            String path = jFileChooser.getCurrentDirectory().getAbsolutePath(),
                    filename = jFileChooser.getSelectedFile().getName();

            System.out.println(path + " " + filename);
            return new String[]{path, filename};
        }
        return null;
    }

    private String getRawDataFilePath() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int r = jFileChooser.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION){
            // set the label to the path of the selected file
            return jFileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    private void connect(float criticalValue) {
        logArea.append("\nstart analyzing\n");
        groups = (new Connector()).getConnectedGroups(triangles, criticalValue);
        System.out.println("group count: " + groups.size());
        logArea.append("get " + groups.size() + " groups\n");
    }

    private void saveData(String outputPath, String prefix) throws IOException {
        TextFileWriter textFileWriter = new TextFileWriter();
        logArea.append("\nsaving data ...\n");
        textFileWriter.generateGroupTextFile(prefix, groups, outputPath);
        logArea.append("saving completed!\n");
    }

    public static void main(String[] args) throws IOException {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new STLAnaylzerUI();
            }
        });

    }

}
