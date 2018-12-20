package parking_manager_agent.util;

public class WindowsCounter {

    private static WindowsCounter ourInstance = new WindowsCounter();
    private int counter;

    private WindowsCounter() {
        counter = 0;
    }

    public static WindowsCounter getInstance() {
        return ourInstance;
    }

    public void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}
