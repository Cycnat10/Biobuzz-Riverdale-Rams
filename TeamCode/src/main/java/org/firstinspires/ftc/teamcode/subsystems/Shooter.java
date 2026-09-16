package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

/**
 * The Shooter subsystem is responsible for controlling the robot's shooter mechanism.
 * It uses a single motor to launch items.
 */
public class Shooter implements Subsystem {
    /**
     * Singleton instance of the Shooter subsystem.
     */
    public static final Shooter INSTANCE = new Shooter();
    /**
     * Runnable to set the shooter to high speed.
     */
    public Runnable highSpeed;
    /**
     * Runnable to set the shooter to normal speed.
     */
    public Runnable normalSpeed;
    /**
     * Runnable to set the shooter to low speed.
     */
    public Runnable lowSpeed;

    /**
     * Private constructor to prevent instantiation outside of the singleton.
     */
    private Shooter() {}

    /**
     * The motor for the shooter system.
     */
    private MotorEx shooter_motor = new MotorEx("motorS");
    /**
     * Command to run the shooter motor forward.
     */
    public Command forward = new SetPower(shooter_motor, 0.7).requires(this);
    /**
     * Command to run the shooter motor in reverse.
     */
    public Command reverse = new SetPower(shooter_motor, -0.7).requires(this);
    /**
     * Command to stop the shooter motor.
     */
    public Command stop = new SetPower(shooter_motor, 0.0).requires(this);

}
