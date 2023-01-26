package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    private Talon leftDrive = new Talon(0);
    private Talon rightDrive = new Talon(1);

    private DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);
    public DriveSubsystem(){
        rightDrive.setInverted(true);
    }
    public void arcadeDrive(double xSpeed, double zRotation) {
        drive.arcadeDrive(xSpeed, -zRotation);
    }
}
