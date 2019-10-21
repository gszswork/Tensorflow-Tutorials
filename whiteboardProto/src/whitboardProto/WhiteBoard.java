package whitboardProto;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat;
import java.util.*;
import java.awt.font.GlyphVector;

public class WhiteBoard extends JFrame
{
	
		JButton brushBut, lineBut, ellipseBut, rectBut, strokeBut, fillBut, rubberBut, circleBut, typeBut;
		Graphics2D graphics2D;
		//global variable
		String action ="brush";
		Color strokeColor=Color.BLACK, fillColor=Color.BLACK;
		int brushSize=5;
        public static void main(String [] args){
        	new WhiteBoard();
        }

        public WhiteBoard(){
        	//temp UI, gonna replace probably
            this.setSize(800, 600);
            this.setTitle("Multiplayer Whiteboard LOL");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel buttonPanel = new JPanel();
            
            
            Box theBox = Box.createHorizontalBox();
            brushBut = setActionButton("brush");
            lineBut = setActionButton("line");
            ellipseBut = setActionButton("ellipse");
            rectBut = setActionButton("rectangle");
            rubberBut = setActionButton("rubber");
            circleBut =setActionButton("circle");
            strokeBut = setColorButton("strokeColor", true);
            fillBut = setColorButton("fillColor", false);
            typeBut =setActionButton("type");
            JSlider sizeSlider = new JSlider(5,50,5);
            sizeSlider.addChangeListener(new ChangeListener() {
            	public void stateChanged(ChangeEvent e) {
            		brushSize=(int) (sizeSlider.getValue());
            	}
            });
            
            theBox.add(brushBut);
            theBox.add(lineBut);
            theBox.add(ellipseBut);
            theBox.add(rectBut);
            theBox.add(strokeBut);
            theBox.add(fillBut);
            theBox.add(rubberBut);
            theBox.add(circleBut);
            theBox.add(sizeSlider);
            theBox.add(typeBut);
            buttonPanel.add(theBox);

            
            this.add(buttonPanel, BorderLayout.SOUTH);
            
            DrawingBoard potato =new DrawingBoard();
            potato.setBackground(Color.white);
            this.add(potato, BorderLayout.CENTER);
            
            
            this.setVisible(true);
            //end UI
        }
        
        //functions for making button
        public JButton setActionButton(String actionName){
        	JButton theBut = new JButton();
            theBut.setText(actionName);
            theBut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					action = actionName;
				}
            });
            
            return theBut;  
        }
        
        
        public JButton setColorButton(String buttonText, final boolean stroke){
        	JButton theBut = new JButton();
            theBut.setText(buttonText);
            
            theBut.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					
					if(stroke){
						strokeColor = JColorChooser.showDialog(null,  "Pick a Stroke", Color.BLACK);
					} else {
						fillColor = JColorChooser.showDialog(null,  "Pick a Fill", Color.BLACK);
					}
					
				}
            });
            
            return theBut;  
        }
        //end function for button

        
        //this is the drawing whitebaord
        private class DrawingBoard extends JPanel
        {
        		//arrays to store shapes and its colors, first in first out so new drawings get over old ones
                ArrayList<Shape> shapes = new ArrayList<Shape>();
                ArrayList<Color> shapeFill = new ArrayList<Color>();
                ArrayList<Color> shapeStroke = new ArrayList<Color>();
                
                Point start, end;

                
                public DrawingBoard(){
                		//implement mouse functions
                        this.addMouseListener(new MouseAdapter(){
                            public void mousePressed(MouseEvent e){
                            	if(action != "brush" && action != "rubber"){
                            	start = new Point(e.getX(), e.getY());
                            	//error prevention god knows why
                            	end=start;
                            	}
                            }

                            public void mouseReleased(MouseEvent e){
                            	if(action != "brush" && action != "rubber"){
                            	Shape s = null;
                            	if (action =="type") {
                            		String inputString= JOptionPane.showInputDialog("what u wanna type buddy?");
                            		Font f = new Font("TimesRoman", Font.PLAIN, 20);
                                    GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), inputString);
                                    s = v.getOutline(start.x, start.y);
                                    //s=drawGlyphVector(v, start.x, start.y);
                        		}

                            	else if (action == "line"){
                            		s = drawLine(start.x, start.y,end.x, end.y);
                            	} 
                            	
                            	else if (action == "ellipse"){
                            		s = drawEllipse(start.x, start.y,end.x, end.y);
                            	}
                            	
                            	else if (action == "rectangle") {
                                    s = drawRectangle(start.x, start.y,end.x, end.y);
                            	}
                            	
                            	else if(action =="circle") {
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
                          } );

                        this.addMouseMotionListener(new MouseMotionAdapter(){
                          public void mouseDragged(MouseEvent e){
                        	  if(action == "brush"){
                      			
                      			int x = e.getX();
                      			int y = e.getY();
                      			
                      			Shape s = null;
                      			
                      			
                      			strokeColor = fillColor;
                      			
                      			s = drawBrush(x,y,brushSize);
                      			System.out.println(s.toString());
                      			shapes.add(s);
                                shapeFill.add(fillColor);
                                shapeStroke.add(strokeColor);
                                  
                                  
                      		} 
                        	  else if (action == "rubber") {
                        		  int x =e.getX();
                        		  int y = e.getY();
                        		  Shape s = null;
                        			
                        			
                        			strokeColor = fillColor;
                        			
                        			s = drawBrush(x,y,brushSize);
                        			System.out.println(s.toString());
                        			shapes.add(s);
                                    shapeFill.add(Color.white);
                                    shapeStroke.add(Color.white);
                        	  }
                        	  
                        	  
                        	end = new Point(e.getX(), e.getY());
                            repaint();
                          }
                        } );
                }
                
                //function called by repaint(), i think its an override??
                public void paint(Graphics g){
                        graphics2D = (Graphics2D)g;
                        //graphics2D.setStroke(new BasicStroke(4));
                        //graphics2D.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, new float[]{1,0.4f,1.5f}, 0));

                        Iterator<Color> strokeCounter = shapeStroke.iterator();
                        Iterator<Color> fillCounter = shapeFill.iterator();
                        
                        
                        //recreate canvas
                        for (Shape s : shapes) {
                        
                        	graphics2D.setPaint(strokeCounter.next());
                        	graphics2D.draw(s);
                        	graphics2D.setPaint(fillCounter.next());
                        	graphics2D.fill(s);
                        }

                        
                }

                
                
                //functions for creating shapes
                //got off internet, need referencing?
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
        
    
}