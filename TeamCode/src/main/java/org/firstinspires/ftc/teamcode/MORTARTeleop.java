package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="MORTARTeleop", group="Linear OpMode")
public class MORTARTeleop extends LinearOpMode {
    //Electronic Variables
    //Extras
    private Limelight3A limelight;
    //Motors
    private DcMotorEx topMotor;
    private DcMotorEx bottomMotor;
    private DcMotorEx turret;
    private DcMotor intake;
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LBack;
    private DcMotor RBack;
    //Servos
    private Servo outtakeFeeder;

    @Override
    public void runOpMode() throws InterruptedException {
        //References to Electronics
        //Servos
        outtakeFeeder = hardwareMap.get(Servo.class, "feeder");
        //Motors
        topMotor = hardwareMap.get(DcMotorEx.class, "top");
        bottomMotor = hardwareMap.get(DcMotorEx.class, "bottom");
        intake = hardwareMap.get(DcMotor.class, "intake");
        LFront = hardwareMap.get(DcMotor.class, "leftfront");
        RFront = hardwareMap.get(DcMotor.class, "rightfront");
        LBack = hardwareMap.get(DcMotor.class, "leftback");
        RBack = hardwareMap.get(DcMotor.class, "rightback");
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        //LimeLght3A
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
        //Variables
        //Booleans
        boolean shooterOn = true;
        boolean OnOffShooter = false;
        boolean ableToSwitchMode = true;
        boolean autoAiming = false;
        boolean ableToAim = true;
        //Modes
        String driveMode = "FAST";
        //Numbers
        double velocity = 800;
        //Setup for Electronics
        //Motors
        LFront.setDirection(DcMotor.Direction.FORWARD);
        LBack.setDirection(DcMotor.Direction.FORWARD);
        RFront.setDirection(DcMotor.Direction.REVERSE);
        RBack.setDirection(DcMotor.Direction.REVERSE);
        topMotor.setDirection(DcMotor.Direction.REVERSE);
        topMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        //PIDF Coefficients
        topMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(8.075, 0, 0, 14));
        bottomMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(8.075, 0, 0, 14));
        //Get the flicker set
        outtakeFeeder.setPosition(0);
        while (opModeIsActive()) {
            //LimeLight3A Uses
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    //Goes through all the Tags
                    for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                        int tagId = tag.getFiducialId();
                        //Checks if the tag is not the Obelisk
                        if (tagId != 21 && tagId != 22 && tagId != 23) {
                            double x = result.getTx();//Tag X Position
                            double a = result.getTa();//Tag Area
                            autoAiming = true;
                            //Auto Aiming Code
                            if (autoAiming) {
                                if (x > 1.1) {
                                    TurnLeft(turret);
                                } else if (x < -1.1) {
                                    TurnRight(turret);
                                } else {
                                    Stop(turret);
                                    autoAiming = false;
                                }
                                if (!autoAiming) {
                                    if (gamepad1.dpad_left) {
                                        TurnLeft(turret);
                                    } else if (gamepad1.dpad_right) {
                                        TurnRight(turret);
                                    }
                                }
                            }
                            //Auto-Velocity Code
                            velocity = 1325 - ((195 * a) - 30);
                        }
                    }
                } else if (autoAiming) {
                    //Stops if there is no April Tag
                    Stop(turret);
                    autoAiming = false;
                } else {
                    //Manual Flywheel speed just in case ;)
                    if (gamepad2.left_trigger != 0) {
                        velocity = 830;
                    } else {
                        velocity = 635;
                    }
                }
            } else if (autoAiming) {
                //Stops if there is no April Tag
                Stop(turret);
                autoAiming = false;
            } else {
                //Manual Flywheel speed just in case ;)
                if (gamepad2.left_trigger != 0) {
                    velocity = 830;
                } else {
                    velocity = 635;
                }
            }
            //Driving
            if (!autoAiming) {
                ApplyInputToMotors(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, driveMode, LFront, RFront, LBack, RBack);
            }
            //Aiming
            if (ableToAim) {
                if (gamepad1.left_trigger != 0 && !autoAiming) {
                    ableToAim = false;

                    autoAiming = true;
                } else if (gamepad1.left_trigger != 0 && autoAiming) {
                    ableToAim = false;

                    autoAiming = false;
                }
            } else {
                if (gamepad1.left_trigger == 0) {
                    ableToAim = true;
                }
            }
            //Shooter On Off
            if (!OnOffShooter) {
                if (gamepad2.x) {
                    OnOffShooter = true;
                    shooterOn = !shooterOn;
                }
            } else {
                if (!gamepad2.x) {
                    OnOffShooter = false;
                }
            }
            //Intake
            if (gamepad1.right_bumper) {
                intake.setPower(-1);
            } else if (gamepad1.left_bumper) {
                intake.setPower(1);
            } else {
                intake.setPower(0);
            }

            //Change Drive Mode
            if (ableToSwitchMode) {
                if (gamepad1.a && driveMode.equals("FAST")) {
                    ableToSwitchMode = false;
                    driveMode = "SLOW";
                } else if (gamepad1.a && driveMode.equals("SLOW")) {
                    ableToSwitchMode = false;
                    driveMode = "FAST";
                }
            } else {
                if (!gamepad1.a) {
                    ableToSwitchMode = true;
                }
            }

            //Apply Powers
            //Applies power to the shooter
            if (shooterOn) {
                topMotor.setVelocity(velocity);
                bottomMotor.setVelocity(velocity);
            } else {//Turn off shooter
                if (OnOffShooter) {
                    topMotor.setVelocity(0);
                    bottomMotor.setVelocity(0);
                    topMotor.setVelocity(-10);
                    bottomMotor.setVelocity(-10);
                    sleep(400);
                    topMotor.setVelocity(0);
                    bottomMotor.setVelocity(0);
                }
            }


            //Telemetry
            //Shooter
            telemetry.addData("Velocity: ", velocity);
            telemetry.addData("Shooter On: ", shooterOn);
            //Drive Mode
            telemetry.addData("Drive Mode: ", driveMode);
            telemetry.update();
        }

    }

    //Applies input to the Drive Motors
    public static void ApplyInputToMotors(double Ly, double Lx, double Rx, String driveMode, DcMotor LFront, DcMotor RFront, DcMotor LBack, DcMotor RBack) {
        //Inputs
        if (driveMode.equals("SLOW")) {
            Ly = Ly * .5;
            Lx = Lx * .5;
        }
        //Computing Powers
        double LeftFrontWheel = Ly - Lx - Rx;
        double RightFrontWheel = Ly + Lx + Rx;
        double LeftBackWheel = Ly + Lx - Rx;
        double RightBackWheel = Ly - Lx + Rx;

        //Applying Power the Drive Train
        LFront.setPower(LeftFrontWheel);
        RFront.setPower(RightFrontWheel);
        LBack.setPower(LeftBackWheel);
        RBack.setPower(RightBackWheel);
    }

    //Turn turret right
    public static void TurnRight(DcMotorEx turret) {
        double power = .12;
        //Applying Power the turret
        turret.setPower(power);
    }

    //Turn turret left
    public static void TurnLeft(DcMotorEx turret) {
        double power = .12;
        //Applying Power the turret
        turret.setPower(-power);
    }

    //Stop turret
    public static void Stop(DcMotorEx turret) {
        //Applying Power the turret
        turret.setPower(0);
    }
}