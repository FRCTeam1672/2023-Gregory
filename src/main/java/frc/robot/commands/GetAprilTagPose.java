package frc.robot.commands;
import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class GetAprilTagPose extends CommandBase {
    private VisionSubsystem visionSubsystem;
    private Pose2d prevEstimatedRobotPose = new Pose2d(0, 0, new Rotation2d(0, 0));
    private Optional<EstimatedRobotPose> estimatedPose;
    private AprilTagFieldLayout aprilTagFieldLayout;
    private PhotonPoseEstimator photonPoseEstimator;
    private Field2d field = new Field2d();
    private AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

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


        //Forward Camera
        Transform3d robotToCam = new Transform3d(new Translation3d(0.36, -0.04, 0.165), new Rotation3d(0,0,0));

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
        getEstimatedGlobalPose(prevEstimatedRobotPose);
        var pose = estimatedPose;
        if (pose != null && pose.isPresent()) {
            field.setRobotPose(pose.get().estimatedPose.toPose2d());
            prevEstimatedRobotPose = pose.get().estimatedPose.toPose2d();
            ahrs.resetDisplacement();
        }
        
        else {
            double x = prevEstimatedRobotPose.getX() + ahrs.getDisplacementX();
            double y = prevEstimatedRobotPose.getY() + ahrs.getDisplacementY();
            SmartDashboard.putNumber("X-Displacement", ahrs.getDisplacementX());
            SmartDashboard.putNumber("Y-Displacement", ahrs.getDisplacementY());
            ahrs.getRotation2d();
            double rotation = ahrs.getFusedHeading();
            SmartDashboard.putNumber("Raw  Rotation", rotation);
            SmartDashboard.putNumber("Rotation-Modified", rotation + 90);   
            Pose2d navXPose2d = new Pose2d(x, y, new Rotation2d(Math.toRadians(rotation)));
            field.setRobotPose(navXPose2d);
        }
    }

    public void getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        if (!visionSubsystem.getCamera().getLatestResult().hasTargets()) {estimatedPose = null; return;}
        photonPoseEstimator.setLastPose(prevEstimatedRobotPose);
        estimatedPose = photonPoseEstimator.update();
    }
}   