package frc.robot;
//import javax.management.timer.Timer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
public class arduino {
    private static SerialPort arduino;

    private static Timer timer;
    public static void set(){
        try{
        arduino = new SerialPort(9600, SerialPort.Port.kUSB);
        System.out.println("connected");
        } catch(Exception e){
        System.out.println("0 failed"); 
            try{
                arduino = new SerialPort(9600, SerialPort.Port.kUSB);
                System.out.println("connected");
               } catch(Exception e1){
                System.out.println("1 failed"); 
                    try{
                        arduino = new SerialPort(9600, SerialPort.Port.kUSB);
                        System.out.println("connected");
                        } catch(Exception e2){
                         System.out.println("2 failed"); 
                     }
                 }
            }
            timer = new Timer();
            timer.start();
        }
        public static void run(){
            if(timer.get() > 5){
                System.out.println("sucess");
                arduino.write(new byte[] {0x12}, 1);
                timer.reset();
            }

            if(arduino.getBytesReceived() >30){
                System.out.print(arduino.readString());
            }

        }
    }