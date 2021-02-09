import java.awt.Color;

public class Juego {
    Ventana v;
    int y;
    Juego(Ventana v){
        this.v=v;
        y=50;//cosas como estas deberian ir en el inicializa
    }
    
    void ejecuta(){
        inicializa();
	System.out.println("Comienza el juego");
        while(true){
            calculaMovimientos();
            compruebaChoques();
            reprinteaPantalla();
            
            try{
                Thread.sleep(50);//sleep de 50 ms
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
    
    void inicializa(){}
    void calculaMovimientos(){
        y++;
    }
    void compruebaChoques(){}
    void reprinteaPantalla(){
        v.g.setColor(Color.BLUE);
        v.g.fillRect(0,0,100,100);
        v.g.drawLine(50,50,300,y);
        v.repaint();
    }
}
