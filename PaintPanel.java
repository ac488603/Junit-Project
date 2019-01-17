// File   : gui-lowlevel/paintpanel/PaintPanel.java
// Purpose: Draws graphics, handles mouse events.
//          Has setters for shape and color.
// Author : Fred Swartz - October 12, 2006 - Placed in public domain.
// Possible enhancements:
//          * Allow dragging shapes up or to left, not just down+right.
//                by using max/min of coordinates.

//package paintdemo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

///////////////////////////////////////////////////////////////////// PaintPanel
class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {
    
    //================================================================ constants
    private static final int SIZE = 300;     // Size of paint area.
    private static final Shape INIIIAL_SHAPE = Shape.RECTANGLE;
    private static final Color INITIAL_COLOR = Color.RED;
    private static final Color COLOR_BACKGROUND = Color.WHITE;
    private enum         State { IDLE, DRAGGING }
    
    //=================================================================== fields
    private State _state = State.IDLE;
    private Shape _shape = INIIIAL_SHAPE;
    private Color _color = INITIAL_COLOR;
    
    private Point _start = null; // Where mouse is pressed.
    private Point _end   = null; // Where mouse is dragged to or released.
    
    //... BufferedImage stores the underlying saved painting.
    //    Initialized first time paintComponent is called.
    private BufferedImage _bufImage = null;
    
    
    //============================================================== constructor
    public PaintPanel() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.white);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    //================================================================= setShape
    public void setShape(Shape shape) {
        _shape = shape;
    }
    
    //================================================================= setColor
    public void setColor(Color color) {
        _color = color;
    }
    
    //=========================================================== paintComponent
    @Override public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;  // Downcast to Graphics2D
        
        //... One time initialization of in-memory, saved image.
        if (_bufImage == null) {
            //... This is the first time, initialize _bufImage
            int w = this.getWidth();
            int h = this.getHeight();
            _bufImage = (BufferedImage)this.createImage(w, h);
            Graphics2D gc = _bufImage.createGraphics();
            gc.setColor(COLOR_BACKGROUND);
            gc.fillRect(0, 0, w, h); // fill in background
        }
        
        //... Display the saved image.
        g2.drawImage(_bufImage, null, 0, 0);
        
        //... Overwrite the screen display with currently dragged image.
        if (_state == State.DRAGGING) {
            //... Write shape that is being dragged over the screen image,
            //    but not into the saved buffered image.  It will be written
            //    on the saved image when the mouse is released.
            drawCurrentShape(g2);
        }
    }
    
    //========================================================= drawCurrentShape
    private void drawCurrentShape(Graphics2D g2) {
        //... Draws current shape on a graphics context, either
        //    on the context passed to paintComponent, or the
        //    context for the BufferedImage.
        
        g2.setColor(_color);    // Set the color.
        
        switch (_shape) {
            case OVAL:
                g2.fillOval(_start.x, _start.y, _end.x - _start.x, _end.y - _start.y);
                break;
                
            case RECTANGLE:
                g2.fillRect(_start.x, _start.y, _end.x - _start.x, _end.y - _start.y);
                break;
                
            case LINE:
                g2.drawLine(_start.x, _start.y, _end.x  , _end.y);
                break;
                
            default:  // Should never happen!
                g2.drawString("Huh?", 10, 20);
                break;
        }
    }
    
    //============================================================= mousePressed
    public void mousePressed(MouseEvent e) {
        _state = State.DRAGGING;   // Assume we're starting a drag.
        
        _start = e.getPoint();     // Save start point, and also initially
        _end   = _start;           // as end point, which drag will change.
    }
    
    //============================================================= mouseDragged
    public void mouseDragged(MouseEvent e) {
        _state = State.DRAGGING;   // We're dragging to create a shape.
        
        _end = e.getPoint();       // Set end point of drag.  May change.
        this.repaint();            // After change, show new shape
    }
    
    //============================================================ mouseReleased
    public void mouseReleased(MouseEvent e) {
        //... If released at end of drag, write shape into the BufferedImage,
        //    which saves it in the drawing.
        _end = e.getPoint();      // Set end point of drag.
        if (_state == State.DRAGGING) {
            _state = State.IDLE;
            
            //... Draw current shape in saved buffered image.
            drawCurrentShape(_bufImage.createGraphics());
            
            this.repaint();
        }
    }
    
    //================================================== ignored mouse listeners
    public void mouseMoved  (MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}