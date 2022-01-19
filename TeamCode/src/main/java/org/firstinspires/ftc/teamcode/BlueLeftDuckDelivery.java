package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="BlueLeftDuckDelivery")
public class BlueLeftDuckDelivery extends Robot {



    @Override
    public void runOpMode() {
        super.runOpMode();
        waitForStart();

        // psuedocode
        // 1. drive forwards to get off the wall
        // 2. strafe right 2 feet
        // 3. turn left 90 degrees
        // 4. strafe right ~ 2 feet
        // 5. turn left 90 degrees
        // 6. dump freight

          driveForwardsInMillimeters(200);
            strafeRightInMillimeters(610);
            turnLeftInDegrees(90);
            strafeRightInMillimeters(610);
            turnLeftInDegrees(90);
            setLinearSlideState(ActuatorStates.HIGH);
            freightDumperServo.setPosition(0.64);
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive() && timer.milliseconds() < 1000) {
            telemetry.update();
        }

//        }
        }
    }


