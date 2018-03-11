//package org.usfirst.frc4048.commands.intake;
//
//import org.usfirst.frc4048.Robot;
//import org.usfirst.frc4048.commands.LoggedCommand;
//import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// *
// */
//public class ToggleIntakeGrip extends LoggedCommand {
//	
//	private static boolean toggle = false;
//	
//	Command closeIntake;
//	Command openIntake;
//
//	public ToggleIntakeGrip() {
//		
//		super("Command is running");
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//		requires(Robot.intake);
//	}
//
//    // Called just before this Command runs the first time
//    protected void loggedInitialize() {
//    	closeIntake = new GripIntake(GripPosition.Close);
//    	openIntake = new GripIntake(GripPosition.Open);
//    	
//    	if(toggle) {
//    		closeIntake.start();
//    		toggle = false;
//    	}
//    	else {
//    		openIntake.start();
//    		toggle = true;
//    	}
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void loggedExecute() {
//    	
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean loggedIsFinished() {
//        return true;
//    }
//
//    // Called once after isFinished returns true
//    protected void loggedEnd() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void loggedInterrupted() {
//    }
//
//	@Override
//	protected void loggedCancel() {
//		// TODO Auto-generated method stub
//		
//	}
//}
