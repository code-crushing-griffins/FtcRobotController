package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Right | Freight")
public class RedRightFreightDelivery extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();


        driveForwardsInMillimeters(153);
        turnRightInDegrees(140);
        driveBackwardsInMillimeters(558);
        setLinearSlideState(LinearSlideStates.HIGH);
        setDumperState2(DumpStates.DUMP);
        setDumperState2(DumpStates.NO_DUMP);
    }
}

