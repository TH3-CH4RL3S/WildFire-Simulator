
interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    void setData(Cell[][] data); // Accepts a grid of Cell objects
}
