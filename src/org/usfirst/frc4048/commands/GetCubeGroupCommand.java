package org.usfirst.frc4048.commands;

import org.usfirst.frc4048.commands.arm.GrabCube;
import org.usfirst.frc4048.commands.arm.MoveArm;
import org.usfirst.frc4048.commands.arm.MoveClaw;
import org.usfirst.frc4048.commands.arm.OpenClaw;
import org.usfirst.frc4048.commands.intake.IntakeCube;
import org.usfirst.frc4048.commands.intake.LowerIntake;
import org.usfirst.frc4048.commands.intake.RaiseIntake;
import org.usfirst.frc4048.subsystems.Claw;
import org.usfirst.frc4048.subsystems.Arm.ArmPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

public class GetCubeGroupCommand extends CommandGroup implements GroupCommandNotifier {

    public GetCubeGroupCommand() {
    	final GroupCommandNotifier n = this;
    	addParallel(new MoveArm(n, ArmPositions.Intake));
    	addParallel(new LowerIntake(n));
    	
    	addSequential(new IntakeCube(n));
    	addSequential(new OpenClaw(n));
    	addSequential(new MoveClaw(n, Claw.WristPostion.Level));
    	addSequential(new GrabCube(n));
    	
    	addSequential(new MoveArm(n, ArmPositions.Home));
    	addSequential(new MoveClaw(n, Claw.WristPostion.Compact));
    	addParallel(new RaiseIntake(n, ));
    	}

	@Override
	public void stop() {
		Scheduler.getInstance().removeAll();
	}

}
