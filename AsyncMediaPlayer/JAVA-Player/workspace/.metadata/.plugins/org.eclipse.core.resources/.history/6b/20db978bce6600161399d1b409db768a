package editor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class getIcons {
	static int width = 64;
	static int height = 64;
	
	private ImageIcon getImageFromPath(String path)
	{
		
		ImageIcon temp = new ImageIcon(getClass().getResource(path));

	    BufferedImage bi = new BufferedImage(temp.getIconWidth(), temp.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics g = bi.createGraphics();
	    g.drawImage(temp.getImage(), 0, 0, width, height, null, null);
	    return new ImageIcon(bi);
	}
	
	static public ImageIcon getPlayButton()
	{
		
		return new getIcons().getImageFromPath("/resources/music_play_button.png");
	}
	
	static public ImageIcon getPauseButton()
	{
		return new getIcons().getImageFromPath("/resources/music_pause_button.png");
	}
	
	static public ImageIcon getStopButton()
	{
		return new getIcons().getImageFromPath("/resources/music_stop_button.png");
	}
	
	static public ImageIcon getFastForwardButton()
	{
		return new getIcons().getImageFromPath("/resources/music_fastforward_button.png");
	}
	
	static public ImageIcon getEndButton()
	{
		return new getIcons().getImageFromPath("/resources/music_end_button.png");
	}
	
	static public ImageIcon getFastRewindButton()
	{
		return new getIcons().getImageFromPath("/resources/music_rewind_button.png");
	}
	
	static public ImageIcon getBegginningButton()
	{
		return new getIcons().getImageFromPath("/resources/music_beginning_button.png");
	}
	
	static public ImageIcon getRemoveButton()
	{
		return new getIcons().getImageFromPath("/resources/editor_remove_button.png");
	}
	
	static public ImageIcon getAddButton()
	{
		return new getIcons().getImageFromPath("/resources/editor_add_button.png");
	}
}
