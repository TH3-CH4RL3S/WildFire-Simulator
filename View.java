import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class View extends JFrame implements Observer {
    private JPanel gridPanel;
    private JPanel buttonPanel;
    private static JLabel label5;
    private static JLabel label1;
    private static JLabel label2;
    private static JLabel label3;
    private static JLabel label4;
    private static JLabel label6;
    private static JLabel label7;
    

    private static String currentRunDirectory;


    public View(int size) {
        // Create the main frame
        this.setSize(700, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold everything with FlowLayout
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(mainPanel, BorderLayout.NORTH);

        // Create a panel to hold the gridPanel with BorderLayout
        JPanel gridHolderPanel = new JPanel(new BorderLayout());
        gridHolderPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Red border around gridPanel

        // Create the grid panel
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(size, size)); // Layout based on grid size
        gridPanel.setPreferredSize(new Dimension(500, 500)); // Set size to 500x500
        gridHolderPanel.add(gridPanel, BorderLayout.WEST); // Add gridPanel to the left side of gridHolderPanel

        // Add gridHolderPanel to mainPanel
        mainPanel.add(gridHolderPanel);

        // Create a panel for labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(7, 1, 0, 10)); // 3 rows, 1 column
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Add space to the left of labels
        mainPanel.add(labelPanel); // Add label panel to the main panel

       // Add labels to the label panel
       label5 = new JLabel("Generation: " + DataExtractor.getCurrentGeneration());
       label1 = new JLabel("Trees: " + DataExtractor.getForestCells());
       label2 = new JLabel("Burning cells: " + DataExtractor.getBurningCells());
       label3 = new JLabel("Smoldering cells: " + DataExtractor.getSmolderCells());
       label4 = new JLabel("Burnt cells: " + DataExtractor.getBurntCells());
       label6 = new JLabel("Windspeed: " + App.getWindSpeed());
       label7 = new JLabel("Wind direction: " + App.getWindDirection());
       labelPanel.add(label7);
       labelPanel.add(label6);
       labelPanel.add(label5);
       labelPanel.add(label1);
       labelPanel.add(label2);
       labelPanel.add(label3);
       labelPanel.add(label4);

        // Create the button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom (south) of the frame

        // Add three buttons to the button panel
        JButton button1 = new JButton("Pause");
        JButton button2 = new JButton("Stop");
        
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        

        //JButton startSimulation = new JButton("Start simulation");
        //button1.add(startSimulation);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Model.getRunning()) {
                    Model.setRunning(false);
                    button1.setText("Resume"); 
                }

                else {
                    Model.setRunning(true);
                    button1.setText("Pause");
                }
                             
            }
        });

        //Stop
        /* 
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Model.setRunning(false);
                String summaryFileName = currentRunDirectory + File.separator + "summary.txt";
                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(summaryFileName, true)))) {
                    writer.printf("Generation %d: Forest %d, Farmland %d, Smolder %d, Burning %d, Burnt %d%n",currentGeneration - 1, forestCells, farmCells, smolderCells, burningCells, burntCells);
                } catch (IOException e) {
                    System.err.println("Error writing to summary file: " + e.getMessage());
                }

                System.exit(1);
                }


        }); */

        this.setVisible(true);
    }

    public static void updateData(){
        label5.setText("Generation: " + DataExtractor.getCurrentGeneration());
        label1.setText("Trees: " + DataExtractor.getForestCells());;
        label2.setText("Burning cells: " + DataExtractor.getBurningCells());
        label3.setText("Smoldering cells: " + DataExtractor.getSmolderCells());
        label4.setText("Burnt cells: " + DataExtractor.getBurntCells());
    }

    @Override
    public void update(Cell[][] grid) {
        gridPanel.removeAll();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                gridPanel.add(cell);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}