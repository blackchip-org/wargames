/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blackchip.wargames;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.airspaces.AirspaceAttributes;
import gov.nasa.worldwind.render.airspaces.BasicAirspaceAttributes;
import gov.nasa.worldwind.render.airspaces.CappedCylinder;
import gov.nasa.worldwind.util.WWUtil;
import java.awt.Color;

/**
 *
 * @author mcgann
 */
public class Impact {
    
    private static int PHASE_EXPANSION = 1;
    private static int PHASE_HOLD = 2;
    private static int PHASE_FADE = 3; 
    private static int PHASE_DONE = 4;
    
    private Position location; 
    private long impactTime; 
    private CappedCylinder explosion; 
    public static final int EXPANSION_TIME = 1000;
    private int phase = PHASE_EXPANSION;
    private double radius = 150 * 1000;
    private String name; 
    
    public Impact(String name, Position location, long impactTime) { 
        this.name = name;
        this.location = location;
        this.impactTime = impactTime;
        this.explosion = new CappedCylinder(location, 0);
        this.explosion.setAltitude(10 * 1000.0);
        AirspaceAttributes attr = new BasicAirspaceAttributes();
        attr.setMaterial(Material.ORANGE);
        attr.setOutlineMaterial(new Material(WWUtil.makeColorDarker(
                Color.ORANGE)));
        attr.setDrawOutline(true);
        this.explosion.setAttributes(attr);
    }
    
    public Renderable getRenderable() { 
        return this.explosion;
    }
    
    public void update(long time) { 
        long delta = time - impactTime;
        if ( phase == PHASE_EXPANSION ) { 
            double percentage = 0;
            if ( delta != 0 ) { 
                percentage = (double)delta / EXPANSION_TIME;
            }
            if ( percentage > 1 ) { 
                percentage = 1;
                phase = PHASE_DONE;
            }
            double currentRadius = radius * percentage;
            this.explosion.setRadius(currentRadius);
        }
    }
    
    public boolean isDone() { 
        return this.phase == PHASE_DONE;
    }
}
