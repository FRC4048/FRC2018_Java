package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.arm.ExtendArmToCube;
import org.usfirst.frc4048.commands.arm.ExtensionIntake;
import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.MoveClawToLevel;
import org.usfirst.frc4048.commands.arm.MoveClawToStraight;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.arm.SetClawPosition;
import org.usfirst.frc4048.commands.intake.GripIntake;
import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.commands.intake.IntakeCubeWithoutSwitch;
import org.usfirst.frc4048.commands.intake.IntakeMode;
import org.usfirst.frc4048.commands.intake.LowerAndCloseIntake;
import org.usfirst.frc4048.commands.intake.LowerIntake;
import org.usfirst.frc4048.commands.intake.RaiseAndOpenIntake;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class GetCubeGroupCommandSplit extends CommandGroup implements GroupCommandCallback {

	// Also use addSequential(new WaitForChildren());
	public GetCubeGroupCommandSplit() {
		this(true, true);
	}
	
	

	public GetCubeGroupCommandSplit(boolean part1, boolean part2) {
		if (part1) {
			addSequential(new CancelIfCubeInClaw(this));
			
			addSequential(new LowerAndCloseIntake(this));
			
			//TODO Should we go to intake position instead of exchange?
			addParallel(new MoveArm(this, ArmPositions.Exchange));
			addParallel(new MoveClawToLevel(this));
			addSequential(new WaitForChildren());
			
			addParallel(new OpenClaw(this));
			addSequential(new MoveArm(this, ArmPositions.Intake));
			addSequential(new WaitForChildren());
	    	addSequential(new ExtensionIntake(this));
	    	
	    	addSequential(new IntakeCubeWithoutSwitch(this, IntakeMode.STRAIGHT_PULL));
		}
		
		if (part2) {
			addSequential(new MoveArm(this, ArmPositions.Intake));	//This is only a precaution in case part 2 ran without part 1
			addSequential(new ExtensionIntake(this));	//This is only a precaution in case part 2 ran without part 1
			
			addParallel(new IntakeCubeWithoutSwitch(this, IntakeMode.STRAIGHT_PULL));	//Restarting intake because it will have stopped in part 1
			
			addSequential(new ExtendArmToCube(this));
			
			addSequential(new GrabCube(this));
			addSequential(new GripIntake(this, GripPosition.Open));
			
			addSequential(new MoveArm(this, ArmPositions.Switch));
			
			if(Robot.USE_WRIST_STRAIGHT) {
				addParallel(new MoveClawToStraight(this));
			}
			addSequential(new RaiseAndOpenIntake(this));
			addSequential(new WaitForChildren());
		}
	}

	@Override
	public void doCancel(final boolean isTimedOut) {
		if (isTimedOut) {
			cancel();
		}
	}
	
	public String toString() {
		return this.getClass().getName();
	}

	@Override
	public boolean hasGroupBeenCanceled()
	{
		return isCanceled();
	}
}
