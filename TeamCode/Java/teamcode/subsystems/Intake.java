package org.firstinspires.ftc.teamcode.subsystems;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.conditionals.IfElseCommand;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;
import kotlin.time.Instant;

/**
 * The Intake subsystem is responsible for controlling the robot's intake mechanism.
 * It uses a single motor to spin and collect items.
 */
public class Intake implements Subsystem {
    /**
     * Singleton instance of the Intake subsystem.
     */
    public static final Intake INSTANCE = new Intake();
    /**
     * Command to set the intake to spin in the reverse direction.
     */
    public Command toggleReverse = new InstantCommand(() -> INSTANCE.setReversed(false));
    /**
     * Command to set the intake to spin in the forward direction.
     */
    public Command toggleForwards = new InstantCommand(() -> INSTANCE.setReversed(true));
    /**
     * Private constructor to prevent instantiation outside of the singleton.
     */
    private Intake() {}
    /**
     * The motor for the intake system.
     */
    private MotorEx intake_motor = new MotorEx("motorI");
    /**
     * The default speed for the intake motor.
     */
    private double speed = 0.7;
    /**
     * Flag to determine the direction of the intake motor.
     * true for forwards, false for reverse.
     */
    private boolean reversed = false;
    /**
     * Command to spin the intake motor. The direction is determined by the 'reversed' flag.
     */
    public Command spin = new IfElseCommand(
            () -> reversed,
            new SetPower(intake_motor, speed).requires(this),
            new SetPower(intake_motor, -speed).requires(this)
    );

    /**
     * Sets the direction of the intake.
     * @param value true for forward, false for reverse.
     */
    public void setReversed(Boolean value){
        reversed = value;
    }
    /**
     * Command to stop the intake motor.
     */
    public Command stop = new SetPower(intake_motor, 0.0).requires(this);
}
 /*
intake - r bumper,
servo that shoots - left bumper

configuration
- intake is all
  */
