package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.MoveClawToLevel;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.intake.GripIntake;
import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 */
public class AutoSwitchMRGroup extends CommandGroup {

    public AutoSwitchMRGroup() {
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
    	
    	//DRIVE TO SWITCH AND RAISE ARM
    	addSequential(new GripIntake(GripPosition.Open));
    	addParallel(new DriveDistance(AutoAction.AUTO_RUN_DISTANCE+10, AutoAction.LOCAL_SWITCH_SPEED, 0,0));
    	addSequential(new MoveArm(ArmPositions.Switch)); //TODO add this back
    	addSequential(new WaitForChildren());
    	addSequential(new DriveDistance(10, 0, .2, 0));
    	//WaitForChildren() waits for the parallel commands to finish
    	addSequential(new MoveClawToLevel());
    	addSequential(new OpenClaw());
    }
}
