
import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyApplet extends Applet implements KeyListener, MouseListener, MouseMotionListener {

    Image img;
    JButton start, high, credits, exit;
    int x, y, ball_x, ball_y, dx, dy, fBrickX, fBrickY, life, score, length_x, length_y;
    int ballSizeX, ballSizeY;
    int bricks[] = new int[104];
    int ballSpeed;
    int curX, curY;
    boolean gameStart, pause, goldenBall;
    MovingBall mb;
    String currentScreen, playerName, mousePosition;
    String inputLoc = "D:\\inp.txt";
    String outputLoc = "D:\\outp.txt";
    FileReader fr;
    FileWriter fw;
    BufferedReader br;
    BufferedWriter bw;
    GenerateBrickNumberForBonus gbn;
    IncreasePaddle inP;
    Graphics g;
    Image offscreen;
    Dimension dim;
    IncreaseBallSize inB;
    IncreaseBallSpeed inS;
    GoldenBall gB;

    public void setLife() {
        life = 3;
    }

    public void setPaddleCoOrdinate() {
        x = 280;
        y = 460;
    }

    public void setBallCoOrdinate() {
        ball_x = ((x + x + length_x) / 2) - (ballSizeX / 2);
        ball_y = y - ballSizeY;
    }

    public void initializeAll() {
        playerName = "";
        goldenBall = false;
        ballSpeed = 50;
        ballSizeX = ballSizeY = 20;
        length_x = 130;
        length_y = 20;
        setLife();
        gameStart = false;
        setOne();
        setPaddleCoOrdinate();
        setBallCoOrdinate();
        setScore();
    }

    public void setScore() {
        score = 0;
    }

    public void createFeatureThread() {
        inP = new IncreasePaddle(this);
        inB = new IncreaseBallSize(this);
        inS = new IncreaseBallSpeed(this);
        gB = new GoldenBall(this);
    }

    public void init() {
        mousePosition = "";
        goldenBall = false;
        ballSpeed = 50;
        ballSizeX = ballSizeY = 20;
        length_x = 130;
        length_y = 20;
        createFeatureThread();
        pause = false;
        this.setSize(700, 500);
        dim = getSize();
        playerName = "";
        setLife();
        currentScreen = "menu";
        img = getImage(getDocumentBase(), "menu.jpg");
        gameStart = false;
        setPaddleCoOrdinate();
        fBrickX = 15;
        fBrickY = 60;

        setBackground(Color.black);

        offscreen = createImage(dim.width, dim.height);
        g = offscreen.getGraphics();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        mb = new MovingBall(this);
        setOne();
        mb.t.start();
        mb.t.suspend();
    }

    public void update(Graphics gp) {
        paint(gp);
    }

    public void addButton(JButton b, int x, int y, String s, String name) {
        /*
         b = new JButton();
         setLayout(null);
         b.setLocation(x, y);
         b.setSize(225, 60);
         b.setIcon(new ImageIcon(s));
         add(b);
         if(name=="start"){
         b.addActionListener(
         new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         currentScreen = "playingGame";
                        
         repaint();
         System.out.println("Game Started");
         }
         }
         );
         }
         */

    }

    public void drawMenu() {
        img = getImage(getDocumentBase(), "menu.jpg");
        g.drawImage(img, 0, 0, 700, 500, this);
        /*
         addButton(start, 237, 168, "C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\start game.png", "start");
         addButton(high, 237, 251, "C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\high scores.png", "high scores");
         addButton(credits, 237, 334, "C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\credits.png", "credits");
         addButton(exit, 237, 417, "C:\\Users\\user\\Documents\\NetBeansProjects\\Dx-Ball\\build\\exit.png", "exit");
         */
    }

    public void drawPaddle() {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, length_x, length_y);

    }

    public void increaseScore() {
        //----generate random number-----
        Random rand = new Random();
        int pnt = rand.nextInt(10) + 1;

        //----add point as random -----
        score = score + pnt;
    }

    public boolean checkForFeature(int bn) {
        for (int i = 0; i < 7; i++) {
            if (gbn.brickNumbers[i] == bn) {
                return true;
            }
        }
        return false;
    }

    public void codeForFeature(int i) {
        // check for increase paddle feature 

        if (i == gbn.brickNumbers[0] && checkForFeature(i) == true) {
            //System.out.println("1st bonus----------" + gbn.brickNumbers[0]);
            //inP = new IncreasePaddle(this);
            inP.start();
        } // check for increase ball feature 
        else if (i == gbn.brickNumbers[1] && checkForFeature(i) == true) {
            //System.out.println("2nd bonus----------" + gbn.brickNumbers[1]);
            //inB = new IncreaseBallSize(this);  
            inB.start();
        } // check for increase ball speed feature 
        else if (i == gbn.brickNumbers[2] && checkForFeature(i) == true) {
            //System.out.println("3rd bonus----------" + gbn.brickNumbers[2]);
            //inB = new IncreaseBallSize(this);  
            inS.start();
        } //check for increase life feature 
        else if (i == gbn.brickNumbers[3] && checkForFeature(i) == true) {
            //System.out.println("4th bonus----------" + gbn.brickNumbers[3]);
            //inB = new IncreaseBallSize(this);  
            //inS.start();
            ++life;
        } // check for increase score feature 
        else if (i == gbn.brickNumbers[4] && checkForFeature(i) == true) {
            //System.out.println("5th bonus----------" + gbn.brickNumbers[4]);
            //inB = new IncreaseBallSize(this);  
            //inS.start();
            Random rand = new Random();
            int nmb = rand.nextInt(6) + 2;
            score = score * nmb;
        } // check for golden ball feature
        else if (i == gbn.brickNumbers[5] && checkForFeature(i) == true) {
            //System.out.println("6th bonus----------" + gbn.brickNumbers[5]);
            //inB = new IncreaseBallSize(this);  
            //inS.start();
            gB.start();

        } // check for doubling score feature
        else if (i == gbn.brickNumbers[6] && checkForFeature(i) == true) {
            //System.out.println("7th bonus----------" + gbn.brickNumbers[6]);
            //inB = new IncreaseBallSize(this);  
            //inS.start();
            score = score * 2;

        }
    }

    public void drawBricks() {
        //g.setColor(Color.orange);
        fBrickX = 15;
        fBrickY = 60;
        boolean colorFlag = true;
        int ck;
        int numberOfBricksRow = 6;

        for (ck = 1; ck <= 17 * numberOfBricksRow; ck++) {
            if (bricks[ck] == 1) {
                break;
            }
        }
        if (ck == 17 * numberOfBricksRow + 1) {
            currentScreen = "gameOver";
            mb.t.suspend();
            dy = -dy;
            //initializeAll();
            repaint();
        }
        for (int i = 1; i <= 17 * numberOfBricksRow; i++) {

            if (colorFlag) {
                g.setColor(Color.GREEN);
                colorFlag = false;
            } else {
                g.setColor(Color.MAGENTA);
                colorFlag = true;
            }

            if (isBallInBrick() && bricks[i] == 1) {
                bricks[i] = 0;
                if (goldenBall == false) {
                    dy = -dy;
                }
                increaseScore();

                codeForFeature(i);

            } else if (bricks[i] == 1) {
                g.fillRect(fBrickX, fBrickY, 35, 25);
            }

            fBrickX += 40;
            if (i % 17 == 0) {
                fBrickY += 30;
                fBrickX = 15;
            }
        }

    }

    public void drawBall() {
        if (goldenBall == false) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.YELLOW);
        }
        g.fillOval(ball_x, ball_y, ballSizeX, ballSizeY);
    }

    public void drawScore() {
        Font myFont = new Font("TimesRoman", Font.BOLD, 18);
        g.setColor(Color.WHITE);
        g.setFont(myFont);
        g.drawString("Your Score is : " + score, 500, 25);
    }

    public void drawCredits() {
        img = getImage(getDocumentBase(), "credits.jpg");
        g.drawImage(img, 0, 0, 700, 500, this);
    }

    public void drawGameOver() {
        img = getImage(getDocumentBase(), "gameOver.jpg");
        g.drawImage(img, 0, 0, 700, 500, this);

    }

    public void drawHighScores() {
        try {
            fr = new FileReader(outputLoc);
            br = new BufferedReader(fr);

            String s;
            LinkedList<String> l = new LinkedList<String>();
            String list[] = new String[5];
            int len = 0;
            while ((s = br.readLine()) != null) {
                l.addLast(s);
                ++len;
            }
            //----------copy to new array-------
            Scores person[] = new Scores[l.size()];
            for (int i = 0; i < l.size(); i++) {
                String a[] = l.get(i).split(" ");
                String name = a[0];
                int score = Integer.parseInt(a[1]);
                person[i] = new Scores(name, score);
            }
            //----------sorting---------------
            for (int i = 0; i < l.size(); i++) {
                for (int j = i + 1; j < l.size(); j++) {
                    if (person[i].score < person[j].score) {
                        Scores temp = new Scores(person[i].name, person[i].score);

                        person[i].name = person[j].name;
                        person[i].score = person[j].score;

                        person[j].name = temp.name;
                        person[j].score = temp.score;

                    }
                }
            }
            //--------sorting done---------
            Font myFont = new Font("TimesRoman", Font.BOLD, 30);
            g.setColor(Color.WHITE);
            g.setFont(myFont);
            g.drawString("High Scores : ", 50, 100);
            myFont = new Font("TimesRoman", Font.BOLD, 22);
            g.setFont(myFont);

            int hx = 50, hy = 160;
            for (int i = 0; i < l.size() && i < 5; i++) {
                g.drawString(person[i].name + "  " + person[i].score, hx, hy);
                hy += 30;
            }
            l.clear();

        } catch (IOException e) {

        }
    }

    public void codeForMovingMouse() {
        //--------mouse movement---------
        g.setColor(Color.WHITE);
        if (mousePosition == "inStartGame") {

            g.drawRect(237, 168, 225, 60);
        }
        if (mousePosition == "inHighScores") {
            g.drawRect(237, 251, 225, 60);
        }
        if (mousePosition == "inCredits") {
            g.drawRect(237, 334, 225, 60);
        }

        if (mousePosition == "inExit") {
            g.drawRect(237, 417, 225, 60);
        }
        //--------mouse movement--------
    }

    public void drawLife(int cx, int cy) {
        g.setColor(Color.red);
        g.fillOval(cx, cy, 10, 10);
    }

    public void paint(Graphics gp) {
        g.clearRect(0, 0, dim.width, dim.width);

        if (currentScreen == "menu") {
            drawMenu();
            codeForMovingMouse();
        } else if (currentScreen == "playingGame") {
            int cx = 15, cy = 10;
            for (int i = 0; i < life; i++) {
                drawLife(cx, cy);
                cx += 12;
            }
            drawBricks();
            drawPaddle();
            drawBall();
            workForDropedBall();
            drawScore();
        } else if (currentScreen == "credits") {
            drawCredits();
        } else if (currentScreen == "gameOver") {
            drawGameOver();

        } else if (currentScreen == "getHighScores") {
            drawGetHighScores();
        } else if (currentScreen == "highScores") {
            drawHighScores();
        }
        gp.drawImage(offscreen, 0, 0, this);
    }

    public void drawGetHighScores() {
        Font myFont = new Font("TimesRoman", Font.BOLD, 18);
        g.setColor(Color.WHITE);
        g.setFont(myFont);
        g.drawString("Enter Your Name :    " + playerName, 180, 220);
    }

    public void workForDropedBall() {
        if (!isballInScreen() && gameStart == true) {
            mb.t.suspend();
            gameStart = false;
            setPaddleCoOrdinate();
            setBallCoOrdinate();

            //dx = -dx;
            dy = -dy;
            life = life - 1;
            if (life == 0) {
                currentScreen = "gameOver";
                //initializeAll();
            }
            repaint();
        }
    }

    public boolean isballInScreen() {
        if (ball_y > 500) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBallInBrick() {
        if (ball_x + (ballSizeX / 2) >= fBrickX && ball_x + (ballSizeX / 2) <= fBrickX + 35 && ball_y + (ballSizeY / 2) >= fBrickY && ball_y + (ballSizeY / 2) <= fBrickY + 25) {
            return true;
        } else {
            return false;
        }
    }

    public void setOne() {
        for (int i = 1; i <= 17 * 6; i++) {
            bricks[i] = 1;
        }

    }

    public void keyTyped(KeyEvent k) {

    }

    public void keyReleased(KeyEvent k) {

    }

    public void keyPressed(KeyEvent k) {
        if (currentScreen == "highScores") {
            int r = k.getKeyCode();
            if (r == k.VK_BACK_SPACE) {
                currentScreen = "menu";
                repaint();
            }
        }
        if (currentScreen == "getHighScores") {
            int r = k.getKeyCode();
            if (r != k.VK_ENTER && r != k.VK_BACK_SPACE && r != k.VK_SPACE) {
                playerName += (char) r;
                //System.out.println(playerName);
                repaint();
            } else if (r == k.VK_BACK_SPACE) {
                if (playerName.length() > 0) {
                    playerName = playerName.substring(0, playerName.length() - 1);
                    repaint();
                }
            } else if (r == k.VK_ENTER && playerName.length() > 0) {
                try {
                    fr = new FileReader(inputLoc);
                    br = new BufferedReader(fr);
                    fw = new FileWriter(outputLoc, true);
                    bw = new BufferedWriter(fw);
                    bw.write(playerName + " " + score);

                    bw.newLine();

                    br.close();
                    bw.close();
                    playerName = "";
                    currentScreen = "menu";
                    repaint();
                } catch (IOException e) {

                }
            }
        }
        if (currentScreen == "credits") {
            int r = k.getKeyCode();
            if (r == k.VK_BACK_SPACE) {
                currentScreen = "menu";
                repaint();
            }
        }
        if (currentScreen == "playingGame") {

            int r = k.getKeyCode();
            if (r == k.VK_LEFT && gameStart && pause == false) {
                if (x > 0) {
                    x -= 20;
                    //System.out.println("Left");
                    repaint();
                }
            } else if (r == k.VK_RIGHT && gameStart && pause == false) {
                if (x + length_x < 700) {
                    x += 20;
                    //System.out.println("Right");
                    repaint();
                };
            }
            if (r == k.VK_P && gameStart==true ) {
                mb.t.suspend();
                if (inP.t.isAlive()) {
                    inP.t.suspend();
                }
                if (inB.t.isAlive()) {
                    inB.t.suspend();
                }
                if (inS.t.isAlive()) {
                    inS.t.suspend();
                }
                if (gB.t.isAlive()) {
                    gB.t.suspend();
                }

                pause = true;

            } else if (r == k.VK_R && pause==true ) {
                mb.t.resume();

                if (inP.t.isAlive()) {
                    inP.t.resume();
                }
                if (inB.t.isAlive()) {
                    inB.t.resume();
                }
                if (inS.t.isAlive()) {
                    inS.t.resume();
                }
                if (gB.t.isAlive()) {
                    gB.t.resume();
                }

                pause = false;
            }
        }

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void indicateBonusBrick() {
        System.out.println("brick number for increase paddle ----" + gbn.brickNumbers[0]);
        System.out.println("brick number for increase ball size ----" + gbn.brickNumbers[1]);
        System.out.println("brick number for increase ball speed ----" + gbn.brickNumbers[2]);
        System.out.println("brick number for increase life ----" + gbn.brickNumbers[3]);
        System.out.println("brick number for increase score ----" + gbn.brickNumbers[4]);
        System.out.println("brick number for golden ball ----" + gbn.brickNumbers[5]);
        System.out.println("brick number for doubling score ----" + gbn.brickNumbers[6]);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentScreen == "gameOver") {
            currentScreen = "getHighScores";
            repaint();
        }
        if (currentScreen == "menu") {
            if (e.getX() >= 237 && e.getX() <= 462) {
                if (e.getY() >= 168 && e.getY() <= 168 + 60) {
                    currentScreen = "playingGame";
                    gbn = new GenerateBrickNumberForBonus();

                    indicateBonusBrick();
                    createFeatureThread();
                    initializeAll();
                    //---------------test----------
                    //for(int i = 0 ; i<10 ; i++){
                    //System.out.println(gbn.brickNumbers[i]);
                    //}
                    //---------------test----------
                    setScore();
                    repaint();
                } else if (e.getY() >= 334 && e.getY() <= 334 + 60) {
                    currentScreen = "credits";
                    repaint();
                } else if (e.getY() >= 417 && e.getY() <= 417 + 60) {
                    System.exit(0);
                } else if (e.getY() >= 251 && e.getY() <= 251 + 60) {
                    currentScreen = "highScores";
                    repaint();
                }
            }

        } else if (!gameStart && currentScreen == "playingGame") {
            mb.t.resume();
            gameStart = true;
        }
        //System.out.println("x = " + e.getX() + " y = " + e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        curX = e.getX();
        curY = e.getY();
        if (e.getX() >= 237 && e.getX() <= 462 && currentScreen == "menu") {
            if (e.getY() >= 168 && e.getY() <= 168 + 60) {
                mousePosition = "inStartGame";
                repaint();
            } else if (e.getY() >= 334 && e.getY() <= 334 + 60) {
                mousePosition = "inCredits";
                repaint();
            } else if (e.getY() >= 417 && e.getY() <= 417 + 60) {
                mousePosition = "inExit";
                repaint();
            } else if (e.getY() >= 251 && e.getY() <= 251 + 60) {
                mousePosition = "inHighScores";
                repaint();
            } else {
                mousePosition = "";
                repaint();
            }
        } else {
            mousePosition = "";
            repaint();
        }

    }

}
