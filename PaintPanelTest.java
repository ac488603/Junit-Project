//Adam Castillo
//108485851
//Project 2
//CSCI 152 Software engineering

/* using code cover and junit 4
 *  coverage is: 
 *   88 percent statement
 *   90 percent branch
 *   100 percent term
 */

import junit.framework.TestCase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.lang.reflect.Field;

import javax.swing.JFrame;
import javax.swing.JRadioButton;


public class PaintPanelTest extends TestCase {
	// variables that are tested
	PaintPanel testpanel;
    Panel theApplet;
    Robot rob;
    JFrame window; 
    
	public void setUp() throws Exception {
		testpanel =  new PaintPanel();   // init test panel 
        //... Create and initialize the panel.
        theApplet = new PaintDemo();
        
        //... Create a window (JFrame) and make panel the content pane.
        window = new JFrame();
        window.setContentPane(theApplet);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Paint Demo 2");
        window.pack();
        window.setLocation(-1,0); //  set location to top left
        window.setResizable(false);
        // System.out.println(theApplet.getSize()); // to get applet size.
        window.setVisible(true);  
		rob  = new Robot();
		
	}
	public void tearDown() throws Exception {
		testpanel = null; // free resources
		theApplet = null;
		rob = null;
		window.setVisible(false);
		window = null;
	}

	public void testRadioButtonSelected() { // test radio button 
        JRadioButton circleButton = new JRadioButton("Oval");
        circleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }}); // init radio button
        
        circleButton.doClick(); //  select button
        
        assertEquals(true, circleButton.isSelected()); // check if button is selected
        
	}
	
	public void testSetColor() throws IllegalArgumentException, IllegalAccessException{ // test set color
		testpanel.setColor(Color.BLUE); //  set color to blue
		Field f;
		Color testColor = Color.RED; 
		
		try {
			//change accessability of field 
			f  =  testpanel.getClass().getDeclaredField("_color"); 
			f.setAccessible(true);
			testColor = (Color)f.get(testpanel);  // get color
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		assertEquals(Color.BLUE, testColor); // check if correct color is selected
	}
	
	public void testSetShape() throws IllegalArgumentException, IllegalAccessException{
		testpanel.setShape(Shape.OVAL); // set shape
		Field f;
		Shape testShape = Shape.LINE; 
		
		try {
			//change accessability of field 
			f  =  testpanel.getClass().getDeclaredField("_shape");
			f.setAccessible(true);
			testShape = (Shape)f.get(testpanel); // get field 
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		assertEquals(Shape.OVAL, testShape); // check if correct shape is selected 
	}
	
	public void testConstructor() // test paint panel constructor 
	{
		Dimension size = testpanel.getPreferredSize(); 
		assertEquals(Color.white, testpanel.getBackground()); // check if correct background color
		assertEquals(300,size.height ); // check if correct width 
		assertEquals(300,size.width ); // check if correct height 
	}


	//from here on i will be using robot to automate mouse movements
	public void testMousePressed() throws Exception
	{
		rob.delay(500);
		rob.mouseMove(0, 0); //  move to top left
        rob.mouseMove(30,100); //  move to canvas 
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); //  click mouse 
	}
	
	public void testMouseReleased() throws Exception
	{
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0); //  move to top left
        rob.mouseMove(30,100); //  move to canvas
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); //  release mouse
	}
	
	public void testMouseDragging() throws Exception
	{
		rob.setAutoDelay(500); // set delay after every action
		rob.mouseMove(0, 0); 
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); //  press mouse
        rob.mouseMove(0,0);
        rob.mouseMove(100,170); //  move mouse diagonally
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); //  release mouse 
	}	
		
	public void testOvalSelect() throws Exception
	{
		rob.setAutoDelay(500); // set delay after every action
		rob.mouseMove(0, 0);
        rob.mouseMove(20,30); //  hover over oval radio button
        
        // select oval
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
		PaintDemo test = (PaintDemo)theApplet;  //  cast applet from panel to PaintDemo
		PaintPanel  canvas =  test._canvas;
		
		//change accessability of field 
		Field f  =  canvas.getClass().getDeclaredField("_shape");
		f.setAccessible(true);
		Shape testShape = (Shape)f.get(canvas); // get field 
		
		assertEquals(Shape.OVAL, testShape); // check if correct shape is selected 
	}	
	
	public void testRectangleSelect() throws Exception
	{
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(20,45); // hover over rectangle 
        
        //select rectangle
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
		PaintDemo test = (PaintDemo)theApplet;  //  cast
		PaintPanel  canvas =  test._canvas;
		
		//change accessability of field  by using refelction
		Field f  =  canvas.getClass().getDeclaredField("_shape");
		f.setAccessible(true);
		Shape testShape = (Shape)f.get(canvas); // get field 
		
		assertEquals(Shape.RECTANGLE, testShape); // check if correct shape is selected
        
	}
	
	public void testLineSelect() throws Exception
	{
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(10,63); // hover over line
        
        // select line
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		//change accessability of field 
		Field f  =  canvas.getClass().getDeclaredField("_shape");
		f.setAccessible(true);
		Shape testShape = (Shape)f.get(canvas); // get field 
		assertEquals(Shape.LINE, testShape); // check if correct shape is selected
              
	}
	
	public void testRedSelect() throws Exception
	{
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(120,30); //  hover over red 
        
        //select red
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
		PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
 
		//change accessability of field 
		Field f  =  canvas.getClass().getDeclaredField("_color"); 
		f.setAccessible(true);
		Color testColor = (Color)f.get(canvas);  // get color
		
		assertEquals(Color.RED, testColor); // check if correct color is selected
	}
		
	
	public void testGreenSelect() throws Exception
	{
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(120, 45); //  hover over green 
        
        //select green
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
		PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f  =  canvas.getClass().getDeclaredField("_color"); 
		f.setAccessible(true);
		Color testColor = (Color)f.get(canvas);  // get color
		assertEquals(Color.GREEN, testColor); // check if correct color is selected
	}
	
	public void testBlueSelect() throws Exception
	{
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(120, 65); //  hover over blue 
        //  select blue
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
		PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		//change accessability of field 
		Field f  =  canvas.getClass().getDeclaredField("_color"); 
		f.setAccessible(true);
		Color testColor = (Color)f.get(canvas);  // get color

		assertEquals(Color.BLUE, testColor); // check if correct color is selected
	}

	 //  from here on i will be testing different combinations of shapes and colors
	public void testSelectBlueRectangle() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select Rectangle
		rob.mouseMove(0, 0);
        rob.mouseMove(20,45);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//select blue
		rob.mouseMove(0, 0);
        rob.mouseMove(120, 65);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.BLUE, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.RECTANGLE, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void testSelectRedRectangle() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select Rectangle
		rob.mouseMove(0, 0);
        rob.mouseMove(20,45);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//select green
		rob.mouseMove(0, 0);
        rob.mouseMove(120,30);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.RED, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.RECTANGLE, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	public void testSelectGreenRectangle() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select Rectangle
		rob.mouseMove(0, 0);
        rob.mouseMove(20,45);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//select green
		rob.mouseMove(0, 0);
        rob.mouseMove(120, 45);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.GREEN, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.RECTANGLE, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void testSelectBlueOval() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select OVAL
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(20,30);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//select BLue
		rob.mouseMove(0, 0);
        rob.mouseMove(120, 65);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.BLUE, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.OVAL, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void testSelectGreenOval() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select OVAL
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(20,30);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//select BLue
		rob.mouseMove(0, 0);
        rob.mouseMove(120, 45);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.GREEN, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.OVAL, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void testSelectRedOval() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select OVAL
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(20,30);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		
       //select red
		rob.mouseMove(0, 0);
        rob.mouseMove(120,30);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.RED, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.OVAL, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void testSelectRedLine() throws Exception
	{
		
		rob.setAutoDelay(500);
		//select Line
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(10,63);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//select red
		rob.setAutoDelay(500);
		rob.mouseMove(0, 0);
        rob.mouseMove(120,30);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel  canvas =  test._canvas;
		
		
		//change accessability of field 
		Field f1  =  canvas.getClass().getDeclaredField("_color"); 
		f1.setAccessible(true);
		Color testColor = (Color)f1.get(canvas);  // get color

		assertEquals(Color.RED, testColor); // check if correct color is selected
		
		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		Shape testShape = (Shape)f2.get(canvas); // get field 
		
		assertEquals(Shape.LINE, testShape); // check if correct shape is selected
		
		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	// lastly testing not reachable case
	//  testing to check if case is unreachable( it is)
	
	public void testSelectNotReachableCase() throws Exception
	{
		
		rob.setAutoDelay(500);   
        PaintDemo test = (PaintDemo)theApplet; 
		PaintPanel canvas = test._canvas;

		//change accessability of field 
		Field f2  =  canvas.getClass().getDeclaredField("_shape");
		f2.setAccessible(true);
		f2.set(canvas,null); // exception thrown, case in unreachable

		rob.mouseMove(0, 0);
        rob.mouseMove(30,100);
        rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        rob.mouseMove(0,0);
        rob.mouseMove(150,220);
        rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
}
