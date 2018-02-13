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
	private final AnalogPotentiometer rotationPot = RobotMap.armrotationPot;
	private final WPI_TalonSRX movementMotor = RobotMap.armmovementMotor;
	private final AnalogPotentiometer extensionPot = RobotMap.armextensionPot;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private final int TIMEOUT = 100;

	private final double EXT_P = 1.0;
	private final double EXT_I = 0.0;
	private final double EXT_D = 0.0;

	private final double ARM_P = 1.0;
	private final double ARM_I = 1.0;
	private final double ARM_D = 0.0;

	/**
	 * Is not a speed, but a setpoint adjustment value
	 */
	private final double FINETUNE_RATE = 1.0;

	// TODO ALL OF THESE SETPOINTS ARE NOT VALID
	private final double MARGIN_VALUE = 5.0;
	private final double EXCHANGE_SETPOINT = 180.0;
	private final double SWITCH_SETPOINT = 320.0;
	private final double LOWSCALE_SETPOINT = 550.0;
	private final double HIGHSCALE_SETPOINT = 780.0;
	private final double CLIMBER_SETPOINT = 1010;
	private final double HOME_SETPOINT = 0.0;
	private final double INTAKE_SETPOINT = 140.0;

	private final double ARM_POT_MIN = 0.0;
	private final double ARM_POT_MAX = 1023.0;
	private final double ARM_ANGLE_MIN = 0.0;
	private final double ARM_ANGLE_MAX = 158.0;
	private final double EXT_POT_MIN = 11.0;
	private final double EXT_POT_MAX = 920.0;
	private final double EXT_LENGTH_MIN = 0.0;
	private final double EXT_LENGTH_MAX = 16.0;

	private double armSetpoint;
	private double extSetpoint;
	private ArmMath armMath = new ArmMath();
	private boolean autoExtension = true;
//	private PIDController armController = new PIDController(ARM_P, ARM_I, ARM_D, rotationPot, movementMotor);
//	private PIDController extController = new PIDController(EXT_P, EXT_I, EXT_D, extensionPot, extensionMotor);

	public Arm() {
		super("Arm");

		extensionMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		extensionMotor.selectProfileSlot(0, 0);
		extensionMotor.configNominalOutputForward(0, TIMEOUT);
		extensionMotor.configNominalOutputReverse(0, TIMEOUT);
		extensionMotor.setNeutralMode(NeutralMode.Brake);
		extensionMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		extensionMotor.config_kP(0, EXT_P, TIMEOUT);
		extensionMotor.config_kI(0, EXT_I, TIMEOUT);
		extensionMotor.config_kD(0, EXT_D, TIMEOUT);

		movementMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		movementMotor.selectProfileSlot(0, 0);
		movementMotor.configNominalOutputForward(0, TIMEOUT);
		movementMotor.configNominalOutputReverse(0, TIMEOUT);
		movementMotor.setNeutralMode(NeutralMode.Brake);
		movementMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
		movementMotor.config_kP(0, ARM_P, TIMEOUT);
		movementMotor.config_kI(0, ARM_I, TIMEOUT);
		movementMotor.config_kD(0, ARM_D, TIMEOUT);

		armSetpoint = HOME_SETPOINT;

		// Used for test bed
//		armController.enable();
//		extController.enable();
	}

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ArmFinetune());
	}

	@Override
	public void periodic() {
		// Put code here to be run every loop

		moveArm();
		moveExtension();

		SmartDashboard.putNumber("ARM SETPOINT", armSetpoint);
		SmartDashboard.putNumber("ARM POT", getArmPos());
		SmartDashboard.putNumber("EXT POT", getExtPos());
	}

	public void armData() {
		SmartDashboard.putNumber("Setpoint", armSetpoint);
		SmartDashboard.putNumber("Current Value", getArmPos());
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void finetuneUp() {
		armSetpoint += FINETUNE_RATE;
	}

	public void finetuneDown() {
		armSetpoint -= FINETUNE_RATE;
	}

	public void stopArm() {
		movementMotor.set(ControlMode.Position, armSetpoint);
//		 armController.setSetpoint(armSetpoint);
	}

	//TODO Confirm if value is negative on real robot
	public int getArmPos() {
		return -movementMotor.getSelectedSensorPosition(0);
	}

	public int getExtPos() {
		return extensionMotor.getSelectedSensorPosition(0);
	}

	// // Used for software pid
	// public double getArmPos() {
	// return rotationPot.get();
	// }
	//
	// // Used for software pid
	// public double getExtPos() {
	// return extensionPot.get();
	// }

	/**
	 * Checks to see if the arm is within the correct position
	 * 
	 * @param position
	 * @return True if arm is in correct position., False otherwise
	 */
	// TODO Test this!!!
	public boolean armAtPosition(ArmPositions position) {
		// int armPos = getArmPos();
		double armPos = getArmPos();
		switch (position) {
		case Intake:
			return armPos >= INTAKE_SETPOINT - MARGIN_VALUE && armPos <= INTAKE_SETPOINT + MARGIN_VALUE;
		case Exchange:
			return armPos >= EXCHANGE_SETPOINT - MARGIN_VALUE && armPos <= EXCHANGE_SETPOINT + MARGIN_VALUE;
		case Switch:
			return armPos >= SWITCH_SETPOINT - MARGIN_VALUE && armPos <= SWITCH_SETPOINT + MARGIN_VALUE;
		case LowScale:
			return armPos >= LOWSCALE_SETPOINT - MARGIN_VALUE && armPos <= LOWSCALE_SETPOINT + MARGIN_VALUE;
		case HighScale:
			return armPos >= HIGHSCALE_SETPOINT - MARGIN_VALUE && armPos <= HIGHSCALE_SETPOINT + MARGIN_VALUE;
		case Climb:
			return armPos >= CLIMBER_SETPOINT - MARGIN_VALUE && armPos <= CLIMBER_SETPOINT + MARGIN_VALUE;
		case Home:
			return armPos >= HOME_SETPOINT - MARGIN_VALUE && armPos <= HOME_SETPOINT + MARGIN_VALUE;
		default:
			return false;
		}
	}

	// TODO Test this!!!
	public void moveToPos(ArmPositions pos) {
		switch (pos) {
		case Intake:
			armSetpoint = INTAKE_SETPOINT;
			break;
		case Exchange:
			armSetpoint = EXCHANGE_SETPOINT;
			break;
		case Switch:
			armSetpoint = SWITCH_SETPOINT;
			break;
		case LowScale:
			System.out.println("GOING TO LOWSCALE");
			armSetpoint = LOWSCALE_SETPOINT;
			break;
		case HighScale:
			System.out.println("GOING TO HIGHSCALE");
			armSetpoint = HIGHSCALE_SETPOINT;
			break;
		case Climb:
			armSetpoint = CLIMBER_SETPOINT;
			break;
		case Home:
			armSetpoint = HOME_SETPOINT;
			break;
		default:
			break;
		}
		System.out.println(armSetpoint);
	}

	/**
	 * Returns if the arm is currently automatically extending
	 * 
	 * @return - true if extension is automatic, false if extension is manual
	 */
	public boolean getAutoExtension() {
		return autoExtension;
	}

	/**
	 * Sets the automatic extension value
	 * 
	 * @param value
	 *            - true if extension is automatic, false if extension is manual
	 */
	public void setAutoExtension(boolean value) {
		autoExtension = value;
	}

	/**
	 * Uses arm math to calculate new position for extension
	 */
	private void moveExtension() {
		if (autoExtension) {
			double angle = armMath.convertPotToAngle(ARM_POT_MIN, ARM_ANGLE_MIN, ARM_POT_MAX, ARM_ANGLE_MAX,
					getArmPos());
			SmartDashboard.putNumber("ARM ANGLE", angle);
			extSetpoint = armMath.convertArmAngleToExtPot(EXT_POT_MIN, EXT_LENGTH_MIN, EXT_POT_MAX, EXT_LENGTH_MAX,
					angle);
			SmartDashboard.putNumber("EXTENSION SETPOINT", extSetpoint);
			extensionMotor.set(ControlMode.Position, (int) extSetpoint);
			
//			extController.setSetpoint(extSetpoint);
		}
	}

	/**
	 * Keeps arm locked to its current setpoint position
	 */
	private void moveArm() {
		 movementMotor.set(ControlMode.Position, (int) armSetpoint);

		// Used for software pid
//		armController.setSetpoint(armSetpoint);
	}
}