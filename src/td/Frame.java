package td;

import javax.swing.*;

import java.awt.*;
import java.util.Observer;

public class Frame extends JFrame {

    private Model model;
    private Dimension dimension;
    private Pan pan;
    private Controller controller;

    public Frame(Model model, Dimension dimension, String title){
        this.model = model;

        this.setTitle(title);
        this.setSize(dimension);
        this.pan = new Pan(this);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().add(pan);
        this.setVisible(true);
    }

    public void addObserver(Observer observer){
        model.addObserver(observer);
    }

    public void setController(Controller controller){
        this.controller = controller;
        this.addKeyListener(controller);
        this.addMouseListener(controller);
    }

    public Controller getController(){
        return controller;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Pan getPan() {
        return pan;
    }

    public void setPan(Pan pan) {
        this.pan = pan;
    }
}
