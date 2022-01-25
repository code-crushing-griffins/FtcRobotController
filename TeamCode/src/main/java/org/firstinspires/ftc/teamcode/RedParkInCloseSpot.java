package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Park In Close Spot")
public class RedParkInCloseSpot extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();

        strafeRightInMillimeters(762);
        driveForwardsInMillimeters(800);


        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive() && timer.milliseconds() < 1000) {
            telemetry.update();
        }

    }
}

