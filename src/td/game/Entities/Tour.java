package td.game.Entities;

import td.Main;

import java.awt.*;

public class Tour implements IEntitie, Cloneable {
    public int cost;
    public int level;
    public int damage;
    public int rayon;
    public int x;
    public int y;
    public String name;
    public Image image;
    public int sp = 3;
    public boolean tir = true;

    public Tour(){

    }

    public Tour clon(){
        Tour t = null;
        try {
            t = (Tour) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void graphics(Graphics g) {
        g.setColor(Color.red);
        g.drawOval(x*Main.CASET-(rayon*8)+16, Main.DECAL+y*Main.CASET-(rayon*8), rayon*16, rayon*16);
        g.drawImage(image, x* Main.CASET, Main.DECAL+y*Main.CASET-48, 32,64, null);
    }

    @Override
    public void update(int ticks) {
        if(ticks%sp == 0){
            tir = true;
        }
    }
}
