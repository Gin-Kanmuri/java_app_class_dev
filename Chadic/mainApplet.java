import java.applet.*;
import java.awt.*;
import java.awt.event.*;
/*
  <applet code = "mainApplet" width = 860 height = 600>
  </applet>
*/
public class mainApplet extends Applet implements ActionListener{
  myCanvas canvas;
  SimpleBlock SB;
  Button b[];

  public void init(){
    setLayout(null);
    setBackground(Color.white);
    SB = new SimpleBlock();
    canvas = new myCanvas(SB);
    canvas.setBounds(10,10,220,320);  
    add(canvas);
    SB.ball = getImage(getDocumentBase(),".//image//ball.gif");
    SB.init(this,canvas);

    b = new Button[3];
    b[0] = new Button("start");
    b[1] = new Button("stop");
    b[2] = new Button("init");

    for(int i=0;i < 3;i++){
      b[i].addActionListener(this);
      b[i].setBounds(280+i*40,120,40,20);
      b[i].addActionListener(this);
      add(b[i]);
    }
    canvas.start();
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == b[0]){
      canvas.waitFlag = false;
    }else if(e.getSource() == b[1]){
      canvas.waitFlag = true;
    }else if(e.getSource() == b[2]){
      SB.init(this,canvas);
    }
  }
}