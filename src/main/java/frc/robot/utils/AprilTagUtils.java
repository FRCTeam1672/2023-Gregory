package frc.robot.utils;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;

public class AprilTagUtils {
    private static PIDController zController = new PIDController(Constants.PIDConstants.kp, Constants.PIDConstants.ki, Constants.PIDConstants.kd);
    private static PIDController xController = new PIDController(Constants.PIDConstants.Xkp, Constants.PIDConstants.Xki, Constants.PIDConstants.Xkd);

    public static double getZRotation(double aprilTagYaw) {
        return -MathUtil.clamp(zController.calculate(aprilTagYaw), -0.75, 0.75);
    } 
    public static double getXAmount(double aprilTagDistance){
        return -MathUtil.clamp(xController.calculate(aprilTagDistance), -0.75, 0.75);
    }
}