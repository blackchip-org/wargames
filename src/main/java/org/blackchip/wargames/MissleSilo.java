/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blackchip.wargames;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.render.airspaces.AirspaceAttributes;
import gov.nasa.worldwind.render.airspaces.BasicAirspaceAttributes;
import gov.nasa.worldwind.render.airspaces.CappedCylinder;
import gov.nasa.worldwind.util.WWUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author mcgann
 */
public class MissleSilo {
 
    private int PHASE_PRE_LAUNCH = 1;
    private int PHASE_POST_LAUNCH = 2;
    private int PHASE_DONE = 3;
    
    private Position location;
    private Position target;
    private WWIcon icon; 
    private CappedCylinder iconSite;
    private boolean launched = false; 
    private long launchTime = 0;
    private long showTime = 1500;
    private int phase = PHASE_PRE_LAUNCH; 
    private int startSize = 64;
    private double speed = 1; 
    private String name;
    
    public MissleSilo(String name, Position location, Position target, 
            double launchTime, double speed) { 
        this.name = name;
        this.location = location;
        this.target = target;
        this.launchTime = (long)(launchTime * 1000);
        this.icon = new UserFacingIcon(Images.SILO_LAUNCH, location);
        this.iconSite = new CappedCylinder(location, 20 * 1000);
        this.iconSite.setAltitude(9 * 1000.0);
        AirspaceAttributes attr = new BasicAirspaceAttributes();
        attr.setMaterial(Material.WHITE);
        attr.setOutlineMaterial(new Material(WWUtil.makeColorDarker(
                Color.WHITE)));
        attr.setDrawOutline(true);
        this.iconSite.setAttributes(attr);
        this.icon.setVisible(false);
        this.speed = speed;
    }

    /**
     * @return the location
     */
    public Position getLocation() {
        return location;
    }

    public String getName() { 
        return name;
    }
    
    /**
     * @return the target
     */
    public Position getTarget() {
        return target;
    }
    
    public double getSpeed() { 
        return speed; 
    }
    
    public WWIcon getIcon() { 
        return this.icon; 
    }
        
    public Renderable getSiteIcon() { 
        return this.iconSite;
    }
    
    public long getLaunchTime() { 
        return this.launchTime; 
    }
    
    
    public boolean hasLaunched() { 
        return this.launched; 
    }
    
    public void reset() { 
        this.icon.setVisible(false);      
        launched = false;
        this.phase = PHASE_PRE_LAUNCH;
    }
    
    public void update(long time) { 
        if ( phase == PHASE_DONE ) { 
            return;
        }
        long delta = time - launchTime;
        if ( phase == PHASE_PRE_LAUNCH ) { 
            if ( delta > 0 ) { 
                System.out.println("LAUNCH: " + name); 
                phase = PHASE_POST_LAUNCH;
                launched = true;
                this.icon.setVisible(true);
            }
        }
        if ( phase == PHASE_POST_LAUNCH ) { 
            long launchDelta = time - launchTime;
            double percentage = (double)launchDelta / showTime;
            if ( percentage > 1 ) { 
                this.icon.setVisible(false);
                phase = PHASE_DONE;
            } else { 
                int size = (int)(startSize * (1 - percentage));
                this.icon.setSize(new Dimension(size, size)); 
            }
        }
    }
    
    private static Position p(double lat, double lon) { 
        return Position.fromDegrees(lat, lon);
    }
    
    // Iran
    static Position S_IR_1  = p(32.130112, 53.189516);
    static Position T_IR_1  = p(35.692460, 51.422305);
    static Position T_IR_2  = p(36.283907, 59.584240);
    static Position T_IR_3  = p(32.653211, 51.673424);
    static Position T_IR_4  = p(38.080409, 46.290120);
    static Position T_IR_5  = p(35.812145, 50.964842);
    static Position T_IR_6  = p(29.608800, 52.543471);
    static Position T_IR_7  = p(31.315170, 48.683659);
    static Position T_IR_8  = p(34.650000, 50.882973);
    static Position T_IR_9  = p(34.308765, 47.057346);
    static Position T_IR_10 = p(37.550000, 45.100361);
    static Position T_IR_11 = p(37.283333, 49.583333);
    static Position T_IR_12 = p(29.489167, 60.863611);
    static Position T_IR_13 = p(30.27973,  57.069619);
    static Position T_IR_14 = p(34.805442, 48.518304);
    static Position T_IR_15 = p(34.080000, 49.700000);
    
    // Israel 
    static Position S_IL_1  = p(30.986086, 35.157808);
    static Position S_IL_2  = p(30.001570, 34.96674);
    static Position S_IL_3  = p(30.758120, 34.616353);
    static Position S_IL_4  = p(31.204967, 34.677401);
    static Position S_IL_5  = p(30.384633, 34.770826);
    static Position S_IL_6  = p(31.099096, 34.959263);
    static Position S_IL_7  = p(32.498222, 35.484861);
    static Position S_IL_8  = p(32.695431, 35.484495);
    
    static Position T_IL_1  = p(32.043461, 34.840684);
    
    // China
    static Position S_CN_1  = p(34.641543, 82.589022);
    static Position S_CN_2  = p(36.561373, 77.869958);
    static Position S_CN_3  = p(31.187244, 82.485150);
    static Position S_CN_4  = p(38.929259, 76.840176);
    static Position S_CN_5  = p(40.500338, 79.709570);
    static Position S_CN_6  = p(33.219492, 80.335405);
    static Position S_CN_7  = p(31.737146, 80.700329);
    static Position S_CN_8  = p(38.859486, 79.015870);
    static Position S_CN_9  = p(37.691181, 79.322953);
    static Position S_CN_10 = p(38.167006, 78.240650);
    
    static Position S_CN_NE_1 = p(49.146152, 123.652591);
    static Position S_CN_NE_2 = p(43.992821, 127.988192);
    static Position S_CN_NE_3 = p(48.474990, 117.572229);
    static Position S_CN_NE_4 = p(42.581594, 114.533404);
    static Position S_CN_NE_5 = p(42.075865, 121.858489);

    static Position T_CN_1 = p(23.129205, 113.264343);
    static Position T_CN_2 = p(31.230215, 121.473516);
    static Position T_CN_3 = p(39.893157, 116.414271);
    static Position T_CN_4 = p(23.353325, 116.681838);
    static Position T_CN_5 = p(22.543103, 114.057882);
    static Position T_CN_6 = p(39.084158, 117.200983);
    static Position T_CN_7 = p(30.275239, 120.154612);
    static Position T_CN_8 = p(22.369076, 114.142342);
    
    // US
    static Position S_US_AK_1 = p(61.478879, -151.403678);
    static Position S_US_AK_2 = p(64.265059, -143.975686);
    static Position S_US_AK_3 = p(64.988367, -163.614666);
    static Position S_US_AK_4 = p(60.902108, -160.684683);

    static Position S_US_W_1  = p(39.981242, -117.996758);
    static Position S_US_W_2  = p(37.080308, -114.939103);
    static Position S_US_W_3  = p(40.396180, -115.801655);
    static Position S_US_W_4  = p(35.355902, -111.589059);
    static Position S_US_W_5  = p(39.865908, -110.395836);
    static Position S_US_W_6  = p(42.884218, -115.786768);
    static Position S_US_W_7  = p(46.905407, -112.170954);
    static Position S_US_W_8  = p(39.362474, -104.917359);
    static Position S_US_W_9  = p(33.209856, -106.961281);
    static Position S_US_W_10 = p(37.508886, -107.215383);
    
    static Position S_US_C_1  = p(32.553443, -101.040925);
    static Position S_US_C_2  = p(34.427987, -95.402161);
    static Position S_US_C_3  = p(32.239636, -93.569128);
    static Position S_US_C_4  = p(35.597119, -101.555564);
    static Position S_US_C_5  = p(37.245256, -95.990328);
    static Position S_US_C_6  = p(39.070366, -100.613320);
    static Position S_US_C_7  = p(40.728018, -95.564965);
    static Position S_US_C_8  = p(26.835018, -98.750782);
    static Position S_US_C_9  = p(36.269720, -98.696230);
    static Position S_US_C_10 = p(45.109678, -101.243142);

    static Position S_US_E_1  = p(46.578482, -68.539641); 
    static Position S_US_E_2  = p(44.857676, -67.792263);     
    static Position S_US_E_3  = p(44.960011, -70.509531); 
    static Position S_US_E_4  = p(44.411270, -72.587401); 
    static Position S_US_E_5  = p(43.668795, -75.330652); 
    static Position S_US_E_6  = p(39.061236, -78.871670); 
    static Position S_US_E_7  = p(36.806694, -81.966459); 
    static Position S_US_E_8  = p(37.996851, -84.433649); 
    static Position S_US_E_9  = p(36.261509, -84.411049); 
    static Position S_US_E_10 = p(43.057326, -76.999316); 
    
    static Position T_US_SE_1 = p(25.776827, -80.194198); 
    static Position T_US_SE_2 = p(29.931115, -90.063308); 
    static Position T_US_SE_3 = p(29.746127, -95.357685); 
    static Position T_US_SE_4 = p(28.535653, -81.377641); 
    static Position T_US_SE_5 = p(30.297031, -81.667582); 
    static Position T_US_SE_6 = p(30.157380, -85.648944); 
    static Position T_US_SE_7 = p(26.274906, -80.110053); 
    static Position T_US_SE_8 = p(27.926370, -82.451517); 
    
    static Position T_US_E_1  = p(42.301736, -71.121311);
    static Position T_US_E_2  = p(40.710084, -74.006107);
    static Position T_US_E_3  = p(39.947838, -75.167461);
    static Position T_US_E_4  = p(39.282976, -76.610023);
    static Position T_US_E_5  = p(38.895993, -77.037997);
    static Position T_US_E_6  = p(40.433572, -79.990194);
    static Position T_US_E_7  = p(35.765193, -78.645687);
    static Position T_US_E_8  = p(36.841153, -76.291188);
    static Position T_US_E_9  = p(41.759035, -72.673476);
    static Position T_US_E_10 = p(43.608000, -70.345529);

    static Position T_US_W_1  = p(47.595317, -122.325936);
    static Position T_US_W_2  = p(45.502720, -122.686172);
    static Position T_US_W_3  = p(38.585596, -121.496137);
    static Position T_US_W_4  = p(37.766468, -122.418524);
    static Position T_US_W_5  = p(34.054508, -118.236420);
    static Position T_US_W_6  = p(32.710231, -117.138063);
    static Position T_US_W_7  = p(36.095926, -115.185269);
    static Position T_US_W_8  = p(33.450432, -112.068976);
    static Position T_US_W_9  = p(40.769395, -111.885530);
    static Position T_US_W_10 = p(35.340302, -106.319573);
    
    static Position T_US_MW_1 = p(41.878114, -87.629798);
    static Position T_US_MW_2 = p(42.329732, -83.045231);
    static Position T_US_MW_3 = p(38.613835, -90.183036);
    static Position T_US_MW_4 = p(39.090299, -94.583267);
    static Position T_US_MW_5 = p(39.101756, -84.502053);
    
    
    // Russia
    static Position S_RU_E_1  = p(63.053392, 163.506816);
    static Position S_RU_E_2  = p(54.427500, 158.444863);
    static Position S_RU_E_3  = p(67.436236, 152.650841);
    static Position S_RU_E_4  = p(62.368204, 145.070902);
    static Position S_RU_E_5  = p(67.456897, 137.031768);
    static Position S_RU_E_6  = p(51.663494, 138.460729); 
    static Position S_RU_E_7  = p(44.389618, 134.616158); 
    static Position S_RU_E_8  = p(67.309188, -177.502523); 
    static Position S_RU_E_9  = p(68.231630, 165.726080); 
    static Position S_RU_E_10 = p(63.158384, 130.472481); 

    
    static Position S_RU_SW_1 = p(45.107771, 42.056630);
    static Position S_RU_SW_2 = p(44.962587, 38.185999);
    static Position S_RU_SW_3 = p(44.101080, 43.972564);
    static Position S_RU_SW_4 = p(47.225367, 39.884025);
    static Position S_RU_SW_5 = p(47.886791, 44.458064);
    
    static Position S_RU_W_1  = p(67.955336, 32.660989);
    static Position S_RU_W_2  = p(56.176631, 30.596096);
    static Position S_RU_W_3  = p(50.834942, 36.130267);
    static Position S_RU_W_4  = p(60.371924, 35.927155);
    static Position S_RU_W_5  = p(64.209489, 31.848779);
    static Position S_RU_W_6  = p(56.875399, 43.853246);
    static Position S_RU_W_7  = p(63.836024, 40.894543);
    static Position S_RU_W_8  = p(66.136434, 47.140691);
    static Position S_RU_W_9  = p(51.391349, 49.258857);
    static Position S_RU_W_10 = p(62.369257, 46.213889);

    static Position S_RU_C_1  = p(67.818876, 82.600637);
    static Position S_RU_C_2  = p(73.887496, 93.423545);
    static Position S_RU_C_3  = p(69.867471, 69.904945);
    static Position S_RU_C_4  = p(66.777725, 99.203434);
    static Position S_RU_C_5  = p(61.775371, 71.018424);
    static Position S_RU_C_6  = p(66.188430, 63.599982);
    static Position S_RU_C_7  = p(70.937353, 118.014471);
    static Position S_RU_C_8  = p(62.364973, 88.319957);
    static Position S_RU_C_9  = p(59.443581, 114.235075);
    static Position S_RU_C_10 = p(54.360838, 96.060820);
    
    static Position T_RU_W_1  = p(55.746909, 37.656280);    
    static Position T_RU_W_2  = p(59.904865, 30.315091);
    static Position T_RU_W_3  = p(56.849967, 60.600423);
    static Position T_RU_W_4  = p(56.316674, 44.000227);
    static Position T_RU_W_5  = p(53.241504, 50.221246);
    static Position T_RU_W_6  = p(55.800540, 49.106042);
    static Position T_RU_W_7  = p(53.781081, 61.987176);
    static Position T_RU_W_8  = p(48.700102, 44.516849);
    static Position T_RU_W_9  = p(56.923587, 56.674491);
    static Position T_RU_W_10 = p(51.650011, 39.200141);
    static Position T_RU_W_11 = p(50.758361, 46.100889); 
    static Position T_RU_W_12 = p(44.952723, 39.184681); 
    static Position T_RU_W_13 = p(53.463486, 49.487513); 
    static Position T_RU_W_14 = p(56.849994, 53.216660); 
    static Position T_RU_W_15 = p(54.316822, 48.366955); 
    static Position T_RU_W_16 = p(57.652775, 39.876145); 
    static Position T_RU_W_17 = p(42.978402, 47.491104); 
    static Position T_RU_W_18 = p(51.783326, 55.100010); 
    static Position T_RU_W_19 = p(54.616696, 39.735244); 
    static Position T_RU_W_20 = p(46.350320, 48.051091); 
    
    static Position T_RU_E_1 = p(55.033364, 82.916484);
    static Position T_RU_E_2 = p(54.983332, 73.366657);
    static Position T_RU_E_3 = p(54.783333, 56.033333);
    static Position T_RU_E_4 = p(56.000099, 92.996013);
    static Position T_RU_E_5 = p(53.333380, 83.764955);
    static Position T_RU_E_6 = p(43.166739, 131.933002);
    static Position T_RU_E_7 = p(52.283461, 104.265055);
    static Position T_RU_E_8 = p(57.166660, 65.550063);
    static Position T_RU_E_9 = p(48.466667, 135.099999);
    static Position T_RU_E_10 = p(53.749999, 87.116674);
    
    
    // Cuba
    static Position S_CU_1 = p(22.856729, -81.350778);
    static Position S_CU_2 = p(22.584077, -80.486039);
    static Position S_CU_3 = p(22.759543, -83.017490);
    static Position S_CU_4 = p(22.178572, -78.975909);
        
    static Position T_CU_1 = p(21.312537, -77.676814);
    static Position T_CU_2 = p(20.413760, -76.074967);
    
    // UK
    static Position S_UK_1 = p(58.233337, -3.922232); 
    static Position S_UK_2 = p(56.956277, -5.358917); 
    static Position S_UK_3 = p(56.875267, -2.723850); 
    static Position S_UK_4 = p(55.629816, -2.670441); 
    static Position S_UK_5 = p(54.634609, -2.323511); 
    
    static Position T_UK_1 = p(51.479383, -0.136678);
    static Position T_UK_2 = p(52.483583, -1.884538);
    static Position T_UK_3 = p(53.414382, -2.990221);
    static Position T_UK_4 = p(51.456311, -2.578747);
    
    // France
    static Position S_FR_1 = p(48.868417, 4.453851); 
    static Position S_FR_2 = p(47.329676, 6.189447); 
    static Position S_FR_3 = p(48.733892, 7.543905); 
    static Position S_FR_4 = p(50.704996, 1.850252); 
    static Position S_FR_5 = p(48.799560, 6.189427); 
    static Position S_FR_6 = p(45.882037, 6.415725); 
    static Position S_FR_7 = p(43.944001, 5.933118); 
    static Position S_FR_8 = p(45.811206, 4.109827); 
    
    static Position T_FR_1 = p(48.849221, 2.366801);
    static Position T_FR_2 = p(43.302799, 5.371193);
    static Position T_FR_3 = p(44.839926, -0.577118);
    static Position T_FR_4 = p(43.603834, 1.441801);
    static Position T_FR_5 = p(49.490470, 0.176972);
    static Position T_FR_6 = p(48.111495, -1.677289);
    
    
    // Turkey
    static Position S_TR_1 = p(39.450642, 31.185824);
    static Position S_TR_2 = p(37.834815, 32.826651);
    static Position S_TR_3 = p(38.629914, 37.878372);
    
    
    public static ArrayList<MissleSilo> createAll() { 
        ArrayList<MissleSilo> sites = new ArrayList<MissleSilo>();
        
        sites.add(new MissleSilo("S_IR_1 -> T_IL_1", 
                S_IR_1, T_IL_1, 0, 1)); 

        sites.add(new MissleSilo("S_IL_1 -> S_IR_1",
                S_IL_1, S_IR_1, 6, 1));
        
        sites.add(new MissleSilo("S_IL_2 -> T_IR_1", 
                S_IL_2, T_IR_1, 7.4, 1)); 
        sites.add(new MissleSilo("S_IL_3 -> T_IR_2", 
                S_IL_3, T_IR_2, 7.9, 1)); 
        sites.add(new MissleSilo("S_IL_4 -> T_IR_3", 
                S_IL_4, T_IR_3, 8.5, 1)); 
        sites.add(new MissleSilo("S_IL_5 -> T_IR_4", 
                S_IL_5, T_IR_4, 8.9, 1)); 
        sites.add(new MissleSilo("S_IL_6 -> T_IR_5", 
                S_IL_6, T_IR_5, 9.7, 1)); 
        sites.add(new MissleSilo("S_IL_7 -> T_IR_6", 
                S_IL_7, T_IR_6, 10.2, 1));         
        sites.add(new MissleSilo("S_IL_8 -> T_IR_7", 
                S_IL_8, T_IR_7, 11.3, 1));
        sites.add(new MissleSilo("S_IL_1 -> T_IR_8", 
                S_IL_1, T_IR_8, 11.8, 1));
        sites.add(new MissleSilo("S_IL_2 -> T_IR_9", 
                S_IL_2, T_IR_9, 12.6, 1));
        sites.add(new MissleSilo("S_IL_3 -> T_IR_10", 
                S_IL_3, T_IR_10, 13.5, 1));
        sites.add(new MissleSilo("S_IL_4 -> T_IR_11", 
                S_IL_4, T_IR_11, 13.7, 1));
        sites.add(new MissleSilo("S_IL_5 -> T_IR_12", 
                S_IL_5, T_IR_12, 14.0, 1));
        sites.add(new MissleSilo("S_IL_6 -> T_IR_13", 
                S_IL_6, T_IR_13, 15.0, 1));
        sites.add(new MissleSilo("S_IL_7 -> T_IR_14", 
                S_IL_7, T_IR_14, 15.3, 1));
        sites.add(new MissleSilo("S_IL_8 -> T_IR_15", 
                S_IL_8, T_IR_15, 15.9, 1));

        sites.add(new MissleSilo("S_CN_1 -> S_IL_1", 
                S_CN_1, S_IL_1, 20.1, 2));
        sites.add(new MissleSilo("S_CN_2 -> S_IL_2", 
                S_CN_2, S_IL_2, 21.5, 2));
        sites.add(new MissleSilo("S_CN_3 -> S_IL_3", 
                S_CN_3, S_IL_3, 21.9, 2));
        sites.add(new MissleSilo("S_CN_4 -> S_IL_4", 
                S_CN_4, S_IL_4, 22.2, 2));
        sites.add(new MissleSilo("S_CN_5 -> S_IL_5", 
                S_CN_5, S_IL_5, 22.4, 2));
        sites.add(new MissleSilo("S_CN_6 -> S_IL_6", 
                S_CN_6, S_IL_6, 22.5, 2));
        sites.add(new MissleSilo("S_CN_7 -> S_IL_7", 
                S_CN_7, S_IL_7, 23.1, 2));
        sites.add(new MissleSilo("S_CN_8 -> S_IL_8", 
                S_CN_8, S_IL_8, 25.3, 2));

        sites.add(new MissleSilo("S_IL_1 -> T_CN_1", 
                S_IL_1, T_CN_1, 27.3, 2.5));
        sites.add(new MissleSilo("S_IL_2 -> T_CN_2", 
                S_IL_2, T_CN_2, 27.7, 2.5));
        sites.add(new MissleSilo("S_IL_3 -> T_CN_3", 
                S_IL_3, T_CN_3, 28.1, 2.5));
        sites.add(new MissleSilo("S_IL_4 -> T_CN_4", 
                S_IL_4, T_CN_4, 28.2, 2.5));
        sites.add(new MissleSilo("S_IL_5 -> T_CN_5", 
                S_IL_5, T_CN_5, 28.9, 2.5));
        sites.add(new MissleSilo("S_IL_6 -> T_CN_6", 
                S_IL_6, T_CN_6, 30.1, 2.5));
        sites.add(new MissleSilo("S_IL_7 -> T_CN_7", 
                S_IL_7, T_CN_7, 31.3, 2.5));
        sites.add(new MissleSilo("S_IL_8 -> T_CN_8", 
                S_IL_8, T_CN_8, 31.5, 2.5));
        

        sites.add(new MissleSilo("S_TR_1 -> S_CN_1", 
                S_TR_1, S_CN_1, 22.7, 3));
        sites.add(new MissleSilo("S_TR_2 -> S_CN_2", 
                S_TR_2, S_CN_2, 23.1, 3));
        sites.add(new MissleSilo("S_TR_3 -> S_CN_3", 
                S_TR_3, S_CN_3, 23.4, 3));
        sites.add(new MissleSilo("S_TR_1 -> S_CN_4", 
                S_TR_1, S_CN_4, 24.1, 3));
        sites.add(new MissleSilo("S_TR_2 -> S_CN_5", 
                S_TR_2, S_CN_5, 24.2, 3));
        sites.add(new MissleSilo("S_TR_3 -> S_CN_6", 
                S_TR_3, S_CN_6, 24.7, 3));
        
        sites.add(new MissleSilo("S_RU_SW_1 -> S_TR_1", 
                S_RU_SW_1, S_TR_1, 25.1, 3));
        sites.add(new MissleSilo("S_RU_SW_2 -> S_TR_2", 
                S_RU_SW_2, S_TR_2, 25.3, 3));
        sites.add(new MissleSilo("S_RU_SW_3 -> S_TR_3", 
                S_RU_SW_3, S_TR_3, 25.5, 3));
        
        sites.add(new MissleSilo("S_TR_1 -> S_RU_SW_5", 
                S_TR_1, S_RU_SW_5, 27.0, 3));
        sites.add(new MissleSilo("S_TR_2 -> S_RU_SW_4", 
                S_TR_2, S_RU_SW_4, 27.1, 3));
        sites.add(new MissleSilo("S_TR_3 -> S_RU_SW_3", 
                S_TR_3, S_RU_SW_3, 27.3, 3));
        sites.add(new MissleSilo("S_TR_1 -> S_RU_SW_2", 
                S_TR_1, S_RU_SW_2, 28.1, 3));
        sites.add(new MissleSilo("S_TR_2 -> S_RU_SW_1", 
                S_TR_2, S_RU_SW_1, 28.2, 3));
        
        sites.add(new MissleSilo("S_US_AK_1 -> S_CN_7", 
                S_US_AK_1, S_CN_7, 28.9, 3));
        sites.add(new MissleSilo("S_US_AK_2 -> S_CN_8", 
                S_US_AK_2, S_CN_8, 29.1, 3));
        sites.add(new MissleSilo("S_US_AK_3 -> S_CN_9", 
                S_US_AK_3, S_CN_9, 29.2, 3));
        sites.add(new MissleSilo("S_US_AK_4 -> S_CN_10", 
                S_US_AK_4, S_CN_10, 29.3, 3));
        
        
        sites.add(new MissleSilo("S_US_W_7 -> S_CN_NE_1", 
                S_US_W_7, S_CN_NE_1, 30.2, 3));
        sites.add(new MissleSilo("S_US_W_8 -> S_CN_NE_2", 
                S_US_W_8, S_CN_NE_2, 30.3, 3));
        sites.add(new MissleSilo("S_US_W_9 -> S_CN_NE_3", 
                S_US_W_9, S_CN_NE_3, 30.5, 3));
        sites.add(new MissleSilo("S_US_W_10 -> S_CN_NE_4", 
                S_US_W_10, S_CN_NE_4, 30.6, 3));
        sites.add(new MissleSilo("S_US_W_1 -> S_CN_NE_5", 
                S_US_W_1, S_CN_NE_5, 31.1, 3));
        
        
        sites.add(new MissleSilo("S_RU_E_1 -> S_US_AK_1", 
                S_RU_E_1, S_US_AK_1, 31.1, 3));
        sites.add(new MissleSilo("S_RU_E_2 -> S_US_AK_2", 
                S_RU_E_2, S_US_AK_2, 31.3, 3));
        sites.add(new MissleSilo("S_RU_E_3 -> S_US_AK_3", 
                S_RU_E_3, S_US_AK_3, 31.4, 3));
        sites.add(new MissleSilo("S_RU_E_4 -> S_US_AK_4", 
                S_RU_E_4, S_US_AK_4, 31.5, 3));
        
        sites.add(new MissleSilo("S_RU_W_1 -> S_US_E_1", 
                S_RU_W_1, S_US_E_1, 31.2, 3));
        sites.add(new MissleSilo("S_RU_W_2 -> S_US_E_2", 
                S_RU_W_2, S_US_E_2, 31.4, 3));
        sites.add(new MissleSilo("S_RU_W_3 -> S_US_E_3", 
                S_RU_W_3, S_US_E_3, 31.6, 3));
        sites.add(new MissleSilo("S_RU_W_4 -> S_US_E_4", 
                S_RU_W_4, S_US_E_4, 31.8, 3));
        sites.add(new MissleSilo("S_RU_W_5 -> S_US_E_5", 
                S_RU_W_5, S_US_E_5, 31.1, 3));
        sites.add(new MissleSilo("S_RU_W_6 -> S_US_E_6", 
                S_RU_W_6, S_US_E_6, 31.2, 3));
        sites.add(new MissleSilo("S_RU_W_7 -> S_US_E_7", 
                S_RU_W_7, S_US_E_7, 31.4, 3));
        sites.add(new MissleSilo("S_RU_W_8 -> S_US_E_8", 
                S_RU_W_8, S_US_E_8, 31.6, 3));
        sites.add(new MissleSilo("S_RU_W_9 -> S_US_E_9", 
                S_RU_W_9, S_US_E_9, 31.7, 3));
        sites.add(new MissleSilo("S_RU_W_10 -> S_US_E_10", 
                S_RU_W_10, S_US_E_10, 31.9, 3));

        
        sites.add(new MissleSilo("S_RU_C_1 -> S_US_C_1", 
                S_RU_C_1, S_US_C_1, 31.3, 3));
        sites.add(new MissleSilo("S_RU_C_2 -> S_US_C_2", 
                S_RU_C_2, S_US_C_2, 31.5, 3));
        sites.add(new MissleSilo("S_RU_C_3 -> S_US_C_3", 
                S_RU_C_3, S_US_C_3, 31.7, 3));
        sites.add(new MissleSilo("S_RU_C_4 -> S_US_C_4", 
                S_RU_C_4, S_US_C_4, 31.9, 3));
        sites.add(new MissleSilo("S_RU_C_5 -> S_US_C_5", 
                S_RU_C_5, S_US_C_5, 31.2, 3));
        sites.add(new MissleSilo("S_RU_C_6 -> S_US_C_6", 
                S_RU_C_6, S_US_C_6, 31.3, 3));
        sites.add(new MissleSilo("S_RU_C_7 -> S_US_C_7", 
                S_RU_C_7, S_US_C_7, 31.5, 3));
        sites.add(new MissleSilo("S_RU_C_8 -> S_US_C_8", 
                S_RU_C_8, S_US_C_8, 31.7, 3));
        sites.add(new MissleSilo("S_RU_C_9 -> S_US_C_9", 
                S_RU_C_9, S_US_C_9, 31.9, 3));
        sites.add(new MissleSilo("S_RU_C_10 -> S_US_C_10", 
                S_RU_C_10, S_US_C_10, 31.9, 3));

        sites.add(new MissleSilo("S_RU_C_1 -> T_US_MW_1", 
                S_RU_C_1, T_US_MW_1, 32.3, 3));
        sites.add(new MissleSilo("S_RU_C_2 -> T_US_MW_2", 
                S_RU_C_2, T_US_MW_2, 32.5, 3));
        sites.add(new MissleSilo("S_RU_C_3 -> T_US_MW_3", 
                S_RU_C_3, T_US_MW_3, 32.7, 3));
        sites.add(new MissleSilo("S_RU_C_4 -> T_US_MW_4", 
                S_RU_C_4, T_US_MW_4, 32.9, 3));
        sites.add(new MissleSilo("S_RU_C_5 -> T_US_MW_5", 
                S_RU_C_5, T_US_MW_5, 33.2, 3));
        
        sites.add(new MissleSilo("S_US_W_1 -> S_RU_E_10", 
                S_US_W_1, S_RU_E_10, 33.0, 3));
        sites.add(new MissleSilo("S_US_W_2 -> S_RU_E_9", 
                S_US_W_2, S_RU_E_9, 33.2, 3));
        sites.add(new MissleSilo("S_US_W_3 -> S_RU_E_8", 
                S_US_W_3, S_RU_E_8, 33.3, 3));
        sites.add(new MissleSilo("S_US_W_4 -> S_RU_E_7", 
                S_US_W_4, S_RU_E_7, 33.4, 3));
        sites.add(new MissleSilo("S_US_W_5 -> S_RU_E_6", 
                S_US_W_5, S_RU_E_6, 33.5, 3));
        sites.add(new MissleSilo("S_US_W_6 -> S_RU_E_5", 
                S_US_W_6, S_RU_E_5, 33.7, 3));
        sites.add(new MissleSilo("S_US_W_7 -> S_RU_E_4", 
                S_US_W_7, S_RU_E_4, 33.8, 3));
        sites.add(new MissleSilo("S_US_W_8 -> S_RU_E_3", 
                S_US_W_8, S_RU_E_3, 33.8, 3));
        sites.add(new MissleSilo("S_US_W_9 -> S_RU_E_2", 
                S_US_W_9, S_RU_E_2, 34.0, 3));
        sites.add(new MissleSilo("S_US_W_10 -> S_RU_E_1", 
                S_US_W_10, S_RU_E_1, 34.1, 3));
        
        sites.add(new MissleSilo("S_US_AK_1 -> S_RU_E_4", 
                S_US_AK_1, S_RU_E_4, 32.4, 3));
        sites.add(new MissleSilo("S_US_AK_2 -> S_RU_E_3", 
                S_US_AK_2, S_RU_E_3, 32.1, 3));
        sites.add(new MissleSilo("S_US_AK_3 -> S_RU_E_2", 
                S_US_AK_3, S_RU_E_2, 32.5, 3));
        sites.add(new MissleSilo("S_US_AK_4 -> S_RU_E_1", 
                S_US_AK_4, S_RU_E_1, 32.7, 3));

        sites.add(new MissleSilo("S_US_E_1 -> T_RU_W_1", 
                S_US_E_1, T_RU_W_1, 32.2, 3));
        sites.add(new MissleSilo("S_US_E_2 -> T_RU_W_2", 
                S_US_E_2, T_RU_W_2, 32.3, 3));
        sites.add(new MissleSilo("S_US_E_3 -> T_RU_W_3", 
                S_US_E_3, T_RU_W_3, 32.5, 3));
        sites.add(new MissleSilo("S_US_E_4 -> T_RU_W_4", 
                S_US_E_4, T_RU_W_4, 32.6, 3));
        sites.add(new MissleSilo("S_US_E_5 -> T_RU_W_5", 
                S_US_E_5, T_RU_W_5, 32.7, 3));
        sites.add(new MissleSilo("S_US_E_6 -> T_RU_W_6", 
                S_US_E_6, T_RU_W_6, 32.8, 3));
        sites.add(new MissleSilo("S_US_E_7 -> T_RU_W_7", 
                S_US_E_7, T_RU_W_7, 32.2, 3));
        sites.add(new MissleSilo("S_US_E_8 -> T_RU_W_8", 
                S_US_E_8, T_RU_W_8, 32.4, 3));
        sites.add(new MissleSilo("S_US_E_9 -> T_RU_W_9", 
                S_US_E_9, T_RU_W_9, 32.5, 3));
        sites.add(new MissleSilo("S_US_E_10 -> T_RU_W_10", 
                S_US_E_10, T_RU_W_10, 32.7, 3));
        sites.add(new MissleSilo("S_US_E_1 -> T_RU_W_11", 
                S_US_E_1, T_RU_W_11, 33.2, 3));
        sites.add(new MissleSilo("S_US_E_2 -> T_RU_W_12", 
                S_US_E_2, T_RU_W_12, 33.3, 3));
        sites.add(new MissleSilo("S_US_E_3 -> T_RU_W_13", 
                S_US_E_3, T_RU_W_13, 33.5, 3));
        sites.add(new MissleSilo("S_US_E_4 -> T_RU_W_14", 
                S_US_E_4, T_RU_W_14, 33.6, 3));
        sites.add(new MissleSilo("S_US_E_5 -> T_RU_W_15", 
                S_US_E_5, T_RU_W_15, 33.7, 3));
        sites.add(new MissleSilo("S_US_E_6 -> T_RU_W_16", 
                S_US_E_6, T_RU_W_16, 33.8, 3));
        sites.add(new MissleSilo("S_US_E_7 -> T_RU_W_17", 
                S_US_E_7, T_RU_W_17, 33.2, 3));
        sites.add(new MissleSilo("S_US_E_8 -> T_RU_W_18", 
                S_US_E_8, T_RU_W_18, 33.4, 3));
        sites.add(new MissleSilo("S_US_E_9 -> T_RU_W_19", 
                S_US_E_9, T_RU_W_19, 33.5, 3));
        sites.add(new MissleSilo("S_US_E_10 -> T_RU_W_20", 
                S_US_E_10, T_RU_W_20, 33.7, 3));

        sites.add(new MissleSilo("S_US_W_1 -> T_RU_E_1", 
                S_US_C_1, T_RU_E_1, 32.4, 3));
        sites.add(new MissleSilo("S_US_W_2 -> T_RU_E_2", 
                S_US_C_2, T_RU_E_2, 32.5, 3));
        sites.add(new MissleSilo("S_US_W_3 -> T_RU_E_3", 
                S_US_C_3, T_RU_E_3, 32.7, 3));
        sites.add(new MissleSilo("S_US_W_4 -> T_RU_E_4", 
                S_US_C_4, T_RU_E_4, 32.8, 3));
        sites.add(new MissleSilo("S_US_W_5 -> T_RU_E_5", 
                S_US_C_5, T_RU_E_5, 32.9, 3));
        sites.add(new MissleSilo("S_US_W_6 -> T_RU_E_6", 
                S_US_C_6, T_RU_E_6, 33.1, 3));
        sites.add(new MissleSilo("S_US_W_7 -> T_RU_E_7", 
                S_US_C_7, T_RU_E_7, 33.2, 3));
        sites.add(new MissleSilo("S_US_W_8 -> T_RU_E_8", 
                S_US_C_8, T_RU_E_8, 33.4, 3));
        sites.add(new MissleSilo("S_US_W_9 -> T_RU_E_9", 
                S_US_C_9, T_RU_E_9, 33.5, 3));
        sites.add(new MissleSilo("S_US_W_10 -> T_RU_E_10", 
                S_US_C_10, T_RU_E_10, 33.7, 3));
        
        sites.add(new MissleSilo("S_RU_W_1 -> T_US_E_1", 
                S_RU_W_1, T_US_E_1, 34.7, 3));
        sites.add(new MissleSilo("S_RU_W_2 -> T_US_E_2", 
                S_RU_W_2, T_US_E_2, 34.9, 3));
        sites.add(new MissleSilo("S_RU_W_3 -> T_US_E_3", 
                S_RU_W_3, T_US_E_3, 35.1, 3));
        sites.add(new MissleSilo("S_RU_W_4 -> T_US_E_4", 
                S_RU_W_4, T_US_E_4, 35.2, 3));
        sites.add(new MissleSilo("S_RU_W_5 -> T_US_E_5", 
                S_RU_W_5, T_US_E_5, 35.3, 3));
        sites.add(new MissleSilo("S_RU_W_6 -> T_US_E_6", 
                S_RU_W_6, T_US_E_6, 35.5, 3));
        sites.add(new MissleSilo("S_RU_W_7 -> T_US_E_7", 
                S_RU_W_7, T_US_E_7, 35.7, 3));
        sites.add(new MissleSilo("S_RU_W_8 -> T_US_E_8", 
                S_RU_W_8, T_US_E_8, 35.7, 3));
        sites.add(new MissleSilo("S_RU_W_9 -> T_US_E_9", 
                S_RU_W_9, T_US_E_9, 35.8, 3));
        sites.add(new MissleSilo("S_RU_W_10 -> T_US_E_10", 
                S_RU_W_10, T_US_E_10, 35.9, 3));

        sites.add(new MissleSilo("S_RU_E_1 -> T_US_W_1", 
                S_RU_E_1, T_US_W_1, 34.8, 3));
        sites.add(new MissleSilo("S_RU_E_2 -> T_US_W_2", 
                S_RU_E_2, T_US_W_2, 34.9, 3));
        sites.add(new MissleSilo("S_RU_E_3 -> T_US_W_3", 
                S_RU_E_3, T_US_W_3, 35.2, 3));
        sites.add(new MissleSilo("S_RU_E_4 -> T_US_W_4", 
                S_RU_E_4, T_US_W_4, 35.4, 3));
        sites.add(new MissleSilo("S_RU_E_5 -> T_US_W_5", 
                S_RU_E_5, T_US_W_5, 35.5, 3));
        sites.add(new MissleSilo("S_RU_E_6 -> T_US_W_6", 
                S_RU_E_6, T_US_W_6, 35.5, 3));
        sites.add(new MissleSilo("S_RU_E_7 -> T_US_W_7", 
                S_RU_E_7, T_US_W_7, 35.8, 3));
        sites.add(new MissleSilo("S_RU_E_8 -> T_US_W_8", 
                S_RU_E_8, T_US_W_8, 35.8, 3));
        sites.add(new MissleSilo("S_RU_E_9 -> T_US_W_9", 
                S_RU_E_9, T_US_W_9, 35.9, 3));
        sites.add(new MissleSilo("S_RU_E_10 -> T_US_W_10", 
                S_RU_E_10, T_US_W_10, 35.9, 3));
        
        sites.add(new MissleSilo("S_RU_E_1 -> S_US_W_1", 
                S_RU_E_1, S_US_W_1, 32.4, 3));
        sites.add(new MissleSilo("S_RU_E_2 -> S_US_W_2", 
                S_RU_E_2, S_US_W_2, 32.9, 3));
        sites.add(new MissleSilo("S_RU_E_3 -> S_US_W_3", 
                S_RU_E_3, S_US_W_3, 32.1, 3));
        sites.add(new MissleSilo("S_RU_E_4 -> S_US_W_4", 
                S_RU_E_4, S_US_W_4, 32.5, 3));

        sites.add(new MissleSilo("S_CN_NE_1 -> S_US_W_5", 
                S_CN_NE_1, S_US_W_5, 33.1, 3));
        sites.add(new MissleSilo("S_CN_NE_2 -> S_US_W_6", 
                S_CN_NE_2, S_US_W_6, 33.4, 3));
        sites.add(new MissleSilo("S_CN_NE_3 -> S_US_W_7", 
                S_CN_NE_3, S_US_W_7, 33.8, 3));
        sites.add(new MissleSilo("S_CN_NE_4 -> S_US_W_8", 
                S_CN_NE_4, S_US_W_8, 33.5, 3));
        sites.add(new MissleSilo("S_CN_NE_5 -> S_US_W_9", 
                S_CN_NE_5, S_US_W_9, 33.9, 3));
        sites.add(new MissleSilo("S_CN_NE_1 -> S_US_W_10", 
                S_CN_NE_1, S_US_W_10, 33.1, 3));
        
        sites.add(new MissleSilo("S_CU_1 -> T_US_SE_1", 
                S_CU_1, T_US_SE_1, 34.1, 1));
        sites.add(new MissleSilo("S_CU_2 -> T_US_SE_2", 
                S_CU_2, T_US_SE_2, 34.5, 1));
        sites.add(new MissleSilo("S_CU_3 -> T_US_SE_3", 
                S_CU_3, T_US_SE_3, 34.9, 1));
        sites.add(new MissleSilo("S_CU_4 -> T_US_SE_4", 
                S_CU_4, T_US_SE_4, 34.4, 1));
        sites.add(new MissleSilo("S_CU_1 -> T_US_SE_5", 
                S_CU_1, T_US_SE_5, 34.9, 1));
        sites.add(new MissleSilo("S_CU_2 -> T_US_SE_6", 
                S_CU_2, T_US_SE_6, 34.1, 1));
        sites.add(new MissleSilo("S_CU_3 -> T_US_SE_7", 
                S_CU_3, T_US_SE_7, 34.2, 1));
        sites.add(new MissleSilo("S_CU_4 -> T_US_SE_8", 
                S_CU_4, T_US_SE_8, 34.9, 1));

        sites.add(new MissleSilo("S_US_C_1 -> S_CU_1", 
                S_US_C_1, S_CU_1, 36.0, 3));
        sites.add(new MissleSilo("S_US_C_2 -> S_CU_2", 
                S_US_C_2, S_CU_2, 36.2, 3));
        sites.add(new MissleSilo("S_US_C_3 -> S_CU_3", 
                S_US_C_3, S_CU_3, 36.4, 3));
        sites.add(new MissleSilo("S_US_C_4 -> S_CU_4", 
                S_US_C_4, S_CU_4, 36.7, 3));
        sites.add(new MissleSilo("S_US_C_5 -> T_CU_1", 
                S_US_C_5, T_CU_1, 36.8, 3));
        sites.add(new MissleSilo("S_US_C_6 -> T_CU_2", 
                S_US_C_6, T_CU_1, 36.9, 3));
        
        sites.add(new MissleSilo("S_UK_1 -> S_RU_W_1", 
                S_UK_1, S_RU_W_1, 36.1, 3));
        sites.add(new MissleSilo("S_UK_2 -> S_RU_W_2", 
                S_UK_2, S_RU_W_2, 36.3, 3));
        sites.add(new MissleSilo("S_UK_3 -> S_RU_W_3", 
                S_UK_3, S_RU_W_3, 36.7, 3));
        sites.add(new MissleSilo("S_UK_4 -> S_RU_W_4", 
                S_UK_4, S_RU_W_4, 36.2, 3));
        sites.add(new MissleSilo("S_UK_5 -> S_RU_W_5", 
                S_UK_5, S_RU_W_5, 36.4, 3));
        sites.add(new MissleSilo("S_UK_1 -> S_RU_C_4", 
                S_UK_1, S_RU_C_4, 37.4, 3));
        sites.add(new MissleSilo("S_UK_2 -> S_RU_C_5", 
                S_UK_1, S_RU_C_4, 37.4, 3));
        sites.add(new MissleSilo("S_UK_3 -> S_RU_C_6", 
                S_UK_1, S_RU_C_4, 37.4, 3));
        sites.add(new MissleSilo("S_UK_4 -> S_RU_C_7", 
                S_UK_1, S_RU_C_4, 37.4, 3));
        sites.add(new MissleSilo("S_UK_5 -> S_RU_C_8", 
                S_UK_1, S_RU_C_4, 37.4, 3));

        
        sites.add(new MissleSilo("S_FR_1 -> S_RU_W_6", 
                S_FR_1, S_RU_W_6, 37.4, 3));
        sites.add(new MissleSilo("S_FR_2 -> S_RU_W_7", 
                S_FR_2, S_RU_W_7, 37.7, 3));
        sites.add(new MissleSilo("S_FR_3 -> S_RU_W_8", 
                S_FR_3, S_RU_W_8, 37.7, 3));
        sites.add(new MissleSilo("S_FR_4 -> S_RU_W_9", 
                S_FR_4, S_RU_W_9, 37.9, 3));
        sites.add(new MissleSilo("S_FR_5 -> S_RU_W_10", 
                S_FR_5, S_RU_W_10, 38.2, 3));
        sites.add(new MissleSilo("S_FR_6 -> S_RU_C_1", 
                S_FR_6, S_RU_C_1, 38.4, 3));
        sites.add(new MissleSilo("S_FR_7 -> S_RU_C_2", 
                S_FR_7, S_RU_C_2, 38.6, 3));
        sites.add(new MissleSilo("S_FR_8 -> S_RU_C_3", 
                S_FR_8, S_RU_C_3, 38.7, 3));
        sites.add(new MissleSilo("S_FR_1 -> S_RU_C_9", 
                S_FR_1, S_RU_C_9, 38.6, 3));
        sites.add(new MissleSilo("S_FR_2 -> S_RU_C_10", 
                S_FR_2, S_RU_C_10, 38.8, 3));
        
        sites.add(new MissleSilo("S_RU_W_10 -> S_UK_1", 
                S_RU_W_10, S_UK_1, 38.2, 3));
        sites.add(new MissleSilo("S_RU_W_9 -> S_UK_2", 
                S_RU_W_9, S_UK_2, 38.4, 3));
        sites.add(new MissleSilo("S_RU_W_8 -> S_UK_3", 
                S_RU_W_8, S_UK_3, 38.9, 3));
        sites.add(new MissleSilo("S_RU_W_7 -> S_UK_4", 
                S_RU_W_7, S_UK_4, 39.1, 3));
        sites.add(new MissleSilo("S_RU_W_6 -> S_UK_5", 
                S_RU_W_6, S_UK_5, 39.2, 3));
        
        sites.add(new MissleSilo("S_RU_W_5 -> S_FR_1", 
                S_RU_W_5, S_FR_1, 40.5, 3));
        sites.add(new MissleSilo("S_RU_W_4 -> S_FR_2", 
                S_RU_W_4, S_FR_2, 40.6, 3));
        sites.add(new MissleSilo("S_RU_W_3 -> S_FR_3", 
                S_RU_W_3, S_FR_3, 40.9, 3));
        sites.add(new MissleSilo("S_RU_W_2 -> S_FR_4", 
                S_RU_W_2, S_FR_4, 41.2, 3));
        sites.add(new MissleSilo("S_RU_W_1 -> S_FR_5", 
                S_RU_W_1, S_FR_5, 41.4, 3));
        sites.add(new MissleSilo("S_RU_W_10 -> S_FR_6", 
                S_RU_W_10, S_FR_6, 41.6, 3));
        sites.add(new MissleSilo("S_RU_W_9 -> S_FR_7", 
                S_RU_W_9, S_FR_7, 41.7, 3));
        sites.add(new MissleSilo("S_RU_W_8 -> S_FR_8", 
                S_RU_W_8, S_FR_8, 41.9, 3));

        sites.add(new MissleSilo("S_RU_W_6 -> T_UK_1", 
                S_RU_W_6, T_UK_1, 41.9, 3));
        sites.add(new MissleSilo("S_RU_W_5 -> T_UK_2", 
                S_RU_W_5, T_UK_2, 42.0, 3));
        sites.add(new MissleSilo("S_RU_W_4 -> T_UK_3", 
                S_RU_W_4, T_UK_3, 42.1, 3));
        sites.add(new MissleSilo("S_RU_W_3 -> T_UK_4", 
                S_RU_W_3, T_UK_4, 42.3, 3));

        sites.add(new MissleSilo("S_RU_W_2 -> T_FR_1", 
                S_RU_W_2, T_FR_1, 42.4, 3));
        sites.add(new MissleSilo("S_RU_W_1 -> T_FR_2", 
                S_RU_W_1, T_FR_2, 42.9, 3));
        sites.add(new MissleSilo("S_RU_C_1 -> T_FR_3", 
                S_RU_C_1, T_FR_3, 42.9, 3));
        sites.add(new MissleSilo("S_RU_C_2 -> T_FR_4", 
                S_RU_C_2, T_FR_4, 43.1, 3));
        sites.add(new MissleSilo("S_RU_C_3 -> T_FR_5", 
                S_RU_C_3, T_FR_5, 43.3, 3));
        sites.add(new MissleSilo("S_RU_C_4 -> T_FR_6", 
                S_RU_C_4, T_FR_6, 43.4, 3));
        
        return sites; 
    }
    
}
