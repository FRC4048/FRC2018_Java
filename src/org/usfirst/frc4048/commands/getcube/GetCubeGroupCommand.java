package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.arm.ExtendArmToCube;
import org.usfirst.frc4048.commands.arm.ExtensionIntake;
import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.MoveClawToLevel;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.intake.GripIntake;
import org.usfirst.frc4048.commands.intake.GripIntake.GripPosition;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.commands.intake.LowerIntake;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GetCubeGroupCommand extends CommandGroup implements GroupCommandCallback {

	// TODO - Determine which commands can be done in parallel - Maybe MoveArm and
	// LowerIntake.
	// Also use addSequential(new WaitForChildren());
	public GetCubeGroupCommand() {
		this(true, true);
	}
	
	

	public GetCubeGroupCommand(boolean part1, boolean part2) {
		if (part1) {
		addSequential(new CancelIfCubeInClaw(this));
		
		addSequential(new GripIntake(this, GripPosition.Close));
		addSequential(new LowerIntake(this));
		addSequential(new MoveArm(this, ArmPositions.Exchange));
		addSequential(new MoveClawToLevel(this));
		
		addSequential(new IntakeCube(this, IntakeCube.IntakeMode.STRAIGHT_PULL));
		
		addSequential(new OpenClaw(this));
		addSequential(new MoveArm(this, ArmPositions.Intake));
    	addSequential(new ExtensionIntake(this));
		
		addSequential(new GripIntake(this, GripPosition.Open));
		
		addSequential(new ExtendArmToCube(this));
		addSequential(new GrabCube(this));
		}
		
		if (part2) {
		addSequential(new MoveArm(this, ArmPositions.Switch));
		addSequential(new GripIntake(this, GripPosition.Open));
		addSequential(new RaiseIntake(this));
		}
	}

	@Override
	public void doCancel(final boolean isTimedOut) {
		if (isTimedOut) {
			new Exception("Hello there!").printStackTrace(System.out);
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
