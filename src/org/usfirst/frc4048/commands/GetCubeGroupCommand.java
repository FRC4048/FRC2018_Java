package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.commands.intake.LowerIntake;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitForChildren;

import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveClaw;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.arm.SetClawPosition;
import org.usfirst.frc4048.subsystems.Claw;

//TODO Is this command still being used?
public class GetCubeGroupCommand extends CommandGroup implements GroupCommandCallback {

	public GetCubeGroupCommand() {
		addParallel(new MoveArm(this, ArmPositions.Intake));
		addParallel(new LowerIntake(this));
		addSequential(new WaitForChildren());

		addSequential(new IntakeCube(this, IntakeCube.IntakeMode.STRAIGHT_PULL));
		addSequential(new OpenClaw(this));
		addSequential(new SetClawPosition(Claw.WristPostion.Level));
		addSequential(new GrabCube(this));

		addSequential(new MoveArm(this, ArmPositions.Home));
		addSequential(new SetClawPosition(Claw.WristPostion.Compact));
		addParallel(new RaiseIntake(this));
	}

	@Override
	public void doCancel(final boolean isTimedOut) {
		if (isTimedOut) {
			Scheduler.getInstance().removeAll();
		}
	}

}
