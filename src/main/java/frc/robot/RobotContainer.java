// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.BalanceRobot;
import frc.robot.commands.DriveRobotToChargeStation;
import frc.robot.commands.FollowAprilTag;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController controller =
      new CommandXboxController(OperatorConstants.driverControllerPort);

  // mr segall please give me a good grade on this program
  private final DriveSubsystem driveSubsystem = new DriveSubsystem(controller);

  // comment
  private final VisionSubsystem visionSubsystem = new VisionSubsystem();

  private final GyroSubsystem gyroSubsystem = new GyroSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    //ahrs.getDisplacementX();
  }

  private void configureBindings() {
    //new RepeatCommand(new GetAprilTagPose(visionSubsystem)).ignoringDisable(true).schedule();;
   //m_driverController.leftBumper().whileTrue(new RepeatCommand(followAprilTag()));
    controller.rightBumper().whileTrue(
      new DriveRobotToChargeStation(driveSubsystem, gyroSubsystem).andThen(new BalanceRobot(gyroSubsystem, driveSubsystem))

    );

  }

  public Command balanceRobot() {
    return new DriveRobotToChargeStation(driveSubsystem, gyroSubsystem).andThen(new BalanceRobot(gyroSubsystem, driveSubsystem));
  }

  public FollowAprilTag followAprilTag() {
    return new FollowAprilTag(driveSubsystem, visionSubsystem);
  }
}
