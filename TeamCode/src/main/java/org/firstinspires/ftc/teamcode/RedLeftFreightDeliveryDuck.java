package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Left | Freight + Duck + Park")
public class RedLeftFreightDeliveryDuck extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();

        driveForwardsInMillimeters(153);
        turnLeftInDegrees(140);
        driveBackwardsInMillimeters(558);
        setLinearSlideState(LinearSlideStates.HIGH);
        setDumperState2(DumpStates.DUMP);
        setDumperState2(DumpStates.NO_DUMP);
        driveForwardsInMillimeters(558);
        turnRightInDegrees(45);
        driveForwardsInMillimeters(700); // 2 feet and some change
        deliverDuck();
        strafeRightInMillimeters(381);
    }
}

