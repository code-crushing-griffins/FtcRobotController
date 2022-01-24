package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Maaiiinn", group = "Linear Opmode")
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


            intakeMotorToggle(gamepad2.x);

            setDuckRotator(gamepad2.right_bumper);

            duckRotator.setPower(gamepad2.right_trigger);


            linearSlideMotor.setPower(-gamepad2.left_stick_y);


            dumperMotor.setPower(gamepad2.right_stick_y * 0.4);

            if (gamepad2.y) {
                setDumperState(DumpStates.DUMP);
            }

            if (gamepad2.b) {
                setDumperState(DumpStates.NO_DUMP);
            }

        }

    }
}

