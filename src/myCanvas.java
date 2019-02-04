/*
  myCanvas.java
*/
import java.awt.event.*;
import java.awt.*;
public class myCanvas extends Canvas implements Runnable{
  Thread th;
  Image buffer;
  Graphics bufferg;
  boolean waitFlag;
  SimpleBlock SB;
  public myCanvas(SimpleBlock SB){
    this.SB = SB;
    waitFlag = false;
    th = new Thread(this);
    addMouseMotionListener(new MyMouseMotionAdapter(SB));
  }
  public void start(){
    Dimension d =  getSize();
    buffer = createImage(d.width,d.height);
    th.start();
  }
  public void run(){
    try{
      while(true){
        if(!waitFlag){
          repaint();
          SB.run();
        }
        th.sleep(30);
      }
    }catch(Exception e){}
  }

  public void update(Graphics g){
    paint(g);
  }
  public void paint(Graphics g){
      if(bufferg == null) bufferg = buffer.getGraphics();
      Dimension d = getSize();
      SB.drawBackGround(bufferg,d);
      SB.drawBall(bufferg,this);
      SB.drawBlock(bufferg);
      SB.drawRacket(bufferg);
      SB.drawScore(bufferg);
      g.drawImage(buffer,0,0,this);
  }
}

class MyMouseMotionAdapter extends MouseMotionAdapter{
  SimpleBlock SB;
  public MyMouseMotionAdapter(SimpleBlock SB){
    this.SB = SB;
  }
  public void mouseMoved(MouseEvent e){
      SB.mouseMoved(e);
  }
}
