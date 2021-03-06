/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blackchip.wargames;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.view.orbit.FlatOrbitView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author mcgann
 */
public class MapFrame extends javax.swing.JFrame {

    /**
     * Creates new form MapFrame
     */
    private WorldWindowGLCanvas map; 
    private Model model; 
    private RenderableLayer missleSiteLayer;
    private IconLayer missleLaunchLayer; 
    private RenderableLayer missleFlightLayer; 
    private SimulationController sim;
    
    private static final int INSET = 50;
    
                
    public MapFrame() {
        // Adjust configuration values before instantiation        Configuration.setValue(AVKey.INITIAL_LATITUDE, 0);
        Configuration.setValue(AVKey.INITIAL_LONGITUDE, 0);
        Configuration.setValue(AVKey.INITIAL_LATITUDE, 20);
        Configuration.setValue(AVKey.INITIAL_ALTITUDE, 50e6);
        Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
        Configuration.setValue(AVKey.VIEW_CLASS_NAME, RestrictedFlatOrbitView.class.getName());

        initComponents();
        map = new WorldWindowGLCanvas();
        this.getContentPane().add(map, java.awt.BorderLayout.CENTER);
        map.setPreferredSize(new java.awt.Dimension(1000, 800));
        this.pack(); 
        model = new BasicModel();
        this.missleSiteLayer = new RenderableLayer();
        this.missleSiteLayer.setMaxActiveAltitude(1e7);
        initModel();
        map.setModel(model);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); 
        this.setBounds(INSET, INSET, (int)screen.getWidth() - INSET * 2, 
                (int)screen.getHeight() - INSET * 2);
        ((FlatOrbitView)map.getView()).getOrbitViewLimits().setZoomLimits(1e6, 50e6);
    }

    private void initModel() { 
        LayerList layers = model.getLayers(); 
        LayerList originalLayers = new LayerList(layers); 
        for ( Layer layer: originalLayers ) { 
            System.out.println(layer.getClass().getName());
            if ( layer.getClass() == StarsLayer.class ) { 
                layers.remove(layer);
            } else if ( layer.getClass() == WorldMapLayer.class ) { 
                layers.remove(layer);
            } else if ( layer.getClass() == CompassLayer.class ) { 
                layers.remove(layer); 
            }
        }
        ArrayList<MissleSilo> silos = MissleSilo.createAll();
        for ( MissleSilo silo: silos ) { 
            this.missleSiteLayer.addRenderable(silo.getSiteIcon());
        }
        sim = new SimulationController(map, silos);
        layers.add(this.missleSiteLayer);
        layers.add(sim.getSiloLayer());
        layers.add(sim.getFlightLayer());
        layers.add(sim.getImpactLayer());

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sliderSim = new javax.swing.JSlider();
        buttonLaunch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(577, 50));

        sliderSim.setValue(0);
        sliderSim.setEnabled(false);

        buttonLaunch.setText("Launch");
        buttonLaunch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLaunchActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(sliderSim, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonLaunch)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(buttonLaunch)
                    .add(sliderSim, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLaunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLaunchActionPerformed
        sliderSim.setEnabled(true);
        sim.launch();
        this.map.redraw();
    }//GEN-LAST:event_buttonLaunchActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLaunch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider sliderSim;
    // End of variables declaration//GEN-END:variables
}
