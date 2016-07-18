  import java.awt.Graphics;
  import java.awt.Graphics2D;
  import java.awt.Rectangle;
  import javax.swing.JComponent;
  
  /**
     This component displays a rectangle that can be moved. 
  */
  public class RectangleComponent extends JComponent
  {  
     private static final int BOX_X = 100;
     private static final int BOX_Y = 100;
     private static final int BOX_WIDTH = 20;
     private static final int BOX_HEIGHT = 30;
     private String MoveLR;
     private String MoveUD;
     private int curX;
     private int curY;
     
     private Rectangle box;
  
     public RectangleComponent()
     {  
    	 curX=BOX_X;
    	 curY=BOX_Y;
    	 MoveLR="right";
    	 MoveUD="down";
        // The rectangle that the paintComponent method draws
        box = new Rectangle(BOX_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT);         
     }
     public void paintComponent(Graphics g)
      {  
         Graphics2D g2 = (Graphics2D) g;      
         g2.draw(box);
      }    
       /**
          Moves the rectangle to the given location.
          @param x the x-position of the new location
          @param y the y-position of the new location
       */
       public void moveBy(int x, int y)
       {
    	   if (MoveLR.equals("right") && box.getX()+BOX_WIDTH>285)
    		   MoveLR="left";
    	   if (MoveLR.equals("left") && box.getX()<0)
    		   MoveLR="right";
    	   if (MoveUD.equals("up") && box.getY()<0)
    		   MoveUD="down";
    	   if (MoveUD.equals("down") && box.getY()+BOX_HEIGHT>370)
    		   MoveUD="up";
    	   
    	   if (MoveLR.equals("right"))
    		   x=x;
    	   if (MoveLR.equals("left"))
    		   x=-x;
    	   if (MoveUD.equals("up"))
    		   y=-y;
    	   if (MoveUD.equals("down"))
    		   y=y;
    	   
    	   box.translate(x, y);
    	   repaint();      
       }
 } 