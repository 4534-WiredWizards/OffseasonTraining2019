// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4534.InfiniteRecharge2020.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc4534.InfiniteRecharge2020.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    //Left and Right Followers now Left and Right 1

    private CANSparkMax rightMaster;
    private CANEncoder rightMasterEncoder;
    private CANSparkMax rightFollower1;
    private CANEncoder rightFollowerEncoder1;
    private CANSparkMax rightFollower2;
    private CANEncoder rightFollowerEncoder2;
    
    private CANSparkMax leftMaster;
    private CANEncoder leftMasterEncoder;
    private CANSparkMax leftFollower1;
    private CANEncoder leftFollowerEncoder1;
    private CANSparkMax leftFollower2;
    private CANEncoder leftFollowerEncoder2;

    private DifferentialDrive diffDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // Demo Code and Accel/Decel Variables
    double workingSpeed = 1;
    double demoSpeed = 0.5;
    boolean demoMode = false;
    // Change motor speed by this variable every loop (typically 20ms)
    double stepSize = 0.1;
    double maxSpeed = workingSpeed;
    double lastSpeed = 0;
    //Arcade drive scaling
    double driveScale = 0;
    double rotationScale = 0;
    //Direct driving varibles
    boolean drivingEnabled = true;

    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        rightMaster = new CANSparkMax(10, MotorType.kBrushless);
        rightMaster.setInverted(true);
        rightMasterEncoder = rightMaster.getEncoder();
        
        rightFollower1 = new CANSparkMax(11, MotorType.kBrushless);
        rightFollower1.setInverted(true);
        rightFollowerEncoder1 = rightFollower1.getEncoder();

        rightFollower2 = new CANSparkMax(12, MotorType.kBrushless);
        rightFollower2.setInverted(true);
        rightFollowerEncoder2 = rightFollower2.getEncoder();



        leftMaster = new CANSparkMax(13, MotorType.kBrushless);
        leftMaster.setInverted(true);
        leftMasterEncoder = leftMaster.getEncoder();
        
        leftFollower1 = new CANSparkMax(14, MotorType.kBrushless);
        leftFollower1.setInverted(true);
        leftFollowerEncoder1 = leftFollower1.getEncoder();

        leftFollower2 = new CANSparkMax(15, MotorType.kBrushless);
        leftFollower2.setInverted(true);
        leftFollowerEncoder2 = leftFollower2.getEncoder();
        

        leftFollower1.follow(leftMaster);
        leftFollower2.follow(leftMaster);
        rightFollower1.follow(rightMaster);
        rightFollower2.follow(rightMaster);
        
        diffDrive = new DifferentialDrive(leftMaster, rightMaster);
        addChild("DiffDrive", diffDrive);
        diffDrive.setSafetyEnabled(true);
        diffDrive.setExpiration(0.1);
        diffDrive.setMaxOutput(1.0);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithJoystick());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Left Encoders", getLeftEncoders());
        SmartDashboard.putNumber("Right Encoders", getRightEncoders());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void arcadeDrive(double speed, double rotation) {
        diffDrive.arcadeDrive(speed*maxSpeed, -rotation*maxSpeed, true);
        lastSpeed = speed;
    }

    public void arcadeDriveScaled(double speed, double rotation) {
        diffDrive.arcadeDrive((speed*maxSpeed) * (1 - driveScale) + driveScale, (rotation*maxSpeed)* (1 - rotationScale) + rotationScale, true);
        lastSpeed = speed;
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        diffDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void setShifter(boolean state) {
        //god i hate this shifter so much i'm so glad it's gone now
        //shifter.set(state);
    }
    
    public void setDemoMode(boolean newDemoMode) {
        demoMode = newDemoMode;
        if (demoMode == true) {
            maxSpeed = demoSpeed;
        } else {
            maxSpeed = workingSpeed;
        }
        return;
    }

    public double setMaxSpeed() {
        return maxSpeed;
    }

    public void toggleShifter() {
        //god i hate this shifter so much i'm so glad it's gone now
        //shifter.set(!shifter.get());
    }

    public double getLeftEncoders() {
        return ((leftMasterEncoder.getPosition() + leftFollowerEncoder1.getPosition() + leftFollowerEncoder2.getPosition())/3);
    }

    public double getRightEncoders() {
        return ((rightMasterEncoder.getPosition() + rightFollowerEncoder1.getPosition() + rightFollowerEncoder2.getPosition())/3);
    }

    public void allowDrive(boolean allow) {
        drivingEnabled = allow;
    }

    public boolean isDrivingAllowed() {
        return drivingEnabled;
    }
}
//finn is a bad programmer