
import com.sun.glass.events.ViewEvent;

public class IncreaseBallSpeed implements Runnable {

    MyApplet ma;
    Thread t;
    

    public IncreaseBallSpeed(MyApplet ma) {
        this.ma = ma;
        t = new Thread(this);
    }

    public void start(){
        t.start();
    }

    public void run() {

        ma.ballSpeed=30;
        ma.repaint();
        try {
            t.sleep(1000 * 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ma.ballSpeed=50;

    }

}
