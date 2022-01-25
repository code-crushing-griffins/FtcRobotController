package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Blue Right | Freight + Duck + Park")
public class BlueRightFreightDeliveryDuck extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();

        driveForwardsInMillimeters(153);
        turnRightInDegrees(140);
        driveBackwardsInMillimeters(558);
        setLinearSlideState(ActuatorStates.HIGH);
        setDumperState2(DumpStates.DUMP);
        setDumperState2(DumpStates.NO_DUMP);
        driveForwardsInMillimeters(558);
        turnLeftInDegrees(45);
        driveForwardsInMillimeters(700); // 2 feet and some change
        deliverDuck();
        strafeLeftInMillimeters(381);
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive() && timer.milliseconds() < 1000) {
            telemetry.update();
        }

    }
}

