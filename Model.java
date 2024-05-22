import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Model implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private static Cell[][] grid;
    private int size; // Size of the grid (assumed to be square)
    private Random random; // Random number generator
    private DataExtractor dataExtractor;

    static double windSpeed; // Wind speed
    static int windX; //Wind direction in X 
    static int windY; // Wind direction in Y

    static private Boolean running = false;


    public Model(int size, String inputFile) {
        
        this.size = size;
        grid = new Cell[size][size]; 
        random = new Random();
        dataExtractor = new DataExtractor();
        running = true;

        if(inputFile == "error")
        {
            System.out.println("Error with inputfile..");
        }

        else if(inputFile == "from_text")
        {
            grid = DataExtractor.readFromFile(App.getFilePath(),size);
        }

        else
        {
            initializeGrid(size, inputFile); // Initialize grid upon creation
        }

        notifyObservers(); 

    }
    public static void setRunning(Boolean flag) {
        running = flag;
    }

    public static Boolean getRunning() {
        return running;
    }

    public static void setWindSpeed(double wSpeed)
    {
        windSpeed = wSpeed;
        //System.out.println("Recived speed in Model");
        //System.out.println(wSpeed);
        
    }

    public static void setWindDirection(String direction)
    {
        //System.out.println("Recived direction in Model");
        //System.out.println(direction);
        switch(direction)
        {
            case "N":
                windX = -1;
                windY = 0;
                break;
            case "NE":
                windX = -1;
                windY = 1;
                break;

            case "E":
                windX = 0;
                windY = 1;
                break;

            case "SE":
                windX = 1;
                windY = 1;
                break;

            case "S":
                windX = 1;
                windY = 0;
                break;

            case "SW":
                windX = 1;
                windY = -1;
                break;

            case "W":
                windX = 0;
                windY = -1;
                break;

            case "NW":
                windX = -1;
                windY = -1;
                break;

        }

        //System.out.println("X" + windX);
        //System.out.println("Y" + windY);

    }

    public double getWindSpeed()
    {
        return windSpeed;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(grid.clone()); // Consider cloning for immutability
        }
    }

    @Override
    public void setData(Cell[][] otherGrid) {
        grid = otherGrid;
        notifyObservers();
    }

    // Initializes the grid with cells
    private void initializeGrid(int size, String inputFile) {
        grid = ImageToArray.getInitArray(size, inputFile);
    }



    public static void setZero(Cell[][] grid) {
        System.out.println("hello");
        for (int i = 0; i < grid.length; i++) {
           for (int j = 0; j < grid[i].length; j++) {
               grid[i][j] = new Cell();
           }
       }   
   }

    public void setIgnitionPoint(int x, int y) {
        grid[y][x].setState(CellState.BURNING);
        notifyObservers();
    }

    public void simulateUpdates() {
        setIgnitionPoint(20, 20);
        while(true) {
            System.out.println(" ");
        while(running) {
            update();
            try {
                Thread.sleep(400); // Delay
            } catch (InterruptedException e) {
                System.out.println("Interrupted during simulation: " + e.getMessage());
                break;
            }
        }
    }
        
    }

    // Simulates updates to the grid, can be called to start the simulation
    public void update() {
    CellState[][] newGrid = new CellState[size][size]; // Prepare a new grid state
     
    // Fill newGrid based on current grid states and rules
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            CellState currentState = grid[i][j].getState();
            boolean hasNeighborOne = false;
            double additionalRisk = 0.0;


            // Check all neighbors for state ON
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (di == 0 && dj == 0) continue; // Skip the cell itself
                    int ni = i + di;
                    int nj = j + dj;
                    if (isValidCell(ni, nj) && grid[ni][nj].getState() == CellState.BURNING) {
                        hasNeighborOne = true;

                        if (windX == di && windY == dj)
                            additionalRisk = 0.1 * windSpeed;
                        
                        else if (-windX == di && -windY == dj) {
                             // Check opposite cell
                            additionalRisk = -0.2 * windSpeed;
                        }
        
                    break;
                    }
                }
                if (hasNeighborOne) break;
            }
            switch (currentState) {
                case FOREST:
                    if((hasNeighborOne && random.nextDouble() < (0.3 + additionalRisk)))
                        newGrid[i][j] = CellState.SMOLDER;
                    else {  
                        newGrid[i][j] = CellState.FOREST;
                    }  
                    break;

                case SMOLDER:
                    if (random.nextDouble() < (0.2 + additionalRisk))
                        newGrid[i][j] = CellState.BURNING;
                    else
                        newGrid[i][j] = CellState.SMOLDER;
                    break;
                
                case BURNING:
                    grid[i][j].incrementBurningDuration();
                    if (grid[i][j].getBurningDuration() >= 50) {
                        newGrid[i][j] = CellState.BURNT;
                    }
                    else {
                        newGrid[i][j] = CellState.BURNING;
                    }
                    break;

                case ROCK:
                    newGrid[i][j] = CellState.ROCK;
                    break;

                case WATER:
                    newGrid[i][j] = CellState.WATER;
                    break;

                case FARMLAND:
                    if((hasNeighborOne && random.nextDouble() < 0.9))
                        newGrid[i][j] = CellState.BURNING;
                    else {  
                        newGrid[i][j] = CellState.FARMLAND;
                     }  
                    break;

                case BURNT:
                    newGrid[i][j] = CellState.BURNT;
                    break;
            }
        }
        }

        // Apply the new states to the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].setState(newGrid[i][j]);
            }
        }

        notifyObservers();
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
