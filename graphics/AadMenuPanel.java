package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

public class AadMenuPanel extends JPanel
{    
    private final static String GET_READY_FILE_NAME = "D:\\Pictures\\Java\\Jump\\getReady.png";
    private final static String THREE_FILE_NAME = "D:\\Pictures\\Java\\Jump\\three.png";
    private final static String TWO_FILE_NAME = "D:\\Pictures\\Java\\Jump\\two.png";
    private final static String ONE_FILE_NAME = "D:\\Pictures\\Java\\Jump\\one.png";
    private final static String GO_FILE_NAME = "D:\\Pictures\\Java\\Jump\\go.png";
    
    private JButton startGameButton = new JButton();
    private JButton exitGameButton = new JButton();
    private StartButtonHandler startButtonHandler = new StartButtonHandler();
    private ExitButtonHandler exitButtonHandler = new ExitButtonHandler();
    
    private JLabel getReady = new JLabel();
    private ImageIcon getReadyImage = null;
    private ImageIcon threeImage = null;
    private ImageIcon twoImage = null;
    private ImageIcon oneImage = null;
    private ImageIcon goImage = null;
    
    private boolean gameStarted = false;
    
    private Image backgroundImage = null;
    private String backgroundFileName = "D:\\Pictures\\Java\\Jump\\Background.png";
    
    public AadMenuPanel()
    {
	try 
	{
	    setPreferredSize(new Dimension(800, 600));
	    backgroundImage = ImageIO.read(new File(backgroundFileName));
	    setLayout(null);
	    
	    getReadyImage = new ImageIcon(ImageIO.read(new File(GET_READY_FILE_NAME)));
	    threeImage = new ImageIcon(ImageIO.read(new File(THREE_FILE_NAME)));
	    twoImage = new ImageIcon(ImageIO.read(new File(TWO_FILE_NAME)));
	    oneImage = new ImageIcon(ImageIO.read(new File(ONE_FILE_NAME)));
	    goImage = new ImageIcon(ImageIO.read(new File(GO_FILE_NAME)));	
	    
	    startGameButton.addActionListener(startButtonHandler);
	    exitGameButton.addActionListener(exitButtonHandler);
	    
	    ImageIcon startButtonIcon = new ImageIcon("D:\\Pictures\\Java\\Jump\\StartButtonTrans.png");
	    ImageIcon endButtonIcon = new ImageIcon("D:\\Pictures\\Java\\Jump\\EndButtonTrans.png");
	    
	    startGameButton.setIcon(startButtonIcon);
	    startGameButton.setOpaque(false);
	    startGameButton.setContentAreaFilled(false);
	    startGameButton.setBorderPainted(false);
	    exitGameButton.setIcon(endButtonIcon);
	    exitGameButton.setOpaque(false);
	    exitGameButton.setContentAreaFilled(false);
	    exitGameButton.setBorderPainted(false);
	    
	    startGameButton.setBounds(266,  150, 266, 150);
	    exitGameButton.setBounds(266, 300, 266, 150);
	    
	    getReady.setIcon((Icon) getReadyImage);	
	    getReady.setBorder(new EmptyBorder(0, 266, 100, 0));
 
	    add(startGameButton);
	    add(exitGameButton);
	    
	    revalidate();
	    repaint();
	} 
	catch (IOException e) 
	{
	    e.printStackTrace();
	}
    }
    
    protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	g.drawImage(backgroundImage, 0, 0, this);
    }

    public boolean getGameStarted() 
    {
	return gameStarted;
    }
    public void setGameStarted(boolean gameStarted) 
    {
	this.gameStarted = gameStarted;
    }

    public void removeButtonsAndGetReady() throws InterruptedException 
    {
	remove(startGameButton);
	remove(exitGameButton);
	setLayout(new BorderLayout());
	add(getReady, BorderLayout.CENTER); 
	revalidate();
	repaint();
	Thread.sleep(1000);
	getReady.setIcon(threeImage);
	Thread.sleep(900);
	getReady.setIcon(twoImage);
	Thread.sleep(900);
	getReady.setIcon(oneImage);
	Thread.sleep(900);
	getReady.setIcon(goImage);	
	Thread.sleep(400);
    }
}


class StartButtonHandler implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {	
	JButton startButton = (JButton)e.getSource();
	AadMenuPanel menuPanel = (AadMenuPanel)startButton.getParent();
	menuPanel.setGameStarted(true);
    }
}
class ExitButtonHandler implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
	System.exit(0);
    }
}
