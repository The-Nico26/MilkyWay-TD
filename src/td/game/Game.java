package td.game;

import td.Main;
import td.Model;
import td.game.Entities.Monster;
import td.game.Entities.Tour;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game implements IGame {

    private int score = 0;
    private int compteur = 0;
    private int argent = 500;
    private int life = 100;

    private Model m;
    private GUI gui;

    private ArrayList<Monster> monsters;
    private ArrayList<Tour> towers;
    private Image select;

    private int towerSelect = 0;

    public Game(Model m){
        this.m = m;
    }

    @Override
    public void graphics(Graphics g) {
        for(int x = 0; x < Main.CASEX; x++){
            for(int y = 0; y < Main.CASEY; y++){
                g.drawImage(m.getMapI()[x][y], x*Main.CASET, Main.DECAL+y*Main.CASET, null);
                if(m.getMapE()[x][y] == 1){
                    g.drawImage(select, x*Main.CASET, Main.DECAL+y*Main.CASET, 16,16,null);
                }
            }
        }

        for(Monster monster : monsters){
            monster.graphics(g);
        }
        for(Tour tower : towers){
            tower.graphics(g);
        }
        gui.graphics(g);
    }

    @Override
    public void update(int ticks) {
        for(Monster monster : monsters){
            monster.update(ticks);
        }
        gameOVER();

        for(Tour tour : towers){
            tour.update(ticks);
            checkTower(tour);
        }

        if(ticks%10 == 0){
            int random = (int)(Math.random()*5);
            for(int r = 0; r < random; r++){
                int monsterR = (int)(Math.random()*m.getMonsters().size());
                int monsterNumber = (int)(Math.random()*2);
                for(int rr = 0; rr < monsterNumber; rr++){
                    int monsterSpwan = (int)(Math.random()*m.getSpawn().size());
                    String[] sp = m.getSpawn().get(monsterSpwan).split(";");
                    Monster mon = m.getMonsters().get(monsterR).clon(m.getMapE());
                    mon.setSpawn(Integer.parseInt(sp[0]),Integer.parseInt(sp[1]));
                    monsters.add(mon);
                }
            }
        }
    }
    public void checkTower(Tour tour){
        try {
            for (Monster monster : monsters) {
                Point a = new Point(tour.x*16+8, tour.y*16+8);
                Point b = new Point(monster.x*16+8, monster.y*16+8);
                if (distance(a, b) <= tour.rayon*12 && tour.tir) {
                    monster.life -= tour.damage;
                    tour.tir = false;
                    if(monster.life <= 0) {
                        monsters.remove(monster);
                        score += monster.score;
                        argent += monster.argent;
                    }
                }
            }
        }catch(Exception e){
            checkTower(tour);
        }
    }
    public double distance(Point p, Point p2){
        return Math.sqrt(Math.pow((p.x-p2.x),2)+Math.pow((p.y-p2.y),2));
    }

    @Override
    public void init() {
        monsters = new ArrayList<>();
        towers = new ArrayList<>();
        try{
            select = ImageIO.read(new File("Images/select.png"));
        }catch(IOException e){
            System.err.println("Image Select introuvable");
        }
        gui = new GUI(this);
        gui.init();

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCompteur() {
        return compteur;
    }

    public void setCompteur(int compteur) {
        this.compteur = compteur;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Tour> getTowers() {
        return towers;
    }

    public void setTowers(ArrayList<Tour> towers) {
        this.towers = towers;
    }

    public void addTower(int mouseX, int mouseY) {
        Tour t = m.getTours().get(towerSelect).clon();
        if(argent >= t.cost) {
            m.getMapE()[mouseX][mouseY] = 10;
            t.x = mouseX;
            t.y = mouseY;
            argent -= t.cost;
            towers.add(t);
        }
    }

    public int getLife() {
        return life;
    }

    public void gameOVER(){
        try {
            for (Monster monster : monsters) {
                if (monster.x == m.getEntreprise()[0] && m.getEntreprise()[1] == monster.y) {
                    int n = monster.damage;
                    monsters.remove(monster);
                    life -= n;

                    if (life <= 0) {
                        m.getScore().add(score);
                        m.setState(StageGame.GAMEOVER);
                        return;
                    }
                }
            }
        }catch(Exception ex){
            gameOVER();
        }
    }

    public void setTowerSelect(int towerSelect) {
        this.towerSelect = towerSelect;
    }
}
