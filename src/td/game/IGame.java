package td.game;

import java.awt.*;

public interface IGame {

    public void graphics(Graphics g);
    public void update(int ticks);
    public void init();
}
