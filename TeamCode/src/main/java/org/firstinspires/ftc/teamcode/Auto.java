package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Autonomous(name = "Auto", group = "LinearOpMode")
public class Auto extends LinearOpMode {
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

        LFront.setDirection(DcMotor.Direction.FORWARD);
        LBack.setDirection(DcMotor.Direction.FORWARD);
        RFront.setDirection(DcMotor.Direction.REVERSE);
        RBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
            LFront.setPower(0.5);
            LBack.setPower(0.5);
            RFront.setPower(0.5);
            RBack.setPower(0.5);
            sleep(200);
            LFront.setPower(0);
            LBack.setPower(0);
            RFront.setPower(0);
            RBack.setPower(0);
    }

    }
