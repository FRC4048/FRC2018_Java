package org.usfirst.frc4048.commands.arm;

import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ExtensionArmToHome extends CommandGroup implements GroupCommandCallback {

	/**
	 * Stores the current callback being used.
	 * Can either be the command itself acting as a callback, or a separate command
	 */
	private final GroupCommandCallback realCallback;
	
	public ExtensionArmToHome()
	{
		this.realCallback = this;
		addSequential(new ExtensionHome(this));
    	addSequential(new MoveArm(this, ArmPositions.Home));
	}
	
    public ExtensionArmToHome(GroupCommandCallback callback) {
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
    	
    	this.realCallback = callback;
    	addSequential(new ExtensionHome(this));
    	addSequential(new MoveArm(this, ArmPositions.Home));
    }
    
    @Override
	public void doCancel(final boolean flag) {
    	boolean isCurrentCommand = this.realCallback == this;
    	if(isCurrentCommand)
    	{		
			if (flag) {
				cancel();
			}
    	} else {
    		if(flag) {
    			realCallback.doCancel(flag);
    		}
    	}
	}
}
