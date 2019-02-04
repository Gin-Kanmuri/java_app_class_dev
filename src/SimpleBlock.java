import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;

public class SimpleBlock{
  MediaTracker mt;
  Image ball; 

  int num = 3; 
  int bx, by; 
  int rx = 120, ry = 280; 
  int ballWidth = 16, ballHeight = 16; 
  int racketWidth = 40, racketHeight = 8; 
  int margin = 10; 
  int score; 
  boolean blockFlag[] = new boolean[30]; 
  int blockX[] = new int[30]; 
  int blockY[] = new int[30]; 
  int blockWidth = 16, blockHeight = 12;
  int oldx=0;
  Thread kicker = null;
  int SPEED = 4; 
  int dx,dy;
  Dimension d; 

  public void init(Applet myApplet,myCanvas canvas){
    d = canvas.getSize();
    dx = SPEED;
    dy = SPEED;

    mt = new MediaTracker(canvas);
    mt.addImage(ball,0);


    int k = 0;  
    int yy;
    for(int i = 0 ; i < 3 ; i++ ){
      yy = i * (blockHeight+3) + margin*3 ;
      for(int j = 0; j < 10 ; j++ ){
        blockX[k] = j * (blockWidth+4) + margin +2 ;
        blockY[k] = yy;
        blockFlag[k] = true; 
        k += 1;
      }
    }

    num = 3;
    score = 0;


    bx = margin + (int)(Math.random() * (float)(d.width - (margin*2+ballWidth+1)));
    by = 150;
  }

  public void mouseMoved(MouseEvent e){
    rx = e.getX();	
 
    if ( rx < margin ) rx = margin;
    if ( rx + racketWidth > d.width - margin ) rx = d.width - margin -racketWidth;
  }

  public void run(){

    try{
      mt.waitForID(0);
    } catch (InterruptedException e) {}

    racketColProcess();
    wallColProcess();
   blockColProcess();

    bx += dx;
    by += dy;
  }


  public void blockColProcess(){
    for(int i = 0 ; i < 30 ; i++ ){
      if ( blockFlag[i] == true ) {
        if ( by + ballHeight >= blockY[i] && by <= blockY[i]+blockHeight
              && bx + ballWidth >= blockX[i] && bx <= blockX[i]+blockWidth ){
           dy = - dy;   
           score += 1;
           blockFlag[i] = false; 
        }
      }
    }
  }

  public void racketColProcess(){
    if ( by + ballHeight >= ry && by + ballHeight <= ry+racketHeight && bx + ballWidth >= rx && bx <= rx+racketWidth ) {
      dy = -SPEED; 
      if ( bx < rx || bx + ballWidth > rx + racketWidth ) { 
        oldx = dx;
        if ( oldx == 0 ) { 
          if ( bx < rx ) dx = -SPEED;  
          else if ( bx + ballWidth > rx + racketWidth ) dx = +SPEED;  
        } else dx = 0; 
      }
    }
  }
  
  public void wallColProcess(){
    if (bx < 0 + margin ) dx = SPEED; 
    else if (bx + ballWidth > d.width - margin) dx = -SPEED;  
    else if (by < 0 + margin) dy = SPEED;
    else if ( by + ballHeight > d.height - margin ) { 

      bx = margin + (int)(Math.random() * (float)(d.width - (margin*2+ballWidth+1)));
      by = 150;
      --num;
    }
  }
  public void drawBackGround(Graphics g,Dimension d){
     g.setColor(Color.orange);
     g.fillRect(0,0,d.width, d.height);
     g.setColor(Color.green);
     g.fillRect(margin,margin,d.width-margin*2, d.height-margin*2);
  }

  public void drawBall(Graphics g,Canvas canvas){

    if (mt.checkID(0) == false) {
      g.setColor(Color.black);
      g.fillRect(0, 0, d.width, d.height);
      g.setColor(Color.yellow);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
      g.drawString("Loading...", 40, 50);
      return;
    }
    else g.drawImage(ball, bx, by,canvas); 
  }

  public void drawBlock(Graphics g){
 
    for(int i = 0 ; i < 30 ; i++ ){
      if ( i < 10 ) g.setColor(Color.blue);
      else if ( 10 <= i && i < 20 ) g.setColor(Color.red); 
      else if ( 20 <= i ) g.setColor(Color.pink); 

      if ( blockFlag[i] == true )
        g.fillRect(blockX[i], blockY[i], blockWidth, blockHeight);  
    }
  }
  public void drawRacket(Graphics g){
 
    g.setColor(Color.white);
    g.fillRect(rx, ry, racketWidth, racketHeight);
  }
  public void drawScore(Graphics g){
    g.setColor(Color.black);
    g.drawString("Score : "+score, 24, 24); 
    if ( num <= 0 ) {   
      g.setColor(Color.red);
      g.drawString("GAME OVER !", 40, 160);
    }else if ( score == 30 ) {  
      g.setColor(Color.black);
      g.drawString("PERFECT !", 40, 160);
    }
  }
}