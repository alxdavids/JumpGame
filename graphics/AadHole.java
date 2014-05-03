package graphics;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AadHole extends AadAbstractPanelComponent
{
    private final static String HOLE_FILE_NAME = "D:\\Pictures\\Java\\Jump\\Hole.png"; 
    
    private ImageIcon image = null;
    
    public AadHole() throws IOException
    {
	image = new ImageIcon(ImageIO.read(new File(HOLE_FILE_NAME)));
	setIcon(image);
    }
}
