/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blackchip.wargames;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.IconLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.RenderableLayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author mcgann
 */
public class SimulationController {
    
    private WorldWindow ww;
    private IconLayer siloLayer;
    private ArrayList<MissleSilo> silos; 
    private RenderableLayer flightLayer;
    private ArrayList<MissleFlight> flights; 
    private RenderableLayer impactLayer;
    private ArrayList<Impact> impacts; 
    
    private long simTime;
    private Timer timer = null; 
    private int interval = 75;
    private double timeScale = 2.0;
    private long startTime = 0;
    private long totalTime; 
    
    public SimulationController(WorldWindow ww, ArrayList<MissleSilo> silos) { 
        this.ww = ww;
        this.silos = silos; 
        this.flights = new ArrayList<MissleFlight>(silos.size());
        this.impacts = new ArrayList<Impact>(silos.size());
        for ( int i = 0; i < silos.size(); i++ ) { 
            flights.add(null);
            impacts.add(null);
        }
     
        siloLayer = new IconLayer();
        for ( MissleSilo silo: silos ) { 
            siloLayer.addIcon(silo.getIcon()); 
        }
        flightLayer = new RenderableLayer();
        impactLayer = new RenderableLayer();
        computeTotalTime();
        System.out.println("TOTAL TIME: " + totalTime);
    }
    
    public Layer getSiloLayer() { 
        return this.siloLayer;
    }
    
    public Layer getFlightLayer() { 
        return this.flightLayer;
    }
    
    public Layer getImpactLayer() { 
        return this.impactLayer;
    }
    
    public void launch() { 
        simTime = 0;
        if ( timer != null ) { 
            timer.stop(); 
            timer = null;
        }
        reset();
        startTime = System.currentTimeMillis();
        timer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                update();
            }
        });
        timer.start();
    }
    
    private void update() { 
        long now = System.currentTimeMillis();
        //simTime += (long)(interval * timeScale);
        boolean done = true;
        simTime = (long)((now - startTime) * timeScale);
        for ( int i = 0; i < silos.size(); i++ ) { 
            MissleSilo silo = silos.get(i);
            MissleFlight flight = flights.get(i);
            Impact impact = impacts.get(i);
            if ( flight == null || impact == null ) { 
                done = false;
            }
            silo.update(simTime);
            if ( silo.hasLaunched() && flight == null ) { 
                flight = new MissleFlight(silo);
                flights.set(i, flight);
                flightLayer.addRenderable(flight.getRenderable());
            }
            if ( flight != null ) { 
                flight.update(simTime);
                if ( flight.hasImpacted() && impact == null ) { 
                    impact = new Impact(silo.getName(), silo.getTarget(), simTime);
                    impacts.set(i, impact);
                    impactLayer.addRenderable(impact.getRenderable());
                }
                if ( ! flight.isDone() ) { 
                    done = false;
                }
            }
            if ( impact != null ) { 
                impact.update(simTime);
                if ( ! impact.isDone() ) { 
                    done = false;
                }
            }
        }
        ww.redraw();
        if ( done ) { 
            System.out.println("DONE: " + simTime);
            this.timer.stop();
        }
    }
    
    private void reset() { 
        simTime = 0;
        this.flights = new ArrayList<MissleFlight>(silos.size());
        this.impacts = new ArrayList<Impact>(silos.size());
        for ( int i = 0; i < silos.size(); i++ ) { 
            flights.add(null);
            impacts.add(null);
        }
        flightLayer.removeAllRenderables();
        impactLayer.removeAllRenderables();
        for ( MissleSilo silo: silos ) { 
            silo.reset();
        }
    }
    
    private long computeTotalTime() { 
        totalTime = 0;
        for ( MissleSilo silo: silos ) { 
            System.out.println(silo.getName());
            Angle distance = Position.greatCircleDistance(silo.getLocation(), 
                    silo.getTarget());
            long flightTime = (long)(distance.getDegrees() * 
                    (1.0/MissleFlight.DEGREES_PER_MILLISECOND) * silo.getSpeed() + 
                    MissleFlight.FADE_OUT_TIME + silo.getLaunchTime());
            totalTime = Math.max(totalTime, flightTime); 
        }
        return totalTime;
    }
}
