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
import org.usfirst.frc4048.arm.math.ArmMath;
import org.usfirst.frc4048.arm.math.ArmStrat;
import org.usfirst.frc4048.arm.math.LinearMoveStrat;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.commands.arm.ArmFinetune;
import org.usfirst.frc4048.commands.arm.ArmFinetuneManual;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.MotorUtils;
import org.usfirst.frc4048.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

public class Arm extends Subsystem {

	public static enum ArmPositions {
		Intake, Exchange, Switch, LowScale, MidScale, HighScale, Climb, Home
	}
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX elbowMotor = RobotMap.armextensionMotor;
//	private final AnalogPotentiometer rotationPot = RobotMap.armrotationPot;
	private final WPI_TalonSRX movementMotor = RobotMap.armmovementMotor;
//	private final AnalogPotentiometer extensionPot = RobotMap.armextensionPot;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private final int TIMEOUT = 100;

	private final double ELBOW_STALL_DELAY = 5.0;
	private double startStallTime = -ELBOW_STALL_DELAY;
	private final MotorUtils elbowStall = new MotorUtils(RobotMap.PDP_ELBOW, 
															RobotMap.CURRENT_THRESHOLD_ELBOW_MOTOR, 
															RobotMap.TIMEOUT_ELBOW_MOTOR);
	
	//TODO Determine new values
	private double ELBW_UP_P = 10.0;
	private double ELBW_UP_I = 0.0;
	private double ELBW_UP_D = 0.0;
	private double ELBW_DOWN_P = 1.5;
	private double ELBW_DOWN_I= 0.0;
	private double ELBW_DOWN_D = 0.0;

	private final double ARM_UP_P = 10.0;
	private final double ARM_UP_I = 0.0;
	private final double ARM_UP_D = 0.0;
	private final double ARM_DOWN_P = 1.5;
	private final double ARM_DOWN_I = 0.0;
	private final double ARM_DOWN_D = 0.0;

	/**
	 * Is not a speed, but a setpoint adjustment value
	 */
	private final double FINETUNE_RATE = 2.0;

	/*
	 * All of these setpoints are used for the arm
	 */
	public static final double ANGLE_MARGIN_VALUE = 5.0;
	public static final double CRITICAL_MARGIN_VALUE = 10.0;
	public static final double HOME_SETPOINT = 0.0;
	public static final double HOME_MAX_ANGLE = 3.0;
	public static final double INTAKE_SETPOINT = 0.0;
	public static final double EXCHANGE_SETPOINT = INTAKE_SETPOINT;
	public static final double SWITCH_SETPOINT = INTAKE_SETPOINT;
	public static final double LOWSCALE_SETPOINT = 120.0;
	public static final double MIDSCALE_SETPOINT = 150.0;
	public static final double HIGHSCALE_SETPOINT = 155.0;
	
	public static final double ELBW_HOME_SETPOINT = 140.0;
	public static final double ELBW_INTAKE_SETPOINT = 2.0;
	public static final double ELBW_EXCHANGE_SETPOINT = 17.0;
	public static final double ELBW_SWITCH_SETPOINT = 35;
	public static final double ELBW_LOW_SCALE_SETPOINT = -90;
	public static final double ELBW_MID_SCALE_SETPOINT = -100;
	public static final double ELBW_HIGH_SCALE_SETPOINT = -85;
	
	/*
	 * All of these values are used for the extension math
	 */
	private final double ARM_POT_MIN = 935;
	private final double ARM_POT_MAX = 84;
	private final double ARM_ANGLE_MIN = 0.0;
	private final double ARM_ANGLE_MAX = 150.0;
	private final double ARM_POT_INVERT = -1.0;
	
	private final double ELBW_POT_MIN = 692.0;
	private final double ELBW_POT_MAX = 423.0;
	private final double ELBW_ANGLE_MIN = 0.0;
	private final double ELBW_ANGLE_MAX = 360.0;
	private final double ELBW_POT_INVERT = 1.0;

	private double armAngleSetpoint;
	private double elbowAngleSetpoint;
	private ArmMath armMath = new ArmMath();

	private boolean goingHome = false;
	
	/**
	 * States if the arm movement is disabled
	 */
	private boolean disableArm = true;
	
	private double armP = -1;
	private double armI = -1;
	private double armD = -1;
	
	private double elbwP = -1;
	private double elbwI = -1;
	private double elbwD = -1;
	
	public Arm() {
		super("Arm");

		elbowMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		elbowMotor.selectProfileSlot(0, 0);
		elbowMotor.configNominalOutputForward(0, TIMEOUT);
		elbowMotor.configNominalOutputReverse(0, TIMEOUT);
		elbowMotor.configPeakOutputForward(Robot.EXT_SCALE_FACTOR, TIMEOUT);
		elbowMotor.configPeakOutputReverse(-Robot.EXT_SCALE_FACTOR, TIMEOUT);
		elbowMotor.setNeutralMode(NeutralMode.Brake);
		elbowMotor.setSensorPhase(false);
		elbowMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		elbowMotor.config_kP(0, ELBW_UP_P, TIMEOUT);
		elbowMotor.config_kI(0, ELBW_UP_I, TIMEOUT);
		elbowMotor.config_kD(0, ELBW_UP_D, TIMEOUT);
		elbowMotor.configAllowableClosedloopError(1, 4, TIMEOUT);
		elbowMotor.config_kP(1, ELBW_DOWN_P, TIMEOUT);
		elbowMotor.config_kI(1, ELBW_DOWN_I, TIMEOUT);
		elbowMotor.config_kD(1, ELBW_DOWN_D, TIMEOUT);
		elbowMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
													LimitSwitchNormal.NormallyOpen, 
													TIMEOUT);

		movementMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		movementMotor.configNominalOutputForward(0, TIMEOUT);
		movementMotor.configNominalOutputReverse(0, TIMEOUT);
		movementMotor.configPeakOutputForward(Robot.ARM_UP_SCALE_FACTOR, TIMEOUT);
		movementMotor.configPeakOutputReverse(-Robot.ARM_DOWN_SCALE_FACTOR, TIMEOUT);
		movementMotor.setNeutralMode(NeutralMode.Brake);
		movementMotor.setSensorPhase(true);
		movementMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		movementMotor.config_kP(0, ARM_UP_P, TIMEOUT);
		movementMotor.config_kI(0, ARM_UP_I, TIMEOUT);
		movementMotor.config_kD(0, ARM_UP_D, TIMEOUT);
		movementMotor.configAllowableClosedloopError(1, 4, TIMEOUT);
		movementMotor.config_kP(1, ARM_DOWN_P, TIMEOUT);
		movementMotor.config_kI(1, ARM_DOWN_I, TIMEOUT);
		movementMotor.config_kD(1, ARM_DOWN_D, TIMEOUT);

		armAngleSetpoint = getArmAngle();
		elbowToPosition(ArmPositions.Home);

		//printPIDValues();
	}

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

		//TODO How should this work for the new arm?
		setDefaultCommand(new ArmFinetune());
	}
	
    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.ARM) {

		@Override
		protected void addAll() {
			add("Arm Pot", getArmPos());
			add("Arm Angle", getArmAngle());
			add("Arm Angle Setpoint", armAngleSetpoint);
			add("Elbow Pot", getElbowPos());
//			add("Extension Pot Setpoint", mathPotExtSetpoint);
			add("Elbow Angle", getElbowAngle());
//			add("Extension Length Setpoint", manualExtSetpoint);
			add("Arm P", armP);
			add("Arm I", armI);
			add("Arm D", armD);
			add("Elbow P", elbwP);
			add("Elbow I", elbwI);
			add("Elbow D", elbwD);
			add("Elbow retracted Switch", elbowMotor.getSensorCollection().isFwdLimitSwitchClosed());
		}
    	
    };


	@Override
	public void periodic() {
		// Put code here to be run every loop

		if(elbowStall.isStalled()) {
			elbowMotor.stopMotor();
			startStallTime = Timer.getFPGATimestamp();
		} else if(Timer.getFPGATimestamp() - startStallTime <= ELBOW_STALL_DELAY){
			DriverStation.reportError("Please wait, elbow is resting after stall", false);
			elbowMotor.stopMotor();
			armAngleSetpoint = getArmAngle();
		} else {
			moveElbow();
		}
		moveArm();
		
		loggingContext.writeData();
		
	}

	public void armData() {
		SmartDashboard.putNumber("ARM ANGLE", getArmAngle());
		SmartDashboard.putNumber("ARM SETPOINT", armAngleSetpoint);
		SmartDashboard.putNumber("ARM POT", getArmPos());
		SmartDashboard.putBoolean("ARM DISABLED", disableArm);
		SmartDashboard.putNumber("ELBOW ANGLE", getElbowAngle());
		SmartDashboard.putNumber("ELBOW SETPOINT", elbowAngleSetpoint);
		SmartDashboard.putNumber("ELBOW POT", getElbowPos());
		
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void printPIDValues() {
		SmartDashboard.putNumber("ELBOW P", ELBW_UP_P);
		SmartDashboard.putNumber("ELBOW I", ELBW_UP_I);
		SmartDashboard.putNumber("ELBOW D", ELBW_UP_D);
	}
	
	public void finetuneUp() {
//		double newSetpoint = armAngleSetpoint + FINETUNE_RATE;
//		if(inAutoRange(newSetpoint)) {
//			armAngleSetpoint = newSetpoint;
//		}
		
		elbowAngleSetpoint += FINETUNE_RATE;
	}

	public void finetuneDown() {
//		double newSetpoint = armAngleSetpoint - FINETUNE_RATE;
//		if(inAutoRange(newSetpoint)) {
//			armAngleSetpoint = newSetpoint;
//		}
		
		elbowAngleSetpoint -= FINETUNE_RATE;
	}

	public void finetuneDownManual() {
		movementMotor.set(ControlMode.PercentOutput, 0.2);
	}

	public void finetuneUpManual() {
		movementMotor.set(ControlMode.PercentOutput, -0.2);
	}

	public void stopArm() {
		armAngleSetpoint = getArmAngle();
	}
	
	public void stopElbow()
	{
		elbowAngleSetpoint = getElbowAngle();
	}

	public double getArmAngle() {
		return armMath.convertPotToAngle(ARM_POT_MIN, ARM_ANGLE_MIN, ARM_POT_MAX, ARM_ANGLE_MAX, getArmPos() * ARM_POT_INVERT);
	}

	public double getElbowAngle() {
		 return armMath.convertElbwPotToAngle(ELBW_POT_MIN, ELBW_ANGLE_MIN, ELBW_POT_MAX, ELBW_ANGLE_MAX, getElbowPos()* ELBW_POT_INVERT);
	}

 	public int getArmPos() {
 		elbwP = ELBW_UP_P;
		elbwI = ELBW_UP_I;
		elbwD = ELBW_UP_D;
		return movementMotor.getSelectedSensorPosition(0);
	}

	public int getElbowPos() {
		elbwP = ELBW_UP_P;
		elbwI = ELBW_UP_I;
		elbwD = ELBW_UP_D;
		return elbowMotor.getSelectedSensorPosition(0);
	}

	/**
	 * Checks to see if the arm is within the correct position
	 * 
	 * @param position
	 * @return True if arm is in correct position., False otherwise
	 */
	public boolean armAtPosition(ArmPositions position) {
		// double armPos = getArmPos();
		double armPos = getArmAngle();
		switch (position) {
		case Intake:
			return armPos >= INTAKE_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= INTAKE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Exchange:
			return armPos >= EXCHANGE_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= EXCHANGE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Switch:
			return armPos >= SWITCH_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= SWITCH_SETPOINT + ANGLE_MARGIN_VALUE;
		case LowScale:
			return armPos >= LOWSCALE_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= LOWSCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case MidScale:
			return armPos >= MIDSCALE_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= MIDSCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case HighScale:
			return armPos >= HIGHSCALE_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= HIGHSCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Climb:
			return armPos >= HOME_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= HOME_SETPOINT + ANGLE_MARGIN_VALUE;
		case Home:
			return armPos >= HOME_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= HOME_SETPOINT + ANGLE_MARGIN_VALUE;
		default:
			return false;
		}
	}

	public void armToPosition(ArmPositions pos) {
		switch (pos) {
		case Intake:
			armAngleSetpoint = INTAKE_SETPOINT;
			break;
		case Exchange:
			armAngleSetpoint = EXCHANGE_SETPOINT;
			break;
		case Switch:
			armAngleSetpoint = SWITCH_SETPOINT;
			break;
		case LowScale:
			armAngleSetpoint = LOWSCALE_SETPOINT;
			break;
		case MidScale:
			armAngleSetpoint = MIDSCALE_SETPOINT;
			break;
		case HighScale:
			armAngleSetpoint = HIGHSCALE_SETPOINT;
			break;
		case Climb:
			armAngleSetpoint = HOME_SETPOINT;
			break;
		case Home:
			armAngleSetpoint = HOME_SETPOINT;
			break;
		default:
			break;
		}
	}
	
	public void setGoingHome(boolean val)
	{
		goingHome = val;
	}
	
	public boolean isGoingHome()
	{
		return goingHome;
	}

	public void setArmToCurrentPos()
	{
		armAngleSetpoint = getArmAngle();
	}
	
	public boolean inAutoRange(double value) {
		double armPos = value;
		return armPos >= EXCHANGE_SETPOINT - CRITICAL_MARGIN_VALUE
				&& armPos <= HIGHSCALE_SETPOINT + CRITICAL_MARGIN_VALUE;
	}
	
	/**
	 * Uses arm math to calculate new position for extension This math only applies
	 * within the exchange and high scale positions
	 */
	private void moveElbow() {
//		if(getArmAngle() <= HOME_MAX_ANGLE) {
//			elbowAngleSetpoint = ELBW_HOME_SETPOINT;
//		}
		
		double elbwSetpoint = armMath.convertAngleToElbwPot(ELBW_POT_MIN, ELBW_ANGLE_MIN, ELBW_POT_MAX, ELBW_ANGLE_MAX, elbowAngleSetpoint);
		elbowMotor.set(ControlMode.Position, elbwSetpoint);
		
		if(getElbowPos() < elbwSetpoint) {
			elbowMotor.selectProfileSlot(1, 0);
		}
	}

	public boolean elbowShouldCompact(ArmPositions position)
	{
		switch (position) {
		case Home:
			return elbowBelowSwitch(); 
		case Intake:
			return elbowBelowSwitch(); 
		case Exchange:
			return elbowBelowSwitch(); 
		case Switch:
			return elbowBelowSwitch(); 
		case LowScale:
			return true;
		case MidScale:
			return true;
		case HighScale:
			return true;
		case Climb:
			return true;
		default:
			return true;
		}
	}
	
	private boolean elbowBelowSwitch() {
		return !(getArmAngle() >= HOME_SETPOINT - ANGLE_MARGIN_VALUE && getArmAngle() <= SWITCH_SETPOINT + ANGLE_MARGIN_VALUE); 
	}
	
	public void elbowToPosition(ArmPositions position) 
	{
		switch (position) {
		case Home :
			elbowAngleSetpoint = ELBW_HOME_SETPOINT;
			break;
		case Intake :
			elbowAngleSetpoint = ELBW_INTAKE_SETPOINT;
			break;
		case Exchange:
			elbowAngleSetpoint = ELBW_EXCHANGE_SETPOINT;
			break;
		case Switch :
			elbowAngleSetpoint = ELBW_SWITCH_SETPOINT;
			break;
		case LowScale : 
			elbowAngleSetpoint = ELBW_LOW_SCALE_SETPOINT;
			break;
		case MidScale :
			elbowAngleSetpoint = ELBW_MID_SCALE_SETPOINT;
			break;
		case HighScale :
			elbowAngleSetpoint = ELBW_HIGH_SCALE_SETPOINT;
			break;
		case Climb :
			elbowAngleSetpoint = ELBW_HOME_SETPOINT;
			break;
		default:
			elbowAngleSetpoint = ELBW_INTAKE_SETPOINT;
			break;
		}
	}
	
	public boolean elbowAtPosition(ArmPositions position)
	{
		switch (position) {
		case Home :
			return getElbowAngle() >= ELBW_HOME_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_HOME_SETPOINT + ANGLE_MARGIN_VALUE;
		case Intake :
			return getElbowAngle() >= ELBW_INTAKE_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_INTAKE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Exchange:
			return getElbowAngle() >= ELBW_EXCHANGE_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_EXCHANGE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Switch :
			return getElbowAngle() >= ELBW_SWITCH_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_SWITCH_SETPOINT + ANGLE_MARGIN_VALUE;
		case LowScale : 
			return getElbowAngle() >= ELBW_LOW_SCALE_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_LOW_SCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case MidScale :
			return getElbowAngle() >= ELBW_MID_SCALE_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_MID_SCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case HighScale :
			return getElbowAngle() >= ELBW_HIGH_SCALE_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_HIGH_SCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Climb :
			return getElbowAngle() >= ELBW_HOME_SETPOINT - ANGLE_MARGIN_VALUE && getElbowAngle() <= ELBW_HOME_SETPOINT + ANGLE_MARGIN_VALUE;
		default:
			return false;
		}
	}
	
	public void setDisabled(boolean val)
	{
		disableArm = val;
	}
	
	/**
	 * Keeps arm locked to its current setpoint position
	 */
	private void moveArm() {
		if(!disableArm) {// && extensionIsClose()) {
			double armSetpoint = armMath.convertAngleToPot(ARM_POT_MIN, ARM_ANGLE_MIN, ARM_POT_MAX, ARM_ANGLE_MAX, armAngleSetpoint) * ARM_POT_INVERT;
			
			//SmartDashboard.putNumber("ARM POT SETPOINT", armSetpoint);
			movementMotor.set(ControlMode.Position, (int) armSetpoint);
			
			if(getArmPos() > armSetpoint)
			{
				/*SmartDashboard.putNumber("ARM P", ARM_DOWN_P);
				SmartDashboard.putNumber("ARM I", ARM_DOWN_I);
				SmartDashboard.putNumber("ARM D", ARM_DOWN_D);*/
				armP = ARM_DOWN_P;
				armI = ARM_DOWN_I;
				armD = ARM_DOWN_D;
				movementMotor.selectProfileSlot(1, 0);
			}
			else
			{
				/*SmartDashboard.putNumber("ARM P", ARM_UP_P);
				SmartDashboard.putNumber("ARM I", ARM_UP_I);
				SmartDashboard.putNumber("ARM D", ARM_UP_D);*/
				armP = ARM_UP_P;
				armI = ARM_UP_I;
				armD = ARM_UP_D;
				movementMotor.selectProfileSlot(0, 0);
			}
		}
//		else if(!disableArm && !extensionIsClose())
//		{
//			movementMotor.set(ControlMode.Position, (int)getArmPos());
//		}
		else if(disableArm)
		{
			movementMotor.disable();
		}
	}

}
