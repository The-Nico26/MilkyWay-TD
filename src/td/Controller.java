package td;

import td.game.Game;
import td.game.StageGame;
import td.game.Timer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener, MouseListener{

    private Frame vue;
    private Model model;
    private Timer t;
    private boolean pause;
    private Rectangle tower1 = new Rectangle(70,0,50,50), tower2 = new Rectangle(130,0,50,50), tower3 = new Rectangle(180,0,50,50), tower4 = new Rectangle(240,0,50,50);

    public Controller(Model model, Frame vue){
        this.model = model;
        this.vue = vue;
        t = Timer.getInstance(this);
    }

    public void update(int ticks){
        if(pause) return;
        model.getGame().update(ticks);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R){
            model.setState(model.getState());
            return;
        }else if(e.getKeyCode() == KeyEvent.VK_P){
            pause = !pause;
            return;
        }else if(e.getKeyCode() == KeyEvent.VK_T){
            Sound.getInstance().replay();
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            Sound.getInstance().close();
        }
        switch(model.getState()){
            case MENU:
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    model.setState(StageGame.HISTORY);
                }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
                break;
            case HISTORY:
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    model.setState(StageGame.GAME);
                    Timer.getInstance().up();
                }
                break;
            case GAME:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    model.getScore().add(((Game)model.getGame()).getScore());
                    model.setState(StageGame.GAMEOVER);
                }
                break;
            case GAMEOVER:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    model.setState(StageGame.MENU);
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseClickX = e.getX(), mouseClickY = e.getY()-28-Main.DECAL;
        Point ee = new Point((int)e.getX(), (int)e.getY());
        if(model.getGame() instanceof Game) {
            if (tower1.contains(ee)) {
                ((Game) model.getGame()).setTowerSelect(0);
            } else if (tower2.contains(ee)) {
                ((Game) model.getGame()).setTowerSelect(1);
            } else if (tower3.contains(ee)) {
                ((Game) model.getGame()).setTowerSelect(2);
            } else if (tower4.contains(ee)) {
                ((Game) model.getGame()).setTowerSelect(3);
            }
        }

        int mouseX = mouseClickX/Main.CASET, mouseY = mouseClickY/Main.CASET;

        System.out.println(mouseX+"|"+mouseY);

        if(model.getMapE()[mouseX][mouseY] == 1){
            ((Game)model.getGame()).addTower(mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isPause(){
        return pause;
    }
}
