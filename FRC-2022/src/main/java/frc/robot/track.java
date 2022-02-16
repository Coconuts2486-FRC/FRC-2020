package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class track {
        // yo thats gay
    //d = (h2-h1) / tan(a1+a2)
    public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    public static final double cameraHeight = 14.5; // inches
    public static final double tapeHeight = 53; // still inches
    public static final double angle1 = 28.9;
    public static double angle2 = 1;
    public static int x = 0;
    public static Joystick joystick = Map.Ids.joystick;
    public static double lastpos = 0;
//4ft
//sus is sus
    public static double getDistance(){
        double ty = table.getEntry("ty").getDouble(0.0);
        angle2 = ty;
        double distance = (tapeHeight - cameraHeight)/ Math.tan(Math.toRadians(angle1)+Math.toRadians(angle2));
        return distance;
    }
    public static double[] adjust(){
    SmartDashboard.putNumber("lastPos", lastpos);
    double Kp = -0.1f;
    double min_command = 0.05;
    double tx = LimeLight.getX();
    double ty = LimeLight.getY();
    double heading_error_Y = -ty;
    double steering_adjust = 0.0;
    double accelerate = 0.0;
    if (joystick.getRawButton(2) && LimeLight.isTarget()){
            double heading_error_X = -tx;
//super sus man
            if (tx > 1.0)
            {
                    steering_adjust = Kp*heading_error_X - min_command;
            }
            else if (tx < 1.0)
            {
                    steering_adjust = Kp*heading_error_X + min_command;
            }


         if (ty > 1.0)
        {
                accelerate = Kp*heading_error_Y - min_command;
        }
        else if (ty < 1.0)
        {
                accelerate = Kp*heading_error_Y + min_command;
        }
    }else if((joystick.getRawButton(2) && !LimeLight.isTarget())){
           
         if (track.lastpos > 1.0)
         {
                steering_adjust = .75;//bro this sus as hell
         }
         else if (track.lastpos < 1.0)
         {
                steering_adjust = -.75;
         }
    }
    double[] both = {steering_adjust,accelerate};
    return both;
}
public static void lastPos(){
        if(LimeLight.isTarget()){
                track.lastpos = LimeLight.getX()/27;
        }
}
}