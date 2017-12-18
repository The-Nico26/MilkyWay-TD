package td;

import td.game.*;
import td.game.Entities.Monster;
import td.game.Entities.Tour;
import td.game.IGame;
import td.game.Menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable{

    private StageGame state;
    private Game game;
    private Menu menu;
    private History history;
    private GameOver gameOver;

    private Image[][] mapI;
    private Integer[][] mapE;
    private ArrayList<String> spawn;

    private ArrayList<Tour> tours;
    private ArrayList<Monster> monsters;

    private ArrayList<Image> imgTour, imgMonster;
    private Integer[] entreprise = {0, 0};

    private ArrayList<Integer> score;

    public Model(StageGame state){
        this.state = state;
        menu = new Menu();
        gameOver = new GameOver(this);
        history = new History();
        Sound.getInstance().play("Images/menu.mp3");

        init();
    }

    public void init(){
        getGame().init();
        mapI = new Image[Main.CASEX][Main.CASEY];
        mapE = new Integer[Main.CASEX][Main.CASEY];
        imgTour = new ArrayList<>();
        imgMonster = new ArrayList<>();
        spawn = new ArrayList<>();
        monsters = new ArrayList<>();
        tours = new ArrayList<>();
        score = new ArrayList<>();

        try{
            File folder = new File("Images/Towers");
            for(File im : folder.listFiles())
                if(im.getName().startsWith("tower"))
                    imgTour.add(ImageIO.read(im));
            folder = new File("Images/Monsters");
            for(File im : folder.listFiles())
                if(im.getName().startsWith("monster"))
                    imgMonster.add(ImageIO.read(im));

        }catch(Exception e){
            System.err.println("Erreur de chargement des images");
            e.printStackTrace();
        }
        loadMap("0");
    }

    public void changed(){
        setChanged();
        notifyObservers();
    }

    public IGame getGame(){
        switch(state){
            case GAME:
                return game;
            case MENU:
                return menu;
            case GAMEOVER:
                return gameOver;
            case HISTORY:
                return history;
        }
        return null;
    }

    public StageGame getState(){
        return state;
    }

    public void setState(StageGame e){
        if(e == StageGame.GAME){
            init();
            game = new Game(this);
        }
        switch(e){
            case MENU:
                Sound.getInstance().play("Images/menu.mp3");
                break;
            case GAMEOVER:
                Sound.getInstance().play("Images/gameover.mp3");
        }
        state = e;
        getGame().init();
        changed();
    }

    public void loadMap(String level){
        try {
            BufferedImage img = ImageIO.read(new File("Images/Maps/" + level + "_map.png"));
            BufferedImage emplace = ImageIO.read(new File("Images/Maps/" + level + "_emplacement.png"));
            for(int x = 0; x < mapI.length; x++){
                for(int y = 0; y < mapI[0].length; y++){
                    mapI[x][y] = img.getSubimage(x*Main.CASET, y*Main.CASET, Main.CASET, Main.CASET);
                    int nbr = emplace.getRGB(x,y);
                    if(nbr == Color.YELLOW.getRGB()) //Emplacement pour les tours
                        mapE[x][y] = 1;
                    else if(nbr == Color.GRAY.getRGB()) //Emplacement pour le trajet
                        mapE[x][y] = 2;
                    else if(nbr == Color.WHITE.getRGB()) //Emplacement pour le choix du monster
                        mapE[x][y] = 3;
                    else if(nbr == Color.CYAN.getRGB()) { //Emplacement pour le game over : entreprise
                        entreprise = new Integer[]{x, y};
                        mapE[x][y] = 4;
                    }
                    else if(nbr == Color.RED.getRGB()){ //Emplacement pour le spawn des monstres
                        spawn.add(x+";"+y);
                        mapE[x][y] = 5;
                    }
                    else //NOTHING
                        mapE[x][y] = 0;
                }
            }
        }catch(IOException e){
            System.err.println("Impossible de lire le niveau : "+level);
        }

        loadMonster(level);
    }

    private void loadMonster(String level){
        try{
            InputStream e = new FileInputStream("Images/Maps/"+level+"_monster.txt");
            InputStreamReader iE = new InputStreamReader(e);
            BufferedReader buff = new BufferedReader(iE);
            String line = "";
            while((line=buff.readLine())!=null){
                if(line.startsWith("#"))continue;

                String[] tab = line.split(";");
                Monster m = new Monster(mapE.clone(), imgMonster.get(Integer.parseInt(tab[0])));
                m.name = tab[1];
                m.life = Integer.parseInt(tab[2])*2;
                m.vitesse = Integer.parseInt(tab[3]);
                m.spawn = Integer.parseInt(tab[4]);
                m.damage = Integer.parseInt(tab[5]);
                m.argent = Integer.parseInt(tab[6]);
                m.score = Integer.parseInt(tab[7]);
                m.lifeM = m.life;
                monsters.add(m);
            }
            buff.close();
        }catch(IOException e){
            System.err.println("Impossible de lire les fichiers");
        }
        loadTowers(level);
    }

    private void loadTowers(String level){
        try{
            InputStream e = new FileInputStream("Images/Maps/"+level+"_towers.txt");
            InputStreamReader iE = new InputStreamReader(e);
            BufferedReader buff = new BufferedReader(iE);
            String line = "";
            while((line=buff.readLine())!=null){
                if(line.startsWith("#"))continue;

                String[] tab = line.split(";");
                Tour t = new Tour();
                t.name = tab[1];
                t.cost = Integer.parseInt(tab[2]);
                t.damage = Integer.parseInt(tab[3]);
                t.level = Integer.parseInt(tab[4]);
                t.rayon = Integer.parseInt(tab[5]);
                t.image = imgTour.get(Integer.parseInt(tab[0]));
                tours.add(t);
            }
            buff.close();
        }catch(IOException e){
            System.err.println("Impossible de lire les fichiers");
        }
    }

    public Image[][] getMapI() {
        return mapI;
    }

    public void setMapI(Image[][] mapI) {
        this.mapI = mapI;
    }

    public Integer[][] getMapE() {
        return mapE;
    }

    public void setMapE(Integer[][] mapE) {
        this.mapE = mapE;
    }


    public ArrayList<Tour> getTours() {
        return tours;
    }


    public ArrayList<Monster> getMonsters() {
        return monsters;
    }


    public ArrayList<String> getSpawn() {
        return spawn;
    }

    public ArrayList<Image> getImgTour() {
        return imgTour;
    }

    public ArrayList<Image> getImgMonster() {
        return imgMonster;
    }


    public Integer[] getEntreprise() {
        return entreprise;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }
}
