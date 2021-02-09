import java.awt.Color;
import java.awt.Graphics;//importo la clase Graphics
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Nave{
	int x;
	int y;
	static int ancho=40;//tiene k ser el alto por 1.35
	static int alto=30;
	int vel=0;
	boolean vivo=true;
	int vidas;
	int tipo;
	

	public BufferedImage image;
	public BufferedImage explosion;
	BufferedImage dibujos[][]=new BufferedImage[4][2];// 4 tipos de naves 2 aspectos por nave
	
	Color colores[]=new Color[3];
	Color c=Color.WHITE;
	
	
	Nave(int x, int y, int tipo, int vidas){
		this.x=x;this.y=y;this.tipo=tipo;this.vidas=vidas;
		colores[0]=Color.WHITE;
		colores[1]=Color.RED;
		colores[2]=Color.YELLOW;
		try{
			image=ImageIO.read(getClass().getResource("escudos.png"));
			dibujos[0][0]=image.getSubimage(4,160,55-4,191-160);
			explosion=image.getSubimage(84,200,135-84,227-200);
			image=ImageIO.read(getClass().getResource("Spaceinvaders.png"));			
			dibujos[1][0]=image.getSubimage(15,100,95,70);
			dibujos[1][1]=image.getSubimage(130,100,95,70);
			dibujos[2][0]=image.getSubimage(230,100,95,70);
			dibujos[2][1]=image.getSubimage(325,100,95,70);
			dibujos[3][0]=image.getSubimage(176,18,271-176,81-18);
			dibujos[3][1]=image.getSubimage(296,18,391-296,81-18);
			//System.out.println("ancho: "+image.getWidth());
			//System.out.println("alto: "+image.getHeight());
			
			
		}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
	}


	public void dibuja(Graphics g, int aspecto){
		if(tipo!=0 && c!=colores[vidas-1]) {
			cambiar_color(dibujos[tipo][0],colores[vidas-1]);
			cambiar_color(dibujos[tipo][1],colores[vidas-1]);
			c=colores[vidas-1];
		}
		g.drawImage(dibujos[tipo][aspecto],x,y,ancho,alto,null);
	}
	public void cambiar_color(BufferedImage I, Color c) {
		int rgb=c.getRGB();
		//BufferedImage R=I;
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
	public void explota(Graphics g){g.drawImage(explosion,x,y,ancho,alto,null);}
}
