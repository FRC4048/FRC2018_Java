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

public class GetCubeGroupCommand extends CommandGroup {

    public GetCubeGroupCommand() {
    	addParallel(new MoveArm(ArmPositions.Intake));
    	addParallel(new LowerIntake());
    	
    	addSequential(new IntakeCube());
    	addSequential(new OpenClaw());
    	addSequential(new MoveClaw(Claw.WristPostion.Level));
    	addSequential(new GrabCube());
    	
    	addSequential(new MoveArm(ArmPositions.Home));
    	addSequential(new MoveClaw(Claw.WristPostion.Compact));
    	addParallel(new RaiseIntake());
    	}

}
