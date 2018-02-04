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
import org.usfirst.frc4048.commands.*;
import org.usfirst.frc4048.commands.auto.AutoAction;
import org.usfirst.frc4048.subsystems.*;
import org.usfirst.frc4048.commands.auto.Action;
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
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drivetrain drivetrain;
    public static Claw claw;
    public static Arm arm;
    public static Intake intake;
    public static Climber climber;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
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
        
        chooser.addDefault("BaseLine", Action.B);
        chooser.addObject("Scale Robot Right", Action.CR);
        chooser.addObject("Scale Robot Left", Action.CL);
        chooser.addObject("Switch Robot Right", Action.WR);
        chooser.addObject("Switch Robot Left", Action.WL);
        chooser.addObject("Local Robot Right", Action.LR);
        chooser.addObject("Local Robot Left", Action.LL);
        chooser.addObject("Do Nothing", Action.N);
        
        
        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
        // TODO - MOve to subsystem
		RobotMap.swerveDriveEncoder.reset();
		RobotMap.swerveDriveEncoder.setDistancePerPulse(RobotMap.SWERVE_DRIVE_ENCODER_DISTANCE_PER_TICK);

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
//        SmartDashboard.putString("DB/String 1", "" + Robot.drivetrain.analogInput1.getValue());
        
        SmartDashboard.putNumber("Distance", RobotMap.swerveDriveEncoder.getDistance());
        Robot.drivetrain.outputAbsEncValues();
        SmartDashboard.putNumber("JoyStick Left X", oi.getLeftJoystick().getX());
        SmartDashboard.putNumber("JoyStick Left Y", oi.getLeftJoystick().getY());
        SmartDashboard.putNumber("JoyStick Right X", oi.getRightJoystick().getX());
    }

    @Override
    public void autonomousInit() {
        char switchPos = 'X';
        char scalePos = 'X';
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
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
    	autonomousCommand = new AutoAction(switchPos, scalePos, autoAction);
    	
        // schedule the autonomous command (example)
    	
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
    	Scheduler.getInstance().run();
        Robot.drivetrain.outputAbsEncValues();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        SmartDashboard.putNumber("Distance", RobotMap.swerveDriveEncoder.getDistance());
        SmartDashboard.putNumber("Gyro Angle", Robot.drivetrain.getGyro());
        Robot.drivetrain.outputAbsEncValues();
    }
    
    @Override
    public void testPeriodic()
    {
        Robot.drivetrain.outputAbsEncValues();
    }
}
