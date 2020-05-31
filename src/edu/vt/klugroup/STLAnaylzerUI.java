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

public class STLAnaylzerUI extends JFrame{
    private static TextFileReader textFileReader;

    private static int
            FRAME_WIDTH = 800,
            FRAME_HEIGHT = 600;

    private List<Triangle> triangles;
    private List<Set<Triangle>> groups;

    private Container mainContainer;

    private JTextField skipNumInputField;
    private JButton readDataButton;

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

        skipNumInputField = new JTextField();
        skipNumInputField.setText("1");
        skipNumInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 4, FRAME_HEIGHT / 15));

        readDataButton = new JButton("read data");
        readDataButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 5, FRAME_HEIGHT / 15));

        criticalValueInputField = new JTextField();
        criticalValueInputField.setText("1");
        criticalValueInputField.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 4, FRAME_HEIGHT / 15));

        connectingButton = new JButton("connect");
        connectingButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 5, FRAME_HEIGHT / 15));

        saveDataButton = new JButton("save");
        saveDataButton.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15));

        logArea = new JTextArea();
        logArea.setPreferredSize(new Dimension(FRAME_WIDTH / 10 * 9, FRAME_HEIGHT / 15 * 4));
    }

    private void assembleUI() {

        getContentPane().add(mainContainer);
        mainContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainContainer.add(skipNumInputField);
        mainContainer.add(readDataButton);

        mainContainer.add(criticalValueInputField);
        mainContainer.add(connectingButton);

        mainContainer.add(saveDataButton);

        mainContainer.add(logArea);
    }

    private void initActions() {
        readDataButton.addActionListener(e -> {
            String path = getRawDataFilePath();
            if (path.length() == 0) {
                System.out.println("empty");
            } else {
                try {
                    int skipNumber = Integer.parseInt(skipNumInputField.getText());
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
            String[] outputInfo = getOutputPathAndFilename();
            try {
                saveData(outputInfo[0], outputInfo[1]);
            } catch (IOException ex) {
                ex.printStackTrace();
                logArea.append(ex + "\n");
            }
        });

    }

    private String[] getOutputPathAndFilename() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int r = jFileChooser.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION){
            // set the label to the path of the selected file
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

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new STLAnaylzerUI();
            }
        });

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
