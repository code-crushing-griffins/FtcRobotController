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
        int shippingHubBuffer = 0;


        if (desiredState == LinearSlideStates.MIDDLE) {
            shippingHubBuffer = 68;
        }
        if (desiredState == LinearSlideStates.LOW) {
            shippingHubBuffer = 138;
        }





        new Thread() {
            @Override
            public void run() {
                setLinearSlideState(desiredState);
            }
        }.start();

        driveForwardsInMillimeters(153);
        turnRightInDegrees(155);
        driveBackwardsInMillimeters(660 - shippingHubBuffer);
        setDumperState(DumpStates.DUMP);
        setDumperState(DumpStates.NO_DUMP);



        if (desiredState == LinearSlideStates.HIGH) {
            driveForwardsInMillimeters(50);
        }
        if (desiredState == LinearSlideStates.MIDDLE) {
            driveBackwardsInMillimeters(18);
        }
        if (desiredState == LinearSlideStates.LOW) {
            driveBackwardsInMillimeters(88);
        }

        turnLeftInDegrees(125);
        strafeRightInMillimeters(890); // drives towards the duck
        deliverDuck();
        turnRightInDegrees(75);
        strafeLeftInMillimeters(550);

//        turnLeftInDegrees(70);
//        strafeRightInMillimeters(845 - carouselBuffer);
//        deliverDuck();
//        turnLeftInDegrees(65);
//        strafeLeftInMillimeters(475);



//        driveForwardsInMillimeters(558);
//        turnLeftInDegrees(50);
//        driveForwardsInMillimeters(700 - duckBuffer); // drives towards the duck
//        deliverDuck();
//        strafeLeftInMillimeters(450);

    }
}

