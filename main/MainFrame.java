package main;

import graphics.AadInitialPanel;
import graphics.AadMenuPanel;
import graphics.MainPanel;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{        
    private MainPanel gamePanel = null;
     
    public MainFrame() 
    {
	setSize(800, 600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Jump");
	
	Container content = getContentPane();
		
	AadMenuPanel menuPanel = new AadMenuPanel();
	content.add(menuPanel);
	
	pack();
	setVisible(true);
	
	while (!menuPanel.getGameStarted())
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
	    menuPanel.removeButtonsAndGetReady();
	}
	catch (Throwable t)
	{
	    t.printStackTrace();
	}
	remove(menuPanel);
	
	gamePanel = new MainPanel(this);
	add(gamePanel);
	content.revalidate();
	content.repaint();
    }
}




