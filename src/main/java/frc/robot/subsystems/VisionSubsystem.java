package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
    private PhotonCamera camera = new PhotonCamera("Arducam_OV9281_USB_Camera");

    public PhotonTrackedTarget getTarget() {
        var result = camera.getLatestResult();

        if(!result.hasTargets()) return null;

        PhotonTrackedTarget target = result.getBestTarget();
        return target;
    }


}
