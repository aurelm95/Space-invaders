import java.awt.Graphics;//importo la clase Graphics
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
//https://stackoverflow.com/questions/2386064/how-do-i-crop-an-image-in-java

public class Escudo{
	int x;
	int y;
	int tipo;
	static int ancho=23;
	static int alto=ancho;
	int vel=0;
	boolean vivo=true;
	int vidas=4;
	BufferedImage image;
	BufferedImage esc[][]=new BufferedImage[5][4];

	Escudo(int x, int y, int tipo){
		this.x=x;this.y=y;this.tipo=tipo;
		try{image= ImageIO.read(getClass().getResource("escudos.png"));}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		for(int i=0;i<5;i++){
			for(int k=0;k<4;k++){//original: 28px
				esc[i][k]=image.getSubimage(4+k*28,232+i*28,23,23);
				//g.drawImage(esc[i][k],k*100,50+i*100,esc[i][k].getWidth(),esc[i][k].getHeight(),null);
			}
		}
	}


	public void dibuja(Graphics g){
		//g.setColor(new Color(255-50*vidas,0,0));
		//g.fillRect(x,y,ancho,alto);
		g.drawImage(esc[tipo][4-vidas],x,y,ancho,alto,null);//un poco chapuza lo del 4-vidas
	}
}
