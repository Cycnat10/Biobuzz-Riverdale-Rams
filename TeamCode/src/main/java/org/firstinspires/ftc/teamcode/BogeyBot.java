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
public class BogeyBot extends LinearOpMode {

    //Motors
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LBack;
    private DcMotor RBack;

    @Override
    public void runOpMode() throws InterruptedException {

        //Motors
        LFront = hardwareMap.get(DcMotor.class, "leftfront");
        RFront = hardwareMap.get(DcMotor.class, "rightfront");
        LBack = hardwareMap.get(DcMotor.class, "leftback");
        RBack = hardwareMap.get(DcMotor.class, "rightback");

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

        waitForStart();


        while (opModeIsActive()) {
            ApplyInputToMotors(gamepad1.left_stick_y, gamepad1.left_stick_x,gamepad1.right_stick_x,LFront,RFront,LBack,RBack);

            if (gamepad2.left_trigger != 0) {
                velocity = 1000;
            } else {
                velocity = 1600;
            }

        }
    }


    //Applies input to the Drive Motors
    public static void ApplyInputToMotors(double Ly, double Lx, double Rx, DcMotor LFront, DcMotor RFront, DcMotor LBack, DcMotor RBack) {
        // Deleted this line which was previously "double Rx, String;" 2 indents forward
        {

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
}
