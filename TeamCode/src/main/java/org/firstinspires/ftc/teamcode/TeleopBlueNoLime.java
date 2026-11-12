package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name="TeleopBlue", group="Linear OpMode")
public class TeleopBlueNoLime extends LinearOpMode {
    //Electronic Variables
    //Extras

    //Motors
    private DcMotorEx Shooter;
    private DcMotorEx turret;
    private DcMotor intake;
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LBack;
    private DcMotor RBack;
    private DcMotor Transfer;

    @Override
    public void runOpMode() throws InterruptedException {
        //References to Electronics
        //Servos
        //Motors
        intake = hardwareMap.get(DcMotor.class, "intake");
        LFront = hardwareMap.get(DcMotor.class, "leftfront");
        RFront = hardwareMap.get(DcMotor.class, "rightfront");
        LBack = hardwareMap.get(DcMotor.class, "leftback");
        RBack = hardwareMap.get(DcMotor.class, "rightback");
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        Transfer = hardwareMap.get(DcMotor.class, "Transfer");
        //LimeLight3A

        //Variables
        //Booleans
        boolean shooterOn = true;
        boolean OnOffShooter = false;
        boolean ableToSwitchMode = true;
        boolean autoAiming = false;
        boolean ableToAim = true;
        boolean TurretLeft = false;
        boolean TurretRight = false;
        boolean TurretStop = true;
        //Modes
        String driveMode = "FAST";
        //Numbers
        double velocity = 1600;
        //Setup for Electronics
        //Motors
        LFront.setDirection(DcMotor.Direction.FORWARD);
        LBack.setDirection(DcMotor.Direction.FORWARD);
        RFront.setDirection(DcMotor.Direction.REVERSE);
        RBack.setDirection(DcMotor.Direction.REVERSE);
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        //PIDF Coefficients
        Shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(8.075, 0, 0, 14));
        //Do turret PIDF Later
//        turret.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients());
        Shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(8.075, 0, 0, 14));
        //Opmode rules
        while (opModeIsActive()) {
            ApplyInputToMotors(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, LFront, RFront, LBack, RBack);

            {
                //Manual Flywheel speed just in case ;)
                if (gamepad2.left_trigger != 0) {
                    velocity = 1000;
                } else {
                    velocity = 1600;
                }
                
                if (gamepad2.dpad_left) {
                    turret.setPower(-0.4);
                } else if (gamepad2.dpad_right) {
                    turret.setPower(0.4);
                } else {
                    turret.setPower(0);
                }


                //Driving


//            //Shooter On Off
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
                if (gamepad1.right_trigger > 0) {
                    intake.setPower(1);
                    Transfer.setPower(0.75);
                } else if (gamepad1.left_trigger > 0) {
                    intake.setPower(1);
                } else if (gamepad1.right_bumper) {
                    intake.setPower(-1);
                    Transfer.setPower(-0.75);
                } else if (gamepad1.left_bumper) {
                    intake.setPower(-1);
                } else {
                    intake.setPower(0);
                    Transfer.setPower(0);
                }

                //Change Drive Mode

                //Apply Powers
                //Applies power to the shooter
                if (shooterOn) {
                    Shooter.setVelocity(velocity);
                }

                //Turn off shooter

                //Applies power to the shooter
                if (shooterOn) {
                    Shooter.setVelocity(velocity);
                } else {//Turn off shooter
                    if (OnOffShooter) {
                        Shooter.setVelocity(0);
                        Shooter.setVelocity(-10);
                        sleep(400);
                        Shooter.setVelocity(0);
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
    }


    //Applies input to the Drive Motors
    public static void ApplyInputToMotors(double Ly, double Lx, double Rx, DcMotor LFront, DcMotor RFront, DcMotor LBack, DcMotor RBack) {
        // Deleted this line which was previously "double Rx, String;" 2 indents forward
        {
            //Inputs
//            if (driveMode.equals("SLOW")) {
//                Ly = Ly * .5;
//                Lx = Lx * .5;
//            }
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
    }

    //Turn turret right
    public static void TurretRight(DcMotorEx turret) {
        double power = .35;
        //Applying Power the turret
        turret.setPower(power);
    }

    //Turn turret left
    public static void TurretLeft(DcMotorEx turret) {
        double power = .35;
        //Applying Power the turret
        turret.setPower(-power);
    }

    //Stop turret
    public static void TurretStop(DcMotorEx turret) {
        //Applying Power the turret
        turret.setPower(0);
    }
}


