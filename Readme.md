Connection detector of 3D shape
======
**Connection detector of 3D shape** is a tool for detecting the connected triangle groups in STL file. For any two triangles, if the distance between them (based on centroid) is less than a critical value, they are thought of as being connected and will put into the same connected group. The program can read the data from a STL file and detect the connnected groups. The data of each connected group can be write into a new STL file. This software is developed based on the requirements of [Dr. Kathy Lu]() at Virginia Tech.

#### Screenshot
![Screenshot software](https://github.com/HongfeiJu/Connection-detector-of-3D-objects/blob/master/screenshot/user_interface.PNG "user interface")

## Download
* [Version 1.0](https://github.com/HongfeiJu/Connection-detector-of-3D-objects/tree/master/out/artifacts/Connection_detector_of_3D_objects_jar)

## License 
* see [MIT LICENSE](https://github.com/HongfeiJu/Connection-detector-of-3D-objects/blob/master/LICENSE) file

## Version 
* Version 1.0

## Install
* Prerequisite: install Java 8 on your computer
* download the jar file from the [link](https://github.com/HongfeiJu/Connection-detector-of-3D-objects/tree/master/out/artifacts/Connection_detector_of_3D_objects_jar)

## How to use
* double click the jar file
* set the skip number (if the data file is too large and beyong the capacity of your computer, you can skip some triangles and the program will treat serveral triangles as one triangle. For example, if the skip number is 3, the program will try three triangles as one. Note: it may comprise the accuracy of the results, don't change the default number unless it is really necessary).
* click the read data button and select the raw data file.
* set the critical value (distance) and click the analyze button.
* click the save button and select target directory and set the prefix of the output files.
Note: the related info will be shown on log pane when every step is executed

#### Developer/Company
* Hongfei Ju
* Homepage: https://www.linkedin.com/in/hongfei-ju-47b2a5108/
