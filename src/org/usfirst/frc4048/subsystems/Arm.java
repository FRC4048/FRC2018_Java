package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;
import org.usfirst.frc4048.arm.math.ArmMath;
import org.usfirst.frc4048.commands.arm.ElbowFinetune;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.utils.MotorUtils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
	private final WPI_TalonSRX armMotor = RobotMap.armmovementMotor;
	private final WPI_TalonSRX elbowMotor = RobotMap.armextensionMotor;
	
	private final int TIMEOUT = 100;

	private final double ELBOW_STALL_DELAY = 5.0;
	private double startStallTime = -ELBOW_STALL_DELAY;
	private final MotorUtils elbowStall = new MotorUtils(RobotMap.PDP_ELBOW, 
															RobotMap.CURRENT_THRESHOLD_ELBOW_MOTOR, 
															RobotMap.TIMEOUT_ELBOW_MOTOR);
	private final MotorUtils armStall = new MotorUtils(RobotMap.PDP_ARM_MOTOR, 
															RobotMap.CURRENT_THRESHOLD_ARM_MOTOR_PROBLEM,
															RobotMap.TIMEOUT_ARM_MOTOR_PROBLEM);
   
	private double ELBW_UP_P = 16.5;
	private double ELBW_UP_I = 0.0;
	private double ELBW_UP_D = 0.0;
	private double ELBW_DOWN_P = 5.5;
	private double ELBW_DOWN_I= 0.0;
	private double ELBW_DOWN_D = 0.0;

	private final double ARM_UP_P = 20.0;
	private final double ARM_UP_I = 0.0;
	private final double ARM_UP_D = 0.0;
	private final double ARM_DOWN_P = 10.0;
	private final double ARM_DOWN_I = 0.0;
	private final double ARM_DOWN_D = 0.0;
	

	/**
	 * Is not a speed, but a setpoint adjustment value
	 */
	private final double FINETUNE_RATE = 2.0;

	/*
	 * All of these setpoints are used for the arm
	 */
	public static final double ANGLE_MARGIN_VALUE = 5.5;
	public static final double CRITICAL_MARGIN_VALUE = 10.0;
	public static final double HOME_SETPOINT = 0.0;
	public static final double HOME_MAX_ANGLE = 3.0;
	public static final double INTAKE_SETPOINT = 0.0;
	public static final double EXCHANGE_SETPOINT = INTAKE_SETPOINT;
	public static final double SWITCH_SETPOINT = INTAKE_SETPOINT;
	public static final double LOWSCALE_SETPOINT = 80.0;
	public static final double MIDSCALE_SETPOINT = 110.0;
	public static final double HIGHSCALE_SETPOINT = 110.0;
	public static final double CLIMB_SETPOINT = 141.0;
	
	public static final double ELBW_HOME_SETPOINT = 131.7;
	public static final double ELBW_INTAKE_SETPOINT = 0.0;
	public static final double ELBW_EXCHANGE_SETPOINT = 45.0;
	public static final double ELBW_SWITCH_SETPOINT = 90;
	public static final double ELBW_LOW_SCALE_SETPOINT = 49;
	public static final double ELBW_MID_SCALE_SETPOINT = 5;
	public static final double ELBW_HIGH_SCALE_SETPOINT = 5;
	public static final double ELBW_CLIMB_SETPOINT=0.0;
	
	private final double HOME_FROM_TOWER = 39.0;
	private final double ARM_POT_MIN = 934;
	private final double ARM_POT_MAX = 200;
	private final double ARM_ANGLE_MIN = 0.0;
	private final double ARM_ANGLE_MAX = 145.0;
	private final double ARM_POT_INVERT = -1.0;
	
	private final double ELBW_POT_MIN = 2771.0;
	private final double ELBW_POT_MAX = 0.0;
	private final double ELBW_ANGLE_MIN = 0.0;
	private final double ELBW_ANGLE_MAX = 157.7;
	private final double ELBW_POT_INVERT = 1.0;
	
	private final double ELBW_FINETUNE_RATE = 2.0;
	
	private double armP = -1;
	private double armI = -1;
	private double armD = -1;
	
	private double elbwP = -1;
	private double elbwI = -1;
	private double elbwD = -1;
	
	private double armAngleSetpoint;
	private double elbowAngleSetpoint;
	
	ArmMath armMath = new ArmMath();
	
	public enum Position {
		CLIMB, SWITCH, LOWSCALE, HIGHSCALE, INTAKE, HOME
	}
	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Arm() {
		super("Arm");

		elbowMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
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

		getMovementMotor().configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		getMovementMotor().configNominalOutputForward(0, TIMEOUT);
		getMovementMotor().configNominalOutputReverse(0, TIMEOUT);
		getMovementMotor().configPeakOutputForward(Robot.ARM_UP_SCALE_FACTOR, TIMEOUT);
		getMovementMotor().configPeakOutputReverse(-Robot.ARM_DOWN_SCALE_FACTOR, TIMEOUT);
		getMovementMotor().setNeutralMode(NeutralMode.Brake);
		getMovementMotor().setSensorPhase(true);
		getMovementMotor().configAllowableClosedloopError(0, 4, TIMEOUT);
		getMovementMotor().config_kP(0, ARM_UP_P, TIMEOUT);
		getMovementMotor().config_kI(0, ARM_UP_I, TIMEOUT);
		getMovementMotor().config_kD(0, ARM_UP_D, TIMEOUT);
		getMovementMotor().configAllowableClosedloopError(1, 4, TIMEOUT);
		getMovementMotor().config_kP(1, ARM_DOWN_P, TIMEOUT);
		getMovementMotor().config_kI(1, ARM_DOWN_I, TIMEOUT);
		getMovementMotor().config_kD(1, ARM_DOWN_D, TIMEOUT);
	}
	
	private WPI_TalonSRX getMovementMotor() {
		// TODO Auto-generated method stub
		return armMotor;
	}
	private WPI_TalonSRX getElbowMotor() {
		return elbowMotor;
	}
	@Override
	public void periodic() {
		moveElbow();
		moveArm();
	}
		
	@Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ElbowFinetune());
	}
	
	public double getArmPos() {
		return getMovementMotor().getSelectedSensorPosition(0);
	}
	

	public int getElbowPos() {
			elbwP = ELBW_UP_P;
			elbwI = ELBW_UP_I;
			elbwD = ELBW_UP_D;
			return elbowMotor.getSelectedSensorPosition(0);
	}
	
	public double getElbowAngle() {
		return armMath.convertElbowEncToAngle(ELBW_POT_MAX, ELBW_POT_MIN, ELBW_ANGLE_MAX, ELBW_ANGLE_MIN, getElbowPos() * ELBW_POT_INVERT);
	}
	
	public double getArmAngle() {
		return armMath.convertPotToAngle(ARM_POT_MAX, ARM_POT_MIN, ARM_ANGLE_MAX, ARM_ANGLE_MIN, getArmPos() * ARM_POT_INVERT);
	}
	
	public void armToPos(Position pos) {
		switch(pos) {
		case INTAKE:
			armAngleSetpoint = INTAKE_SETPOINT;
			break;
		case SWITCH: 
			armAngleSetpoint = SWITCH_SETPOINT;
			break;
		case LOWSCALE:
			armAngleSetpoint = LOWSCALE_SETPOINT;
			break;
		case HIGHSCALE:
			armAngleSetpoint = HIGHSCALE_SETPOINT;
			break;
		case HOME:
			armAngleSetpoint = HOME_SETPOINT;
			break;
		case CLIMB:
			if(armAngleSetpoint >= (HIGHSCALE_SETPOINT - ANGLE_MARGIN_VALUE)) {
				armAngleSetpoint = CLIMB_SETPOINT;
				break;
			} else {
				break;
			}
		default:
			break;
		}
	}
	
	public boolean armAtPos(Position pos) {
		switch(pos) {
		case CLIMB:
			return (getArmAngle() <= CLIMB_SETPOINT + ANGLE_MARGIN_VALUE) && (getArmAngle() >= CLIMB_SETPOINT - ANGLE_MARGIN_VALUE);
		case HIGHSCALE:
			return (getArmAngle() <= HIGHSCALE_SETPOINT + ANGLE_MARGIN_VALUE) && (getArmAngle() >= HIGHSCALE_SETPOINT - ANGLE_MARGIN_VALUE);
		case HOME:
			return (getArmAngle() <= HOME_SETPOINT + ANGLE_MARGIN_VALUE) && (getArmAngle() >= HOME_SETPOINT - ANGLE_MARGIN_VALUE);
		case INTAKE:
			return (getArmAngle() <= INTAKE_SETPOINT + ANGLE_MARGIN_VALUE) && (getArmAngle() >= INTAKE_SETPOINT - ANGLE_MARGIN_VALUE);
		case LOWSCALE:
			return (getArmAngle() <= LOWSCALE_SETPOINT + ANGLE_MARGIN_VALUE) && (getArmAngle() >= LOWSCALE_SETPOINT - ANGLE_MARGIN_VALUE);
		case SWITCH:
			return (getArmAngle() <= SWITCH_SETPOINT + ANGLE_MARGIN_VALUE) && (getArmAngle() >= SWITCH_SETPOINT - ANGLE_MARGIN_VALUE);
		default:
			return false;
		}
	}
	
	public void moveArm() {
		double armPos = armMath.convertAngleToPot(ARM_POT_MAX, ARM_POT_MIN, ARM_ANGLE_MAX, ARM_ANGLE_MIN, armAngleSetpoint) * ARM_POT_INVERT;
		
		getMovementMotor().set(ControlMode.Position, armPos);
		
		if (armPos > armAngleSetpoint) {
			armP = ARM_DOWN_P;
			armI = ARM_DOWN_I;
			armD = ARM_DOWN_D;
		} else {
			armP = ARM_UP_P;
			armI = ARM_UP_I;
			armD = ARM_UP_D;
		}
	}
	
	public void elbwToPos(Position pos) {
		switch(pos) {
		case INTAKE:
			elbowAngleSetpoint = ELBW_INTAKE_SETPOINT;
			break;
		case SWITCH: 
			elbowAngleSetpoint = ELBW_SWITCH_SETPOINT;
			break;
		case LOWSCALE:
			elbowAngleSetpoint = ELBW_LOW_SCALE_SETPOINT;
			break;
		case HIGHSCALE:
			elbowAngleSetpoint = ELBW_HIGH_SCALE_SETPOINT;
			break;
		case HOME:
			elbowAngleSetpoint = ELBW_HOME_SETPOINT;
			break;
		case CLIMB:
			elbowAngleSetpoint = ELBW_CLIMB_SETPOINT;
			break;
		default:
			break;
		}
	}
	
	public boolean elbwAtPos(Position pos) {
		switch(pos) {
		case CLIMB:
			return (getElbowAngle() <= ELBW_CLIMB_SETPOINT + ANGLE_MARGIN_VALUE) && (getElbowAngle() >= ELBW_CLIMB_SETPOINT - ANGLE_MARGIN_VALUE);
		case HIGHSCALE:
			return (getElbowAngle() <= ELBW_HIGH_SCALE_SETPOINT + ANGLE_MARGIN_VALUE) && (getElbowAngle() >= ELBW_HIGH_SCALE_SETPOINT - ANGLE_MARGIN_VALUE);
		case HOME:
			return (getElbowAngle() <= ELBW_HOME_SETPOINT + ANGLE_MARGIN_VALUE) && (getElbowAngle() >= ELBW_HOME_SETPOINT - ANGLE_MARGIN_VALUE);
		case INTAKE:
			return (getElbowAngle() <= ELBW_INTAKE_SETPOINT + ANGLE_MARGIN_VALUE) && (getElbowAngle() >= ELBW_INTAKE_SETPOINT - ANGLE_MARGIN_VALUE);
		case LOWSCALE:
			return (getElbowAngle() <= ELBW_LOW_SCALE_SETPOINT + ANGLE_MARGIN_VALUE) && (getElbowAngle() >= ELBW_LOW_SCALE_SETPOINT - ANGLE_MARGIN_VALUE);
		case SWITCH:
			return (getElbowAngle() <= ELBW_SWITCH_SETPOINT + ANGLE_MARGIN_VALUE) && (getElbowAngle() >= ELBW_SWITCH_SETPOINT - ANGLE_MARGIN_VALUE);
		default:
			return false;
		}
	}
	
	public boolean armBellowSwitch() {
		return !(getArmAngle() <= SWITCH_SETPOINT + ANGLE_MARGIN_VALUE);
	}
	
	public boolean retractElbow(Position pos) {
		switch(pos) {
		case CLIMB:
			return false;
		case HIGHSCALE:
			return true;
		case HOME:
			return armBellowSwitch();
		case INTAKE:
			return armBellowSwitch();
		case LOWSCALE:
			return true;
		case SWITCH:
			return armBellowSwitch();
		default:
			return true;
		}
	}
	
	public void moveElbow() {
		double elbowPos = armMath.convertAngleToPot(ELBW_POT_MAX, ELBW_POT_MIN, ELBW_ANGLE_MAX, ELBW_ANGLE_MIN, elbowAngleSetpoint) * ELBW_POT_INVERT;
		
		getElbowMotor().set(ControlMode.Position, elbowPos);
		
		double a = getArmAngle() - getElbowAngle() + HOME_FROM_TOWER;
		double b = getArmAngle() - elbowAngleSetpoint + HOME_FROM_TOWER;
		
		if ((a < 180 && b > a) || (a > 180 && a > b)) {
			getElbowMotor().selectProfileSlot(0, 0);
		} else {
			getElbowMotor().selectProfileSlot(1, 0);
		}
	}
	
	public void fineTuneUp() {
		elbowAngleSetpoint += ELBW_FINETUNE_RATE;
	}
	
	public void fineTuneDown() {
		elbowAngleSetpoint -= ELBW_FINETUNE_RATE;
	}
}