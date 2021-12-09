package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoImpl;

public abstract class Robot extends LinearOpMode {
    DcMotor frontLeftMotor; // 0
    DcMotor frontRightMotor; // 1
    DcMotor backLeftMotor; // 2
    DcMotor backRightMotor; // 3
    DcMotor armDeliveryMotor;
    DcMotor intakeMotor;
    DcMotor beltDriveMotor;
    DcMotor duckRotatorMotor;

    private int desiredMMToMove = 0;





    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        armDeliveryMotor =  hardwareMap.get(DcMotor.class, "armDeliveryMotor");
        intakeMotor =  hardwareMap.get(DcMotor.class, "intakeMotor");
        beltDriveMotor =  hardwareMap.get(DcMotor.class, "beltDriveMotor");
        duckRotatorMotor = hardwareMap.get(DcMotor.class, "duckRotatorMotor");

        armDeliveryMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



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
        telemetry.addData("linear slide position", this.armDeliveryMotor.getTargetPosition());
        telemetry.addData("desired mm", this.desiredMMToMove);
        telemetry.update();
    }



    void intakeMotorMove (double power) {
        intakeMotor.setPower(power);
    }



    void beltDriveMove (double power) {
        beltDriveMotor.setPower(power);

    }


    void duckRotatorMotorMove (double power) {
        duckRotatorMotor.setPower(power);

    }






    void armDeliveryMotorMove (double power) {
        armDeliveryMotor.setPower(power);}



    // position corresponds to the freight target thing
    // it can be either 0 1 2 or 3
    void moveFreightArmToTarget(int postion) {
        if (armDeliveryMotor.isBusy()) {
            return;
        }

        // first invert the direction
        DcMotorSimple.Direction direction = armDeliveryMotor.getDirection();


        if (postion == 3) {
            armDeliveryMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // do something with desiredMMToMove
            if (desiredMMToMove == 0) {
                desiredMMToMove = 225;
            }
            if (desiredMMToMove == 100) {
                desiredMMToMove = 225 - 100;
            }
            if (desiredMMToMove == 225) {
                return;
            }
        }


        if (postion == 2) {
            armDeliveryMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // do something with desiredMMToMove

            if (desiredMMToMove == 0) {
                desiredMMToMove = 100;
            }
            if (desiredMMToMove == 100) {
                return;
            }
            if (desiredMMToMove == 225) {
                return;
            }
        }


        if (postion == 1) {
            // get the current targetPosition
            int currentTarget = armDeliveryMotor.getTargetPosition();

            // if we are already at the 0 position we don't want to do anything
            if (currentTarget == 0) {
                return;
            }

            armDeliveryMotor.setDirection(direction.inverted());

            // desiredMMToMove is a global variable so we assume
            // that it is already at the correct value
        }


        armDeliveryMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // core hex motor
        final double COUNTS_PER_MOTOR_REV = 4.0;
        final double DRIVE_GEAR_REDUCTION = 72.1;
        final double WHEEL_CIRCUMFERENCE_MM = 35 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;


        int position = (int) COUNTS_PER_MM * desiredMMToMove;


        armDeliveryMotor.setTargetPosition(position);

        armDeliveryMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//its about drive it its about power
        //we stay hungry we devour put in the work put in the hours and take whats ours

        armDeliveryMotor.setPower(0.5);



        while (opModeIsActive() && armDeliveryMotor.isBusy()) {
            updateTelemetry();
        }
        // always make sure that the motor is going the correct direction
        // when moving the motor back to the default position we change direction
        armDeliveryMotor.setDirection(direction);
    }

    void strafeRightInMillimeters(int mm) {
        strafeInMillimeters(mm);

    }

    void strafeLeftInMillimeters(int mm) {
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        strafeInMillimeters(mm);
        // we want to make sure when drivers take control that they are not inverse
        // so we always reset the motor direction when we strafe left
        resetMotorDirection();
    }

    void resetMotorDirection() {
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    void strafeInMillimeters(int mm) {

        final double COUNTS_PER_MOTOR_REV = 28.0;
        final double DRIVE_GEAR_REDUCTION = 40.1;
        final double WHEEL_CIRCUMFERENCE_MM = 75 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int target = (int) COUNTS_PER_MM * mm;

        frontLeftMotor.setTargetPosition(target);
        frontRightMotor.setTargetPosition(target);
        backLeftMotor.setTargetPosition(target);
        backRightMotor.setTargetPosition(target);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftMotor.setPower(0.8);
        frontRightMotor.setPower(0.8);
        backLeftMotor.setPower(0.8);
        backRightMotor.setPower(0.8);

    }
}
