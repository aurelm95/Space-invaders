
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;


public class Juego implements KeyListener, MouseListener{
	// Ventana
	/*static int v_alto=800;
	static int v_ancho=800;
	Ventana v=new Ventana(v_ancho,v_alto);*/
	static int v_alto=800;
	static int v_ancho=800;
	Ventana v;
	public BufferedImage espacio;
	

	// Naves enemigas		
	static int npf=10; // numero de naves por fila
	static int filas=5;// filas de naves
	static int top=100; // distancia al top de la ventana de la fila superior de naves
	static int left=40;// distancia al margen por la izquierda
	static int dv=10;// distancia vertical entre naves
	static int dh=10;// distancia horizontal entre naves
	static int cooldown_invaders=50;// numero de frames de cooldown para disparo de los invaders
	int cic=0;//cooldown invaders contador
	int invaders_vivos=npf*filas;
	ArrayList<Nave> naves = new ArrayList<>();
	ArrayList<Bala> balas_invaders =new ArrayList<>();
	int desp=0;//contador para el desplazamiento horizontal de las naves
	static int despx=2;//desplazamiento horiontal, coincide con la velocidad de la nave
	static int despy=10;//desplazamiento vertical
	static int avx=(v_ancho-2*left-npf*Nave.ancho-(npf-1)*dh)/despx;//numero de avances horizontales antes de bajar
	static int cambio_de_imagen=20;//numero de frames necesarios para cambiar de aspecto de los invaders para simular el movimiento
	int cdic=0;//contador para la variable de arriba
	int asp=0;//aspecto del invader. sera 0 o 1. se ira cambiando en funcion de las variables de arriba
	int bajo=top+filas*(Nave.alto+dv);//parte mas baja de los aliens
	static int limite=500;


	// Jugador
	static int jugx=v_ancho/2-20;//menos la mitad del ancho de la nave
	static int jugy=600;
	static int vidas=3;
	Nave jugador=new Nave(jugx,jugy,0,vidas);
	static int cooldown_jugador=10;
	int cjc=0;//contador cooldown jugador
	int puntuacion=0;
	static int ds=5;//distancia del jugador al suelo
	static int dt=25;//distancia del suelo al texto
	ArrayList<Bala> balas_jugador =new ArrayList<>();
	
	
	// Escudo
	//ArrayList<Escudo> escudos = new ArrayList<>();
	static int e_d=(v_ancho-3*3*Escudo.ancho)/4;//distancia entre escudos
	static int e_l=e_d;//distancia left del margen
	static int e_y=limite+Nave.ancho;//pos y de los escudos
	Escudo escudos[][]=new Escudo[3][5];
	
	
	// Menu
	public BufferedImage fondo;	
	Boton b1;
	Boton b2;	
	Boton b3;
	Boton b4;	
	public int pantalla=0;//esta variable detecta en que pantalla del menu se esta
	// 0=princial
	// 1=nueva_partida

	// Sonidos
	/*Clip s_disparo;	
	Clip s_musica;	
	Clip s_muerte;*/
	

	Juego(Ventana ventana){
		this.v=ventana;		
		this.v_ancho=ventana.ANCHO;
		this.v_alto=ventana.ALTO;
		System.out.println("Ventana: "+this.v_ancho+"x"+this.v_alto);
		v.addKeyListener(this);
		v.addMouseListener(this);
		try{
			espacio=ImageIO.read(getClass().getResource("espacio.jpeg"));
			fondo=ImageIO.read(getClass().getResource("portada.jpg"));

			/*s_disparo=AudioSystem.getClip();	
			s_musica=AudioSystem.getClip();	
			s_muerte=AudioSystem.getClip();
			AudioInputStream a1=AudioSystem.getAudioInputStream(new File("shoot.wav").toURI().toURL());
			s_disparo.open(a1);
			AudioInputStream a2=AudioSystem.getAudioInputStream(new File("musica.wav").toURI().toURL());
			s_musica.open(a2);
			AudioInputStream a3=AudioSystem.getAudioInputStream(new File("invaderkilled.wav").toURI().toURL());
			s_muerte.open(a3);*/
		}
		catch(Exception e){System.out.println("no se ha encontrado la imagen");}
		//menu();
		//nueva_partida();
		ejecuta();
	}
	
	// funcion para el menu
	void menu(){
		v.g.drawImage(fondo,0,0,v.ANCHO,v.ALTO,null);
		v.g.setColor(Color.WHITE);
		v.g.setFont(new Font("ComicSans", Font.BOLD, 20));//Font.PLAIN
		principal();
	}
	// funcion para el menu	
	void principal() {
		b1=new Boton((v.ANCHO-280)/2,200,280,50,"Nueva partida",v.g);
		b2=new Boton((v.ANCHO-280)/2,350,280,50,"Cargar partida",v.g);
		v.repaint();
	}
	// funcion para el menu	
	void nueva_partida() {
		v.g.drawImage(fondo,0,0,v.ANCHO,v.ALTO,null);
		b3=new Boton((v.ANCHO-280)/2,200,280,50,"Jugar",v.g);
		b4=new Boton((v.ANCHO-280)/2,350,280,50,"Armeria",v.g);
		v.repaint();
	}

	void ejecuta(){
		System.out.println("Comienza el juego");
		v.g.clearRect(0, 0, v.ANCHO, v.ALTO);
		inicializa();
		for(int k=0;k<3;k++){
			inicializa_nivel(k+1);
			while(invaders_vivos>0 && bajo<limite && jugador.vidas>0){
				//System.out.println("Bucle");
				calculaMovimientos();
				compruebaChoques();
				reprinteaPantalla();
				//if(invaders_vivos==0){break;}
				try{
					Thread.sleep(20);//sleep de 20 ms
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}				
			}
			if(bajo>limite || jugador.vidas==0){break;}
		}
		game_over();
	}

	void inicializa(){
		for(int k=0;k<3;k++){
			escudos[k][0]=new Escudo((e_d+3*Escudo.ancho)*k+e_l,e_y,4);
			escudos[k][1]=new Escudo((e_d+3*Escudo.ancho)*k+e_l,e_y-Escudo.alto,1);
			escudos[k][2]=new Escudo((e_d+3*Escudo.ancho)*k+e_l+Escudo.ancho,e_y-Escudo.alto,0);
			escudos[k][3]=new Escudo((e_d+3*Escudo.ancho)*k+e_l+2*Escudo.ancho,e_y-Escudo.alto,2);
			escudos[k][4]=new Escudo((e_d+3*Escudo.ancho)*k+e_l+2*Escudo.ancho,e_y,3);
		}		
	}

	void inicializa_nivel(int nivel){
		naves.removeAll(naves);
		balas_jugador.removeAll(balas_jugador);
		balas_invaders.removeAll(balas_invaders);
		invaders_vivos=npf*filas;
		sonido("musica.wav");
		//s_musica.start();
		for(int k=0;k<npf*filas;k++){
			Nave n=new Nave(left+(k%npf)*(dh+Nave.ancho),top+(k/npf)*(dv+Nave.alto),1+(k/npf)%3,nivel);
			naves.add(n);
		}
		cic=0;//cooldown invaders contador
		invaders_vivos=npf*filas;
		cdic=0;//contador para la variable de cambio de imagen
		cjc=0;//contador cooldown jugador
		desp=0;//contador para el desplazamiento horizontal de las naves

		//dificultad
		cooldown_invaders=50/nivel;
		despx=2+nivel-1;
		despy=5+nivel-1;
		avx=(v_ancho-2*left-npf*Nave.ancho-(npf-1)*dh)/despx;
		
	}

	void calculaMovimientos(){
		if(desp<avx){
			for(Nave n: naves){n.x+=despx;}
			//System.out.println("izquierda");
		}
		else{
			for(Nave n: naves){n.x-=despx;}
			//System.out.println("derecha");
		}
		desp+=1;
		if(desp>=2*avx){desp=0;}
		if(desp==0 || desp==avx){
			for(Nave n: naves){
				n.y+=despy;			
			}
		}
		if(jugador.x+jugador.vel>5 && jugador.x+jugador.vel+jugador.ancho<v_ancho-5){
			jugador.x+=jugador.vel;
		}
		for(Bala b:balas_jugador){
			b.y+=b.vel;
		}
		for(Bala b:balas_invaders){
			b.y+=b.vel;
		}
		if(cic==0){
			int r=new Random().nextInt(invaders_vivos);
			//System.out.println(r);
			int cont=0;
			for(Nave n:naves){
				if(n.vivo==true){cont+=1;}
				if(cont==r){balas_invaders.add(new Bala(n.x+n.ancho/2,n.y+n.alto,4));break;}
			}
			cic+=1;
		}
		else{cic+=1;if(cic==cooldown_invaders){cic=0;/*System.out.println(balas_invaders.size());System.out.println(balas_jugador.size());*/}}
		cjc+=1;//cooldown jugador
	}

	void compruebaChoques(){
		/*for(int k=0;k<balas_jugador.size();k++){
			if(balas_jugador.get(k).y<top){balas_jugador.remove(balas_jugador.get(k));System.out.println(balas_jugador.get(k).y);}
		}*/
		
		try{
			if(balas_jugador.get(0).y<0){balas_jugador.remove(balas_jugador.get(0));}
			if(balas_jugador.get(0).vivo==false){balas_jugador.remove(balas_jugador.get(0));}
			if(balas_invaders.get(0).y>jugy+ds){balas_invaders.remove(balas_invaders.get(0));}
			if(balas_invaders.get(0).vivo==false){balas_invaders.remove(balas_invaders.get(0));}
		}
		catch(Exception e){}
		//System.out.printl("balas_jug: "+balas_jugador.size()+" balas_invaders: "+balas_invaders.size());
		for(int k=naves.size()-1;k>-1;k--){
			if(naves.get(k).vivo==false){naves.remove(naves.get(k));}
			else{if(naves.get(k).y+Nave.alto>bajo) {bajo=naves.get(k).y+Nave.alto;}}
		}
		try{
		for(Bala b:balas_jugador){
			if(b.vivo==true){
				for(Nave n:naves){
					if(b.y>n.y && b.y<n.y+n.alto && b.x>n.x && b.x<n.x+n.ancho && n.vivo==true){
						//n.vivo=false;
						n.vidas-=1;
						if(n.vidas==0){n.vivo=false;invaders_vivos-=1;puntuacion+=20*n.tipo;}
						b.vivo=false;
						sonido("invaderkilled.wav");
						//s_muerte.start();
						continue;
					}
					if(b.y+b.alto>n.y && b.y+b.alto<n.y+n.alto && b.x>n.x && b.x<n.x+n.ancho && n.vivo==true){
						//n.vivo=false;
						n.vidas-=1;
						if(n.vidas==0){n.vivo=false;invaders_vivos-=1;puntuacion+=20*n.tipo;}
						b.vivo=false;
						sonido("invaderkilled.wav");
						//s_muerte.start();
						continue;
					}
				}
			}
			if(b.vivo==true){
				for(int k=0;k<3;k++){
					for(int i=0;i<5;i++){
						if(b.y>=escudos[k][i].y && b.y<=escudos[k][i].y+escudos[k][i].alto && b.x>=escudos[k][i].x && b.x<=escudos[k][i].x+escudos[k][i].ancho && escudos[k][i].vivo==true){
							escudos[k][i].vidas-=1;
							if(escudos[k][i].vidas==0){escudos[k][i].vivo=false;}
							b.vivo=false;
							continue;
						}
						if(b.y+b.alto>=escudos[k][i].y && b.y+b.alto<=escudos[k][i].y+escudos[k][i].alto && b.x>=escudos[k][i].x && b.x<=escudos[k][i].x+escudos[k][i].ancho && escudos[k][i].vivo==true){
							escudos[k][i].vidas-=1;
							if(escudos[k][i].vidas==0){escudos[k][i].vivo=false;}
							b.vivo=false;
							continue;
						}
					}
				}
			}
			if(b.vivo==true){
				for(Bala bi:balas_invaders){
					if(bi.vivo==true){
						if(b.y<=bi.y){
							if(b.x>=bi.x && b.x<bi.x+bi.ancho){
								b.vivo=false;
								bi.vivo=false;
								sonido("invaderkilled.wav");
								//s_muerte.start();
							}
							else if(b.x+b.ancho>=bi.x && b.x+b.ancho<bi.x+bi.ancho){
								b.vivo=false;
								bi.vivo=false;
								sonido("invaderkilled.wav");
								//s_muerte.start();
							}
						}
					}
				}
			}
			
		}
		for(Bala b:balas_invaders){
			if(b.vivo==true){
				if(b.y>jugador.y && b.y<jugador.y+jugador.alto && b.x>jugador.x && b.x<jugador.x+jugador.ancho){
					b.vivo=false;
					jugador.vidas-=1;
					continue;
				}
				if(b.y+b.alto>jugador.y && b.y+b.alto<jugador.y+jugador.alto && b.x>jugador.x && b.x<jugador.x+jugador.ancho){
					b.vivo=false;
					jugador.vidas-=1;
					continue;
				}
				if(b.y>jugador.y+jugador.alto+ds){b.vivo=false;}
			}
			if(b.vivo==true){
				for(int k=0;k<3;k++){
					for(int i=0;i<5;i++){
						if(b.y>=escudos[k][i].y && b.y<=escudos[k][i].y+escudos[k][i].alto && b.x>=escudos[k][i].x && b.x<=escudos[k][i].x+escudos[k][i].ancho && escudos[k][i].vivo==true){
							escudos[k][i].vidas-=1;
							if(escudos[k][i].vidas==0){escudos[k][i].vivo=false;}
							b.vivo=false;
							continue;
						}
						if(b.y+b.alto>=escudos[k][i].y && b.y+b.alto<=escudos[k][i].y+escudos[k][i].alto && b.x>=escudos[k][i].x && b.x<=escudos[k][i].x+escudos[k][i].ancho && escudos[k][i].vivo==true){
							escudos[k][i].vidas-=1;
							if(escudos[k][i].vidas==0){escudos[k][i].vivo=false;}
							b.vivo=false;
							continue;
						}
					}
				}
			}
						
		}
		}//fin try
		catch(Exception e){}
	}
	void reprinteaPantalla(){
		//System.out.println("reprinteo la ventana");
		v.g.setColor(Color.BLACK);
		
		v.g.fillRect(0,0,v_ancho,v_alto);
		v.g.drawImage(espacio,0,0,v_ancho,v_alto,null);
		//v.g.drawLine(50,50,300,y);
		/*for(int k=0;k<npf*filas;k++){
			naves.get(k).dibuja(v.g);
		}*/
		v.g.setColor(Color.GREEN);
		cdic+=1;
		if(cdic==cambio_de_imagen){asp=(asp+1)%2;cdic=0;}
		for(Nave n: naves){
			if(n.vivo==true){n.dibuja(v.g,asp);}
			else{n.explota(v.g);}
		}
		v.g.setColor(Color.RED);
		for(Bala b:balas_invaders){
			if(b.vivo==true){b.dibuja(v.g);}
		}
		v.g.setColor(Color.GREEN);
		for(Bala b:balas_jugador){
			if(b.vivo==true){b.dibuja(v.g);}
		}
		for(int k=0;k<3;k++){
			for(int i=0;i<5;i++){
				if(escudos[k][i].vivo==true){escudos[k][i].dibuja(v.g);}
			}
		}
		v.g.setColor(Color.BLUE);
		jugador.dibuja(v.g,0);
		v.g.setColor(Color.WHITE);
		v.g.drawLine(0,jugador.y+jugador.alto+ds,v_ancho,jugador.y+jugador.alto+ds);
		v.g.drawString("Vidas restantes: "+jugador.vidas, 5, jugador.y+jugador.alto+ds+dt);
		v.g.drawString("Puntuacion: "+puntuacion, 5, 70);
		v.g.setColor(Color.BLUE);
		//v.g.drawLine(left,0,left,v_alto);
		//v.g.drawLine(v_ancho-left,0,v_ancho-left,v_alto);
		v.repaint();
	}

	public void game_over(){
		System.out.println("GAME OVER");
		v.g.setColor(Color.BLACK);
		v.g.fillRect(0,0,v.ANCHO,v.ALTO);
		v.g.setColor(Color.WHITE);
		v.g.setFont(new Font("ComicSans", Font.BOLD, 50));
		v.g.drawString("GAME OVER",200,300);
		v.g.setFont(new Font("ComicSans", Font.BOLD, 40));
		v.g.drawString("Tu puntuacion: "+puntuacion,200,400);
		try{
			BufferedReader d = new BufferedReader(new FileReader("puntuacion.txt"));
			int maxp=Integer.parseInt(d.readLine());
			if(puntuacion>maxp){
				maxp=puntuacion;
				DataOutputStream dos = new DataOutputStream(new FileOutputStream("puntuacion.txt"));
				dos.writeBytes(Integer.toString(maxp));
			}
			
			v.g.drawString("Record actual: "+maxp,200,500);
		}
		catch(Exception e){}
		
		v.repaint();
	}
 
	public void keyPressed(KeyEvent e) {      
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_LEFT) {
			jugador.vel=-10;
		}
		if (key==KeyEvent.VK_RIGHT) {
			jugador.vel=10;
		}
		if (key==KeyEvent.VK_SPACE && cjc>cooldown_jugador) {
			balas_jugador.add(new Bala(jugador.x+jugador.ancho/2,jugador.y-jugador.alto,-5));
			sonido("shoot.wav");
			//s_disparo.start();
			cjc=0;
			//System.out.println("espacio");
		}
	}

	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_LEFT) {
			jugador.vel=0;
		}
		if (key==KeyEvent.VK_RIGHT) {
			jugador.vel=0;
		}
	}
	public void keyTyped(KeyEvent e) {
	}
	

	public static void main(String[] args) throws Exception{
		Ventana v=new Ventana(800,800);
		//Menu m=new Menu(v);
		Juego j=new Juego(v);
		/*
		cosas por hacer:
			*quizas hacer que dure mas la imagen de la explosion
			*poner super ataque
			*hacer menu con loggin y tienda
			*platillo rojo
			solo se puede hasta lvl 3
		*/
		//System.out.println(Random.nextInt(10));
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
			pantalla=2;
			ejecuta();
			pantalla=1;
		}
		if(pantalla==1 && b4.clicado(m.getX(), m.getY())) {
			System.out.println(b4.texto);
		}		
	}

	public void sonido(String nombre){
		try{
			AudioInputStream a=AudioSystem.getAudioInputStream(new File(nombre).toURI().toURL());
			Clip clip=AudioSystem.getClip();
			clip.open(a);
			clip.start();
		}
		catch(Exception e){System.out.println(e);}
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
}
