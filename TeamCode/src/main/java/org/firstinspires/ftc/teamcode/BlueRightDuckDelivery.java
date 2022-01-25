package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Blue Right | Duck + Park")
public class BlueRightDuckDelivery extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();


        driveForwardsInMillimeters(153);
        turnRightInDegrees(90);
        driveForwardsInMillimeters(838);
        deliverDuck();
        strafeLeftInMillimeters(457);
    }
}

