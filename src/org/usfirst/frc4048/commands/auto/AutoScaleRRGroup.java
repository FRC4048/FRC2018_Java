package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.commands.CalculateSonarDistance;
import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.PrintCommand;
import org.usfirst.frc4048.commands.RotateAngle;
import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveArm;
//import org.usfirst.frc4048.commands.arm.MoveClawToLevel;
//import org.usfirst.frc4048.commands.arm.MoveClawToStraight;
import org.usfirst.frc4048.commands.arm.OpenClaw;
//import org.usfirst.frc4048.commands.intake.GripIntake;
//import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Drivetrain.SonarSide;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 */
public class AutoScaleRRGroup extends CommandGroup {

    public AutoScaleRRGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
		addSequential(new GrabCube());
//       	addSequential(new GripIntake(GripPosition.Open));
    	//MOVE HALF WAY
    	addParallel(new DriveDistance(AutoAction.DISTANCE_TO_MIDDLE_OF_SCALE/2, AutoAction.LOCAL_SCALE_SPEED,0,0));
    	addSequential(new MoveArm(ArmPositions.HighScale)); //TO	DO add this back
    	//WaitForChildren() waits for the parallel commands to finish
    	addSequential(new WaitForChildren());
    	
    	//ADJUST ANGLE AND DISTANCE FROM WALL
    	addSequential(new RotateAngle(0));
    	addSequential(new CalculateSonarDistance(SonarSide.RIGHT, AutoAction.DISTANCE_FROM_WALL_SCALE+10));
    	addSequential(new DriveDistance(0, 0, 0, 0));
    	
    	//MOVE FINAL DISTANCE
    	addSequential(new DriveDistance(119.0, AutoAction.LOCAL_SCALE_SPEED,0,0));
    	addSequential(new RotateAngle(0));
    	
    	//ADJUST ANGLE AND DISTANCE FROM WALL
    	addSequential(new RotateAngle(-45));
    	addSequential(new CalculateSonarDistance(SonarSide.RIGHT, AutoAction.DISTANCE_FROM_WALL_SWITCH+25));
    	addSequential(new DriveDistance(0, 0, 0, 0));
    	addSequential(new RotateAngle(-45));
    	
    	//ROTATE AND DROP CUBE (on scale?)
//    	addParallel(new DriveDistance(20, AutoAction.LOCAL_SCALE_SPEED,-AutoAction.LOCAL_SCALE_SPEED,0));
//    	addSequential(new MoveClawToStraight());
//    	addSequential(new WaitForChildren());
    	addSequential(new OpenClaw());
    	//WE WILL ADD THIS BACK LATER
    }
}
