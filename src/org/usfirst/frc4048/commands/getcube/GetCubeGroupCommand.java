package org.usfirst.frc4048.commands.getcube;

import org.usfirst.frc4048.commands.GroupCommandCallback;
import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.MoveClaw;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.commands.intake.LowerIntake;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;
import org.usfirst.frc4048.subsystems.Claw;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GetCubeGroupCommand extends CommandGroup implements GroupCommandCallback {

	// TODO - Determine which commands can be done in parallel - Maybe MoveArm and
	// LowerIntake

	public GetCubeGroupCommand() {
		addSequential(new CancelIfCubeInClaw(this));
		addSequential(new MoveArm(this, ArmPositions.Intake));
		addSequential(new LowerIntake(this));
		// addSequential(new WaitForChildren());

		addSequential(new IntakeCube(this, IntakeCube.IntakeMode.STRAIGHT_PULL));
		addSequential(new OpenClaw(this));
		addSequential(new SetClawPosition(Claw.WristPostion.Level));
		//addSequential(new MoveClaw(this, Claw.WristPostion.Level));
		addSequential(new GrabCube(this));

		addSequential(new MoveArm(this, ArmPositions.Home));
		addSequential(new SetClawPosition(Claw.WristPostion.Compact));
		// addSequential(new MoveClaw(this, Claw.WristPostion.Compact));
		addSequential(new RaiseIntake(this));
	}

	@Override
	public void doCancel(final boolean isTimedOut) {
		if (isTimedOut) {
			cancel();
		}
	}

}
