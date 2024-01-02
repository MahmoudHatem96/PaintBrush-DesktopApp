/*
 *File: PaintBrush.java
 *Author: Mahmoud Hatem
 *Date: December 28, 2023
 *Description: This file contains the implementation of the PaintBrush class
 */

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font; 
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;


/**
 * This class represents an PaintBrush entity and provides methods to interact with it.
 */
public class PaintBrush extends Applet{
	
	int x, y, h, w;
	ShapeOfShapes currentShape;
	ArrayList<ShapeOfShapes> arrayShapeOfShapes = new ArrayList<>();

	public int mode;
	public static final int rect = 0;
	public static final int oval = 1;
	public static final int line = 2;
	public static final int freeHand = 3;
	public static final int eraser = 4;
	public static final int clearAll = 5;
	
	Color currentColor = Color.black;
	Button btnRed = new Button("Red");
	Button btnBlue = new Button("Blue");
	Button btnGreen = new Button("Green");
	Font btnFont = new Font("Arial", Font.BOLD, 20); 

	Button btnRectangle = new Button("Rectangle");
	Button btnOval = new Button("Oval");
	Button btnLine = new Button("Line");
	Button btnFreeHand = new Button("Free Hand");
	Button btnEraser = new Button("Eraser");
	Button btnClearAll = new Button("Clear All");
	
	Checkbox chkbxSolid = new Checkbox("Fill Shape");
	public static boolean isSolid = false;
	
	
	/**
	* initializing interactive buttons for each shape and color provided above, in addition to how they respond to mouse on draw area.
	*/
	public void init(){
		add(btnRed);
		add(btnBlue);
		add(btnGreen);
		btnRed.setFont(btnFont);
		btnBlue.setFont(btnFont);
		btnGreen.setFont(btnFont);
		
		add(btnRectangle);
		btnRectangle.setFont(btnFont);
		add(btnOval);
		btnOval.setFont(btnFont);
		add(btnLine);
		btnLine.setFont(btnFont);
		add(btnFreeHand);
		btnFreeHand.setFont(btnFont);
		add(btnEraser);
		btnEraser.setFont(btnFont);
		add(btnClearAll);
		btnClearAll.setFont(btnFont);
		
		add(chkbxSolid);
		chkbxSolid.setFont(btnFont);


		
		btnRed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.red;
				repaint();
			}
		});		
		
		btnBlue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.blue;
				repaint();
			}
		});	

		btnGreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.green;
				repaint();
			}
		});

		btnRectangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = 0;
				repaint();
			}
		});
		btnOval.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = 1;
				repaint();
			}
		});
		btnLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = 2;
				repaint();
			}
		});
		btnFreeHand.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = 3;
				repaint();
			}
		});
		btnEraser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mode = 4;
				repaint();
			}
		});
		btnClearAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				arrayShapeOfShapes.clear();
				repaint();
			}
		});

		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
					x = e.getX();
					y = e.getY();
					
					
				}
			public void mouseReleased(MouseEvent e){
				switch (mode){
					case 0:
					currentShape = new Rectangle(x, y, w, h, currentColor, isSolid);
					break;
					case 1:
					currentShape = new Oval(x, y, w, h, currentColor, isSolid);
					break;
					case 2:
					currentShape = new Line(x, y, w, h, currentColor, isSolid);
					break;
					case 3:
					currentShape = new FreeHand(x, y, w, h, currentColor, isSolid);
					x=w;
					y=h;
					break;
					

				}
					arrayShapeOfShapes.add(currentShape);
					repaint();				
					
				}
		});
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent e){
				w = e.getX();
				h = e.getY();
				switch (mode){
					case 3:
					arrayShapeOfShapes.add(currentShape = new FreeHand(x, y, w, h, currentColor, isSolid));	
					x=w;
					y=h;
					break;
					case 4:
					arrayShapeOfShapes.add(currentShape = new Eraser(x, y, w, h, currentColor, isSolid));	
					x=w;
					y=h;
					break;
					
				}
				
				repaint();
			
			}
		  
		});
		
		chkbxSolid.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				Checkbox pressCheckbox = (Checkbox) e.getSource();
				if(pressCheckbox.getState()){
					isSolid = true;
				}else{
					isSolid = false;
				}
			}
		});
	}	
	/**
	* providing a color guide to show the user which color button is active and the condition to activate adding objects to the array list.
	*/
	public void paint(Graphics g) {
		g.setColor(currentColor);
		g.fillOval(350, 10, 20, 20); 
		for (ShapeOfShapes r : arrayShapeOfShapes){
			r.draw(g);
		}
	}	
}
/**
 * This class represents an ShapeOfShapes providing the constructor, abstract method for children: Rectangle, Oval, Line, FreeHand, and Eraser.
 */
abstract class ShapeOfShapes{
	
	protected int x1, y1, x2, y2;
	Color colorOfShape;
	boolean solid;
	public ShapeOfShapes(int x1, int y1, int x2, int y2, Color color, boolean solid){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.colorOfShape = color;
		this.solid = solid;
	}
	public int getX1(){
		return x1;
	}
	public void setX1(int x){
		x1 = x;
		}
		public int getX2(){
		return x2;
	}
	public void setX2(int x){
		x2 = x;
		}
		public int getY1(){
		return y1;
	}
	public void setY1(int x){
		y1 = x;
		}
		public int getY2(){
		return y2;
	}
	public void setY2(int x){
		y2 = x;
		}
	//method to be inherited to all children  
	abstract void draw(Graphics g);
}
/**
 * This class represents a Rectangle, a child class declaring the way of drawing rectangle in addition to its color and whether it's filled.
 */
class Rectangle extends ShapeOfShapes{
	public Rectangle(int x, int y, int w, int h, Color color, boolean solid){
		super(x,y,w,h, color, solid);
	}
	public void draw(Graphics g){
			g.setColor(colorOfShape);
			int x = Math.min(getX1(), getX2());
			int y = Math.min(getY1(), getY2());
			int width = Math.abs(getX2() - getX1());
			int height = Math.abs(getY2() - getY1());
		if (solid==true){
			g.fillRect(x, y, width, height);
		}else{
			g.drawRect(x, y, width, height);
		}
	}
}
/**
 * This class represents a Oval, a child class declaring the way of drawing oval in addition to its color and whether it's filled.
 */
class Oval extends ShapeOfShapes{
	public Oval(int x, int y, int w, int h, Color color, boolean solid){
		super(x,y,w,h, color, solid);
	}
	public void draw(Graphics g){
			g.setColor(colorOfShape);
			int x = Math.min(getX1(), getX2());
			int y = Math.min(getY1(), getY2());
			int width = Math.abs(getX2() - getX1());
			int height = Math.abs(getY2() - getY1());
		if (solid==true){
			g.fillOval(x, y, width, height);
		}else{
			g.drawOval(x, y, width, height);
		}
	}
}
/**
 * This class represents a Line, a child class declaring the way of drawing line in addition to its color.
 */
class Line extends ShapeOfShapes{
	public Line(int x, int y, int w, int h, Color color, boolean solid){
		super(x,y,w,h, color, solid);
	}
	public void draw(Graphics g){
			g.setColor(colorOfShape);
			if  (getX1() < getX2() && getY1()>getY2()){
			int x1 = Math.min(getX1(), getX2());
			int y1 = Math.max(getY1(), getY2());	
			int x2 = Math.max(getX1(), getX2());
			int y2 = Math.min(getY1(), getY2());
			} else if (getX1()<getX2() && getY1()<getY2()){
			int x1 = Math.max(getX1(), getX2());
			int y1 = Math.min(getY1(), getY2());	
			int x2 = Math.min(getX1(), getX2());
			int y2 = Math.max(getY1(), getY2());
			}
			g.drawLine(getX1(), getY1(), getX2(), getY2());
	}
}
/**
 * This class represents FreeHand, a child class declaring the way of drawing freely in addition to its color.
 */
class FreeHand extends ShapeOfShapes{
	public FreeHand(int x, int y, int w, int h, Color color, boolean solid){
		super(x,y,w,h, color, solid);
	}
	public void draw(Graphics g){
			g.setColor(colorOfShape);
			g.drawLine(x1, y1, x2, y2);
	}
}
/**
 * This class represents Eraser, a child class declaring the way of erasing any part of the shapes.
 */
class Eraser extends ShapeOfShapes{
	public Eraser (int x, int y, int w, int h, Color color, boolean solid){
		super(x,y,w,h,color, solid);
	}
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.fillOval(x1, y1, 15, 15);
	}
}