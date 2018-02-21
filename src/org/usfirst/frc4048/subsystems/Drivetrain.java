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

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.swerve.drive.CanTalonSwerveEnclosure;
import org.usfirst.frc4048.swerve.drive.SwerveDrive;
import org.usfirst.frc4048.swerve.math.CentricMode;
import org.usfirst.frc4048.utils.Logging;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private final PowerDistributionPanel powerDistributionPanel = RobotMap.drivetrainPowerDistributionPanel;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    //Gear ratio of swerve wheels
    private final double GEAR_RATIO = (1988/1.2);
    //Width between drivetrain wheels
    private final double WIDTH = 22.0;
    //Length between drivetrain wheels
    private final double LENGTH = 23.25;
    
    public SwerveDrive swerveDrivetrain;
    private CanTalonSwerveEnclosure frontLeftWheel;
    private CanTalonSwerveEnclosure frontRightWheel;
    private CanTalonSwerveEnclosure rearLeftWheel;
    private CanTalonSwerveEnclosure rearRightWheel;
    
    private AnalogInput analogInputFrontRight = RobotMap.swerveDriveAnalogInputFrontRight;
    private AnalogInput analogInputFrontLeft = RobotMap.swerveDriveAnalogInputFrontLeft;
    private AnalogInput analogInputRearLeft = RobotMap.swerveDriveAnalogInputRearLeft;
    private AnalogInput analogInputRearRight = RobotMap.swerveDriveAnalogInputRearRight;
    
    private final Encoder encoder = RobotMap.swerveDriveEncoder;
    
    private final PigeonIMU pigeon = RobotMap.swerveDrivePigeon1;
    
    private final boolean REVERSE_ENCODER = true;
    private final boolean REVERSE_OUTPUT = true;
    
    private final AnalogInput leftSonar = RobotMap.leftSonarPort;
    private final AnalogInput rightSonar = RobotMap.rightSonarPort;
    private final double SCALE_FACTOR = 2.45; //Scale factor for the sonar (MB1240/20 = 2.45, MB1230 = 1.84(not verified by datasheet)) 
    private final double MB1023_SCALE_FACTOR = 40.3149606; // 1024/(2*5)/2.54
    public static enum SonarSide {RIGHT, LEFT};
    //This \/ is used to schedual the drive distance after calling CalculateSonarDistance()
    public double globalDriveDistance;
    public double globalDriveDirSpeed;
    
    
    //Absolute Encoder Zero Consts
    /*
     * 1 = Front Right
     * 2 = Front Left
     * 3 = Back Left
     * 4 = Back Right
     */
    private final int FR_ZERO = 1028;
    private final int FL_ZERO = 1784;
    private final int RL_ZERO = 301;
    private final int RR_ZERO = 1448;
    
    private final double P = 10;
    private final double I = 0;
    private final double D = 0;
    private final double F = 0;
    
    private final double LEFT_JOY_X_MIN_DEADZONE = -0.0234375;
    private final double LEFT_JOY_X_MAX_DEADZONE = 0.015748031437397003;
    private final double LEFT_JOY_Y_MIN_DEADZONE = -0.03125;
    private final double LEFT_JOY_Y_MAX_DEADZONE = 0.015748031437397003;
    private final double RIGHT_JOY_X_MIN_DEADZONE = -0.0078125;
    private final double RIGHT_JOY_X_MAX_DEADZONE = 0.031496062874794006;
        
    private final int TIMEOUT = 100;    //TODO Is this timeout right?
    
    
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
//    	String x[] = {"LeftSonarVoltage", "LeftSonarDistance", "RightSonarVoltage", "RightSonarDistance", "SteerFL", "SteerFR", "SteerRL", "SteeRR", "DistEncoder"};

    	Robot.logging.traceSubsystem(Logging.Subsystems.DRIVETRAIN, false, leftSonar.getVoltage(),
        							 getSonar(SonarSide.LEFT),
        							 rightSonar.getVoltage(),
        							 getSonar(SonarSide.RIGHT),
        							 frontLeftSteerMotor.getSelectedSensorPosition(TIMEOUT),
        							 frontRightSteerMotor.getSelectedSensorPosition(TIMEOUT),
        							 rearLeftSteerMotor.getSelectedSensorPosition(TIMEOUT),
        							 rearRightSteerMotor.getSelectedSensorPosition(TIMEOUT),
        							 encoder.getDistance());
    }
    

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void init() {
		pigeon.setYaw(0, TIMEOUT);
		pigeon.setFusedHeading(0, TIMEOUT);
		
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
		
		resetDriveEncoder();
		
		resetQuadEncoder();

		globalDriveDirSpeed = 0;
		globalDriveDistance = 0;
    }
    
    private void initSteerMotor(WPI_TalonSRX steerMotor)
    {
    	steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    	
    	steerMotor.selectProfileSlot(0, 0); //Idx, Pdx
    	
    	//steerMotor.configEncoderCodesPerRev()
    	steerMotor.configPeakOutputForward(1, TIMEOUT);
    	steerMotor.configPeakOutputReverse(-1, TIMEOUT);
    	
    	steerMotor.configNominalOutputForward(0, TIMEOUT);
    	steerMotor.configNominalOutputReverse(0, TIMEOUT);
    	
    	steerMotor.setNeutralMode(NeutralMode.Brake);
    	
    	steerMotor.configAllowableClosedloopError(0, 4, TIMEOUT);//arg0 = slotIdx, arg2 = timeoutMs
    	
    	steerMotor.config_kP(0, P, TIMEOUT);//1st arg is slotIdx
    	steerMotor.config_kI(0, I, TIMEOUT);
    	steerMotor.config_kD(0, D, TIMEOUT);
    	steerMotor.config_kF(0, F, TIMEOUT);
    }
    
    public void resetQuadEncoder() {
    	frontRightSteerMotor.setSelectedSensorPosition((int)((analogInputFrontRight.getValue() - FR_ZERO)/4000.0 * GEAR_RATIO), 0, TIMEOUT);
    	frontLeftSteerMotor.setSelectedSensorPosition((int) ((analogInputFrontLeft.getValue() - FL_ZERO)/4000.0 * GEAR_RATIO), 0, TIMEOUT);
    	rearLeftSteerMotor.setSelectedSensorPosition((int) ((analogInputRearLeft.getValue() - RL_ZERO)/4000.0 * GEAR_RATIO), 0, TIMEOUT);
    	rearRightSteerMotor.setSelectedSensorPosition((int) ((analogInputRearRight.getValue()- RR_ZERO)/4000.0 * GEAR_RATIO), 0, TIMEOUT);
    	
    	frontRightSteerMotor.set(ControlMode.Position, 0);
    	frontLeftSteerMotor.set(ControlMode.Position, 0);
    	rearLeftSteerMotor.set(ControlMode.Position, 0);
    	rearRightSteerMotor.set(ControlMode.Position, 0);
    }
    
    public void resetDriveEncoder()
    {
    	encoder.reset();
		encoder.setDistancePerPulse(RobotMap.SWERVE_DRIVE_ENCODER_DISTANCE_PER_TICK);
    }
    
    
    
    @SuppressWarnings("unused")
	private void setGyro(double angle)
    {
    	pigeon.setYaw(angle, TIMEOUT);
    	pigeon.setFusedHeading(angle, TIMEOUT);
    }
    
    public double getGyro()
    {
    	double angle = 0 - pigeon.getFusedHeading();
    	
    	return angle % 360;
    }
    
    
    /**
     * Outputs absolute encoder positions
     */
    public void outputAbsEncValues()
    {
    	SmartDashboard.putNumber("Front Right Abs", analogInputFrontRight.getValue());
    	SmartDashboard.putNumber("Front Left Abs", analogInputFrontLeft.getValue());
    	SmartDashboard.putNumber("Rear Left Abs", analogInputRearLeft.getValue());
    	SmartDashboard.putNumber("Rear Right Abs", analogInputRearRight.getValue());
    }
    
    /**
     * Turns wheels to reset position
     */
    public void setZero()
    {
    	frontRightSteerMotor.set(ControlMode.Position, 0);
    	frontLeftSteerMotor.set(ControlMode.Position, 0);
    	rearLeftSteerMotor.set(ControlMode.Position, 0);
    	rearRightSteerMotor.set(ControlMode.Position, 0);
    }
    
    public double getDistance() {
    	return encoder.getDistance();
    }
    
    /**
     * Outputs direction of encoder
     * @return - true if encoder direction is positive, false if encoder direction is negative
     */
    public boolean getEncoderDirection() {
    	return encoder.getDirection();
    }
    
    public void move(double fwd, double str, double rcw)
    {
    	if(fwd <= LEFT_JOY_X_MAX_DEADZONE && fwd >= LEFT_JOY_X_MIN_DEADZONE)
    		fwd = 0.0;
    	if(str <= LEFT_JOY_Y_MAX_DEADZONE && str >= LEFT_JOY_Y_MIN_DEADZONE)
    		str = 0.0;
    	if(rcw <= RIGHT_JOY_X_MAX_DEADZONE && rcw >= RIGHT_JOY_X_MIN_DEADZONE)
    		rcw = 0.0;
    
    	swerveDrivetrain.move(fwd, str, rcw, getGyro());
    }
    
    public double getSonar(SonarSide side) {
    	SmartDashboard.putNumber("Voltage", leftSonar.getVoltage());
    	
    	if(side == SonarSide.LEFT) {
    		return(leftSonar.getVoltage()*MB1023_SCALE_FACTOR);
    	} else if(side == SonarSide.RIGHT) {
    		return(rightSonar.getVoltage()*MB1023_SCALE_FACTOR);
    	} else { 
    		return 0;
    	}
    }
    
    //This is for putting the Drivetrain headings to the log
    public String[] drivetrianHeadings() {
    	String x[] = {"LeftSonarVoltage", "LeftSonarDistance", "RightSonarVoltage", "RightSonarDistance", "SteerFL", "SteerFR", "SteerRL", "SteeRR", "DistEncoder"};
    	return x;
    }
    
    public void stop()
    {
    	swerveDrivetrain.stop();
    }
}

