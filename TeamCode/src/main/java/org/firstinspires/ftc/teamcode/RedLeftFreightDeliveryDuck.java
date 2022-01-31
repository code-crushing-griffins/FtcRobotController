package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Left | Freight + Duck + Park")
public class RedLeftFreightDeliveryDuck extends Robot {



    @Override
    public void runOpMode() {
        initializeDuckDetector();
        super.runOpMode();
        waitForStart();

        LinearSlideStates desiredState = detectDuck();

        int shippingHubBuffer = 0;
        int carouselBuffer = 0;
        if (desiredState == LinearSlideStates.MIDDLE) {
            shippingHubBuffer = 68;
            carouselBuffer = 40;
        }
        if (desiredState == LinearSlideStates.LOW) {
            shippingHubBuffer = 100;
            carouselBuffer = 45;
        }

        new Thread() {
            public void run() {
                setLinearSlideState(desiredState);
            }
        }.start();

        driveForwardsInMillimeters(153);
        turnLeftInDegrees(150);
        driveBackwardsInMillimeters(575 - shippingHubBuffer);
        setDumperState(DumpStates.DUMP);
        setDumperState(DumpStates.NO_DUMP);
        turnLeftInDegrees(70);
        strafeRightInMillimeters(845 - carouselBuffer);
        deliverDuck();
        turnLeftInDegrees(65);
        strafeLeftInMillimeters(475);
    }
}

