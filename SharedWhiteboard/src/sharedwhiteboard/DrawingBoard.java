package sharedwhiteboard;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.MouseMotionAdapter;

public class DrawingBoard extends JPanel {

	public String action="Brush";
	public Color strokeColor=Color.BLACK, fillColor=Color.BLACK;

	/**
	 * Create the panel.
	 */
	
	public Color backgroundColor;
	
	/* this part is done by Xiong */
	 ArrayList<Shape> shapes = new ArrayList<Shape>();
     ArrayList<Color> shapeFill = new ArrayList<Color>();
     ArrayList<Color> shapeStroke = new ArrayList<Color>();
     
     Point start, end;
	//JButton brushBut, lineBut, ellipseBut, rectBut, strokeBut, fillBut, rubberBut, circleBut;
	Graphics2D graphics2D;

	//global variable
	//String action ="Brush";
	
	public DrawingBoard() {
		backgroundColor = Color.white;
		
        setBackground(backgroundColor);
        setPreferredSize(new Dimension(1024, 768));
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(action == "Brush"){
          			
          			int x = e.getX();
          			int y = e.getY();
          			
          			Shape s = null;
          			
          			
          			strokeColor = fillColor;
          			
          			s = drawBrush(x,y,5);
          			System.out.println(s.toString());
          			System.out.println("strokeColor"+strokeColor);
          			shapes.add(s);
                    shapeFill.add(fillColor);
                    shapeStroke.add(strokeColor);
                      
                      
          		} 
            	  else if (action == "Eraser") {
            		  int x =e.getX();
            		  int y = e.getY();
            		  Shape s = null;
            			
            			
            			strokeColor = fillColor;
            			
            			s = drawBrush(x,y,10);
            			System.out.println(s.toString());
            			shapes.add(s);
                        shapeFill.add(Color.white);
                        shapeStroke.add(Color.white);
            	  }
            	  
            	  
            	end = new Point(e.getX(), e.getY());
                repaint();
			}
		});
		
		 
	        
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Action::::"+action);	
				System.out.println("More Colours::::"+strokeColor);
				if(action != "Brush" && action != "Eraser"){
                	start = new Point(e.getX(), e.getY());
                	//error prevention god knows why
                	end=start;
                	}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Action::::"+action);
			  	if(action != "Brush" && action != "Eraser"){
                	Shape s = null;
                	
                	if (action == "Line"){
                		s = drawLine(start.x, start.y,end.x, end.y);
                	} 
                	
                	else if (action == "Ellipse"){
                		s = drawEllipse(start.x, start.y,end.x, end.y);
                	}
                	
                	else if (action == "Rectangle") {
                        s = drawRectangle(start.x, start.y,end.x, end.y);
                	}
                	
                	else if(action =="Circle") {
                			s =drawCircle(start.x, start.y,end.x, end.y);
                	}
                	
                      
                	System.out.println(s.toString());
                	

                	shapes.add(s);
                    shapeFill.add(fillColor);
                    shapeStroke.add(strokeColor); 
                    
                      start = null;
                      end=null;
                      repaint();
			  	}
			}
		});

	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
        graphics2D = (Graphics2D)g;
        graphics2D.setStroke(new BasicStroke(4));

        Iterator<Color> strokeCounter = shapeStroke.iterator();
        Iterator<Color> fillCounter = shapeFill.iterator();
        
        
      //  recreate canvas
        for (Shape s : shapes) {
        
        	graphics2D.setPaint(strokeCounter.next());
        	graphics2D.draw(s);
        	graphics2D.setPaint(fillCounter.next());
        	graphics2D.fill(s);
        }

        
}

	
	  private Rectangle2D.Float drawRectangle(
              int x1, int y1, int x2, int y2)
      {
      	
              int x = Math.min(x1, x2);
              int y = Math.min(y1, y2);
              
              
              int width = Math.abs(x1 - x2);
              int height = Math.abs(y1 - y2);
              
              return new Rectangle2D.Float(
                      x, y, width, height);
      }
      
      
      private Ellipse2D.Float drawCircle(
              int x1, int y1, int x2, int y2)
      {
              int x = Math.min(x1, x2);
              int y = Math.min(y1, y2);
              int width = Math.abs(x1 - x2);
              int height = Math.abs(y1 - y2);
              int radius = Math.max(width, height);

              return new Ellipse2D.Float(
                      x, y, radius, radius);
      }
      
      private Line2D.Float drawLine(
              int x1, int y1, int x2, int y2)
      {

              return new Line2D.Float(
                      x1, y1, x2, y2);
      }
      
      private Ellipse2D.Float drawBrush(
              int x1, int y1, int brushSize)
      {
      	return new Ellipse2D.Float(
                  x1, y1, brushSize, brushSize);
      	
      }
      private Ellipse2D.Float drawEllipse(
              int x1, int y1, int x2, int y2)
      {
              int x = Math.min(x1, x2);
              int y = Math.min(y1, y2);
              int width = Math.abs(x1 - x2);
              int height = Math.abs(y1 - y2);

              return new Ellipse2D.Float(
                      x, y, width, height);
      }

		
		

}
