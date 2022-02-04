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

        int duckBuffer = 0;

        if (desiredState == LinearSlideStates.MIDDLE) {
            shippingHubBuffer = 68;
            duckBuffer = 35;
        }
        if (desiredState == LinearSlideStates.LOW) {
            shippingHubBuffer = 138;
            duckBuffer = 65;
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
        turnLeftInDegrees(55);
        driveForwardsInMillimeters(700 - duckBuffer); // drives towards the duck
        deliverDuck();
        strafeLeftInMillimeters(500);

    }
}

