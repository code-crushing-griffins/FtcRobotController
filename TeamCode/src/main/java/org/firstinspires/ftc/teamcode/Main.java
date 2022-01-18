package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main", group = "Linear Opmode")
public class Main extends Robot {

    boolean motorToggle = false;

    @Override
    public void runOpMode() {
        super.runOpMode();

        waitForStart();

        while (opModeIsActive()) {
            updateTelemetry();


            // Left side goes forward
            if (gamepad1.left_stick_y <= 0) {
                leftSideGoForwards(-gamepad1.left_stick_y);
            }

            // Right side go forwards
            if (gamepad1.right_stick_y <= 0) {
                rightSideGoForwards(-gamepad1.right_stick_y);
            }


            // left side goes backwards
            if (gamepad1.left_stick_y >= 0) {
                leftSideGoBackwards(gamepad1.left_stick_y);
            }

            // right side goes backwards
            if (gamepad1.right_stick_y >= 0) {
                rightSideGoBackwards(gamepad1.right_stick_y);
            }


            // strafe left
            if (gamepad1.left_trigger != 0) {
                strafeLeft(gamepad1.left_trigger);

            }

            // strafe right
            if (gamepad1.right_trigger != 0) {
                strafeRight(gamepad1.right_trigger);
            }

//            if (gamepad2.right_bumper) {
//                setLinearSlideState(ActuatorStates.HIGH);
//            }
//
//
//            if (gamepad2.y) {
//                setLinearSlideState(ActuatorStates.MIDDLE);
//            }
//
//
//            if (gamepad2.b) {
//                setLinearSlideState(ActuatorStates.ZERO);
//            }

            dumpToggle(gamepad2.a);

            intakeMotorToggle(gamepad2.x);

            setDuckRotator(gamepad2.right_bumper);


//            if (gamepad2.dpad_down) {
//                moveDumper(ActuatorStates.ZERO);
//            }
//            if (gamepad2.dpad_right) {
//                moveDumper(ActuatorStates.MIDDLE);
//            }
//            if (gamepad2.dpad_up) {
//                moveDumper(ActuatorStates.HIGH);
//            }

            if (gamepad2.dpad_up) {
                double p = freightDumperServo.getPosition();
                freightDumperServo.setPosition(p - 0.01);
            }

            if (gamepad2.dpad_down) {
                double p = freightDumperServo.getPosition();
                freightDumperServo.setPosition(p + 0.01);
            }

            if (gamepad2.left_bumper) {
                duckRotator.setDirection(duckRotator.getDirection().inverted());
            }


            linearSlideMotor.setPower(-gamepad2.left_stick_y);
            setCRServoPower(gamepad2.right_stick_y);

            duckRotator.setPower(gamepad2.left_trigger);

//            armDeliveryMotor.setPower(gamepad2.right_stick_y);

        }

    }
}


