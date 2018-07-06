public class IncreaseBallSize implements Runnable {

    MyApplet ma;
    Thread t;
    boolean flag;

    public IncreaseBallSize(MyApplet ma) {
        this.ma = ma;
        t = new Thread(this);
        
    }
    
    public void start(){
        t.start();
    }

    

    public void run() {

        ma.ballSizeX += 20;
        ma.ballSizeY += 20;
        ma.repaint();
        try {
            t.sleep(1000 * 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ma.ballSizeX -= 20;
        ma.ballSizeY -= 20;

    }

}