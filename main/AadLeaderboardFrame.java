package main;

import graphics.AadInitialPanel;
import graphics.AadLeaderboardPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class AadLeaderboardFrame extends JFrame
{
    public static final String LEADERBOARD_FILE_PATH = "D:\\Jump\\Leaderboard.txt";
    
    private int score = 0;
    private String initials = "";
    private String fileText = "";
    private boolean pressedAlready = false;
    
    private AadInitialPanel initialPanel = new AadInitialPanel();
    private AadLeaderboardPanel leaderboardPanel = new AadLeaderboardPanel();
    
    public AadLeaderboardFrame(int score, boolean pressedAlready) 
    {
	this.score = score;
	this.pressedAlready = pressedAlready;
	setSize(800, 600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Leaderboard");
	setLayout(new BorderLayout());
		
	initialPanel.setFrame(this);
	initialPanel.setScore(score);
	leaderboardPanel.setFrame(this);
	leaderboardPanel.setScore(score);
	
	Container content = getContentPane();
	
	if (scoreForLeaderboard()
	  && !pressedAlready)
	{
	    content.add(initialPanel);
	}
	else
	{
	    content.add(leaderboardPanel);
	}
	
	pack();
	setVisible(true);
	
	if (scoreForLeaderboard())
	{
	    while (!initialPanel.getInitialsEntered())
	    {
		try
		{
		    Thread.sleep(200);
		}
		catch (InterruptedException e)
		{
		    e.printStackTrace();
		}
	    }
	    try 
	    {
		writeNewEntryToFile();
	    } 
	    catch (IOException e) 
	    {
		e.printStackTrace();
	    }
	    content.remove(initialPanel);
	    leaderboardPanel.readLeaderboardFile();
	    content.add(leaderboardPanel);
	    content.revalidate();
	    content.repaint();
	}
    }

    private void writeNewEntryToFile() throws IOException 
    {
	File folder = new File("D:\\Jump");
	File file = new File(AadLeaderboardFrame.LEADERBOARD_FILE_PATH);
	if (!folder.exists())   
	{
	    folder.mkdir();		
	    file.createNewFile();
	}
	else if (!file.exists())
	{
	    file.createNewFile();
	}
	
	int lineCount = 0;
	boolean lineAdded = false;
	
	BufferedReader br = new BufferedReader(new FileReader(file));
	String line = br.readLine();
	ArrayList linesOfText = new ArrayList<>();
	while (lineCount < 5)
	{
	    lineCount++;
	    if (line != null) 
	    {
		int indexOfScore = line.lastIndexOf(":");
		if (indexOfScore > -1)
		{
		    String scoreString = line.substring(indexOfScore+1);
		    String scoreTrimmed = scoreString.trim();
		    int score = Integer.parseInt(scoreTrimmed);
		    if (this.score > score
		      && !lineAdded)
		    {
			linesOfText.add("Initials:" + initials + " Score:" + this.score);
			linesOfText.add(line);
			lineCount++;
			lineAdded = true;
		    }
		    else
		    {
			linesOfText.add(line);
		    }
		}
	    }
	    else if (!lineAdded)
	    {
		linesOfText.add("Initials:" + initials + " Score:" + score);
		lineAdded = true;
	    }
	    line = br.readLine();
	}
	    
	BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	for (int i=0; i<linesOfText.size(); i++)
	{	    
	    String text = (String)linesOfText.get(i);
	    bw.write(text);
	    bw.newLine();
	}
	bw.close();
    }

    private boolean scoreForLeaderboard() 
    {
	int lineCount = 0;
	
	try
	{
	    File file = new File(LEADERBOARD_FILE_PATH);
	    if (!file.exists())
	    {
		return true;
	    }
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String line = br.readLine();
	    while (line != null)
	    {
		lineCount++;
		int indexOfScore = line.lastIndexOf(":");
		if (indexOfScore > -1)
		{
		    String scoreString = line.substring(indexOfScore+1);
		    String scoreTrimmed = scoreString.trim();
		    int score = Integer.parseInt(scoreTrimmed);
		    if (this.score > score)
		    {
			return true;
		    }
		}
		line = br.readLine();
	    }
	    br.close();
	    
	    if (lineCount < 5)
	    {
		return true;
	    }
	}
	catch (Throwable e)
	{
	    e.printStackTrace();
	}
	
	return false;
    }
    
    public int getScore()
    {
	return score;
    }
    public void setScore(int score)
    {
	this.score = score;
    }
    public String getInitials()
    {
	return initials;
    }
    public void setInitials(String initials) 
    {
	this.initials = initials;
    }
}
