package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import main.AadLeaderboardFrame;
import main.MainFrame;

public class MainPanel extends JPanel
{    
    private final static String MAN_ONE = "D:\\Pictures\\Java\\Jump\\man1.png";
    private final static String MAN_TWO = "D:\\Pictures\\Java\\Jump\\man2.png";
    private final static String BACKGROUND = "D:\\Pictures\\Java\\Jump\\Background.png";
    
    private final static int GROUND_LEVEL = 357;
    private final static int JUMP_SPEED = 40;
    
    private int jumpX = 0;
    private int jumpY = GROUND_LEVEL;
    private float dy;
    private float grav = 0.25f;
    private boolean jumping = false;
    private boolean shouldFall = false;
    private AadJumpPanel jumpPanel = new AadJumpPanel();
    private JLabel scoreLabel = new JLabel();
    private JLabel gameOverLabel = new JLabel();
    private JButton restartGameButton = new JButton();
    private JButton exitGameButton = new JButton();
    private JButton leaderBoardButton = new JButton();
    private leaderBoardButtonHandler leaderBoardButtonHandler = new leaderBoardButtonHandler();
    private RestartButtonHandler restartButtonHandler = new RestartButtonHandler();
    private ExitGameButtonHandler exitButtonHandler = new ExitGameButtonHandler();
    private int score = 0;
    private int scrollSpeed = 1;
            
    private Timer engine;
   
    Image jumpPlayer = null;
    Image backgroundImage = null;
    
    private MainFrame frame = null;
    
    public MainPanel(MainFrame frame)
    {
	this.frame = frame;
	
	setLayout(new BorderLayout());
	add(jumpPanel, BorderLayout.SOUTH);
	try 
	{
	    jumpPlayer = ImageIO.read(new File(MAN_ONE));
	    backgroundImage = ImageIO.read(new File(BACKGROUND));    
	} 
	catch (IOException e) 
	{
	    e.printStackTrace();
	}
	
	setPreferredSize(new Dimension(800, 600));
	try 
	{
	    startGame();
	} 
	catch (InterruptedException e1) 
	{
	    e1.printStackTrace();
	}
		
	InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "jump");
        am.put("jump", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                if (jumpY > GROUND_LEVEL-4 && jumpY < GROUND_LEVEL+4) 
                {
                    dy = -8;
                    jumping = true;
                }
            }
        });
        
        Thread runningThread = new Thread() 
	{
	    public void run() 
	    {
		try 
		{
		    startManRunning();
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	    }
	};
	runningThread.start();
	
	scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	scoreLabel.setForeground(Color.ORANGE);
	scoreLabel.setText("Score: " + score);
	add(scoreLabel, BorderLayout.NORTH);
	Thread scoreIncrementer = new Thread()
	{
	    public void run()
	    {
		while (!shouldFall)
		{
		    score++;
		    scoreLabel.setText("Score: " + score);
		    try 
		    {
			Thread.sleep(500);
		    } 
		    catch (InterruptedException e) 
		    {
			e.printStackTrace();
		    }
		    repaint();
		}
	    }
	};
	scoreIncrementer.start();
	
	Thread fallingThread = new Thread() 
	{
	    public void run() 
	    {
		try 
		{
		    while (!shouldFall)
		    {
			checkIfShouldFall();
		    }
		    Thread.sleep(500);
		    initialiseGameOver();

		    revalidate();
		    repaint();
		} 
		catch (Throwable e) 
		{
		    e.printStackTrace();
		}
	    }
	};
	fallingThread.start();
        
        engine = new Timer(40, new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
        	if (jumping)
        	{
        	    jumpY += dy;        		
        	    dy += grav;
        	    if (jumpY > GROUND_LEVEL)
        	    {
        		jumping = false;
        		jumpY = GROUND_LEVEL;
        	    }
        	}
        	else if (shouldFall)
        	{
        	    dy = 20;
        	    jumpY += dy;
        	}
        	repaint();
            }
        });
       
        engine.start();
    }
    
    public static void resetVariablesToInitialState()
    {
	
    }    
    
    private void initialiseGameOver() 
    {
	gameOverLabel.setBorder(new EmptyBorder(0, 220, 0, 0));	
	gameOverLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 64));
	gameOverLabel.setForeground(Color.RED);
	gameOverLabel.setText("GAME OVER");
	
	restartGameButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	restartGameButton.setOpaque(false);
	restartGameButton.setContentAreaFilled(false);
	restartGameButton.setBorderPainted(false);
	restartGameButton.addActionListener(restartButtonHandler);
	restartGameButton.setForeground(Color.orange);
	leaderBoardButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	leaderBoardButton.setOpaque(false);
	leaderBoardButton.setContentAreaFilled(false);
	leaderBoardButton.setBorderPainted(false);
	leaderBoardButton.addActionListener(leaderBoardButtonHandler);
	leaderBoardButton.setForeground(Color.orange);
	exitGameButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	exitGameButton.setOpaque(false);
	exitGameButton.setContentAreaFilled(false);
	exitGameButton.setBorderPainted(false);
	exitGameButton.addActionListener(exitButtonHandler);
	exitGameButton.setForeground(Color.orange);
	leaderBoardButton.setText("Leaderboard");
	restartGameButton.setText("Restart game");
	exitGameButton.setText("Exit game");
	
	restartButtonHandler.setFrame(frame);
	leaderBoardButtonHandler.setFrame(frame);
	leaderBoardButtonHandler.setScore(score);
	
	JPanel gameOverStuff = new JPanel();
	JPanel panelButtons = new JPanel();
	gameOverStuff.setLayout(new BorderLayout());
	gameOverStuff.setOpaque(false);
	gameOverStuff.setBorder(null);
	panelButtons.setLayout(new BorderLayout());
	panelButtons.setOpaque(false);
	panelButtons.setBorder(null);
	panelButtons.add(leaderBoardButton, BorderLayout.NORTH);
	panelButtons.add(restartGameButton, BorderLayout.CENTER);
	panelButtons.add(exitGameButton, BorderLayout.SOUTH);
	gameOverStuff.add(gameOverLabel, BorderLayout.NORTH);
	gameOverStuff.add(panelButtons, BorderLayout.SOUTH);
	add(gameOverStuff, BorderLayout.CENTER);
    }
        
    public static void main(String[] args) 
    {
	new MainFrame();
    }
    
    protected void paintComponent(Graphics g) 
    {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.drawImage(backgroundImage, 0, 0, this);
	g2.drawImage(jumpPlayer, jumpX, jumpY, this);
    }
    
    private void startGame() throws InterruptedException 
    {	
	Thread scrollingThread = new Thread() 
	{
	    public void run() 
	    {
		try 
		{		    
		    while (true)
		    {
			ArrayList components = jumpPanel.getListOfComponents();
			for (int i=components.size()-1; i>-1; i--)
			{
			    AadAbstractPanelComponent component = (AadAbstractPanelComponent) components.get(i);
			    int x1 = component.getX();
			    if (score > 50 && score < 250)
			    {
				scrollSpeed = score / 50;
				engine.setDelay(JUMP_SPEED/scrollSpeed);
			    }
			    if (x1 <= -266)
			    {
				components.remove(component);
				jumpPanel.remove(component);
				AadAbstractPanelComponent newComponent;
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(100);
				if (randomInt < 50)
				{
				    newComponent = new AadHole();
				}
				else
				{
				    newComponent = new AadGround();
				}
				newComponent.setBounds(797, 150, 267, 150);
				components.add(newComponent);
				jumpPanel.add(newComponent);
				continue;
			    }
      			    int x1new = x1 - scrollSpeed;
			    component.setBounds(x1new, 88, 267, 150);
			}
			Thread.sleep(10);
		    }
		} 
		catch(Throwable t) 
		{
		    t.printStackTrace();
		}
	    }
	};
	scrollingThread.start();
    }
    
    private void startManRunning() throws IOException
    {
	while (true)
	{
	    jumpPlayer = ImageIO.read(new File(MAN_ONE));
	    try 
	    {
		Thread.sleep(100);
	    } 
	    catch (InterruptedException e) 
	    {
		e.printStackTrace();
	    }
	    jumpPlayer = ImageIO.read(new File(MAN_TWO));
	    try 
	    {
		Thread.sleep(100);
	    } 
	    catch (InterruptedException e) 
	    {
		e.printStackTrace();
	    }
	}
    }
    
    private boolean checkIfShouldFall()
    {	
	ArrayList components = jumpPanel.getListOfComponents();
	AadAbstractPanelComponent comp = (AadAbstractPanelComponent) components.get(0);
	AadAbstractPanelComponent comp2 = (AadAbstractPanelComponent) components.get(1);
	boolean componentOneIsHole = comp instanceof AadHole;
	boolean componentTwoIsHole = comp2 instanceof AadHole;
	boolean compOneFall = componentOneIsHole && comp.getX() > -80;
	boolean compTwoFall = componentTwoIsHole && comp2.getX() < 96;	
	shouldFall = !jumping && (compOneFall || compTwoFall);
	System.out.println("compOne = " + compOneFall);
	System.out.println("compTwo = " + compTwoFall);
	return shouldFall;
    }
    
    public boolean gameOver()
    {
	return shouldFall;
    }
}

class RestartButtonHandler implements ActionListener
{
    private MainFrame frame = null;
    public void actionPerformed(ActionEvent e)
    {	
	frame.dispose();
	Thread restartGame = new Thread()
	{
	    public void run()
	    {
		new MainFrame();
		System.out.println(SwingUtilities.isEventDispatchThread());
	    }
	};
	restartGame.start();
	System.out.println(SwingUtilities.isEventDispatchThread());
    }    
    public MainFrame getFrame()
    {
	return frame;
    }
    public void setFrame(MainFrame frame)
    {
	this.frame = frame;
    }
}
class leaderBoardButtonHandler implements ActionListener
{
    private MainFrame frame = null;
    private int score = -1;
    private boolean pressedAlready = false;
    public void actionPerformed(ActionEvent e)
    {	
	Thread leaderboardFrame = new Thread()
	{
	    public void run()
	    {	
		new AadLeaderboardFrame(score, pressedAlready);
		pressedAlready = true;
	    }
	};
	leaderboardFrame.start();	
    }
    public MainFrame getFrame()
    {
	return frame;
    }
    public void setFrame(MainFrame frame)
    {
	this.frame = frame;
    }
    public int getScore()
    {
	return score;
    }
    public void setScore(int score)
    {
	this.score = score;
    }
}
class ExitGameButtonHandler implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
	System.exit(0);
    }
}
