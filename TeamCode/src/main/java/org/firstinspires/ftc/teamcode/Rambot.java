package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name="RambotTeleOp", group="Linear OpMode")
public class Rambot extends LinearOpMode {
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LBack;
    private DcMotor RBack;

    @Override
    public void runOpMode() throws InterruptedException {
        LBack = hardwareMap.get(DcMotor.class, "LBack");
        LFront = hardwareMap.get(DcMotor.class, "LFront");
        RBack = hardwareMap.get(DcMotor.class, "RBack");
        RFront = hardwareMap.get(DcMotor.class, "RFront");
        waitForStart();
        LFront.setDirection(DcMotor.Direction.FORWARD);
        LBack.setDirection(DcMotor.Direction.FORWARD);
        RFront.setDirection(DcMotor.Direction.REVERSE);
        RBack.setDirection(DcMotor.Direction.REVERSE);
        ApplyInputToMotors(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, LFront, RFront, LBack, RBack);

    }
    public static void ApplyInputToMotors(double Ly, double Lx, double Rx, DcMotor LFront, DcMotor RFront, DcMotor LBack, DcMotor RBack)
    {
        //Inputs
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
