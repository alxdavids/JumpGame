package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.AadLeaderboardFrame;

public class AadInitialPanel extends JPanel
{
    private final static String BACKGROUND = "D:\\Pictures\\Java\\Jump\\Background.png";

    private Image backgroundImage = null;
    private JTextField textField = new JTextField();
    private static String initials = "";
    private JButton startButton = new JButton(); 
    private InitialStartButtonHandler handler = new InitialStartButtonHandler();
    private boolean initialsEntered = false;
    private JPanel textPanel = new JPanel();
    private int score = 0;
    private AadLeaderboardFrame aadLeaderboardFrame = null;
    
    public AadInitialPanel()
    {
	setLayout(new BorderLayout());
	setPreferredSize(new Dimension(800, 600));
	
	intialisePropertiesOfComponents();
	add(textPanel, BorderLayout.CENTER);
	add(startButton, BorderLayout.SOUTH);
	
	repaint();
	
	try
	{
	    backgroundImage = ImageIO.read(new File(BACKGROUND));
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    private void intialisePropertiesOfComponents() 
    {
	textField.setOpaque(false);
	textField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	textField.setForeground(Color.orange);
	handler.setTextField(textField);
	handler.setScore(score);
	textField.setBorder(BorderFactory.createTitledBorder("Enter your initials to be added to the leaderboard! (5 characters or less)"));
	textField.setPreferredSize(new Dimension(300, 200));
	
	textPanel.setOpaque(false);
	textPanel.add(textField);
	textPanel.setSize(new Dimension(300, 200));
	textPanel.setBorder(null);
	
	startButton.setOpaque(false);
	startButton.setText("Go");
	startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	startButton.setForeground(Color.orange);
	startButton.setPreferredSize(new Dimension(300, 200));
	startButton.setContentAreaFilled(false);
	startButton.setBorderPainted(false);
	startButton.addActionListener(handler);
    }
    
    public boolean getInitialsEntered()
    {
	return initialsEntered;
    }
    public void setInitialsEntered(boolean initialsEntered) 
    {
	this.initialsEntered = initialsEntered;
    }

    public boolean validInitials() 
    {
	String initials = textField.getText();
	if (initials.isEmpty())
	{
	    return false;
	}
	
	if (initials.length() > 5)
	{
	    return false;
	}
	return true;
    }

    public static void setInitials(String initialsEntered) 
    {
	initials = initialsEntered;
    }
    
    protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	g.drawImage(backgroundImage, 0, 0, this);
    }
    
    public void setTextField(JTextField textField)
    {
	this.textField = textField;
    }
    public int getScore()
    {
	return score;
    }
    public void setScore(int score)
    {
	this.score = score;
	handler.setScore(score);
    }

    public void setFrame(AadLeaderboardFrame aadLeaderboardFrame) 
    {
	this.aadLeaderboardFrame = aadLeaderboardFrame;
	this.handler.setFrame(aadLeaderboardFrame);
    }
}

class InitialStartButtonHandler implements ActionListener
{
    private JTextField textField = null;
    private int score = 0;
    private AadLeaderboardFrame frame = null;
    
    public void actionPerformed(ActionEvent e)
    {	
	JButton startButton = (JButton)e.getSource();
	AadInitialPanel initialPanel = (AadInitialPanel)startButton.getParent();
	if (initialPanel.validInitials())
	{
	    String initials = textField.getText();
	    AadInitialPanel.setInitials(initials);
	    frame.setInitials(initials);
	    /*try 
	    {
		writeToLeaderboardFile(initials);
	    } 
	    catch (IOException e1) 
	    {
		e1.printStackTrace();
	    }*/
	    initialPanel.setInitialsEntered(true);
	}
    }
    
    
    public void setFrame(AadLeaderboardFrame frame) 
    {
	this.frame = frame;
    }


    public void setTextField(JTextField textField) 
    {
	this.textField = textField;
    }


    private void writeToLeaderboardFile(String initials) throws IOException 
    {
	File file = new File(AadLeaderboardFrame.LEADERBOARD_FILE_PATH);
	if (!file.exists())   
	{
	    try 
	    {
		file.createNewFile();
	    } 
	    catch (IOException e) 
	    {
		e.printStackTrace();
	    }
	}
	
	BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	bw.write("Initials:");
	bw.write(initials);
	bw.write(" Score:");
	bw.write("" + score);
	bw.write("\n");
	bw.close();
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
