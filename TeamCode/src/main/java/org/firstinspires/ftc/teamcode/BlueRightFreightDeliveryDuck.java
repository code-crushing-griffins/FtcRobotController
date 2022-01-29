package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Blue Right | Freight + Duck + Park")
public class BlueRightFreightDeliveryDuck extends Robot {



    @Override
    public void runOpMode() {
        initializeDuckDetector();
        super.runOpMode();
        waitForStart();

        LinearSlideStates desiredState = detectDuck();
        // for the middle and bottom positions we need extra space
        // to deliver the freight duh? like bruh can you understand stuff?
        int shippingHubBuffer = 0;
        if (desiredState == LinearSlideStates.MIDDLE) {
            shippingHubBuffer = 68;
        }
        if (desiredState == LinearSlideStates.LOW) {
            shippingHubBuffer = 100;
        }

        new Thread() {
            @Override
            public void run() {
                setLinearSlideState(desiredState);
            }
        }.start();

        driveForwardsInMillimeters(153);
        turnRightInDegrees(150);
        driveBackwardsInMillimeters(558 - shippingHubBuffer);
        setDumperState(DumpStates.DUMP);
        setDumperState(DumpStates.NO_DUMP);
        driveForwardsInMillimeters(558);
        turnLeftInDegrees(50);
        driveForwardsInMillimeters(700); // 2 feet and some change
        deliverDuck();
        strafeLeftInMillimeters(500);

    }
}

