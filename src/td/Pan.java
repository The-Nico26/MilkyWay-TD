package td;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

public class Pan extends JPanel implements Observer {

    private Frame view;
    private static Font font;
    private static Font ttfBase;

    public Pan(Frame view){
        this.view = view;
        this.view.setResizable(false);
        this.view.addObserver(this);
    }

    public void paintComponent(Graphics g){
        cleanScreen(g);
        this.view.getModel().getGame().graphics(g);
    }

    private void cleanScreen(Graphics g){
        g.setFont(Pan.getFonte(25));
        g.setColor(Color.black);
        g.fillRect(0, 0, view.getSize().width, view.getSize().height);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
        view.revalidate();
    }

    public static Font getFonte(int size){
        if(font == null) {
            try {
                InputStream myStream = new BufferedInputStream(new FileInputStream("Images/game.ttf"));
                ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("akshar font not loaded.");
            }
        }
        font = ttfBase.deriveFont(Font.PLAIN, size);
        return font;
    }
}
