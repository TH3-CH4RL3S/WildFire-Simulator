import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private CellState state; // Use the enum type
    private int burningDuration;
    public Cell() {
        this.state = CellState.FOREST; // Set initial state using the enum
        setPreferredSize(new Dimension(10, 10));
    }

    public void setState(CellState state) {
        if (this.state != state) {
            this.state = state;
            if (state == CellState.BURNING) {
                this.burningDuration = 0; // Reset if state changes to BURNING
            }
        }
        repaint(); // Repaint the cell to reflect the updated state
    }

    public CellState getState() {
        return state;
    }
     
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(state.getColor()); // Use the color associated with the state
        g.fillRect(0, 0, getWidth(), getHeight()); // Fill the cell with the chosen color
    }

    public int getBurningDuration() {
        return burningDuration;
    }

    public void incrementBurningDuration() {
        if (this.state == CellState.BURNING) {
            this.burningDuration++;
        }
    }

    public void resetBurningDuration() {
        this.burningDuration = 0;
    }
}
