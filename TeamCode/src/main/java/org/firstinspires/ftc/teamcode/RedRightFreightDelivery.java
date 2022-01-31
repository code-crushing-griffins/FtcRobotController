package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Right | Freight | Park")
public class RedRightFreightDelivery extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        initializeDuckDetector();
        waitForStart();

        LinearSlideStates desiredState = detectDuck();

        int shippingHubBuffer = 0;
        if (desiredState == LinearSlideStates.MIDDLE) {
            shippingHubBuffer = 68;
        }
        if (desiredState == LinearSlideStates.LOW) {
            shippingHubBuffer = 100;
        }

        new Thread() {
            public void run() {
                setLinearSlideState(desiredState);
            }
        }.start();

        driveForwardsInMillimeters(153);
        turnRightInDegrees(150);
        driveBackwardsInMillimeters(558 - shippingHubBuffer);
        setLinearSlideState(desiredState);
        setDumperState(DumpStates.DUMP);
        setDumperState(DumpStates.NO_DUMP);
        driveForwardsInMillimeters(100);
        turnLeftInDegrees(55);
        strafeRightInMillimeters(650);
        driveForwardsInMillimeters(1100);
    }
}

