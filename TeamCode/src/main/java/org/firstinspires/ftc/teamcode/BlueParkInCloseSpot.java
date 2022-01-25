package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Blue Park In Close Spot")
public class BlueParkInCloseSpot extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();


        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive() && timer.milliseconds() < 1000) {
            telemetry.update();
        }

//        }
    }
}

