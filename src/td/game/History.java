package td.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class History implements IGame {
    Image fond;
    @Override
    public void graphics(Graphics g) {
        g.drawImage(fond, 0,0, null);
    }

    @Override
    public void update(int ticks) {

    }

    @Override
    public void init() {
        try{
            fond = ImageIO.read(new File("Images/history.png"));
        }catch(IOException e){
            System.err.println("Pas trouvé history.png");
        }
    }
}
