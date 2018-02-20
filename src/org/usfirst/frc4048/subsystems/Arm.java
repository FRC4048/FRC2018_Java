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
import org.usfirst.frc4048.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

public class Arm extends Subsystem {

	public static enum ArmPositions {
		Intake, Exchange, Switch, LowScale, HighScale, Climb, Home
	}
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final WPI_TalonSRX extensionMotor = RobotMap.armextensionMotor;
//	private final AnalogPotentiometer rotationPot = RobotMap.armrotationPot;
	private final WPI_TalonSRX movementMotor = RobotMap.armmovementMotor;
//	private final AnalogPotentiometer extensionPot = RobotMap.armextensionPot;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private final int TIMEOUT = 100;

	private double EXT_P = 20.0;
	private double EXT_INTAKE_P = 10.0;
	private double EXT_I = 0.0;
	private double EXT_D = 0.0;

	private final double ARM_UP_P = 10.0;
	private final double ARM_UP_I = 0.0;
	private final double ARM_UP_D = 0.0;
	private final double ARM_DOWN_P = 2.5;
	private final double ARM_DOWN_I = 0.0;
	private final double ARM_DOWN_D = 0.0;

	/**
	 * Is not a speed, but a setpoint adjustment value
	 */
	private final double FINETUNE_RATE = 0.25;

	// TODO ALL OF THESE SETPOINTS ARE NOT VALID
	/*
	 * All of these setpoints are used for the arm
	 */
	public static final double ANGLE_MARGIN_VALUE = 7.5;
	public static final double CRITICAL_MARGIN_VALUE = 10.0;
	public static final double HOME_SETPOINT = 0.0;
	public static final double HOME_MAX_ANGLE = 9.0;
	public static final double INTAKE_SETPOINT = 16.0;
	public static final double EXCHANGE_SETPOINT = 40.0;
	public static final double SWITCH_SETPOINT = 78.0;
	public static final double LOWSCALE_SETPOINT = 118.0;	//Is mid scale
	public static final double HIGHSCALE_SETPOINT = 145.5;
	// public static final double POT_MARGIN_VALUE = 5.0;
	// public static final double MATH_MARGIN_VALUE = 100.0;
	// public static final double HOME_SETPOINT = 0.0;
	// public static final double INTAKE_SETPOINT = 140.0;
	// public static final double EXCHANGE_SETPOINT = 180.0;
	// public static final double SWITCH_SETPOINT = 320.0;
	// public static final double LOWSCALE_SETPOINT = 550.0;
	// public static final double HIGHSCALE_SETPOINT = 780.0;
	// public static final double CLIMBER_SETPOINT = 1010;
	/*
	 * All of these setpoints are used for the extension
	 */
	public static final double EXT_MARGIN_VALUE = 1.5;
	public static final double EXT_HOME_SETPOINT = 0.0;
	public static final double EXT_INTAKE_SETPOINT = 4.5;
	public static final double EXT_INTAKE_SETPOINT_GOTO = 15.0;
	// public static final double EXT_CLIMB_SETPOINT = 1020;
	// public static final double EXT_HOME_SETPOINT = 0.0;
	// public static final double EXT_INTAKE_SETPOINT = 450.0;
	// public static final double EXT_CLIMB_SETPOINT = 1020;
	/*
	 * All of these values are used for the extension math
	 */
	private final double ARM_POT_MIN = 935;
	private final double ARM_POT_MAX = 84;
	private final double ARM_ANGLE_MIN = 0.0;
	private final double ARM_ANGLE_MAX = 158.0;
	private final double ARM_POT_INVERT = -1.0;
	
	private final double EXT_POT_MIN = 840.0;
	private final double EXT_POT_MAX = 551.0;
	private final double EXT_LENGTH_MIN = 0.0;
	private final double EXT_LENGTH_MAX = 15.25;
	private final double EXT_POT_INVERT = 1.0;

	private double armAngleSetpoint;
	private double manualExtSetpoint;
	private double mathPotExtSetpoint = 0.0;
//	private boolean autoExt = false;
	private ArmMath armMath = new ArmMath();

	private boolean goingHome = false;
	
	public Arm() {
		super("Arm");

		extensionMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		extensionMotor.selectProfileSlot(0, 0);
		extensionMotor.configNominalOutputForward(0, TIMEOUT);
		extensionMotor.configNominalOutputReverse(0, TIMEOUT);
		extensionMotor.configPeakOutputForward(Robot.EXT_SCALE_FACTOR, TIMEOUT);
		extensionMotor.configPeakOutputReverse(-Robot.EXT_SCALE_FACTOR, TIMEOUT);
		extensionMotor.setNeutralMode(NeutralMode.Brake);
		extensionMotor.setSensorPhase(false);
		extensionMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		extensionMotor.config_kP(0, EXT_P, TIMEOUT);
		extensionMotor.config_kI(0, EXT_I, TIMEOUT);
		extensionMotor.config_kD(0, EXT_D, TIMEOUT);
		extensionMotor.configAllowableClosedloopError(1, 4, TIMEOUT);
		extensionMotor.config_kP(1, EXT_INTAKE_P, TIMEOUT);
		extensionMotor.config_kI(1, EXT_I, TIMEOUT);
		extensionMotor.config_kD(1, EXT_D, TIMEOUT);

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
		extensionToHome();
		mathPotExtSetpoint = 0.0;

		printPIDValues();
	}

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

		// TODO Change this to automatic when tested
		setDefaultCommand(new ArmFinetune());
	}

	@Override
	public void periodic() {
		// Put code here to be run every loop

		moveArm();
		moveExtension();

		SmartDashboard.putNumber("ARM ANGLE", getArmAngle());
		SmartDashboard.putNumber("ARM SETPOINT", armAngleSetpoint);
		SmartDashboard.putNumber("ARM POT", getArmPos());
		SmartDashboard.putNumber("ARM ERROR", movementMotor.getClosedLoopError(0));
		SmartDashboard.putNumber("ARM VOLTAGE", movementMotor.getMotorOutputVoltage());
		
		SmartDashboard.putNumber("EXTENSION LENGTH", getExtLength());
		SmartDashboard.putNumber("EXTENSION SETPOINT", manualExtSetpoint);
		SmartDashboard.putNumber("EXT POT", getExtPos());
	}

	public void armData() {
		SmartDashboard.putNumber("Setpoint", armAngleSetpoint);
		SmartDashboard.putNumber("ARM POT", getArmPos());
		
		SmartDashboard.putNumber("EXT POT", getExtPos());
		SmartDashboard.putNumber("EXTENSION LENGTH", getExtLength());
		SmartDashboard.putNumber("EXTENSION POT SETPOINT", mathPotExtSetpoint);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void getPIDValues() {
//		ARM_P = SmartDashboard.getNumber("ARM P", ARM_P);
//		ARM_I = SmartDashboard.getNumber("ARM I", ARM_I);
//		ARM_D = SmartDashboard.getNumber("ARM D", ARM_D);
//		movementMotor.config_kP(0, ARM_P, TIMEOUT);
//		movementMotor.config_kI(0, ARM_I, TIMEOUT);
//		movementMotor.config_kD(0, ARM_D, TIMEOUT);
//
//		EXT_P = SmartDashboard.getNumber("EXT P", EXT_P);
//		EXT_I = SmartDashboard.getNumber("EXT I", EXT_I);
//		EXT_D = SmartDashboard.getNumber("EXT D", EXT_D);
//		extensionMotor.config_kP(0, EXT_P, TIMEOUT);
//		extensionMotor.config_kI(0, EXT_I, TIMEOUT);
//		extensionMotor.config_kD(0, EXT_D, TIMEOUT);
	}

	public void printPIDValues() {
		

		SmartDashboard.putNumber("EXT P", EXT_P);
		SmartDashboard.putNumber("EXT I", EXT_I);
		SmartDashboard.putNumber("EXT D", EXT_D);
	}
	
	public void finetuneUp() {
		armAngleSetpoint += FINETUNE_RATE;
	}

	public void finetuneDown() {
		armAngleSetpoint -= FINETUNE_RATE;
	}

	public void finetuneDownManual() {
		movementMotor.set(ControlMode.PercentOutput, 0.2);
	}

	public void finetuneUpManual() {
		movementMotor.set(ControlMode.PercentOutput, -0.2);
	}

	public void stopArm() {
		movementMotor.set(ControlMode.Position, armAngleSetpoint);
	}

	public double getArmAngle() {
		return armMath.convertPotToAngle(ARM_POT_MIN, ARM_ANGLE_MIN, ARM_POT_MAX, ARM_ANGLE_MAX, getArmPos() * ARM_POT_INVERT);
	}

	public double getExtLength() {
		 return armMath.convertExtPotToLength(EXT_POT_MIN, EXT_LENGTH_MIN, EXT_POT_MAX, EXT_LENGTH_MAX, getExtPos()* EXT_POT_INVERT);
	}

	// TODO Confirm if value is negative on real robot
 	public int getArmPos() {
		return movementMotor.getSelectedSensorPosition(0);
	}

	public int getExtPos() {
		return extensionMotor.getSelectedSensorPosition(0);
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
		case HighScale:
			return armPos >= HIGHSCALE_SETPOINT - ANGLE_MARGIN_VALUE
					&& armPos <= HIGHSCALE_SETPOINT + ANGLE_MARGIN_VALUE;
		case Climb:
			return armPos >= HOME_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= HOME_SETPOINT + ANGLE_MARGIN_VALUE;
		case Home:
			return armPos >= HOME_SETPOINT - ANGLE_MARGIN_VALUE && armPos <= HOME_SETPOINT + ANGLE_MARGIN_VALUE;
		default:
			return true;
		}
	}

	public void moveToPos(ArmPositions pos) {
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
	
	/**
	 * Set extension to fully retracted position, or home position.
	 */
	public void extensionToHome() {
		manualExtSetpoint = EXT_HOME_SETPOINT;
	}

	public boolean extensionAtHome() {
		double extension = getExtLength();
		return extension <= EXT_HOME_SETPOINT + EXT_MARGIN_VALUE
				&& extension >= EXT_HOME_SETPOINT - EXT_MARGIN_VALUE;
	}
	
	public void setGoingHome(boolean val)
	{
		goingHome = val;
	}
	
	public boolean isGoingHome()
	{
		return goingHome;
	}

	public void extensionToIntakeBegin() {
		manualExtSetpoint = EXT_INTAKE_SETPOINT;
	}
	
	public void extensionToIntakeEnd() {
		manualExtSetpoint = EXT_INTAKE_SETPOINT_GOTO;
	}
	
	public void setExtToCurrentPos()
	{
		manualExtSetpoint = getExtLength();
	}
	
	public void setExtIntakePID()
	{
		extensionMotor.selectProfileSlot(1, 0);
	}

	public void setExtNormalPID()
	{
		extensionMotor.selectProfileSlot(0, 0);
	}
	
	public boolean extensionAtIntake() {
		double extension = getExtLength();
		return extension <= EXT_INTAKE_SETPOINT + ANGLE_MARGIN_VALUE
				&& extension >= EXT_INTAKE_SETPOINT - ANGLE_MARGIN_VALUE;
	}

	public void setArmToCurrentPos()
	{
		armAngleSetpoint = getArmAngle();
	}
	
	public boolean inAutoRange() {
		double armPos = getArmAngle();
		return armPos >= EXCHANGE_SETPOINT - CRITICAL_MARGIN_VALUE
//				&& armPos <= LOWSCALE_SETPOINT + CRITICAL_MARGIN_VALUE;
				&& armPos <= HIGHSCALE_SETPOINT + CRITICAL_MARGIN_VALUE;
	}
	
	public boolean isArmMoving()
	{
		return !(getArmAngle() >= armAngleSetpoint + ANGLE_MARGIN_VALUE && getArmAngle() <= armAngleSetpoint - ANGLE_MARGIN_VALUE);
	}
	
	public boolean isExtMoving()
	{
		return !(getArmAngle() >=  + EXT_MARGIN_VALUE && getArmAngle() <= armAngleSetpoint - ANGLE_MARGIN_VALUE);
	}
	
	/**
	 * Uses arm math to calculate new position for extension This math only applies
	 * within the exchange and high scale positions
	 */
	private void moveExtension() {
		if(getArmAngle() <= HOME_MAX_ANGLE) {
			manualExtSetpoint = EXT_HOME_SETPOINT;
		}
		
		if (inAutoRange() && !goingHome) {
			double angle = getArmAngle();
			double extSetpoint = armMath.convertArmAngleToExtPot(EXT_POT_MIN, EXT_LENGTH_MIN, EXT_POT_MAX, EXT_LENGTH_MAX, angle) * EXT_POT_INVERT;
			mathPotExtSetpoint = extSetpoint;
			extensionMotor.set(ControlMode.Position, (int) extSetpoint);
		} else {
			double extPot = armMath.convertLengthToExtPot(EXT_POT_MIN, EXT_LENGTH_MIN, EXT_POT_MAX, EXT_LENGTH_MAX, manualExtSetpoint) * EXT_POT_INVERT;
			extensionMotor.set(ControlMode.Position, (int) extPot);
		}
	}

	/**
	 * Keeps arm locked to its current setpoint position
	 */
	private void moveArm() {
		double armSetpoint = armMath.convertAngleToPot(ARM_POT_MIN, ARM_ANGLE_MIN, ARM_POT_MAX, ARM_ANGLE_MAX, armAngleSetpoint) * ARM_POT_INVERT;
		
//		if(isExtMoving())
//		{
//			armSetpoint = getArmAngle();
//		}
		
		SmartDashboard.putNumber("ARM POT SETPOINT", armSetpoint);
		movementMotor.set(ControlMode.Position, (int) armSetpoint);
		
		if(getArmPos() > armSetpoint)
		{
			SmartDashboard.putNumber("ARM P", ARM_DOWN_P);
			SmartDashboard.putNumber("ARM I", ARM_DOWN_I);
			SmartDashboard.putNumber("ARM D", ARM_DOWN_D);
			movementMotor.selectProfileSlot(1, 0);
		}
		else
		{
			SmartDashboard.putNumber("ARM P", ARM_UP_P);
			SmartDashboard.putNumber("ARM I", ARM_UP_I);
			SmartDashboard.putNumber("ARM D", ARM_UP_D);
			movementMotor.selectProfileSlot(0, 0);
		}

	}

	public String armHeadings() {
		return "";
	}
}
