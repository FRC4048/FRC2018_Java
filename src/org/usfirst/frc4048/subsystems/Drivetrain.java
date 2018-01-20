// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;
import org.usfirst.frc4048.swerve.drive.SwerveDrive;
import org.usfirst.frc4048.swerve.math.CentricMode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.sensors.PigeonIMU;



/**
 *
 */
public class Drivetrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX frontLeftDriveMotor = RobotMap.drivetrainfrontLeftDriveMotor;
    private final WPI_TalonSRX frontLeftSteerMotor = RobotMap.drivetrainfrontLeftSteerMotor;
    private final WPI_TalonSRX frontRightDriveMotor = RobotMap.drivetrainfrontRightDriveMotor;
    private final WPI_TalonSRX frontRightSteerMotor = RobotMap.drivetrainfrontRightSteerMotor;
    private final WPI_TalonSRX rearLeftDriveMotor = RobotMap.drivetrainrearLeftDriveMotor;
    private final WPI_TalonSRX rearLeftSteerMotor = RobotMap.drivetrainrearLeftSteerMotor;
    private final WPI_TalonSRX rearRightDriveMotor = RobotMap.drivetrainrearRightDriveMotor;
    private final WPI_TalonSRX rearRightSteerMotor = RobotMap.drivetrainrearRightSteerMotor;
    @SuppressWarnings("unused")
	private final PowerDistributionPanel powerDistributionPanel = RobotMap.drivetrainPowerDistributionPanel;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    //Gear ratio of swerve wheels
    private final double GEAR_RATIO = (1988/1.2);
    //Width between drivetrain wheels
    private final double WIDTH = 27.5;
    //Length between drivetrain wheels
    private final double LENGTH = 19;
    
    private SwerveDrive swerveDrivetrain;
    private CanTalonSwerveEnclosure frontLeftWheel;
    private CanTalonSwerveEnclosure frontRightWheel;
    private CanTalonSwerveEnclosure rearLeftWheel;
    private CanTalonSwerveEnclosure rearRightWheel;
    
    private AnalogInput analogInput1 = RobotMap.swerveDriveAnalogInputFrontRight;
    private AnalogInput analogInput2 = RobotMap.swerveDriveAnalogInputFrontLeft;
    private AnalogInput analogInput3 = RobotMap.swerveDriveAnalogInputRearLeft;
    private AnalogInput analogInput4 = RobotMap.swerveDriveAnalogInputRearRight;
    
    private final Encoder encoder1 = RobotMap.swerveDriveEncoder;
    
    private final PigeonIMU pigeon1 = RobotMap.swerveDrivePigeon1;
    
    private final boolean REVERSE_ENCODER = true;   //TODO
    private final boolean REVERSE_OUTPUT = true;	//TODO
    
    //Absolute Encoder Zero Consts
    /*
     * 1 = Front Right
     * 2 = Front Left
     * 3 = Back Left
     * 4 = Back Right
     */
    
    private final int ZERO1 = 2650;
    private final int ZERO2 = 964;
    private final int ZERO3 = 3765;
    private final int ZERO4 = 2160;
    
    private final double P = 10;
    private final double I = 0;
    private final double D = 0;
    private final double F = 0;
    
    //TODO
    private final int timeout = 100;
        
    /**
     * Constructor for robot drivetrain
     * Initializes all of the wheel enclosures and speed controllers
     */
    public Drivetrain() {
    	super("Drivetrain");
    	    	
    	frontLeftWheel = new CanTalonSwerveEnclosure("FrontLeftWheel", 
    												frontLeftDriveMotor, frontLeftSteerMotor, GEAR_RATIO);
    	
    	frontRightWheel = new CanTalonSwerveEnclosure("FrontRightWheel", 
													frontRightDriveMotor, frontRightSteerMotor, GEAR_RATIO);
    	
    	rearLeftWheel = new CanTalonSwerveEnclosure("RearLeftWheel", 
													rearLeftDriveMotor, rearLeftSteerMotor, GEAR_RATIO);
    	
    	rearRightWheel = new CanTalonSwerveEnclosure("RearRightWheel", 
													rearRightDriveMotor, rearRightSteerMotor, GEAR_RATIO);

    	init();
    	
    	swerveDrivetrain = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel, 
    										WIDTH, LENGTH);
	}
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void init() {
		pigeon1.setYaw(0, timeout);
		pigeon1.setFusedHeading(0, timeout);
		
		encoder1.reset();
		encoder1.setDistancePerPulse(0.0942478739);
		
		initSteerMotor(frontRightSteerMotor);
		initSteerMotor(frontLeftSteerMotor);
		initSteerMotor(rearLeftSteerMotor);
		initSteerMotor(rearRightSteerMotor);
		
		frontRightWheel.setReverseEncoder(REVERSE_ENCODER);
		frontLeftWheel.setReverseEncoder(REVERSE_ENCODER);
		rearLeftWheel.setReverseEncoder(REVERSE_ENCODER);
		rearRightWheel.setReverseEncoder(REVERSE_ENCODER);
		
		frontRightWheel.setReverseSteerMotor(REVERSE_OUTPUT);
		frontLeftWheel.setReverseSteerMotor(REVERSE_OUTPUT);
		rearLeftWheel.setReverseSteerMotor(REVERSE_OUTPUT);
		rearRightWheel.setReverseSteerMotor(REVERSE_OUTPUT);
		
		RobotMap.swerveDriveEncoder.reset();
		RobotMap.swerveDriveEncoder.setDistancePerPulse(0.1);
		
		resetQuadEncoder();
    }
    
    private void initSteerMotor(WPI_TalonSRX steerMotor)
    {
    	steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, timeout);
    	
    	steerMotor.selectProfileSlot(0, 0); //Idx, Pdx
    	
    	//steerMotor.configEncoderCodesPerRev()
    	steerMotor.configPeakOutputForward(1, timeout);
    	steerMotor.configPeakOutputReverse(-1, timeout);
    	
    	steerMotor.configNominalOutputForward(0, timeout);
    	steerMotor.configNominalOutputReverse(0, timeout);
    	
    	steerMotor.configAllowableClosedloopError(0, 4, timeout);//arg0 = slotIdx, arg2 = timeoutMs
    	
    	steerMotor.config_kP(0, P, timeout);//1st arg is slotIdx
    	steerMotor.config_kI(0, I, timeout);
    	steerMotor.config_kD(0, D, timeout);
    	steerMotor.config_kF(0, F, timeout);
    }
    
    public void resetQuadEncoder() {
    	frontRightSteerMotor.setSelectedSensorPosition((int)((analogInput1.getValue() - ZERO1)/4000.0 * GEAR_RATIO), 0, timeout);
    	frontLeftSteerMotor.setSelectedSensorPosition((int) ((analogInput2.getValue() - ZERO2)/4000.0 * GEAR_RATIO), 0, timeout);
    	rearLeftSteerMotor.setSelectedSensorPosition((int) ((analogInput3.getValue() - ZERO3)/4000.0 * GEAR_RATIO), 0, timeout);
//    	rearRightSteerMotor.setSelectedSensorPosition((int) ((analogInput4.getValue()- ZERO4)/4000.0 * GEAR_RATIO), 0, timeout);
    	rearRightSteerMotor.setSelectedSensorPosition(0, 0, timeout);

    	
    	frontRightSteerMotor.set(ControlMode.Position, 0);
    	frontLeftSteerMotor.set(ControlMode.Position, 0);
    	rearLeftSteerMotor.set(ControlMode.Position, 0);
//    	rearRightSteerMotor.set(ControlMode.Position, 0); // TODO - restore once encoder is fixed
    }
    
    public double getGyro()
    {
    	double angle = 0 - pigeon1.getFusedHeading();
    	return angle % 360;
    }
    
    public void setFieldCentric(boolean x)
    {
    	if (x) {
    		swerveDrivetrain.setCentricMode(CentricMode.FIELD);
    	} else {
    		swerveDrivetrain.setCentricMode(CentricMode.ROBOT);
    	}
    }
    
  
    public void setGyro(double angle)
    {
    	pigeon1.setYaw(angle, timeout);
    	pigeon1.setFusedHeading(angle, timeout);
    }
    
    public double getDistance() {
    	return RobotMap.swerveDriveEncoder.getDistance();
    }
    
    public void move(double fwd, double str, double rcw)
    {
    	swerveDrivetrain.move(fwd, str, rcw, getGyro());
    }
    
    public int getFREncoderPos()
    {
    	return frontRightSteerMotor.getSelectedSensorPosition(0);
    }

}

