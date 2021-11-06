package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main", group = "Linear Opmode")
public class Main extends Robot {
    @Override
    public void runOpMode() {
        super.runOpMode();

        waitForStart();

        while (opModeIsActive()) {
            updateTelemetry();


            // Left side goes forward
            if (gamepad1.left_stick_y <= 0) {
                leftSideGoForwards(gamepad1.left_stick_y);
            }

            // Right side go forwards
            if (gamepad1.right_stick_y <= 0) {
                rightSideGoForwards(gamepad1.right_stick_y);
            }


            // left side goes backwards
            if (gamepad1.left_stick_y >= 0) {
                leftSideGoBackwards(-gamepad1.left_stick_y);
            }

            // right side goes backwards
            if (gamepad1.right_stick_y >= 0) {
                rightSideGoBackwards(-gamepad1.right_stick_y);
            }


            // strafe left
            if (gamepad1.left_trigger != 0) {
                strafeLeft(gamepad1.left_trigger);

            }

            // strafe right
            if (gamepad1.right_trigger != 0) {
                strafeRight(gamepad1.right_trigger);
            }
        }
    }

}
