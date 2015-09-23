/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blackchip.wargames;

import gov.nasa.worldwind.render.PatternFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author mcgann
 */
public class Images {
    
    private static final Dimension SILO_SIZE = new Dimension(64, 64);
       
    public static final BufferedImage SILO_SITE;
    public static final BufferedImage SILO_LAUNCH;


    static { 
        AffineTransform tx = new AffineTransform();
        BufferedImage image = PatternFactory.createPattern(
                PatternFactory.PATTERN_TRIANGLE_UP,SILO_SIZE, 1.0f, 
                new Color(255, 0, 0, 128));        
        BufferedImage imageSite = PatternFactory.createPattern(
                PatternFactory.PATTERN_TRIANGLE_UP, new Dimension(16, 16), 1.0f, 
                new Color(0xff, 0xff, 0xff)); 
        tx.rotate(Math.toRadians(180), image.getWidth() / 2, 
                image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
        SILO_LAUNCH = op.filter(image, null); 
        AffineTransform tx2 = new AffineTransform();
        tx2.rotate(Math.toRadians(180), 8, 8);
        AffineTransformOp op2 = new AffineTransformOp(tx2, AffineTransformOp.TYPE_BICUBIC);
        SILO_SITE = op2.filter(imageSite, null);
    }
}
