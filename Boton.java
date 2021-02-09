import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Boton{
	int x;
	int y;
	int ancho;
	int alto;
	String texto="";
	Graphics g;
	
	
	Boton(int x, int y, int ancho, int alto, String texto, Graphics g){
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
		this.texto=texto;
		this.g=g;
		g.setFont(new Font("ComicSans", Font.BOLD, 40));//Font.PLAIN
		g.setColor(Color.WHITE);
		g.drawRect(x, y, ancho, alto);
		g.drawString(texto, x+5, y+40);
	}
	
	public boolean clicado(int mx, int my) {
		if(mx<x+ancho && mx>x && my>y && my<y+alto){
			return true;
		}
		return false;
	}
	
}
