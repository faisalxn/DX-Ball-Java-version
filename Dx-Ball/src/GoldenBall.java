/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class GoldenBall implements Runnable {
    MyApplet ma;
    Thread t;
    
    public GoldenBall(MyApplet ma) {
        this.ma = ma ;
        t = new Thread(this);
    }
    
    public void start(){
        t.start();
    }
    
    public void run(){
        ma.goldenBall=true;
        ma.repaint();
        try{
            t.sleep(1000*10);
        } catch(Exception e){
            e.printStackTrace();
        }
        ma.goldenBall=false ;
    }
    
    
    
    
    
}
