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
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.time.temporal.IsoFields;
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.commands.arm.ResetClawGyro;
import org.usfirst.frc4048.commands.arm.SetClawPosition;
import org.usfirst.frc4048.commands.auto.AutoAction;
import org.usfirst.frc4048.subsystems.*;
import org.usfirst.frc4048.subsystems.Drivetrain.SonarSide;
import org.usfirst.frc4048.subsystems.Claw.WristPostion;
import org.usfirst.frc4048.commands.auto.Action;
import org.usfirst.frc4048.swerve.math.*;
import org.usfirst.frc4048.utils.Logging;
import org.usfirst.frc4048.utils.WorkQueue;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Action> chooser = new SendableChooser<>();
   
    public static OI oi;
    public static Drivetrain drivetrain;
    public static Claw claw;
    public static Arm arm;
    public static Intake intake;
    public static Climber climber;
    public static Logging logging;
    public WorkQueue wq;

    //use this to see the debug commands and values for smart dashboard
    public Boolean enableDebug = false;
    Action autoAction;
    Action oldAutoAction;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    	
        RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drivetrain = new Drivetrain();
        claw = new Claw();
        arm = new Arm();
        intake = new Intake();
        climber = new Climber();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        
        WorkQueue wq = new WorkQueue(512);
        logging = new Logging(100, wq);
		logging.startThread(); //Starts the logger
		drivetrain.drivetrianHeadings();
		
        chooser.addDefault("Autorun(cross the base line)", Action.Baseline);
        chooser.addObject("Robot on LEFT, do SWITCH", Action.LSwitch);
        chooser.addObject("Robot on LEFT, do SCALE", Action.LScale);
        chooser.addObject("Robot on LEFT, do SWITCH if its left or SCALE if on the left", Action.LLocalSwitchPriority);
        chooser.addObject("Robot on LEFT, do SCALE if its left or SWITCH if on the left", Action.LLocalScalePriority);
        chooser.addObject("Robot on RIGHT, do SWITCH", Action.RSwitch);
        chooser.addObject("Robot on RIGHT, do SCALE", Action.RScale);
        chooser.addObject("Robot on RIGHT, do SWITCH if its right or SCALE if on the right", Action.RLocalSwitchPriority);
        chooser.addObject("Robot on RIGHT, do SCALE if its right or SWITCH if on the right", Action.RLocalScalePriority);
        chooser.addObject("Robot in MIDDLE, do SWITCH", Action.MiddleSwitch);
        chooser.addObject("Do Nothing", Action.Nothing);
        
        
        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putData("Move Claw to Compact", new SetClawPosition(WristPostion.Compact));
        SmartDashboard.putData("Move Claw to Level", new SetClawPosition(WristPostion.Level));
        SmartDashboard.putData("Reset Claw Gyro", new ResetClawGyro());
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
    	logging.traceMessage(Logging.MessageLevel.InfoMessage, "---------------------------- Robot Disabled ----------------------------");
		Robot.drivetrain.resetDriveEncoder();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();        
        
        autoAction = chooser.getSelected();
        if (autoAction != oldAutoAction)
        {
        	// every time the driver changes autonomous selection
        	logging.traceMessage(Logging.MessageLevel.InfoMessage,  "AutoAction user selection: " + autoAction.toString());
            oldAutoAction = autoAction;
        }
    	
    	dashboardData();
        //SmartDashboard.putNumber("JoyStick Left X", oi.getLeftJoystick().getX());
        //SmartDashboard.putNumber("JoyStick Left Y", oi.getLeftJoystick().getY());
        //SmartDashboard.putNumber("JoyStick Right X", oi.getRightJoystick().getX());
    }

    @Override
    public void autonomousInit() {
    	logging.traceMessage(Logging.MessageLevel.InfoMessage, "---------------------------- Autonomous mode starting ----------------------------");
    	Robot.drivetrain.swerveDrivetrain.setModeField();
        char switchPos = 'X';
        char scalePos = 'X';
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	logging.traceMessage(Logging.MessageLevel.InfoMessage,  "Field plate selection:" + gameData);

        if(gameData.length() < 2)
        {
        	DriverStation.reportError("Bad game specific data recieved " + gameData, false);
  
        }
        if(gameData.charAt(0) == 'R' || gameData.charAt(0) == 'L')
        {
        	switchPos = gameData.charAt(0);
        }
        if(gameData.charAt(1) == 'R' || gameData.charAt(1) == 'L')
        {
        	scalePos = gameData.charAt(1);
        }
    	System.out.println("Scale Pos = " + scalePos);
    	System.out.println("Switch Pos = " + switchPos);
    	
    	
    	Action autoAction = chooser.getSelected();
    	System.out.println("Action in Auto " + autoAction.toString());
    	System.out.println("Game Data: " + gameData);
    	autonomousCommand = new AutoAction(switchPos, scalePos, autoAction);
    	logging.traceMessage(Logging.MessageLevel.InfoMessage,  "Autonomous Command:" + autonomousCommand.getName());

    	
        // schedule the autonomous command (example)
    	
        if (autonomousCommand != null) autonomousCommand.start();
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
    	logging.traceMessage(Logging.MessageLevel.InfoMessage, "---------------------------- Teleop mode starting ----------------------------");

    	Robot.drivetrain.swerveDrivetrain.setModeField();
    	
    	autoAction = chooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.cancel();
        
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        dashboardData();
//        Robot.drivetrain.outputAbsEncValues();
    }
    
    @Override
    public void testPeriodic()
    {
    	Scheduler.getInstance().run();
    	
        dashboardData();
    }
    
    public void dashboardData() {
    	if(enableDebug) {
    		Robot.drivetrain.outputAbsEncValues();
    		Robot.arm.armData();
    		Robot.oi.dashboardButtons();
    		SmartDashboard.putNumber("Distance", RobotMap.swerveDriveEncoder.getDistance());
        	SmartDashboard.putNumber("Robot Gyro Value", Robot.drivetrain.getGyro());
        	SmartDashboard.putNumber("Left Sonar Distance", Robot.drivetrain.getSonar(SonarSide.LEFT));
            SmartDashboard.putNumber("Claw Gyro", Robot.claw.getGyroVal());
            SmartDashboard.putNumber("Pitch Motor Val", RobotMap.clawpitchMotor.getMotorOutputPercent());
        	//SmartDashboard.putNumber("Right Sonar Distance", Robot.drivetrain.getSonar(SonarSide.RIGHT));
    	}
    	SmartDashboard.putString("Action for Auto", autoAction.toString());
    	SmartDashboard.putNumber("Claw Gyro Value", Robot.claw.getGyroVal());
    }
 }
