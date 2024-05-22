import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataExtractor implements Observer {
    private Cell[][] grid;
    private static String currentRunDirectory;
    private static int forestCells = 0;
    private static int smolderCells = 0;
    private static int burningCells = 0;
    private static int farmCells = 0;
    private static int burntCells = 0;
    private static int currentGeneration = 0;

    public static int getForestCells(){
        return forestCells;
    }
    public static int getSmolderCells(){
        return smolderCells;
    }
    public static int getBurningCells(){
        return burningCells;
    }
    public static int getFarmCells(){
        return farmCells;
    }
    public static int getBurntCells(){
        return burntCells;
    }
    public static int getCurrentGeneration(){
        return currentGeneration;
    }
    
    @Override
    public void update(Cell[][] grid) {
        this.grid = grid;
        if (currentRunDirectory == null) {
            createRunDirectory();
        }
        writeToFile();
        writeSummaryToFile();
        View.updateData();
    }


    private void createRunDirectory() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss");
        currentRunDirectory = "Run_" + now.format(formatter);
        try {
            Files.createDirectories(Paths.get(currentRunDirectory));
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }

    private void writeToFile() {
        String fileName = currentRunDirectory + File.separator + currentGeneration + ".txt";
        currentGeneration++;;
        CellState currentState;

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {

            forestCells = 0;
            smolderCells = 0;
            burningCells = 0;
            farmCells = 0;
            burntCells = 0;

            for (Cell[] row : grid) {
                for (Cell cell : row) {
                    currentState = cell.getState();
                    switch (currentState) {
                        case FOREST:
                            writer.print("1 ");
                            forestCells++; 
                            break;
        
                        case SMOLDER:
                            writer.print("2 ");
                            smolderCells++; 
                            break;
                        
                        case BURNING:
                            writer.print("3 ");
                            burningCells++; 
                            break;
        
                        case ROCK:
                            writer.print("4 ");  
                            break;
                           
                        case WATER:
                            writer.print("5 ");         
                            break;
        
                        case FARMLAND:
                            writer.print("6 ");
                            farmCells++; 
                            break;
                            
                        case BURNT:
                            writer.print("7 ");
                            burntCells++; 
                            break; 
                    }
                }
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void writeSummaryToFile() {
        String summaryFileName = currentRunDirectory + File.separator + "summary.txt";
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(summaryFileName, true)))) {
            writer.printf("Generation %d: Forest %d, Farmland %d, Smolder %d, Burning %d, Burnt %d%n",currentGeneration - 1, forestCells, farmCells, smolderCells, burningCells, burntCells);
        } catch (IOException e) {
            System.err.println("Error writing to summary file: " + e.getMessage());
        }
    }

    public static Cell[][] readFromFile(String fileName, int size) {
        Cell[][] grid = new Cell[size][size];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < size) {
                String[] values = line.trim().split("\\s+");
                for (int col = 0; col < Math.min(values.length, size); col++) {
                    int cellStateId = Integer.parseInt(values[col]);
                    grid[row][col] = new Cell();
                    grid[row][col].setState(CellState.fromId(cellStateId));
                }
                row++;
            }

            // Find the correct generation string
            int lastIndex = fileName.lastIndexOf('\\');
            // Extract the part after the last backslash
            String lastPart = fileName.substring(lastIndex + 1);
            //System.out.println("Last part: " + lastPart); // Outputs: 17
            String croppedFileName = lastPart.replace(".txt", "");
            currentGeneration = Integer.parseInt(croppedFileName);
            //System.out.println(currentGeneration);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid format in file: " + e.getMessage());
        }
        return grid;
    }
}
