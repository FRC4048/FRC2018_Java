package org.usfirst.frc4048.subsystems;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.RobotMap;

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
	
	private double armP = -1;
	private double armI = -1;
	private double armD = -1;
	
	private double elbwP = -1;
	private double elbwI = -1;
	private double elbwD = -1;
	
	private double armAngleSetpoint;
	private double elbowAngleSetpoint;
	
	enum position {
		CLIMB, SWITCH, LOWSCALE, HIGHSCALE, INTAKE, HOME
	}
	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Arm() {
		super("Arm");

		elbowMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
//		elbowMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
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

	@Override
	public void periodic() {
		moveArm();
	}
		
	@Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public void armToPos() {
		
	}
	
	public void armAtPos() {
		
	}
	
	public void moveArm() {
		
	}
}

