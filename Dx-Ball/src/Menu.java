
import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Menu implements ActionListener  {
    Image img;
    JButton start,high,credits,exit;
    MyApplet a ;
    
    Menu(MyApplet ap,Graphics g){
        a = ap ; 
        
        
    }
    
    public void addButton(JButton b,int x,int y,String s){
        
        b = new JButton();
        a.setLayout(null);
        b.setLocation(x, y);
        b.setSize(225, 60);
        b.setIcon(new ImageIcon(s));
        a.add(b);
        b.addActionListener(this);
    }

    public void drawMenu(Graphics g) {
        g.drawImage(img, 0, 0, 700, 500, a);
        a.setLayout(null);
        addButton(start, 237 , 168, "C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\start game.png" );
        addButton(high,237, 251 ,"C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\high scores.png" );
        addButton(credits, 237, 334,"C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\credits.png" );
        addButton(exit, 237, 417, "C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\exit.png");    
    }

     
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start){
           a.currentScreen = "playingGame";
           a.repaint();
           System.out.println("Performing");
        }
    }
 

}
