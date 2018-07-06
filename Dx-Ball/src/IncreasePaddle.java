
import com.sun.glass.events.ViewEvent;

public class IncreasePaddle implements Runnable {

    MyApplet ma;
    Thread t;

    public IncreasePaddle(MyApplet ma) {
        this.ma = ma;
        t = new Thread(this);
    }

    public void start(){
        t.start();
    }

    public void run() {

        ma.length_x += 100;
        ma.repaint();
        try {
            t.sleep(1000 * 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ma.length_x -= 100;

    }

}
