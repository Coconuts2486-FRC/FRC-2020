package frc.robot.Vision;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotMap;

/**
 * LimeLight
 */
public class LimeLight {
    public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    public static boolean led = false;

    public static double getX() {
        // Returns the X axis of the target
        double x = table.getEntry("tx").getDouble(0.0);
        return x;
 //       return table.getEntry("tx").getDouble(0.0) / 27; // translated degrees -27 to 27 to a value between -1 and 1
    }

    public static double getY() {
        // Returns the Y axis of the target
        return table.getEntry("ty").getDouble(0.0) / 27; // translated degrees -27 to 27 to a value between -1 and 1
    }

    public static boolean isTarget() {
        // Returns true if there is a target in frame
        int tv = (int) table.getEntry("tv").getDouble(0.0);
        if (tv == 1) {
            return true;
        } else {
            return false;
        }
    }
    public static double Calibrate(double h1,double h2,double distance){
        double a1 = Math.atan(((h2-h1)/distance))- LimeLight.getY();

        return a1;
    }

    // turns LEDs on
    public static void ledOn(){

        table.getEntry("ledmode").setNumber(3);
    }

    // turns LEDs off
    public static void ledOff(){

        table.getEntry("ledmode").setNumber(1);
    }

    // controls state of LEDs
    public static void ledControl(int button){

        if (RobotMap.operator.getRawButtonPressed(button)){
            if (!led){

                LimeLight.ledOn();
                led = true;
            }
            else{

                LimeLight.ledOff();
                led = false;
            }

        }
    }
}