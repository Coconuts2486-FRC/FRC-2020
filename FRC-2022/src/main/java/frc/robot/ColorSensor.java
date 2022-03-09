package frc.robot;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor {
    public static I2C.Port i2cPort = I2C.Port.kOnboard;
    public static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    public static String DetectedColor() {
        Color color = m_colorSensor.getColor();
        double red = color.red;
        double green = color.green;
        double blue = color.blue;
        String DetectedColor = "None";

        if (red <= 0.57 && red >= 0.33 && 
            green <= 0.46 && green >= 0.31 && 
            blue <= 0.22 && blue >= 0.09) {
                DetectedColor = "Red";
        } else if (red <= 0.24 && red >= 0.11 &&
                    green <= 0.48 && green >= 0.36 &&
                    blue <= 0.50 && blue >= 0.29){
                DetectedColor = "Blue";
        } else {
                DetectedColor = "None";
        }
        return(DetectedColor);
    }
}