package m;


import java.awt.Frame;//importo la clase Frame
import java.awt.Graphics;//importo la clase Graphics
import java.awt.Image;//importo la clase Graphics
import java.awt.*;//para importar Frame y Button etc
import java.awt.event.*;
import java.awt.Font;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*try{
			BufferedImage i=ImageIO.read(getClass().getResourceAsStream("Spaceinvaders.png"));
			g.drawImage(i,100,100,100,100,null);
		}
		catch(IOException e){System.out.println("no se ha encontrado la imagen");}*/



public class P extends Frame implements WindowListener{
	private static final long serialVersionUID = 1L;
	int ANCHO=650;
	int ALTO=800;

	private BufferedImage image;

	Image img;
	Graphics g;

	P(int ancho, int alto){
		super("Space Invaders");
		setSize(ANCHO,ALTO);
		setVisible(true);
		this.ANCHO=ancho;
		this.ALTO=alto;           
		img=createImage(ANCHO,ALTO);
		g=img.getGraphics();
		g.setFont(new Font("ComicSans", Font.BOLD, 20));//Font.PLAIN
		try{
		image= ImageIO.read(getClass().getResource("Spaceinvaders.png"));
		System.out.println("ancho: "+image.getWidth());
		System.out.println("alto: "+image.getHeight());
		}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		addWindowListener(this);
		aure3();
	}
	public void update(Graphics g){
		paint(g);
	}

	public void paint(Graphics g){
		//g.setColor(Color.BLACK);
		//g.clearRect(0,0,ANCHO,ALTO);
		g.drawImage(img,0,0,null);
	}

	public void aure3(){
		g.drawImage(image,700,0,image.getWidth(),image.getHeight(), null);
		//g.drawImage(image,0,0,image.getWidth(),image.getHeight(),20,10,160,90, null);//platillo volante rojo
		//g.drawImage(image,0,0,image.getWidth(),image.getHeight(),0,250,120,90, null);
		
		//g.drawImage(image,0,200,600,700, null);
	}

	public void aure(){
		try{
			System.out.println("entro en el try");
			BufferedImage i=ImageIO.read(getClass().getResourceAsStream("Spaceinvaders.jpg"));//si comento esta linea se printea, sin no no.
			g.drawImage(i,100,100,100,100,null);
		}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		g.setColor(Color.BLUE);
		g.fillRect(0,0,100,200);
	}

	public void aure2(){
		try{
			System.out.println("entro en el try");
			image = ImageIO.read(new File("Spaceinvaders.jpg"));
			g.drawImage(image, 0, 0, this);
		}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		g.setColor(Color.BLUE);
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
		P v=new P(650,800);
		//Juego j=new Juego(v);
		//BufferedImage i=new ImageIO.read(new File("Spaceinvaders.png"));
		//v.g.drawImage(imageIO.read(new File("Spaceinvaders.png")));
		//BufferedImage i=new ImageIO.read(ImageLoader.class.getResource("Spaceinvaders.png"));
		//v.g.drawImage(i);
	}
}

