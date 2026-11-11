package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class BogeyBot extends LinearOpMode {
    private DcMotor LFront;
    private DcMotor RFront;
    private DcMotor LBack;
    private DcMotor RBack;

    LFront = hardwareMap.get(DcMotor .class, "leftfront");
    RFront = hardwareMap.get(DcMotor.class, "rightfront");
    LBack = hardwareMap.get(DcMotor.class, "leftback");
    RBack = hardwareMap.get(DcMotor.class, "rightback");




}
