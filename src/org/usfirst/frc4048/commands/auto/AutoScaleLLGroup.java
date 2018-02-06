package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.PrintCommand;
import org.usfirst.frc4048.commands.RotateAngle;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.commands.auto.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScaleLLGroup extends CommandGroup {

    public AutoScaleLLGroup() {
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
    	
    	addParallel(new DriveDistance(AutoAction.LOCAL_SCALE_DISTANCE, AutoAction.LOCAL_SCALE_SPEED,0,0));
    	//addSequential(new MoveArm(ArmPositions.HighScale)); //TODO add this back
    	addSequential(new PrintCommand());
    	addSequential(new RotateAngle(90));
    	addSequential(new DriveDistance(5, AutoAction.LOCAL_SCALE_SPEED,0,0));
    	//Use addSequential to drop the cube
    }
}
