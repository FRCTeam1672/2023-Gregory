package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class DriveSubsystem extends SubsystemBase {
    private WPI_TalonSRX frontLeftDrive = new WPI_TalonSRX(11);
    private WPI_TalonSRX frontRightDrive = new WPI_TalonSRX(12);
    private WPI_VictorSPX rearLeftDrive = new WPI_VictorSPX(13);
    private WPI_VictorSPX rearRightDrive = new WPI_VictorSPX(14);
    private CommandXboxController driveController;

    private DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);

    public DriveSubsystem(CommandXboxController m_driverController) {
        this.driveController = m_driverController;

        rearLeftDrive.follow(frontLeftDrive);
        rearRightDrive.follow(frontRightDrive);

        frontLeftDrive.setNeutralMode(NeutralMode.Brake);
        frontRightDrive.setNeutralMode(NeutralMode.Brake);
        rearLeftDrive.setNeutralMode(NeutralMode.Brake);
        rearRightDrive.setNeutralMode(NeutralMode.Brake);
    }

    public DifferentialDrive getDrive() {
        return drive;
    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        if (xSpeed == 0.0 && zRotation == 0.0) {
            drive.stopMotor();
            return;
        }
        drive.arcadeDrive(xSpeed, -zRotation);
    }

    @Override
    public void periodic() {
        double xSpeed = -driveController.getLeftY();
        double zRotation = driveController.getRightX();

        arcadeDrive(xSpeed, zRotation);
    }
}
