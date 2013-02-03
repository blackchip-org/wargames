/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blackchip.wargames;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.SurfacePolyline;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mcgann
 */
public class MissleFlight {
    
    private int PHASE_INACTIVE = 0;
    private int PHASE_FLIGHT = 1;
    private int PHASE_IMPACT = 2;
    private int PHASE_DONE = 3;
    
    private MissleSilo silo; 
    private SurfacePolyline polyline; 
    public static final double DEGREES_PER_MILLISECOND = 1.0 / 1000.0; 
    private double distance; 
    private int phase = PHASE_INACTIVE;
    private long impactTime = 0;
    public static final long FADE_OUT_TIME = 5000;
    
    public MissleFlight(MissleSilo silo) { 
        this.silo = silo;
        Angle angle = Position.greatCircleDistance(silo.getLocation(), 
                silo.getTarget());
        distance = angle.getDegrees();
        List<Position> points = new ArrayList<Position>();
        points.add(silo.getLocation());
        points.add(silo.getLocation());
        this.polyline = new SurfacePolyline(points);
        BasicShapeAttributes attr = new BasicShapeAttributes();
        attr.setOutlineMaterial(Material.YELLOW);
        attr.setOutlineWidth(3);
        attr.setEnableAntialiasing(true);
        this.polyline.setAttributes(attr);
        
    }
    
    public SurfacePolyline getRenderable() { 
        return this.polyline;
    }
    
    public boolean hasImpacted() {
        return impactTime != 0;
    }
    
    public boolean isDone() { 
        return phase == PHASE_DONE;
    }
    
    public void update(long time) { 
        if ( phase == PHASE_DONE ) { 
            return;
        }
        long delta = time - silo.getLaunchTime(); 
        if ( phase == PHASE_INACTIVE ) { 
            if ( time < 0 ) { 
                return;
            }
            phase = PHASE_FLIGHT;
        }
        if ( phase == PHASE_FLIGHT ) { 
            ArrayList<Position> points = (ArrayList<Position>)polyline.getLocations();
            Position currentLocation = null;
            double currentDistance = DEGREES_PER_MILLISECOND * delta * silo.getSpeed(); 
            if ( currentDistance > distance ) { 
                currentLocation = silo.getTarget();
                phase = PHASE_IMPACT;
                impactTime = time; 
            } else { 
                double percentage = currentDistance / distance; 
                currentLocation = Position.interpolateGreatCircle(percentage, 
                        silo.getLocation(), silo.getTarget());
            }
            points.set(1, currentLocation);
            polyline.setLocations(points);
        }
        if ( phase == PHASE_IMPACT ) { 
            long afterImpact = time - impactTime;
            double fade = 0;
            if ( afterImpact != 0 ) { 
                fade = (double)afterImpact / FADE_OUT_TIME;
            }
            if ( fade > 1 ) { 
                phase = PHASE_DONE;
                this.polyline.setVisible(false);
                System.out.println("IMPACT: " + time + ": " + silo.getName());
            } else { 
                this.polyline.getAttributes().setOutlineOpacity(1 - fade);
            }
            
        }
    }
}
