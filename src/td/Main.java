package td;

import td.game.StageGame;

import java.awt.*;

public class Main {

    public static final int WIDTH  = 1207;
    public static final int HEIGHT = 745;
    public static final int CASEX = 75;
    public static final int CASEY = 42;
    public static final int CASET = 16;
    public static final int DECAL = 45;

    public static void main(String[] args) {
        Model model = new Model(StageGame.MENU);
        Frame view = new Frame(model, new Dimension(WIDTH, HEIGHT), "Milkyway TD");
        Controller c = new Controller(model, view);
        view.setController(c);
    }
}
