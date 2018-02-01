// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4048;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix.sensors.PigeonIMU;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static WPI_TalonSRX drivetrainfrontLeftDriveMotor;
    public static WPI_TalonSRX drivetrainfrontLeftSteerMotor;
    public static WPI_TalonSRX drivetrainfrontRightDriveMotor;
    public static WPI_TalonSRX drivetrainfrontRightSteerMotor;
    public static WPI_TalonSRX drivetrainrearLeftDriveMotor;
    public static WPI_TalonSRX drivetrainrearLeftSteerMotor;
    public static WPI_TalonSRX drivetrainrearRightDriveMotor;
    public static WPI_TalonSRX drivetrainrearRightSteerMotor;
    public static PowerDistributionPanel drivetrainPowerDistributionPanel;
    public static WPI_TalonSRX clawgripMotor;
    public static WPI_TalonSRX clawpitchMotor;
    public static DigitalInput clawcubeSwitch;
    public static AnalogInput clawpressureSensor;
    public static SpeedController armextensionMotor;
    public static AnalogPotentiometer armrotationPot;
    public static WPI_TalonSRX armmovementMotor;
    public static AnalogPotentiometer armextensionPot;
    public static SpeedController intakeleftIntakeMotor;
    public static SpeedController intakerightIntakeMotor;
    public static DigitalInput intakecubeSwitch;
    public static SpeedController intakedeployMotor;
    public static DigitalInput intakeupperLimit;
    public static DigitalInput intakelowerLimit;
    public static WPI_TalonSRX climberclimbMotor;
    public static AnalogInput climbersonar;
    public static DigitalInput climberlatchSwitch;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static AnalogInput swerveDriveAnalogInputFrontRight;
    public static AnalogInput swerveDriveAnalogInputFrontLeft;
    public static AnalogInput swerveDriveAnalogInputRearLeft;
    public static AnalogInput swerveDriveAnalogInputRearRight;
    
    public static Encoder swerveDriveEncoder;
    
    public static double SWERVE_DRIVE_ENCODER_DISTANCE_PER_TICK = 0.0942478739;
    
    public static PigeonIMU swerveDrivePigeon1;
	
    @SuppressWarnings("deprecation")
	public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drivetrainfrontLeftDriveMotor = new WPI_TalonSRX(6);
        
        
        drivetrainfrontLeftSteerMotor = new WPI_TalonSRX(2);
        
        
        drivetrainfrontRightDriveMotor = new WPI_TalonSRX(5);
        
        
        drivetrainfrontRightSteerMotor = new WPI_TalonSRX(1);
        
        
        drivetrainrearLeftDriveMotor = new WPI_TalonSRX(7);
        
        
        drivetrainrearLeftSteerMotor = new WPI_TalonSRX(3);
        
        
        drivetrainrearRightDriveMotor = new WPI_TalonSRX(8);
        
        
        drivetrainrearRightSteerMotor = new WPI_TalonSRX(4);
        
        
        drivetrainPowerDistributionPanel = new PowerDistributionPanel(0);
        LiveWindow.addSensor("Drivetrain", "PowerDistributionPanel", drivetrainPowerDistributionPanel);
        
        clawgripMotor = new WPI_TalonSRX(12);
        
        
        clawpitchMotor = new WPI_TalonSRX(13);
        
        
        clawcubeSwitch = new DigitalInput(0);
        LiveWindow.addSensor("Claw", "cubeSwitch", clawcubeSwitch);
        
        clawpressureSensor = new AnalogInput(6);
        LiveWindow.addSensor("Claw", "pressureSensor", clawpressureSensor);
        
        armextensionMotor = new Spark(3);
        LiveWindow.addActuator("Arm", "extensionMotor", (Spark) armextensionMotor);
        armextensionMotor.setInverted(false);
        armrotationPot = new AnalogPotentiometer(4, 1.0, 0.0);
        LiveWindow.addSensor("Arm", "rotationPot", armrotationPot);
        
        armmovementMotor = new WPI_TalonSRX(11);
        
        
        armextensionPot = new AnalogPotentiometer(7, 1.0, 0.0);
        LiveWindow.addSensor("Arm", "extensionPot", armextensionPot);
        
        intakeleftIntakeMotor = new Talon(0);
        LiveWindow.addActuator("Intake", "leftIntakeMotor", (Talon) intakeleftIntakeMotor);
        intakeleftIntakeMotor.setInverted(false);
        intakerightIntakeMotor = new Talon(1);
        LiveWindow.addActuator("Intake", "rightIntakeMotor", (Talon) intakerightIntakeMotor);
        intakerightIntakeMotor.setInverted(false);
        intakecubeSwitch = new DigitalInput(7);
        
        LiveWindow.addSensor("Intake", "cubeSwitch", intakecubeSwitch);
        
        intakedeployMotor = new Spark(2);
        LiveWindow.addActuator("Intake", "deployMotor", (Spark) intakedeployMotor);
        intakedeployMotor.setInverted(false);
        intakeupperLimit = new DigitalInput(1);
        LiveWindow.addSensor("Intake", "upperLimit", intakeupperLimit);
        
        intakelowerLimit = new DigitalInput(2);
        LiveWindow.addSensor("Intake", "lowerLimit", intakelowerLimit);
        
        climberclimbMotor = new WPI_TalonSRX(10);
        
        
        climbersonar = new AnalogInput(5);
        LiveWindow.addSensor("Climber", "sonar", climbersonar);
        
        climberlatchSwitch = new DigitalInput(3);
        LiveWindow.addSensor("Climber", "latchSwitch", climberlatchSwitch);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    	
        swerveDriveAnalogInputFrontRight = new AnalogInput(0);
        
        swerveDriveAnalogInputFrontLeft = new AnalogInput(1);
        
        swerveDriveAnalogInputRearLeft = new AnalogInput(2);
        
        swerveDriveAnalogInputRearRight = new AnalogInput(3);
        
        swerveDriveEncoder = new Encoder(5, 6);
        
        swerveDrivePigeon1 = new PigeonIMU(9);
    }
}