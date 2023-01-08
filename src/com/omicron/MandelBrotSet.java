package com.omicron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MandelBrotSet extends JComponent {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int ITERATIONS = 50;

    public double startX = -2;
    public double width = 4;
    public double startY = 2;
    public double height = 4;

    public double dx = width/(WIDTH-1);
    public double dy = height/(HEIGHT-1);
    public JFrame frame;
    private BufferedImage buffer;
    private boolean isJulia;

    public MandelBrotSet(boolean isJulia) {
        this.isJulia = isJulia;
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        frame = new JFrame("Mandelbrot Set");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        render();
        frame.pack();
        frame.setVisible(true);
        SpecialListener listener = new SpecialListener(this);
        frame.addMouseMotionListener(listener);
        frame.addMouseWheelListener(listener);
        frame.addMouseListener(listener);

    }

    public void update()
    {
        SwingUtilities.updateComponentTreeUI(frame);
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, null);
    }

    public void render(){
        //System.out.println(width);
        for (int x=0; x<WIDTH; x++){
            for (int y=0; y<HEIGHT; y++){
                int color;
                if(isJulia)
                    color = calculateJuliaPoint(x, y);
                else
                    color = calculatePoint(x, y);
                if(color != ITERATIONS)
                    buffer.setRGB(x, y, 255 * color / ITERATIONS);
                else
                    buffer.setRGB(x, y, 0);
            }
        }

    }

    public static double log2(double N)
    {

        // calculate log2 N indirectly
        // using log() method
        double result = (Math.log(N) / Math.log(2));

        return result;
    }

    public int calculatePoint(int x, int y){

        ComplexNumber number = convertToComplex(x, y);
        ComplexNumber z = number;
        int i;
        for (i=0; i<ITERATIONS; i++){

            z = z.times(z).add(number);

            if (z.abs()>2.0){
                break;
            }

        }
        if(i == ITERATIONS)
            return ITERATIONS;

        return (int) (i + 1 - Math.log(log2(z.abs())));

    }

    public int calculateJuliaPoint(int x, int y){

        ComplexNumber z = convertToComplex(x, y);
        ComplexNumber number = new ComplexNumber(0.6, 0.55);
        int i;
        for (i=0; i<ITERATIONS; i++){

            z = z.times(z).times(z).times(z).add(number);

            if (z.abs()>2.0){
                break;
            }

        }
        if(i == ITERATIONS)
            return ITERATIONS;

        return (int) (i + 1 - Math.log(log2(z.abs())));

    }

    public  ComplexNumber convertToComplex(int x, int y){

        double real = startX + x*dx;
        double imaginary = startY - y*dy;
        return new ComplexNumber(real, imaginary);

    }




    public static void main(String[] args) {

        MandelBrotSet mandy = new MandelBrotSet(false);
        mandy.render();

    }


}