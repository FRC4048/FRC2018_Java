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

import org.usfirst.frc4048.commands.AutonomousCommand;
import org.usfirst.frc4048.commands.BlankCommand;
import org.usfirst.frc4048.commands.CancelCommand;
import org.usfirst.frc4048.commands.Drive;
import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.ReconfigEncoders;
import org.usfirst.frc4048.commands.RotateAngle;
import org.usfirst.frc4048.commands.ToggleMode;
import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.arm.SetClawPosition;
import org.usfirst.frc4048.commands.arm.SetClawPositionAndWait;
import org.usfirst.frc4048.commands.auto.AutoAction;
import org.usfirst.frc4048.commands.getcube.GetCubeGroupCommand;
import org.usfirst.frc4048.commands.getcube.GetCubeGroupCommandOrig;
import org.usfirst.frc4048.commands.getcube.GetCubeGroupCommandTest;
import org.usfirst.frc4048.commands.getcube.GetCubeGroupCommandTestSplit;
import org.usfirst.frc4048.commands.intake.FlushCube;
import org.usfirst.frc4048.commands.intake.GripIntake;
import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.commands.intake.IntakeCube.IntakeMode;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.commands.intake.ToggleIntake;
import org.usfirst.frc4048.commands.intake.ToggleIntakeGrip;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import org.usfirst.frc4048.subsystems.Wrist.WristPostion;


import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick leftJoystick;
    public JoystickButton toggleIntake;
    public JoystickButton intakeFlush;
    public JoystickButton ditchCube;
    public JoystickButton toggleMode;
    public Joystick rightJoystick;
    public JoystickButton releaseCube;
    public JoystickButton grabCube;
    public JoystickButton moveToSwitch;
    public JoystickButton moveToLowScale;
    public JoystickButton moveToHighScale;
    public JoystickButton moveToExchange;
//    public JoystickButton moveToClimb;
    public JoystickButton toggleGripIntake;
    public JoystickButton openIntake;
    public JoystickButton cancelFunction;
    public JoystickButton intakeCube;
    public Joystick controller;
    public JoystickButton raiseIntake;
    public JoystickButton setClawPositionandWait;
    public final XboxTriggerRight xboxTriggerRight;
    public final XboxTriggerLeft xboxTriggerLeft;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public XboxController xboxController;
    public boolean rightTriggerPrevPressed = false;
    
    public JoystickButton reconfigEncoders;
    public JoystickButton overrideButton;

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        controller = new Joystick(2);
        
		cancelFunction = new JoystickButton(controller, 7); // Back Button
        cancelFunction.whileHeld(new CancelCommand());
//		 moveToClimb = new JoystickButton(controller, 8); // Start Button
//		 moveToClimb.whenPressed(new MoveArm(ArmPositions.Climb));
        
		moveToExchange = new JoystickButton(controller, 1); // X Button
        moveToExchange.whenPressed(new MoveArm(ArmPositions.Exchange));
        
//        moveToHighScale = new JoystickButton(controller, 4); // Y Button
//        moveToHighScale.whenPressed(new MoveArm(ArmPositions.Switch));
//		moveToLowScale = new JoystickButton(controller, 2); // B Button
//        moveToLowScale.whenPressed(new MoveArm(ArmPositions.Switch));
		moveToHighScale = new JoystickButton(controller, 4); // Y Button
        moveToHighScale.whenPressed(new MoveArm(ArmPositions.HighScale));
		moveToLowScale = new JoystickButton(controller, 2); // B Button
        moveToLowScale.whenPressed(new MoveArm(ArmPositions.LowScale));
        
        moveToSwitch = new JoystickButton(controller, 3); // A Button
        moveToSwitch.whenPressed(new MoveArm(ArmPositions.Switch));
		grabCube = new JoystickButton(controller, 6); // Right Bumper
        grabCube.whenPressed(new GrabCube());
        releaseCube = new JoystickButton(controller, 5);	//Left Bumper
        releaseCube.whenPressed(new OpenClaw());
        rightJoystick = new Joystick(1);
        
        toggleMode = new JoystickButton(rightJoystick, 10);
        toggleMode.whenPressed(new ToggleMode());
        ditchCube = new JoystickButton(rightJoystick, 9);
        ditchCube.whenPressed(new BlankCommand());
        intakeFlush = new JoystickButton(rightJoystick, 8);
        intakeFlush.whileHeld(new FlushCube());
        toggleIntake = new JoystickButton(rightJoystick, 7);
        toggleIntake.whenPressed(new ToggleIntake());
//        toggleMode = new JoystickButton(rightJoystick, 9);
//        toggleMode.whenPressed(new ToggleMode());
//        ditchCube = new JoystickButton(rightJoystick, 8);
//        ditchCube.whenPressed(new BlankCommand());
//        intakeFlush = new JoystickButton(rightJoystick, 7);
//        intakeFlush.whileHeld(new FlushCube());
//        toggleIntake = new JoystickButton(rightJoystick, 6);
//        toggleIntake.whenPressed(new ToggleIntake());
        leftJoystick = new Joystick(0);
        
   
        intakeCube=new JoystickButton(leftJoystick, 9);
        intakeCube.whenPressed(new IntakeCube((IntakeCube.IntakeMode.STRAIGHT_PULL)));
        setClawPositionandWait= new JoystickButton(leftJoystick, 10);
        setClawPositionandWait.whenPressed(new SetClawPositionAndWait(WristPostion.Level));
        toggleGripIntake = new JoystickButton(leftJoystick, 11);
        toggleGripIntake.whenPressed(new ToggleIntakeGrip());

        
        
        
        


        // SmartDashboard Buttons
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        reconfigEncoders = new JoystickButton(leftJoystick, 11);
        reconfigEncoders.whenPressed(new ReconfigEncoders());
        
        xboxController = new XboxController(2);
        
        xboxTriggerRight = new XboxTriggerRight(xboxController);
        // Use this trigger for the straight intake for testing only.
        // xboxTriggerRight.whenActive(new IntakeCube(IntakeMode.STRAIGHT_PULL));
        xboxTriggerRight.whenActive(new GetCubeGroupCommandTest()); //maybe change this
		//xboxTriggerRight.whenActive(new GetCubeGroupCommand()); 
        xboxTriggerLeft = new XboxTriggerLeft(xboxController);
        xboxTriggerLeft.whenActive(new IntakeCube(IntakeMode.TOGGLE_PULL_LEFT_OR_RIGHT));
        
        overrideButton = new JoystickButton(leftJoystick, 7);
    }
   
    public void dashboardButtons() {
    	SmartDashboard.putData("Drive Forward", new DriveDistance(20, 0.2, 0.0, 0.0));
        SmartDashboard.putData("Rotate 90", new RotateAngle(90));
        SmartDashboard.putData("AutoAction", new AutoAction());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }

    public Joystick getController() {
        return controller;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    
	public XboxController getXboxController() {
    	return xboxController;
    }
    
	public boolean cancelPressed() {
    	return cancelFunction.get();
    }
    
	public boolean getLeftstickUp() {
    	return xboxController.getY(Hand.kLeft) <= -0.75;
    }
    
	public boolean getLeftstickDown() {
    	return xboxController.getY(Hand.kLeft) >= 0.75;
    }
	
	public boolean getGetCubeOverride()
	{
		return overrideButton.get();
	}
	
	public boolean getUpDPAD()
	{
		if(xboxController.getPOV() <= 15 &&
			xboxController.getPOV() >= 345) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getDownDPAD()
	{
		if(xboxController.getPOV() >= 195 &&
			xboxController.getPOV() <= 165) {
			return true;
		} else {
			return false;
		}
	}
}

