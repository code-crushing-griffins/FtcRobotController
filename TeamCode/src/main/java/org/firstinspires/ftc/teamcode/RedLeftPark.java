package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Left | Park")
public class RedLeftPark extends Robot {

    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();

        driveForwardsInMillimeters(305);
        turnRightInDegrees(60);
        driveForwardsInMillimeters(800);
    }
}

