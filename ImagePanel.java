package m;


import java.awt.Frame;//importo la clase Frame
import java.awt.Graphics;//importo la clase Graphics
import java.awt.Image;//importo la clase Graphics
import java.awt.*;//para importar Frame y Button etc
import java.awt.event.*;
import java.awt.Font;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class ImagePanel extends Frame implements WindowListener{
	//private static final long serialVersionUID = 1L;
	public int ANCHO;
	public int ALTO;

	private BufferedImage image;
	private BufferedImage image2;
	BufferedImage esc[][]=new BufferedImage[5][4];
	Image img;
	Graphics g;

	int d=28;
	int px=23;

	ImagePanel(int ancho, int alto){
		super("Space Invaders");
		this.ANCHO=ancho;
		this.ALTO=alto;
		setSize(ANCHO,ALTO);
		setVisible(true);		           
		img=createImage(ANCHO,ALTO);
		g=img.getGraphics();
		try{
		image= ImageIO.read(getClass().getResource("Spaceinvaders.png"));
		image2= ImageIO.read(getClass().getResource("escudos.png"));
		System.out.println("ancho: "+image2.getWidth());
		System.out.println("alto: "+image2.getHeight());
		}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		addWindowListener(this);
		BufferedImage alien=image.getSubimage(325,100,95,70);
		System.out.println("color"+Color.red.getRGB());
		cambiar_color(alien,new Color(0,0,0),Color.RED);
		//Color c = new Color(255,0,0);
	    /*alien.setRGB(50,50,c.getRGB());
		g.setColor(Color.BLACK);*/
		g.fillRect(0,0,ANCHO,ALTO);
		for(int i=0;i<5;i++){
			for(int k=0;k<4;k++){
				esc[i][k]=image2.getSubimage(4+k*d,232+i*d,px,px);
				g.drawImage(esc[i][k],k*100,300+i*100,esc[i][k].getWidth(),esc[i][k].getHeight(),null);
			}
		}
		// 4 1 0 2 3 
		g.drawImage(esc[4][0],100,100,px,px,null);
		g.drawImage(esc[1][0],100,100-px,px,px,null);
		g.drawImage(esc[0][0],100+px,100-px,px,px,null);
		g.drawImage(esc[2][0],100+2*px,100-px,px,px,null);
		g.drawImage(esc[3][0],100+2*px,100,px,px,null);
		
		g.drawImage(alien,300,300,alien.getWidth(),alien.getHeight(),null);
		
		repaint();
	}

	public void update(Graphics g){
		paint(g);
	}
	
	public void cambiar_color(BufferedImage I, Color c) {
		int rgb=c.getRGB();
		for(int k=0;k<I.getWidth();k++) {
			for(int i=0;i<I.getHeight();i++) {
				//System.out.println("color"+I.getRGB(k, i));
				if(I.getRGB(k, i)!=0) {
					//System.out.print("Cambio de "+I.getRGB(k, i)+" a ");
					I.setRGB(k, i, rgb);
					//System.out.println(""+I.getRGB(k, i));
					}
				
			}
		}
		//System.out.println("color"+Color.red.getRGB());
		//return I;
	}

	public void paint(Graphics g){
		//g.setColor(Color.BLACK);
		//g.clearRect(0,0,ANCHO,ALTO);
		g.drawImage(img,0,0,null);
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
		ImagePanel ImagePanel=new ImagePanel(650,800);
	}
}
