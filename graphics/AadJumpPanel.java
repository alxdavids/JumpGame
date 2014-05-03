package graphics;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AadJumpPanel extends JPanel
{
    private final static int HEIGHT = 150;
    private final static int WIDTH = 267;
    
    private ArrayList listOfComponents = new ArrayList();
    private boolean jumping = false;
    private int yNew = 0;
    private int x = 0;   
    
    public AadJumpPanel()
    {
	setLayout(null);
	setPreferredSize(new Dimension(800, 238));
	setOpaque(false);
	try
	{
	    initialisePanelFormat();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}		
    }

    private void initialisePanelFormat() throws IOException 
    {
	final AadGround ground1 = new AadGround();
	final AadGround ground2 = new AadGround();
	final AadHole hole1 = new AadHole();
	final AadGround ground3 = new AadGround();
	
	
	ground1.setBounds(0, 88, 267, 150);
	ground2.setBounds(267, 88, 267, 150);
	ground3.setBounds(801, 88, 267, 150);
	hole1.setBounds(534, 88, 267, 150);
	
	add(ground1);
	add(ground2);
	add(hole1);
	add(ground3);
	
	listOfComponents.add(ground1);
	listOfComponents.add(ground2);
	listOfComponents.add(hole1);
	listOfComponents.add(ground3);	
    }
    public ArrayList getListOfComponents()
    {
	return listOfComponents;
    }
}
