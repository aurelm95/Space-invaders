import java.awt.Frame;//importo la clase Frame
import java.awt.Graphics;//importo la clase Graphics
import java.awt.Image;//importo la clase Graphics
import java.awt.*;//para importar Frame y Button etc
import java.awt.event.*;
import java.awt.Font;


public class Ventana extends Frame implements WindowListener{
	private static final long serialVersionUID = 1L;
	int ANCHO;
	int ALTO;

	Image img;
	Graphics g;

	Ventana(int ancho, int alto){
		super("Space Invaders");
		this.ANCHO=ancho;
		this.ALTO=alto;
		setSize(ANCHO,ALTO);
		setVisible(true);
		img=createImage(ANCHO,ALTO);
		g=img.getGraphics();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));//Font.PLAIN
		addWindowListener(this);
		//aure();
	}
	public void update(Graphics g){
		paint(g);
	}

	public void paint(Graphics g){
		//g.setColor(Color.BLACK);
		//g.clearRect(0,0,ANCHO,ALTO);
		g.drawImage(img,0,0,null);
	}

	public void aure(){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,100,200);
	}

	// para el implements WindowListener
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowClosing(WindowEvent e){System.exit(0);}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}

	public static void main(String[] args) throws Exception{
		Ventana v=new Ventana(800,800);
		new Juego(v);
		//BufferedImage i=new ImageIO.read(new File("Spaceinvaders.png"));
		//v.g.drawImage(imageIO.read(new File("Spaceinvaders.png")));
		//BufferedImage i=new ImageIO.read(ImageLoader.class.getResource("Spaceinvaders.png"));
		//v.g.drawImage(i);
	}
}

