package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    private Talon leftDrive = new Talon(0);
    private Talon rightDrive = new Talon(1);
    private XboxController driveController = new XboxController(0);

    private DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);
    public DifferentialDrive getDrive() {
        return drive;
    }
    
    public DriveSubsystem(){
        rightDrive.setInverted(true);
    }
    
    public void arcadeDrive(double xSpeed, double zRotation) {
        drive.arcadeDrive(xSpeed, -zRotation);
    }
    
    @Override
    public void periodic() {
        double xSpeed = -driveController.getLeftY();
        double zRotation = driveController.getRightX();
        
        if(xSpeed > 0.8) {
            xSpeed = 0.8;
        }
        else if(xSpeed < -0.8) {
            xSpeed = -0.8;
        }

        if(zRotation > 0.8) {
            zRotation = 0.8;
        }
        else if(zRotation < -0.8) {
            zRotation = -0.8;
        }

        arcadeDrive(xSpeed, zRotation);
    }
}
