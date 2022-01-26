package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Blue Left | Freight")
public class BlueLeftFreightDelivery extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();

        LinearSlideStates desiredState = detectDuck();
        // for the middle and bottom positions we need extra space
        // to deliver the freight
        int shippingHubBuffer = 0;
        if (desiredState == LinearSlideStates.MIDDLE) {
            shippingHubBuffer = 85;
        }
        if (desiredState == LinearSlideStates.LOW) {
            shippingHubBuffer = 120;
        }

        driveForwardsInMillimeters(153);
        turnLeftInDegrees(140);
        driveBackwardsInMillimeters(600 - shippingHubBuffer);
        setLinearSlideState(desiredState);
        setDumperState2(DumpStates.DUMP);
        setDumperState2(DumpStates.NO_DUMP);
    }
}

