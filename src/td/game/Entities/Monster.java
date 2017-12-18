package td.game.Entities;

import td.Main;

import java.awt.*;

public class Monster implements IEntitie, Cloneable {
    public int damage;
    public int life;
    public int lifeM;
    public int x;
    public int y;
    public int vitesse;
    public int spawn;
    private int deplacement = 0;
    public Integer[][] map;
    public String name;
    public Image image;
    public int argent;
    public int score;

    public Monster(Integer[][] m, Image image){
        this.image = image;
    }

    public void init(Integer[][] m){
        map = new Integer[Main.CASEX][Main.CASEY];
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++){
                map[x][y] = m[x][y];
            }
        }
    }

    public Monster clon(Integer[][] map){
        Monster m = null;
        try {
            m = (Monster) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        m.init(map);
        return m;
    }

    public void setSpawn(int x, int y){
        this.x = x;
        this.y = y;
        map[this.x][this.y] = 0;
    }
    @Override
    public void graphics(Graphics g) {
        g.drawImage(image, x*Main.CASET-8, Main.DECAL+y*Main.CASET-8, 32,32,null);
        g.setColor(Color.red);
        g.fillRect(x*Main.CASET-15, y*Main.CASET+25, 50,5);
        g.setColor(Color.GREEN);
        g.fillRect(x*Main.CASET-15, y*Main.CASET+25, (life/lifeM)*50,5);
        g.setColor(Color.black);
    }

    @Override
    public void update(int ticks) {
        if(deplacement == 0){
            deplacement();
            deplacement = vitesse;
        }else {
            deplacement--;
        }
    }

    private void deplacement(){
        if(isPossible(x, y-1, 3)){
            y--;
        }else if(isPossible(x, y+1, 3)){
            y++;
        }else if(isPossible(x-1, y, 3)){
            x--;
        }else if(isPossible(x+1, y, 3)){
            x++;
        }else if(isPossible(x, y-1, 2)){
            y--;
        }else if(isPossible(x, y+1, 2)){
            y++;
        }else if(isPossible(x-1, y, 2)){
            x--;
        }else if(isPossible(x+1, y, 2)){
            x++;
        }
        map[x][y] = 0;
    }

    private boolean isPossible(int x, int y, int number) {
        return x >= 0 && y >= 0 && x < map.length && y < map[0].length && (map[x][y] == number || map[x][y] == 4);
    }

}
