package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class DriveSubsystem extends SubsystemBase {
    private VictorSP leftDrive = new VictorSP(0);
    private VictorSP rightDrive = new VictorSP(1);
    private CommandXboxController driveController;

    private DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);
    public DifferentialDrive getDrive() {
        return drive;
    }
    
    public DriveSubsystem(CommandXboxController m_driverController){
        this.driveController = m_driverController;
        rightDrive.setInverted(true);
    }
    
    public void arcadeDrive(double xSpeed, double zRotation) {
        drive.arcadeDrive(xSpeed, -zRotation);
    }
    
    @Override
    public void periodic() {
        double xSpeed = -driveController.getLeftY();
        double zRotation = driveController.getRightX();

        if(xSpeed == 0.0 && zRotation == 0.0){
            drive.stopMotor();
            return;
        }

        arcadeDrive(MathUtil.clamp(xSpeed, -1, 1), MathUtil.clamp(zRotation, -0.9, 0.9));
    }
}
