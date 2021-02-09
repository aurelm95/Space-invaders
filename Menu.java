package m;


import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.event.*; 

import javax.imageio.ImageIO;

public class Menu implements MouseListener,KeyListener{
	// Ventana
	Ventana v;
	
	public BufferedImage fondo;
	
	Boton b1;
	Boton b2;
	
	Boton b3;
	Boton b4;
	
	public int pantalla=0;//esta variable detecta en que pantalla del menu se esta
	// 0=princial
	// 1=nueva_partida

	Menu(Ventana ventana){
		this.v=ventana;
		v.addMouseListener(this);
		try{fondo=ImageIO.read(getClass().getResource("portada.jpg"));}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		v.g.drawImage(fondo,0,0,v.ANCHO,v.ALTO,null);
		v.g.setColor(Color.WHITE);
		v.g.setFont(new Font("ComicSans", Font.BOLD, 20));//Font.PLAIN
		principal();		
	}
	
	void principal() {
		b1=new Boton((v.ANCHO-280)/2,200,280,50,"Nueva partida",v.g);
		b2=new Boton((v.ANCHO-280)/2,350,280,50,"Cargar partida",v.g);
		v.repaint();
	}
	
	void nueva_partida() {
		v.g.drawImage(fondo,0,0,v.ANCHO,v.ALTO,null);
		b3=new Boton((v.ANCHO-280)/2,200,280,50,"Jugar",v.g);
		b4=new Boton((v.ANCHO-280)/2,350,280,50,"Armería",v.g);
		v.repaint();
	}
	


	public void keyPressed(KeyEvent e){
	}

	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}


	@Override
	public void mouseClicked(MouseEvent m) {		
		if(pantalla==0 && b1.clicado(m.getX(), m.getY())) {
			System.out.println(b1.texto);
			pantalla=1;
			nueva_partida();
			return ;
		}
		if(pantalla==0 && b2.clicado(m.getX(), m.getY())) {
			System.out.println(b2.texto);
		}
		if(pantalla==1 && b3.clicado(m.getX(), m.getY())) {
			System.out.println(b3.texto);
			v.removeMouseListener(this);
			new Juego(v);
		}
		if(pantalla==1 && b4.clicado(m.getX(), m.getY())) {
			System.out.println(b4.texto);
		}
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception{
		Ventana v=new Ventana(800,800);
		new Menu(v);
	}
}
