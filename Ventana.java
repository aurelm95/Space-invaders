import java.awt.Frame;//importo la clase Frame
import java.awt.Graphics;//importo la clase Graphics
import java.awt.Image;//importo la clase Graphics



public class Ventana extends Frame{//la clase Ventana hereda de la clase Frame
    int ANCHO=600;
    int ALTO=600;
    
    Image img;
    Graphics g;
    
    public static void main(String[] args){
        //creo objeto de la clase ventana
        new Ventana();
    }
        Ventana(){
            this.setSize(ANCHO,ALTO);
            this.setVisible(true);
            
            img=createImage(ANCHO,ALTO);
            g=img.getGraphics();
            
            Juego juego=new Juego(this);//llamo juego a un objeto de la clase Juego
            juego.ejecuta();
        }
    public void update(Graphics g){
        paint(g);
    }
    
    public void paint(Graphics g){
        g.drawImage(img,0,0,null);
    }
}

