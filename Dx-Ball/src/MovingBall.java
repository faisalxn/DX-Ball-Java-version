

public class MovingBall implements Runnable {
    Thread t;
    MyApplet ma;
    public MovingBall(MyApplet ma){
        this.ma = ma ;
        ma.setBallCoOrdinate();
        ma.dx = 8 ; ma.dy = 11 ;
        t = new Thread(this);
       // t.start();
    }
    public void run(){
        while(true){
            ma.ball_x+=ma.dx;
            ma.ball_y-=ma.dy;
            if(ma.ball_x+10>700 || ma.ball_x+10<0) ma.dx = -ma.dx ;
            //if( (ma.ball_y+20>ma.y && ma.ball_y+20<ma.y+ma.length_y && ma.ball_x+10>ma.x && ma.ball_x+10<ma.x+ma.length_x )  || ma.ball_y<0) ma.dy = -ma.dy ;
            if( (ma.ball_y+ma.ballSizeY>ma.y && ma.ball_y+ma.ballSizeY<ma.y+ma.length_y && ma.ball_x+(ma.ballSizeX/2)>ma.x && ma.ball_x+(ma.ballSizeX/2)<ma.x+ma.length_x )  || ma.ball_y<0) ma.dy = -ma.dy ;
            
            ma.repaint();
            try{
                t.sleep(ma.ballSpeed);
            } catch(InterruptedException e){
                
            }
        }
    } 
}
