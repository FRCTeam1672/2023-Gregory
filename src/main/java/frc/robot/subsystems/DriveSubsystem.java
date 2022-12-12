// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private Talon leftDrive = new Talon(0);
  private Talon rightDrive = new Talon(1);
  private final DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);
  
  public DriveSubsystem(){
    rightDrive.setInverted(true);
  }

  /**
   * Arcade drive
   * 
   * If both of the params are == 0, then the motors will stop
   * @param forwards [-1.0 - 1.0] 1.0 is forwards
   * @param turn [-1.0, 1.0] 1.0 is clockwise
   */
  public void arcadeDrive(double forwards, double turn){
    if(forwards == 0.0 && turn == 0.0){
      leftDrive.stopMotor();
      rightDrive.stopMotor();
      return;
    }
    this.drive.arcadeDrive(forwards, turn);
  }
}
