package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class Robot extends LinearOpMode {
    DcMotor frontLeftMotor; // 0
    DcMotor frontRightMotor; // 1
    DcMotor backLeftMotor; // 2
    DcMotor backRightMotor; // 3
    DcMotor armDeliveryMotor;

    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        armDeliveryMotor =  hardwareMap.get(DcMotor.class, "armDeliveryMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

    }


    void leftSideGoForwards(double power) {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(power);
    }

    void rightSideGoForwards(double power) {
        frontRightMotor.setPower(power);
        backRightMotor.setPower(-power);
    }

    void leftSideGoBackwards(double power) {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
    }

    void rightSideGoBackwards(double power) {
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
    }

    void strafeLeft(double power) {
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(-power);
    }

    void strafeRight(double power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    void stopMotors() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    void updateTelemetry() {
        telemetry.addData("frontLeftMotor", this.frontLeftMotor.getPower());
        telemetry.addData("backLeftMotor", this.backLeftMotor.getPower());
        telemetry.addData("frontRightMotor", this.frontRightMotor.getPower());
        telemetry.addData("backRightMotor", this.backRightMotor.getPower());
        telemetry.update();
    }



    // position corresponds to the freight target thing
    // it can be either 1 2 or 3
    void moveFreightArmToTarget(int postion) {
        if (armDeliveryMotor.isBusy()) {
            return;
        }

        armDeliveryMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        final double COUNTS_PER_MOTOR_REV = 4.0;
        final double DRIVE_GEAR_REDUCTION = 72.1;
        final double WHEEL_CIRCUMFERENCE_MM = 90 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;

        int temporaryPosition = (int) COUNTS_PER_MM * 20;

        armDeliveryMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armDeliveryMotor.setTargetPosition(temporaryPosition);

        armDeliveryMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armDeliveryMotor.setPower(0.2);

        while (opModeIsActive() && armDeliveryMotor.isBusy()) {

        }
    }
}
