import java.awt.Graphics;//importo la clase Graphics

public class Bala{
	int x;
	int y;
	boolean vivo=true;
	static int ancho=4;
	static int alto=20;
	int vel;
	Bala(int x, int y, int vel){
		this.x=x;this.y=y;this.vel=vel;
	}


	public void dibuja(Graphics g){
		//g.setColor(Color.WHITE);
		g.fillRect(x,y,ancho,alto);
	}
}
