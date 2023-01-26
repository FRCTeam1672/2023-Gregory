package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.utils.AprilTagUtils;

public class FollowAprilTag extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private VisionSubsystem visionSubsystem;

    public FollowAprilTag(DriveSubsystem driveSubsystem, VisionSubsystem visionSubsystem) {
        addRequirements(driveSubsystem);
        addRequirements(visionSubsystem);
        this.driveSubsystem = driveSubsystem;
        this.visionSubsystem = visionSubsystem;
    }
    @Override
    public void execute() {
        PhotonTrackedTarget target = visionSubsystem.getTarget();
        //null or in front
        if(target == null || target.getYaw() == 0) {
            driveSubsystem.getDrive().stopMotor();
            return;
        }
        double zRotation = AprilTagUtils.getZRotation(target.getYaw());
        double x = AprilTagUtils.getXAmount(target.getBestCameraToTarget().getX());
        System.out.println("Z: " + zRotation + " | X: " + x);
        driveSubsystem.arcadeDrive(x, zRotation);        
    }
}
