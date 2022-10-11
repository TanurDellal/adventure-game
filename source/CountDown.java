import java.io.Serializable;
import java.util.Timer; 
import java.util.TimerTask;

// A countdown class to create timers. At the moment it provides an on-edge experience by time-constricting the player's input. 
public class CountDown implements Serializable {

    private final int timeDelay = 1000; // time in milliseconds till the timer starts (first execution of 'run()')
    private final int timePeriod = 1000; // time in milliseconds between each countdown (each execution of 'run()')
    private int counter = 6; 
    transient private Timer timer; // Use transient to avoid serialization (can't serialize Timer)


    public CountDown() {

        timer();
    }

    public void timer() {
        
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() { // Make the timer print the countdown with 1 second delay and period.
    
            public void run() {

                System.out.println(Seconds());
            }
        }, timeDelay, timePeriod);
    }
    
    private final int Seconds() { // Return the seconds to be printed as part of the countdown. Stop the timer when it prints 0.

        if (counter == 1) {

            timer.cancel();
        }
        return --counter;
    }

    public int getCounter() {

        return counter;
    }

    public void stopTimer() {
        
        timer.cancel();
    }
}
