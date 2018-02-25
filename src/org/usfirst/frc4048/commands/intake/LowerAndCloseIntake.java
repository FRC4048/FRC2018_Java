package org.usfirst.frc4048.commands.intake;

import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 */
public class LowerAndCloseIntake extends CommandGroup {

	public LowerAndCloseIntake()
	{
		this(GroupCommandCallback.NONE);
	}
	
    public LowerAndCloseIntake(GroupCommandCallback callback) {
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
    	
    	addParallel(new GripIntake(callback, GripPosition.Open));
    	addSequential(new LowerIntake(callback));
//    	addSequential(new WaitForChildren());
    }
}
