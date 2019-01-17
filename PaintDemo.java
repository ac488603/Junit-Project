// File   : gui-lowlevel/paintdemo/PaintDemo.java
// Purpose: A simple painting program.
//          Illustrates use of mouse listeners and BufferedImage.
//          Runs as both applet and main program.
// Author : Fred Swartz 2002, 2006-10-13 - Placed in public domain.
// Possible Enhancements:
//   * Clear drawing area
//   * Other shapes.
//   * An eraser (define new shape, draw on buffered image during drag).

//package paintdemo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

////////////////////////////////////////////////////////////////////// PaintDemo
public class PaintDemo extends Panel {//extends JApplet {
    //=================================================================== fields
    PaintPanel _canvas = new PaintPanel();
    
    //===================================================================== main
    public static void main(String[] args) {
        //... Create and initialize the panel.
        Panel theApplet = new PaintDemo();
        
        //... Create a window (JFrame) and make panel the content pane.
        JFrame window = new JFrame();
        window.setContentPane(theApplet);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Paint Demo 2");
        window.pack();
        window.setLocationRelativeTo(null); // Center window.
        window.setResizable(false);
        // System.out.println(theApplet.getSize()); // to get applet size.
        window.setVisible(true);            // Make the window visible.
    }
    
    //============================================================== constructor
    public PaintDemo() {
        //... Create radio buttons for shapes.........................
        JRadioButton circleButton = new JRadioButton("Oval");
        circleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _canvas.setShape(Shape.OVAL);
            }});
            
        JRadioButton rectangleButton = new JRadioButton("Rectangle", true);
        rectangleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _canvas.setShape(Shape.RECTANGLE);
            }});
            
        JRadioButton lineButton = new JRadioButton("Line");
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _canvas.setShape(Shape.LINE);
            }});
            
        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(circleButton);
        shapeGroup.add(rectangleButton);
        shapeGroup.add(lineButton);
        
        //--- Layout the  shape buttons
        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new GridLayout(3,1));
        shapePanel.add(circleButton);
        shapePanel.add(rectangleButton);
        shapePanel.add(lineButton);
        
        //... Create radio buttons for colors...............................
        JRadioButton redButton = new JRadioButton("Red", true);
        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _canvas.setColor(Color.RED);
            }});
            
        JRadioButton greenButton = new JRadioButton("Green");
        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _canvas.setColor(Color.GREEN);
            }});
            
        JRadioButton blueButton = new JRadioButton("Blue");
        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _canvas.setColor(Color.BLUE);
            }});
            
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(redButton);
        colorGroup.add(greenButton);
        colorGroup.add(blueButton);
        
        //... Layout the color buttons
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(3,1));
        colorPanel.add(redButton);
        colorPanel.add(greenButton);
        colorPanel.add(blueButton);
        
        //... Create a panel to hold the separate button panels.............
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(shapePanel);
        buttonPanel.add(colorPanel);
        buttonPanel.add(Box.createHorizontalGlue());
        
        //... layout the applet ...........................................
        setLayout(new BorderLayout(5,5));
        add(buttonPanel, BorderLayout.NORTH);
        add(_canvas    , BorderLayout.CENTER);
    }
}