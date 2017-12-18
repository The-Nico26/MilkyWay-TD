package td.game;

import td.Controller;

public class Timer implements Runnable {
    private static Timer timer;
    private Thread t;
    private int time = 0;
    private boolean isAlive = true;
    private int seconds = 0;

    private Controller controller;

    private Timer(){}

    public static Timer getInstance(){
        return Timer.getInstance(null);
    }

    public static Timer getInstance(Controller controller){
        if(controller == null){
            return timer;
        }
        if(timer == null){
            timer = new Timer();
            timer.controller = controller;
        }
        return timer;
    }

    public void up(){
        t = new Thread(timer);
        t.start();
    }

    public static void reinit(){
        Timer.getInstance().time  = 0;
        Timer.getInstance().isAlive = false;
    }

    public int getSeconds(){
        return Math.round(seconds/2);
    }
    @Override
    public void run() {
        if(controller == null){
            System.err.println("Controller non défini");
            return;
        }
        int n = 0;
        try {
            while (isAlive) {
                n++;
                if(!controller.isPause()) {
                    if (n % 10 == 0) {
                        time++;
                        controller.update(time);
                    }
                    if(n % 60 == 0) {
                        seconds++;
                    }
                }

                if(n % 30 == 0) {
                    controller.getModel().changed();
                }
                if(n % 60 == 0) {
                    n = 1;
                }
                Thread.sleep(10);
            }

        }catch(InterruptedException e){
            System.err.println("Erreur d'horloge");
        }
    }
}
