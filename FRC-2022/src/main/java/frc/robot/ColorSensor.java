package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Manipulators.Intake;


public class ColorSensor {
    public static I2C.Port i2cPort = I2C.Port.kOnboard;
    public static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    public static String DetectedColor() {
        Color color = m_colorSensor.getColor();
        double red = color.red;
        double green = color.green;
        double blue = color.blue;
        String DetectedColor = "None";

        if (red <= 0.58 && red >= 0.47 && 
            green <= 0.39 && green >= 0.31 && 
            blue <= 0.16 && blue >= 0.09) {
                DetectedColor = "Red";
        } else if (red <= 0.21 && red >= 0.12 &&
                    green <= 0.42 && green >= 0.34 &&
                    blue <= 0.5 && blue >= 0.39){
                DetectedColor = "Blue";
        } else {
                DetectedColor = "None";
        }
        return(DetectedColor);
    }
}