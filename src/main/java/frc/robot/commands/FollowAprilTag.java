package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

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
            driveSubsystem.arcadeDrive(0, 0);
        }
        //right
        else if(target.getYaw() > 0){
            driveSubsystem.arcadeDrive(0.45, 0.45);
        }
        //left
        else {
            driveSubsystem.arcadeDrive(0.45, -0.45);
        }        
    }
}
