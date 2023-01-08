package com.omicron;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SpecialListener extends MouseInputAdapter {
    public MandelBrotSet set;
    private boolean on = false;
    private boolean dragging = false;
    private int offsetX = 0;
    private int offsetY = 0;
    private int offsetXsaved = 0;
    private int offsetYsaved = 0;
    public SpecialListener(MandelBrotSet set) {
        this.set = set;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        set.height += ((double) e.getWheelRotation()) / (40 / set.height);
        //System.out.println(set.height);
        set.width += ((double) e.getWheelRotation()) / (40 / set.width);
        set.dx = set.width/(MandelBrotSet.WIDTH-1);
        set.dy = set.height/(MandelBrotSet.HEIGHT-1);
        set.render();
        set.update();
    }

    public void mouseDragged(MouseEvent e){
        if(dragging == false)
        {
            //System.out.println("izi");
            offsetX = (int) (e.getX());
            offsetY = (int) (e.getY());
            dragging = true;
        }
        else
        {
            //System.out.println("notizi");
            int x = offsetXsaved + offsetX - e.getX();
            int y = offsetYsaved + offsetY - e.getY();
            set.startX = -2 + ((double) (x)) / (800 / set.width);
            //System.out.println(set.startX);
            set.startY = 2 - ((double)(y)) / (800 / set.height);
           // System.out.println(set.startY);
        }
        set.render();
        set.update();
    }

    public void mousePressed(MouseEvent e) {
        on = true;
        //System.out.println("test");
    }

    /**
     * {@inheritDoc}
     */
    public void mouseReleased(MouseEvent e) {
        on = false;
        dragging = false;
        //System.out.println(dragging);
        offsetXsaved += offsetX - e.getX();
        //System.out.println(offsetXsaved);
        offsetYsaved += offsetY - e.getY();
        //System.out.println(offsetYsaved);
    }
}
