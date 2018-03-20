package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.Robot;
import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.intake.AlwaysIntake;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class GetCubeIntake extends CommandGroup implements GroupCommandCallback {
//
//	// Also use addSequential(new WaitForChildren());
//	public GetCubeIntake() {
//		this(true, true);
//	}
	
	public GetCubeIntake(boolean part1, boolean part2) {
		if(part1) {
//			addSequential(new CancelIfCubeInClaw(this));
			addSequential(new MoveArm(this, ArmPositions.Intake));
			addSequential(new IntakeCube(this, IntakeCube.IntakeMode.STRAIGHT_PULL));		
		}
		
		if (part2) {
			addParallel(new AlwaysIntake());
			addSequential(new MoveArm(this, ArmPositions.Switch));
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
