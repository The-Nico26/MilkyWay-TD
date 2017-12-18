package td.game;

import td.Main;
import td.Model;
import td.Pan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOver implements IGame {

    public Image fond;
    private Model m;
    public GameOver(Model m){
        this.m = m;
    }

    @Override
    public void graphics(Graphics g) {
        g.drawImage(fond, 0, 0, Main.WIDTH, Main.HEIGHT, null);
        g.setFont(Pan.getFonte(100));
        g.setColor(new Color(195,2,2));
        g.drawString(""+(m.getScore().isEmpty() ? 0 : m.getScore().get(m.getScore().size()-1)),630,525);
    }

    @Override
    public void update(int ticks) {

    }

    @Override
    public void init() {
        try{
            fond = ImageIO.read(new File("Images/gameOver.png"));
        }catch(IOException e){
            System.err.println("Erreur de lire l'image");
        }
    }

}
