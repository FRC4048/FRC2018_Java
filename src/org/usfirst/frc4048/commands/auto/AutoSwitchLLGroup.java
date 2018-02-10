package org.usfirst.frc4048.commands.auto;

import org.usfirst.frc4048.commands.BlankCommand;
import org.usfirst.frc4048.commands.DriveDistance;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.PrintCommand;
import org.usfirst.frc4048.commands.RotateAngle;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 */
public class AutoSwitchLLGroup extends CommandGroup {

    public AutoSwitchLLGroup() {
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
    	
    	addParallel(new DriveDistance(AutoAction.DISTANCE_TO_MIDDLE_OF_SWITCH, AutoAction.LOCAL_SWITCH_SPEED,0,0));
    	addSequential(new MoveArm(ArmPositions.Switch)); //TODO add this back
    	//addSequential(new PrintCommand());
    	//WaitForChildren() waits for the parallel commands to finish
    	addSequential(new WaitForChildren());
    	addSequential(new RotateAngle(90));
    	addSequential(new DriveDistance(5, 0,	AutoAction.LOCAL_SWITCH_SPEED,0));
    	//addSequential(new MoveClaw()); TODO fix this
    	addSequential(new OpenClaw());   
    	}
}