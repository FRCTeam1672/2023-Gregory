package frc.robot.utils;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;

public class GyroUtils {
    private static PIDController rollController = new PIDController(Constants.PIDConstants.Rkp, Constants.PIDConstants.Rki, Constants.PIDConstants.Rkd);
    
    public static double getRoll(double robotRoll) {
        return -MathUtil.clamp(rollController.calculate(robotRoll), -0.75, 0.75);
    }
}
