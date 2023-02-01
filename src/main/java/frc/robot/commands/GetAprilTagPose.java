package frc.robot.commands;
import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class GetAprilTagPose extends CommandBase {
    private VisionSubsystem visionSubsystem;
    Pose2d prevEstimatedRobotPose = new Pose2d(0, 0, new Rotation2d(0, 0));
    Optional<EstimatedRobotPose> estimatedPose;
    AprilTagFieldLayout aprilTagFieldLayout;
    PhotonPoseEstimator photonPoseEstimator;
    private Field2d field = new Field2d();



    public GetAprilTagPose(VisionSubsystem visionSubsystem) {
        addRequirements(visionSubsystem);
        
        this.visionSubsystem = visionSubsystem;

        try {
            aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }

        SmartDashboard.putData("Field", field);

        if (!visionSubsystem.getCamera().getLatestResult().hasTargets()) { return; }

        //Forward Camera
        
        Transform3d robotToCam = visionSubsystem.getCamera().getLatestResult().getBestTarget().getBestCameraToTarget();
        //Cam mounted facing forward, half a meter forward of center, half a meter up from center.

        // Construct PhotonPoseEstimator
        photonPoseEstimator = new PhotonPoseEstimator(
            aprilTagFieldLayout, 
            PoseStrategy.LOWEST_AMBIGUITY, 
            visionSubsystem.getCamera(), 
            robotToCam
        );
        
    }

    @Override
    public void execute() {
        System.out.println("it is running");
        var pose = getEstimatedGlobalPose(prevEstimatedRobotPose);

        if (pose.isPresent()) {
            System.out.println("Pose Present!");
            System.out.println(pose.get().estimatedPose.toString());
            field.setRobotPose(pose.get().estimatedPose.toPose2d());
        }

        else {
            System.out.println("Pose Not Present!");
        }
    }

    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return photonPoseEstimator.update();
    }

    // public Optional<EstimatedRobotPose> getEstimatedGlobalPose() {
    //     photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
    //     if (photonPoseEstimator.update().isPresent())
    //     {
    //         estimatedPose = photonPoseEstimator.update();
    //         prevEstimatedRobotPose = estimatedPose.get().estimatedPose.toPose2d();
    //     }
    // }
}