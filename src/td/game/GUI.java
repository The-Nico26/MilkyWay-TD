package td.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI {
    Game g;
    Image barre;

    public GUI(Game game){
        g = game;
    }

    public void graphics(Graphics g){
        g.setColor(Color.black);
        g.drawImage(barre, 0, -5, null);
        g.drawString(" "+this.g.getScore(), 990, 25);
        g.drawString(""+this.g.getArgent(), 790, 25);
        g.drawString((Timer.getInstance().getSeconds()/60)+" : "+(Timer.getInstance().getSeconds()%60), 580, 25);
        g.drawString(""+this.g.getLife(), 370, 25);
    }

    public void init(){
        try{
            barre = ImageIO.read(new File("Images/barre.png"));
        } catch(IOException e) {
            System.err.println("Erreur de lecture d'image");
        }
    }

}
