package td.game;

import td.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu implements td.game.IGame {

    public Image fond;

    @Override
    public void graphics(Graphics g) {
        g.drawImage(fond, 0, 0, Main.WIDTH, Main.HEIGHT, null);

    }

    @Override
    public void update(int ticks) {

    }

    @Override
    public void init() {
        try{
            fond = ImageIO.read(new File("Images/fond2.png"));
        }catch(IOException e){
            System.err.println("Erreur de lecture d'image");
        }
    }
}
