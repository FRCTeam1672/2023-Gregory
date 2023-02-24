package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.utils.GyroUtils;

public class BalanceRobot extends CommandBase {
    private GyroSubsystem gyroSubsystem;
    private DriveSubsystem driveSubsystem;

    public BalanceRobot(GyroSubsystem gyroSubsystem, DriveSubsystem driveSubsystem) {
        addRequirements(gyroSubsystem);
        this.gyroSubsystem = gyroSubsystem;
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void execute() {
        AHRS ahrs = this.gyroSubsystem.getAHRS();
        SmartDashboard.putNumber("Move Value", GyroUtils.getRoll(ahrs.getRoll()));
        this.driveSubsystem.arcadeDrive(-GyroUtils.getRoll(ahrs.getRoll()), 0);
    }
}
