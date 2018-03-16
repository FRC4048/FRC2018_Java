// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc4048;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.commands.arm.*;
import org.usfirst.frc4048.arm.*;
import org.usfirst.frc4048.commands.auto.AutoAction;
//import org.usfirst.frc4048.commands.getcube.GetCubeGroupCommand;
import org.usfirst.frc4048.commands.intake.*;
//import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
//import org.usfirst.frc4048.commands.intake.IntakeCube.IntakeMode;
import org.usfirst.frc4048.subsystems.*;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Drivetrain.SonarSide;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;
import org.usfirst.frc4048.commands.auto.Action;
import org.usfirst.frc4048.swerve.math.*;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.WorkQueue;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	Command autonomousCommand;
	SendableChooser<Action> chooser = new SendableChooser<>();
	SendableChooser<Boolean> diagnosticChoose = new SendableChooser<>();

	public static OI oi;
	public static Drivetrain drivetrain;
	// public static Claw claw;
	// public static Wrist wrist;
	public static Arm arm;
//	public static Pincher pincher;
	 public static Intake intake;
	// public static Climber climber;
	public static Logging logging;
	public static PowerDistPanel powerdistpanel;
	public WorkQueue wq;

	// use this to see the debug commands and values for smart dashboard
	public final Boolean enableDebug = false;
	public final Boolean enableTesting = false;
	public Boolean enableDiagnostic = false;

	Action autoAction;
	Action oldAutoAction;

	public static double GLOBAL_SCALE_FACTOR = 1;
	public static double ARM_UP_SCALE_FACTOR = 0.75;
	public static double ARM_DOWN_SCALE_FACTOR = 0.30;
	public static double EXT_SCALE_FACTOR = 1.00;

	public static final boolean USE_WRIST_STRAIGHT = true;

	public static double timeOfStart = 0;

	StringBuilder absEncoderSB = new StringBuilder();
	StringBuilder armPotSB = new StringBuilder();
	StringBuilder elbowPotSB= new StringBuilder();
	StringBuilder armAngleSB= new StringBuilder();
	StringBuilder elbowAngleSB= new StringBuilder();
	StringBuilder distanceEncoderSB= new StringBuilder();
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		absEncoderSB = new StringBuilder();
		
		RobotMap.init();
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		drivetrain = new Drivetrain();
		// claw = new Claw();
		// wrist = new Wrist();
		arm = new Arm();
//		pincher = new Pincher();
		intake = new Intake();
		powerdistpanel = new PowerDistPanel();
		// climber = new Climber();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		WorkQueue wq = new WorkQueue(512);
		logging = new Logging(100, wq);
		logging.startThread(); // Starts the logger

		chooser.addDefault("Autorun(cross the base line)", Action.Baseline);
		chooser.addObject("Robot on LEFT, do SWITCH", Action.LSwitch);
		chooser.addObject("Robot on LEFT, do SCALE", Action.LScale);
		chooser.addObject("Robot on LEFT, do SWITCH if its left or SCALE if on the left", Action.LLocalSwitchPriority);
		chooser.addObject("Robot on LEFT, do SCALE if its left or SWITCH if on the left", Action.LLocalScalePriority);
		chooser.addObject("Robot on RIGHT, do SWITCH", Action.RSwitch);
		chooser.addObject("Robot on RIGHT, do SCALE", Action.RScale);
		chooser.addObject("Robot on RIGHT, do SWITCH if its right or SCALE if on the right",
				Action.RLocalSwitchPriority);
		chooser.addObject("Robot on RIGHT, do SCALE if its right or SWITCH if on the right",
				Action.RLocalScalePriority);
		chooser.addObject("Robot in MIDDLE, do SWITCH", Action.MiddleSwitch);
		chooser.addObject("Do Nothing", Action.Nothing);

		// Diagnostic chooser
		diagnosticChoose.addDefault("Disable Diagnostic", false);
		diagnosticChoose.addObject("Enable Diagnostic", true);
		

		// Add commands to Autonomous Sendable Chooser
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to
	 * reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		logging.traceMessage(Logging.MessageLevel.INFORMATION,
				"---------------------------- Robot Disabled ----------------------------");
		Robot.arm.setArmToCurrentPos();
		Robot.drivetrain.resetDriveEncoder();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		Robot.arm.setDisabled(true);

		autoAction = chooser.getSelected();
		
		if (autoAction != oldAutoAction) {
			// every time the driver changes autonomous selection
			logging.traceMessage(Logging.MessageLevel.INFORMATION,
					"AutoAction user selection: " + autoAction.toString());
			oldAutoAction = autoAction;
		}
		enableDiagnostic = diagnosticChoose.getSelected();
		dashboardData();
	}

	@Override
	public void autonomousInit() {
		timeOfStart = Timer.getFPGATimestamp();
		Robot.arm.ResetElbowEncoder();
		logging.traceMessage(Logging.MessageLevel.INFORMATION,
				"---------------------------- Autonomous mode starting ----------------------------");
		logging.printHeadings();
		Robot.drivetrain.swerveDrivetrain.setModeField();
		char switchPos = 'X';
		char scalePos = 'X';
		StringBuilder gameInfo = new StringBuilder();
		gameInfo.append("Match Number=");
		gameInfo.append(DriverStation.getInstance().getMatchNumber());
		gameInfo.append(", Alliance Color=");
		gameInfo.append(DriverStation.getInstance().getAlliance().toString());
		gameInfo.append(", Match Type=");
		gameInfo.append(DriverStation.getInstance().getMatchType().toString());
		logging.traceMessage(Logging.MessageLevel.INFORMATION, gameInfo.toString());

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		logging.traceMessage(Logging.MessageLevel.INFORMATION, "Field plate selection:" + gameData);

		if (gameData.length() < 2) {
			DriverStation.reportError("Bad game specific data recieved " + gameData, false);

		}
		if (gameData.charAt(0) == 'R' || gameData.charAt(0) == 'L') {
			switchPos = gameData.charAt(0);
		}
		if (gameData.charAt(1) == 'R' || gameData.charAt(1) == 'L') {
			scalePos = gameData.charAt(1);
		}
		System.out.println("Scale Pos = " + scalePos);
		System.out.println("Switch Pos = " + switchPos);

		Action autoAction = chooser.getSelected();
		System.out.println("Action in Auto " + autoAction.toString());
		System.out.println("Game Data: " + gameData);
		autonomousCommand = new AutoAction(switchPos, scalePos, autoAction);

		// schedule the autonomous command (example)

		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		dashboardData();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		logging.traceMessage(Logging.MessageLevel.INFORMATION,
				"---------------------------- Teleop mode starting ----------------------------");
		logging.printHeadings();
		Robot.drivetrain.swerveDrivetrain.setModeField();

		autoAction = chooser.getSelected();
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		dashboardData();
	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();

		dashboardData();
	}
	/**
	 * Put things for diagnostic mode here
	 */
	public void diagnostics() {
		absEncoderSB.setLength(0);
		absEncoderSB.append(String.format("FR %d", Robot.drivetrain.getAnalogInputFrontRight().getValue()));
		absEncoderSB.append(String.format(" FL %d", Robot.drivetrain.getAnalogInputFrontLeft().getValue()));
		absEncoderSB.append(String.format(" RL %d", Robot.drivetrain.getAnalogInputRearLeft().getValue()));
		absEncoderSB.append(String.format(" RR %d", Robot.drivetrain.getAnalogInputRearRight().getValue()));
		SmartDashboard.putString("DIAGNOSTICS ABS ENCODER(TURN WHEELS)", absEncoderSB.toString());
		
		distanceEncoderSB.setLength(0);
		distanceEncoderSB.append(String.format("Encoder Val %.3f", Robot.drivetrain.getDistance()));
		SmartDashboard.putString("DIAGNOSTICS DISTANCE ENCODER (TURN FRONT RIGHT WHEEL)", distanceEncoderSB.toString());
		
		SmartDashboard.putBoolean("DIAGNOSTICS arm Forward Limit Switch", Robot.arm.getMovementMotor().getSensorCollection().isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("DIAGNOSTICS arm Reverse Limit Switch", Robot.arm.getMovementMotor().getSensorCollection().isRevLimitSwitchClosed());
		
		armPotSB.setLength(0);
		armPotSB.append(String.format("Max Arm Pot %.3f", Robot.arm.GET_MAX_ARM_POT()));
		armPotSB.append(String.format(" Min Arm Pot %.3f", Robot.arm.GET_MIN_ARM_POT()));
		armPotSB.append(String.format(" Current Pot %d", Robot.arm.getArmPos()));
		SmartDashboard.putString("DIAGNOSTICS ARM POT VALS (MOVE ARM)", armPotSB.toString());
		
		elbowPotSB.setLength(0);
		elbowPotSB.append(String.format("Max Elbow Pot %.3f", Robot.arm.GET_MAX_ELBOW_POT()));
		elbowPotSB.append(String.format(" Min Elbow Pot %.3f", Robot.arm.GET_MIN_ELBOW_POT()));
		elbowPotSB.append(String.format(" Current Elbow Pot %d", Robot.arm.getElbowPos()));
		SmartDashboard.putString("DIAGNOSTICS ELBOW POT VALS (MOVE ELBOW)", elbowPotSB.toString());
		
		armAngleSB.setLength(0);	
		armAngleSB.append(String.format("Max Arm Angle %.3f", Robot.arm.GET_MAX_ARM_ANGLE()));
		armAngleSB.append(String.format(" Min Arm Angle %.3f", Robot.arm.GET_MIN_ARM_ANGLE()));
		armAngleSB.append(String.format(" Current Arm Angle %.3f", Robot.arm.getArmAngle()));
		SmartDashboard.putString("DIAGNOSTICS ARM ANGLE VALS (MOVE ARM", armAngleSB.toString());
		
		elbowAngleSB.setLength(0);
		elbowAngleSB.append(String.format("Max Elbow Angle %.3f", Robot.arm.GET_MAX_ELBOW_ANGLE()));
		elbowAngleSB.append(String.format("MIn Elbow Angle %.3f", Robot.arm.GET_MIN_ELBOW_ANGLE()));
		elbowAngleSB.append(String.format("Current ELbow Angle %.3f", Robot.arm.getElbowAngle()));
		SmartDashboard.putString("DIAGNOSTICS ELBOW ANGLE VALS (MOVE ELBOW)", elbowAngleSB.toString());
		
	}
	
	public void dashboardData() {
		if (enableDebug) {
			Robot.drivetrain.outputAbsEncValues();
			Robot.arm.armData();
			Robot.oi.dashboardButtons();
			SmartDashboard.putNumber("Distance", RobotMap.swerveDriveEncoder.getDistance());
			SmartDashboard.putNumber("Robot Gyro Value", Robot.drivetrain.getGyro());
			SmartDashboard.putNumber("Left Sonar Distance", Robot.drivetrain.getSonar(SonarSide.LEFT));
			// SmartDashboard.putNumber("Claw Gyro", Robot.wrist.getGyroVal());
			SmartDashboard.putNumber("Pitch Motor Val", RobotMap.clawpitchMotor.getMotorOutputPercent());
			SmartDashboard.putNumber("Global Distance", Robot.drivetrain.globalDriveDistance);
			SmartDashboard.putNumber("Global Speed", Robot.drivetrain.globalDriveDirSpeed);
			SmartDashboard.putData("Calc Right",
					new CalculateSonarDistance(SonarSide.RIGHT, AutoAction.DISTANCE_FROM_WALL_SWITCH));
			SmartDashboard.putData("Calc Left",
					new CalculateSonarDistance(SonarSide.LEFT, AutoAction.DISTANCE_FROM_WALL_SWITCH));
			// SmartDashboard.putBoolean("Intake Has Cube", intake.hasCube());
			// SmartDashboard.putBoolean("Has Cube", claw.cubePresent());
		}
		if (enableTesting) {
			// GLOBAL_SCALE_FACTOR = SmartDashboard.getNumber("Global Scale Factor", 0s.0);
			SmartDashboard.putNumber("JoyStick Left X", oi.getLeftJoystick().getX());
			SmartDashboard.putNumber("JoyStick Left Y", oi.getLeftJoystick().getY());
			SmartDashboard.putNumber("JoyStick Right X", oi.getRightJoystick().getX());
			// SmartDashboard.putData("Move Claw to Compact", new
			// SetClawPosition(WristPostion.Compact));
			// SmartDashboard.putData("Move Claw to Level", new
			// SetClawPosition(WristPostion.Level));
			SmartDashboard.putNumber("Global Scale Factor", GLOBAL_SCALE_FACTOR);
			SmartDashboard.putData("Drive Command", new Drive());
			// SmartDashboard.putData("Reset drive encoder",
			// Robot.drivetrain.resetDriveEncoder());
			// SmartDashboard.putData("Get Cube Command", new GetCubeGroupCommand());
			SmartDashboard.putData("Reset Quad Encoders", new ReconfigEncoders());
			SmartDashboard.putData("Rotate Angle", new RotateAngle(90));// Rotates robot by 90 degrees
			SmartDashboard.putData("Toggle Drive Mode", new ToggleMode());
//			SmartDashboard.putData("grab Cube", new GrabCube());
			SmartDashboard.putData("Move Arm", new MoveArm(ArmPositions.Intake));// Sets Arm position to Intake position
			// SmartDashboard.putData("Lower Arm to Cube", new ExtendArmToCube());
//			// SmartDashboard.putData("Move Claw", new MoveClaw());
//			SmartDashboard.putData("Open Claw", new OpenClaw());
			// SmartDashboard.putData("Set Claw Position Compact", new
			// SetClawPosition(WristPostion.Compact));//Sets claw position to compact
			// SmartDashboard.putData("Set Claw Position Level", new
			// SetClawPosition(WristPostion.Level));//Sets claw position to level
			// SmartDashboard.putData("Set Claw Position Straight", new
			// SetClawPosition(WristPostion.Straight));//Sets claw position to straight
			// SmartDashboard.putData("Flush Cube", new FlushCube());
			// SmartDashboard.putData("Lower intake", new LowerIntake());
			// SmartDashboard.putData("Raise Intake", new RaiseIntake());
			// SmartDashboard.putData("Toggle Intake", new ToggleIntake());
			// SmartDashboard.putData("Intake Cube Straight", new
			// IntakeCube(IntakeMode.STRAIGHT_PULL));
			// SmartDashboard.putData("Intake Cube Toggle", new
			// IntakeCube(IntakeMode.TOGGLE_PULL_LEFT_OR_RIGHT));
			// SmartDashboard.putData("Open Intake", new GripIntake(GripPosition.Open));
			// SmartDashboard.putData("Close Intake", new GripIntake(GripPosition.Close));
			// SmartDashboard.putData(new GripIntake(GripPosition.Open));
			SmartDashboard.putData("Drive 0.2", new DriveDistance(300, .2, 0, 0));
			// SmartDashboard.putData("Extension Arm To Cube", new
			// ExtensionAndArmToIntake());
		}
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putData("Run Diagnostics", diagnosticChoose);
		SmartDashboard.putData(new ResetElbowEncoder());
		// SmartDashboard.putData("Reset Claw Gyro", new ResetClawGyro());
//		SmartDashboard.putData("Close Claw", new GrabCube());
		// SmartDashboard.putNumber("Claw Gyro Value", Robot.wrist.getGyroVal());
		SmartDashboard.putNumber("Arm Angle", arm.getArmAngle());
		SmartDashboard.putString("Action for Auto", autoAction.toString());
//		SmartDashboard.putBoolean("Diagnostics are running?", enableDiagnostic);
		if (enableDiagnostic) {
			diagnostics();
		}
	}
}
