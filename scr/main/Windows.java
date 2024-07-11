package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import gameObject.Constants;
import graphics.Assets;
import input.KeyBoard;
import states.GameState;

public class Windows extends JFrame implements Runnable{

 
    private Canvas canvas;
    private Thread thread;
    private boolean running=false;
    
    private BufferStrategy bs;
    private Graphics g;
    
    private final int FPS=60;
    private double TARGETTIME=1000000000/FPS;
    private double delta=0;
    private int AVARAGEFPS=FPS;
    
    private GameState gameState;
    private KeyBoard keyBoard;
    
    public Windows() {
        setTitle("Space Invader");
        setSize(Constants.ANCHO,Constants.ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        
        canvas = new Canvas();
        
        keyBoard= new KeyBoard();
        
        canvas.setPreferredSize(new Dimension(Constants.ANCHO,Constants.ALTO));
        canvas.setMaximumSize(new Dimension(Constants.ANCHO,Constants.ALTO));
        canvas.setMinimumSize(new Dimension(Constants.ANCHO,Constants.ALTO));
        canvas.setFocusable(true);
   
        add(canvas);
        canvas.addKeyListener(keyBoard);
        
        setVisible(true);
        
   }
   
   
   public static void main(String[] args) {
        // TODO code application logic here
        new Windows().start();
    }
   
   
   private void update(){
       gameState.update();
       keyBoard.update();
   }
   
   private void draw(){
       bs=canvas.getBufferStrategy();
       if(bs== null){
           canvas.createBufferStrategy(3);
           return;
       }
       g=bs.getDrawGraphics();
       
       //-----------------------------------------
      
       g.setColor(Color.BLACK);
       g.fillRect(0, 0, Constants.ANCHO, Constants.ALTO);
       
       gameState.draw(g);
       
       g.setColor(Color.WHITE);
       g.drawString(""+AVARAGEFPS,40,40);
       
       //------------------------------------------
       
       g.dispose();
       bs.show();
   }

   private void init() {
	   Assets.init();
	   gameState=new GameState();
	   
   }
  
    @Override
    public void run() {
        long now =0;
        long lastTime = System.nanoTime();
        int frames=0;
        long time=0;
        
        init();
               
        while(running){
            now=System.nanoTime();
            delta += (now-lastTime)/TARGETTIME;
            time+= (now-lastTime);
            lastTime = now;
             if (delta>=1){
                 update();
                 draw();
                 delta--;
                 frames++;
             }
             if (time >=1000000000){
                 AVARAGEFPS =frames;
                 frames=0;
                 time=0;
             }
        }
        
        stop();
        
    }
    
    private void start(){
        thread=new Thread(this);
        thread.start();
        running=true;
    }
    
    private void stop(){
        try{
            thread.join();
            running=false;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
