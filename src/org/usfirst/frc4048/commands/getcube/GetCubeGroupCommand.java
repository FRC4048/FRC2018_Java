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
import org.usfirst.frc4048.commands.intake.IntakeMode;
import org.usfirst.frc4048.commands.intake.LowerAndCloseIntake;
import org.usfirst.frc4048.commands.intake.LowerIntake;
import org.usfirst.frc4048.commands.intake.RaiseAndOpenIntake;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Wrist.WristPostion;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class GetCubeGroupCommand extends CommandGroup implements GroupCommandCallback {

	// Also use addSequential(new WaitForChildren());
	public GetCubeGroupCommand() {
		this(true, true);
	}
	
	

	public GetCubeGroupCommand(boolean part1, boolean part2) {
		if (part1) {
			addSequential(new CancelIfCubeInClaw(this));
			
			addSequential(new LowerAndCloseIntake(this));
			
			addParallel(new MoveArm(this, ArmPositions.Exchange));
			addParallel(new MoveClawToLevel(this));
			addSequential(new IntakeCube(this, IntakeMode.STRAIGHT_PULL));
			addSequential(new WaitForChildren());
			
			addParallel(new OpenClaw(this));
			addSequential(new MoveArm(this, ArmPositions.Intake));
			addSequential(new WaitForChildren());
	    	addSequential(new ExtensionIntake(this));
			
			addParallel(new GripIntake(this, GripPosition.Open));
			addSequential(new ExtendArmToCube(this));
			addSequential(new WaitForChildren());
			
			addSequential(new GrabCube(this));
		}
		
		if (part2) {
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
