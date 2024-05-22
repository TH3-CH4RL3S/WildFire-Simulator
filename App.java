import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

public class App {

    static JFrame frame;

    static JButton austButton;
    static JButton lapplandButton;
    static JButton caliButton;
    static JButton smallButton1;
    static JButton smallButton2;
    
    static int chosenMap = -1;
    static String imagePath;
    static String filePath;
    private static String selectedFolderPath = "C:/Users/charl/Desktop/System-ochprogramvaruutveckling/test2/WildfireSim/images";

    static String windDirection = "null";
    static double windSpeed = -1.0;

    public static double getWindSpeed(){
        return windSpeed;
    }
    public static String getWindDirection(){
        return windDirection;
    }

    public static int getMap(){
        return chosenMap;
    }

    public static String getImagePath(){
        return imagePath;
    }

    public static String getFilePath(){
        return filePath;
    }

    static void chooseMap() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Top Panel
        JPanel startTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startTextPanel.setBackground(Color.white);
        startTextPanel.setPreferredSize(new Dimension(800, 100));

        JLabel welcomeLabel = new JLabel("Welcome to our simulator, choose a map: ");
        welcomeLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        startTextPanel.add(welcomeLabel);
        mainPanel.add(startTextPanel);

        // Middle Panel
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        imagePanel.setBackground(Color.white);
        imagePanel.setPreferredSize(new Dimension(800, 250));

        // Bottom Panel
        JPanel smallButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // Adjust horizontal gap
        smallButtonsPanel.setBackground(Color.white);
        smallButtonsPanel.setPreferredSize(new Dimension(800, 100));

        // Generate from text
        JPanel smallButtonPanel1 = new JPanel(new BorderLayout());
        JLabel smallButtonDescLabel1 = new JLabel("Generate from text");
        smallButtonDescLabel1.setFont(new Font("Verdana", Font.PLAIN, 15));

        smallButton1 = new JButton("Choose file");
        smallButton1.setPreferredSize(new Dimension(100, 50));
        smallButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapChosen(4); // Using new index
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Store the path of the selected file
                    filePath = selectedFile.getAbsolutePath();            
                    smallButton1.setText("Success!");
                }
            }
        });
        smallButtonPanel1.add(smallButton1, BorderLayout.CENTER);
        smallButtonPanel1.add(smallButtonDescLabel1, BorderLayout.SOUTH);

        //Upload your own map:
        
        // Small Button 2
        JPanel smallButtonPanel2 = new JPanel(new BorderLayout());
        JLabel smallButtonDescLabel2 = new JLabel("Generate from image");
        smallButtonDescLabel2.setFont(new Font("Verdana", Font.PLAIN, 15));
        smallButton2 = new JButton("Choose image");
        smallButton2.setPreferredSize(new Dimension(100, 50));
        smallButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapChosen(5);
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Store the path of the selected image file
                    imagePath = selectedFile.getAbsolutePath();

                    // Move the uploaded image to the selected folder
                    moveImageToFolder();
                    smallButton2.setText("Success!");

                }

                else
                {
                smallButton2.setText("Failed..");

                }
            }
        });

        smallButtonPanel2.add(smallButton2, BorderLayout.CENTER);
        smallButtonPanel2.add(smallButtonDescLabel2, BorderLayout.SOUTH);
        
        // Add small buttons to the panel
        smallButtonsPanel.add(smallButtonPanel1);
        smallButtonsPanel.add(smallButtonPanel2);

        mainPanel.add(smallButtonsPanel);
        
        // Panel for each image button and its text
        JPanel caliPanel = new JPanel(new BorderLayout());
        JLabel caliDescLabel = new JLabel("California");
        caliDescLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        caliButton = new JButton(new ImageIcon("WildfireSim/images/california.jpg"));
        caliButton.setPreferredSize(new Dimension(200, 200));


        caliButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapChosen(1);
            }
        });

        caliPanel.add(caliButton, BorderLayout.CENTER);
        caliPanel.add(caliDescLabel, BorderLayout.SOUTH);

        JPanel lapplandPanel = new JPanel(new BorderLayout());
        JLabel lapplandDescLabel = new JLabel("Lappland");
        lapplandDescLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        lapplandButton = new JButton(new ImageIcon("WildfireSim/images/lappland.jpg"));
        lapplandButton.setPreferredSize(new Dimension(200, 200));
        lapplandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapChosen(2);
            }
        });
        lapplandPanel.add(lapplandButton, BorderLayout.CENTER);
        lapplandPanel.add(lapplandDescLabel, BorderLayout.SOUTH);

        JPanel austPanel = new JPanel(new BorderLayout());
        JLabel austDescLabel = new JLabel("Australia");
        austDescLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        austButton = new JButton(new ImageIcon("WildfireSim/images/Australia.jpg"));
        austButton.setPreferredSize(new Dimension(200, 200));
        austButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapChosen(3);
            }
        });
        austPanel.add(austButton, BorderLayout.CENTER);
        austPanel.add(austDescLabel, BorderLayout.SOUTH);

        // Add each image button and its text panel to the main image panel
        imagePanel.add(caliPanel);
        imagePanel.add(lapplandPanel);
        imagePanel.add(austPanel);

        mainPanel.add(imagePanel);

        // Bottom Panel
        JPanel weatherPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Adjust horizontal and vertical gaps
        weatherPanel.setBackground(Color.white);
        weatherPanel.setPreferredSize(new Dimension(800, 100));

        JLabel enterLabel = new JLabel("Enter wind speed: ");
        JTextField textField = new JTextField(10); // Adjust column count to change width

        weatherPanel.add(enterLabel);
        weatherPanel.add(textField);

        JLabel directionLabel = new JLabel("Wind direction: ");
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        JComboBox<String> directionComboBox = new JComboBox<>(directions);

        weatherPanel.add(directionLabel);
        weatherPanel.add(directionComboBox);

        directionComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                windDirection = (String) directionComboBox.getSelectedItem();
            }
        });

        mainPanel.add(weatherPanel);

        // "Start simulation" Button Panel
        JPanel startButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButtonPanel.setBackground(Color.white);
        startButtonPanel.setPreferredSize(new Dimension(800, 50));

        JButton startSimulation = new JButton("Start simulation");
        startButtonPanel.add(startSimulation);

        startSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                windSpeed = Double.parseDouble(textField.getText());
                // Call simulatorFrame(choosenMap, windDirection, windspeed);
                Model.setWindSpeed(windSpeed);
                Model.setWindDirection(windDirection);
                Model.setRunning(true);

               /* System.out.println(App.chosenMap);
                System.out.println(App.windSpeed);
                System.out.println(App.windDirection);
                */
                frame.dispose();
            }
        });

        mainPanel.add(startButtonPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    static void mapChosen(int map) {
        austButton.setBorder(null);
        lapplandButton.setBorder(null);
        caliButton.setBorder(null);
        smallButton1.setBorder(null);
        smallButton2.setBorder(null);

        if (map == 1) {
            caliButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            chosenMap = 1;
        } else if (map == 2) {
            lapplandButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            chosenMap = 2;
        } else if (map == 3) {
            austButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            chosenMap = 3;
        } else if (map == 4) {
            smallButton1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            chosenMap = 4;
        } else if (map == 5) {
            smallButton2.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            chosenMap = 5;
        }
        
    }


        private static void moveImageToFolder() {
        // Check if an image path is selected
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Get the source image file path
                Path sourcePath = Paths.get(imagePath);
                // Get the destination folder path
                Path destinationFolder = Paths.get(selectedFolderPath);
                // Move the image file to the destination folder
                Files.move(sourcePath, destinationFolder.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image moved to: " + destinationFolder.resolve(sourcePath.getFileName()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No image to move.");
        }
    }

    public static void main(String[] args) {
        chooseMap();
        while(!Model.getRunning())
        {   
            //waiting lounge
            System.out.println("Inside");
            System.out.println("x");
        }

        int gridSize = 100;
        int map = getMap();

        String map_source = "error"; //Default

        if(map > 0 && map <= 3)
        {
            // Lappland, Cali or Australia
            switch (map) {
                case 1:
                    map_source = "WildfireSim/images/cali_map.png"; //Cali
                    break;
                case 2:
                    map_source = "WildfireSim/images/swe_map.png"; //Lappland
                    break;
                case 3:
                    map_source = "WildfireSim/images/aus_map.png"; //Australia
                    break;                              
            }
        }

        else if(map == 4)
        {
            //Load from text log
            map_source = "from_text";
        }

        else if(map == 5)
        {
            // Egen vald map vald för att ladda upp
            //System.out.println("Egen bild vald..");
            String inputFile = getImagePath();
            System.out.println("Image Path: " + imagePath);
            map_source = "WildfireSim/test_egen_vald_map.png";
            ImageResizer.Converter(gridSize, inputFile , map_source);
        }
        
        Subject model = new Model(gridSize, map_source);
        Observer view = new View(gridSize);
        Observer dataExtractor = new DataExtractor();
        //Cell cell = new Cell();

        model.addObserver(view);
        model.addObserver(dataExtractor);

        // Start the simulation of grid updates in the model
        ((Model)model).simulateUpdates(); 
            
    }
}