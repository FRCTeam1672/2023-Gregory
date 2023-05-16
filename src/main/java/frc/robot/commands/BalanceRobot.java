package frc.robot.commands;

import org.littletonrobotics.junction.Logger;

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
    private int finished = 0;

    public BalanceRobot(GyroSubsystem gyroSubsystem, DriveSubsystem driveSubsystem) {
        addRequirements(gyroSubsystem);
        this.gyroSubsystem = gyroSubsystem;
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void execute() {
        System.out.println("balanceing");
        Logger.getInstance().recordOutput("Balanceing", true);
        AHRS ahrs = this.gyroSubsystem.getAHRS();
        double xSpeed = -GyroUtils.getRoll(ahrs.getRoll());
        if(xSpeed == 0.0){
            finished++;
        }
        else{
            finished = 0;
        }
        this.driveSubsystem.arcadeDrive(xSpeed, 0);
    }
    @Override
    public void end(boolean interrupted) {
        Logger.getInstance().recordOutput("Balanceing", false);
    }
}
