import java.awt.*;

public enum CellState {
    FOREST(Color.GREEN),
    ROCK(Color.GRAY),
    WATER(Color.BLUE),
    FARMLAND(Color.YELLOW),
    SMOLDER(Color.ORANGE),
    BURNING(Color.RED),
    BURNT(Color.BLACK);

    private final Color color;

    CellState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static CellState fromId(int id) {
        switch (id) {
            case 1: return FOREST;
            case 2: return SMOLDER;
            case 3: return BURNING;
            case 4: return ROCK;
            case 5: return WATER;
            case 6: return FARMLAND;
            case 7: return BURNT;
            default: throw new IllegalArgumentException("Unknown CellState ID");
        }
    }
}