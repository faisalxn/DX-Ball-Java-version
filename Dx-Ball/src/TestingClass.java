
import java.util.Random;
import javax.swing.JFrame;

public class TestingClass {

    public static void main(String args[]) {
        MyApplet game=new MyApplet();
        JFrame myFrame=new JFrame("Test");
        myFrame.add(game);
        myFrame.pack();
        myFrame.setVisible(true);
        game.init();
        
        
    }
}
