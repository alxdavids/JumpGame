package graphics;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AadGround extends AadAbstractPanelComponent
{
    private final static String GROUND_FILE_NAME = "D:\\Pictures\\Java\\Jump\\Ground.png"; 
    
    private ImageIcon image = null;
        
    public AadGround() throws IOException
    {
	image = new ImageIcon(ImageIO.read(new File(GROUND_FILE_NAME)));
	setIcon(image);
    }
}
