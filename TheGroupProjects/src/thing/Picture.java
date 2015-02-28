package thing;
/*************************************************************************
 *  Compilation:  javac Picture.java
 *  Execution:    java Picture imagename
 *
 *  Data type for manipulating individual pixels of an image. The original
 *  image can be read from a file in jpg, gif, or png format, or the
 *  user can create a blank image of a given size. Includes methods for
 *  displaying the image in a window on the screen or saving to a file.
 *
 *  % java Picture mandrill.jpg
 *
 *  Remarks
 *  -------
 *   - pixel (x, y) is column x and row y, where (0, 0) is upper left
 *
 *   - see also GrayPicture.java for a grayscale version
 *
 *************************************************************************/
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * This class provides methods for manipulating individual pixels of an image.
 * The original image can be read from a file in JPEG, GIF, or PNG format, or
 * the user can create a blank image of a given size. This class includes
 * methods for displaying the image in a window on the screen or saving to a
 * file.
 * <p>
 * By default, pixel (x, y) is column x, row y, where (0, 0) is upper left. The
 * method setOriginLowerLeft() change the origin to the lower left.
 * <p>
 * For additional documentation, see <a
 * href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 * <i>Introduction to Programming in Java: An Interdisciplinary Approach</i> by
 * Robert Sedgewick and Kevin Wayne.
 */
public final class Picture implements ActionListener {
	private BufferedImage image; // the rasterized image
	private JFrame frame; // on-screen view
	private String filename; // name of file
	private boolean isOriginUpperLeft = true; // location of origin
	private final int width, height; // width and height

	/**
	 * Create a blank w-by-h picture, where each pixel is black.
	 */

	public Picture(int w, int h) {
		width = w;
		height = h;
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		// set to TYPE_INT_ARGB to support transparency
		filename = w + "-by-" + h;
	}

	/**
	 * Copy constructor.
	 */
	public Picture(Picture pic) {
		width = pic.width();
		height = pic.height();
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		filename = pic.filename;
		for (int i = 0; i < width(); i++)
			for (int j = 0; j < height(); j++)
				image.setRGB(i, j, pic.get(i, j).getRGB());
	}

	/**
	 * Create a picture by reading in a .png, .gif, or .jpg from the given
	 * filename or URL name.
	 */
	public Picture(String filename) {
		this.filename = filename;
		try {
			// try to read from file in working directory
			File file = new File(filename);
			if (file.isFile()) {
				image = ImageIO.read(file);
			}

			// now try to read from file in same directory as this .class file
			else {
				URL url = getClass().getResource(filename);
				if (url == null) {
					url = new URL(filename);
				}
				image = ImageIO.read(url);
			}
			width = image.getWidth(null);
			height = image.getHeight(null);
		} catch (IOException e) {
			// e.printStackTrace();
			throw new RuntimeException("Could not open file: " + filename);
		}
	}

	/**
	 * Create a picture by reading in a .png, .gif, or .jpg from a File.
	 */
	public Picture(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not open file: " + file);
		}
		if (image == null) {
			throw new RuntimeException("Invalid image file: " + file);
		}
		width = image.getWidth(null);
		height = image.getHeight(null);
		filename = file.getName();
	}

	/**
	 * Return a JLabel containing this Picture, for embedding in a JPanel,
	 * JFrame or other GUI widget.
	 */
	public JLabel getJLabel() {
		if (image == null) {
			return null;
		} // no image available
		ImageIcon icon = new ImageIcon(image);
		return new JLabel(icon);
	}

	/**
	 * Set the origin to be the upper left pixel.
	 */
	public void setOriginUpperLeft() {
		isOriginUpperLeft = true;
	}

	/**
	 * Set the origin to be the lower left pixel.
	 */
	public void setOriginLowerLeft() {
		isOriginUpperLeft = false;
	}

	/**
	 * Display the picture in a window on the screen.
	 */
	public void show() {

		// create the GUI for viewing the image if needed
		if (frame == null) {
			frame = new JFrame();

			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menuBar.add(menu);
			JMenuItem menuItem1 = new JMenuItem(" Save...   ");
			menuItem1.addActionListener(this);
			menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			menu.add(menuItem1);
			frame.setJMenuBar(menuBar);

			frame.setContentPane(getJLabel());
			// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setTitle(filename);
			frame.setResizable(false);
			frame.pack();
			frame.setVisible(true);
		}

		// draw
		frame.repaint();
	}

	/**
	 * Return the height of the picture in pixels.
	 */
	public int height() {
		return height;
	}

	/**
	 * Return the width of the picture in pixels.
	 */
	public int width() {
		return width;
	}

	/**
	 * Return the color of pixel (i, j).
	 */
	public Color get(int i, int j) {
		if (isOriginUpperLeft)
			return new Color(image.getRGB(i, j));
		else
			return new Color(image.getRGB(i, height - j - 1));
	}

	/**
	 * Set the color of pixel (i, j) to c.
	 */
	public void set(int i, int j, Color c) {
		if (c == null) {
			throw new RuntimeException("can't set Color to null");
		}
		if (isOriginUpperLeft)
			image.setRGB(i, j, c.getRGB());
		else
			image.setRGB(i, height - j - 1, c.getRGB());
	}

	/**
	 * Is this Picture equal to obj?
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Picture that = (Picture) obj;
		if (this.width() != that.width())
			return false;
		if (this.height() != that.height())
			return false;
		for (int x = 0; x < width(); x++)
			for (int y = 0; y < height(); y++)
				if (!this.get(x, y).equals(that.get(x, y)))
					return false;
		return true;
	}

	/**
	 * Save the picture to a file in a standard image format. The filetype must
	 * be .png or .jpg.
	 */
	public void save(String name) {
		save(new File(name));
	}

	/**
	 * Save the picture to a file in a standard image format.
	 */
	public void save(File file) {
		this.filename = file.getName();
		if (frame != null) {
			frame.setTitle(filename);
		}
		String suffix = filename.substring(filename.lastIndexOf('.') + 1);
		suffix = suffix.toLowerCase();
		if (suffix.equals("jpg") || suffix.equals("png")) {
			try {
				ImageIO.write(image, suffix, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error: filename must end in .jpg or .png");
		}
	}

	/**
	 * Opens a save dialog box when the user selects "Save As" from the menu.
	 */
	public void actionPerformed(ActionEvent e) {
		FileDialog chooser = new FileDialog(frame,
				"Use a .png or .jpg extension", FileDialog.SAVE);
		chooser.setVisible(true);
		if (chooser.getFile() != null) {
			save(chooser.getDirectory() + File.separator + chooser.getFile());
		}
	}

	public void grayScale() {
		int r, g, b = 0;
		double x;
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				r = get(i, j).getRed();
				g = get(i, j).getGreen();
				b = get(i, j).getBlue();
				x = (r + g + b) / 3.0;

				// a crisper grayScale formula
				// x = .299*r + .577*g + .114*b;

				int y = (int) (Math.round(x));
				Color gray = new Color(y, y, y);
				image.setRGB(i, j, gray.getRGB());
			}
		}
	}

	public void mirror() {
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				image.setRGB(width() - i - 1, j, get(i, j).getRGB());
			}
		}
	}

	public void mirror2() {
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				image.setRGB(i, j, get(width() - i - 1, j).getRGB());
			}
		}
	}

	public void mirror3(int ht, int wth) {
		for (int i = 0; i < wth; i++) {
			for (int j = 0; j < ht; j++) {
				image.setRGB(width() - i - 1, j, get(i, j).getRGB());
			}
		}
	}

	public void removeBlue() {
		int r, g, b = 0;
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				r = get(i, j).getRed();
				g = get(i, j).getGreen();
				b = get(i, j).getBlue() / 1000;
				image.setRGB(i, j, new Color(r, g, 0).getRGB());
			}
		}
	}

	public void neg() {
		int r, g, b = 0;
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				r = get(i, j).getRed();
				g = get(i, j).getGreen();
				b = get(i, j).getBlue();
				image.setRGB(i, j,
						new Color((255 - r), (255 - g), (255 - b)).getRGB());
			}
		}
	}

	/**
	 * This method that replaces the current pixel color with the color from
	 * another picture at the same row and column when the current pixel color
	 * is close to a specified color.
	 */
	public void chromakey(Picture x) 
	{
	    Color z;
	    Color r;
	    for(int c =0; c<x.width();c++)
	    {
	    	for(int b=0;b<x.height();b++)	
	    	{
	    
	    		z=get(c, b);
	    		r=x.get(c,b);

				if(z.getRed() + z.getGreen() < z.getBlue()){
					image.setRGB(c, b, r.getRGB());
				}
	    }	}
	}
	
	public boolean isOdd(int num)
	{
		if(Math.abs(num) % 2 == 0)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isEven(int num)
	{
		if(Math.abs(num) % 2 == 0)
		{
			return true;
		}
		
		return false;
	}
	
	public void encode(Picture x)
	{
		for(int c =0; c<width();c++)
	    {
	    	for(int b=0;b<height();b++)	
	    	{
	    		if(x.get(c, b).getRed() <= 100 && x.get(c, b).getGreen() <= 100 && x.get(c, b).getBlue() <= 100)
	    		{
	    			if(isOdd(image.getRGB(c, b)))
	    			{
	    				image.setRGB(c, b, image.getRGB(c, b) + 1);
	    			}
	    		
	    			else
	    			{
	    				if(isEven(image.getRGB(c, b)))
	    				{
	    					image.setRGB(c, b, image.getRGB(c, b) + 1);
	    				}
	    			}
	    		}
	    	}
	    }
	}
	 
	public void decode()
	{
		for(int c =0; c < width();c++)
	    {
	    	for(int b=0;b < height();b++)	
	    	{
	    		if(isOdd(Math.abs(image.getRGB(c, b))))
	    		{
	    			image.setRGB(c, b, Color.white.getRGB());
	    		}
	    		
	    		else 
	    		{
	    			image.setRGB(c, b, Color.black.getRGB());
	    		}
	    	}
	    }

	}
	
	//sepia
	public void custom()
	{
		int r, g, b = 0;
		double r2, g2, b2 =0;
		for(int x =0; x<width();x++)
	    {
	    	for(int y=0;y<height();y++)	
	    	{
	    		r = get(x, y).getRed();
				g = get(x, y).getGreen();
				b = get(x, y).getBlue(); 
	    		
				r2 = (r * .393 + g * .769 + b * .189);
				g2 = (r *.349 + g * .686 + b * .168);
				b2 = (r * .272 + g * .534 + b * .131);
				
				if(r2 > 255)
				{
					r2 = 255;
				}
				
				if(g2 > 255)
				{
					g2 = 255;
				}
				
				if(b2 > 255)
				{
					b2 = 255;
				}
				
				image.setRGB(x, y, new Color((int)r2,  (int)g2,  (int)b2).getRGB());
	    	}
	    }
	    
	}

	/**
	 * Test client. Reads a picture specified by the command-line argument, and
	 * shows it in a window on the screen.
	 */
	public static void main(String[] args) {
		// Picture pic = new Picture ("Cat.jpg");
		// pic.save("Cat.jpg");
		Picture pic2 = new Picture("temple.jpg");
		pic2.save("temple.jpg");
		pic2.mirror3(92, 276);   
		pic2.show();
		// pic.grayScale();
		// pic.removeBlue();
		// pic.neg();
		// pic.pixelate();
		// pic.mirror();
		// pic.mirror2();
		// pic.invertPic();
		// pic.randomize();
		// pic.rotate();
		// pic.show();

		//Picture one = new Picture("mark2.png");
		//Picture two = new Picture("moon.jpg");
		//one.chromakey(two);
		//one.show();
		//Picture t = new Picture("beach.png");
		//Picture f = new Picture("message.png");
		//t.encode(f);
		//t.show();
		//t.decode();
		//t.show();
		Picture c = new Picture("Cat.jpg");
		c.custom();
		c.show();

		// Picture three = new Picture(200,200);
		// three.show();
		// Picture two = new Picture(three);

	}

}
