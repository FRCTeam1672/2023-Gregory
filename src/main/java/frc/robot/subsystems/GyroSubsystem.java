package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroSubsystem extends SubsystemBase {
    private AHRS ahrs;

    public GyroSubsystem() {
        this.ahrs = new AHRS(Port.kMXP);
        System.out.println("is connected?:  " + this.ahrs.isConnected());
    }


    public AHRS getAHRS() {
        return this.ahrs;
    }
}
