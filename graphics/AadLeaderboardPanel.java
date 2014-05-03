package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.AadLeaderboardFrame;

public class AadLeaderboardPanel extends JPanel
{
    private final static String BACKGROUND = "D:\\Pictures\\Java\\Jump\\Background.png";
    
    private Image backgroundImage = null;
    private int score = 0;
    private JLabel leaderboard = new JLabel();
    private JButton closeButton = new JButton();
    private CloseButtonHandler handler = new CloseButtonHandler();
    private AadLeaderboardFrame frame = null;
    
    public AadLeaderboardPanel()
    {
	setLayout(new BorderLayout());
	setPreferredSize(new Dimension(800, 600));
	try
	{
	    backgroundImage = ImageIO.read(new File(BACKGROUND));
	    leaderboard.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	    leaderboard.setForeground(Color.orange);
	    leaderboard.setBorder(new EmptyBorder(0, 150, 100, 0));
	    readLeaderboardFile();
	    add(leaderboard, BorderLayout.CENTER);
	    closeButton.addActionListener(handler);
	    closeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
	    closeButton.setForeground(Color.RED);
	    closeButton.setBorder(new EmptyBorder(100, 0, 0, 0));
	    closeButton.setPreferredSize(new Dimension(300, 200));
	    closeButton.setContentAreaFilled(false);
	    closeButton.setBorderPainted(false);
	    closeButton.setOpaque(false);
	    closeButton.setText("Close");
	    add(closeButton, BorderLayout.SOUTH);
	    
	    repaint();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
    
    public void readLeaderboardFile()
    {
	File file = new File(AadLeaderboardFrame.LEADERBOARD_FILE_PATH);
	String leaderboardString = "<html>Leaderboard<br><br>";
	
	if (!file.exists())
	{
	    return;
	}
	
	try 
	{
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String line = br.readLine();
	    while (line != null)
	    {
		int indexOfInitials = line.indexOf(":");
		int indexOfScore = line.lastIndexOf(":");
		int indexOfScoreString = line.indexOf("Score:");
		
		if (indexOfInitials > -1)
		{
		    String initialsString = line.substring(indexOfInitials+1, indexOfScoreString-1);
		    String scoreString = line.substring(indexOfScore+1);
		    String scoreTrimmed = scoreString.trim();
		    leaderboardString += initialsString + "  " + scoreTrimmed + "<br>";
		}		
		
		line = br.readLine();
	    }
	    leaderboardString += "</html>";
	    writeOutLeaderboard(leaderboardString);
	    br.close();
	} 
	catch (IOException e) 
	{
	    e.printStackTrace();
	}
	
    }
    
    private void writeOutLeaderboard(String leaderboardString)
    {
	leaderboard.setText(leaderboardString);
    }
    
    protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	g.drawImage(backgroundImage, 0, 0, this);
    }

    public int getScore()
    {
	return score;
    }
    public void setScore(int score) 
    {
	this.score = score;
    }

    public void setFrame(AadLeaderboardFrame frame) 
    {
	this.frame = frame;
	handler.setFrame(frame);
    }
}


class CloseButtonHandler implements ActionListener
{
    private AadLeaderboardFrame frame = null;

    public void actionPerformed(ActionEvent arg0) 
    {
	frame.dispose();
    }

    public void setFrame(AadLeaderboardFrame frame) 
    {
	this.frame  = frame;
    }   
}
