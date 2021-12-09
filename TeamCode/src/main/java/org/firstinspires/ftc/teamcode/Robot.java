package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;

public abstract class Robot extends LinearOpMode {
    DcMotor frontLeftMotor; // 0
    DcMotor frontRightMotor; // 1
    DcMotor backLeftMotor; // 2
    DcMotor backRightMotor; // 3
    DcMotorEx armDeliveryMotor;
    DcMotor intakeMotor;
    DcMotorEx beltDriveMotor;
    DcMotor duckRotator;

    public enum ActuatorStates {
        ZERO,
        MIDDLE,
        HIGH
    }

    private ActuatorStates  slideState = ActuatorStates.ZERO;
    private  ActuatorStates dumperState = ActuatorStates.ZERO;

    private boolean duckToggle = true;




    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        armDeliveryMotor =  (DcMotorEx) hardwareMap.get(DcMotor.class, "armDeliveryMotor");
        intakeMotor =  hardwareMap.get(DcMotor.class, "intakeMotor");
        beltDriveMotor =  (DcMotorEx) hardwareMap.get(DcMotor.class, "beltDriveMotor");
        duckRotator = hardwareMap.get(DcMotor.class, "duckRotatorMotor");





        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        armDeliveryMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        beltDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

    void setDuckRotator(boolean button) {
        if (duckToggle && button) {
            duckToggle = false;
            if (duckRotator.getPower() == 0) {
                duckRotator.setPower(1);
            } else {
                duckRotator.setPower(0);
            }
        }

        if (!button) {
            duckToggle = true;
        }
    }

    void updateTelemetry() {
        telemetry.addData("frontLeftMotor", this.frontLeftMotor.getPower());
        telemetry.addData("backLeftMotor", this.backLeftMotor.getPower());
        telemetry.addData("frontRightMotor", this.frontRightMotor.getPower());
        telemetry.addData("backRightMotor", this.backRightMotor.getPower());
        telemetry.addData("slide state", slideState);
        telemetry.addData("dumper state", dumperState);
        telemetry.addData("slide motor busy?", armDeliveryMotor.isBusy());
        telemetry.addData("dumper motor busy?", beltDriveMotor.isBusy());
        telemetry.addData("slide motor velocity", armDeliveryMotor.getVelocity());
        telemetry.addData("dumper motor velocity", beltDriveMotor.getVelocity());
        telemetry.addData("dumper position", beltDriveMotor.getCurrentPosition());
        telemetry.addData("slide position", armDeliveryMotor.getCurrentPosition());
        telemetry.update();
    }



    void moveFreightArmToTarget(ActuatorStates desiredState) {

        armDeliveryMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int desiredMMToMove = 0;


        // There are three possible states that the slide can be in
        // There are two possible transitions that can happen in each state
        switch (slideState) {
            case ZERO:
                if (desiredState == ActuatorStates.ZERO) {
                    return;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    desiredMMToMove = 100;
                    this.slideState = ActuatorStates.MIDDLE;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    desiredMMToMove = 225;
                    this.slideState = ActuatorStates.HIGH;
                }
                break;
            case MIDDLE:
                if (desiredState == ActuatorStates.ZERO) {
                    desiredMMToMove = 100;
                    armDeliveryMotor.setDirection(Direction.REVERSE);
                    this.slideState = ActuatorStates.ZERO;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    return;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    desiredMMToMove = 125;
                    this.slideState = ActuatorStates.HIGH;
                }
                break;
            case HIGH:
                if (desiredState == ActuatorStates.ZERO) {
                    desiredMMToMove = 225;
                    armDeliveryMotor.setDirection(Direction.REVERSE);
                    this.slideState = ActuatorStates.ZERO;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    desiredMMToMove = 125;
                    armDeliveryMotor.setDirection(Direction.REVERSE);
                    this.slideState = ActuatorStates.MIDDLE;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    return;
                }
                break;
            default:
                return;
        }


        final double COUNTS_PER_MOTOR_REV = 4.0;
        final double DRIVE_GEAR_REDUCTION = 72.1;
        final double WHEEL_CIRCUMFERENCE_MM = 35 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;


        int position = (int) COUNTS_PER_MM * desiredMMToMove;


        armDeliveryMotor.setTargetPosition(position);


        armDeliveryMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armDeliveryMotor.setVelocity(250);

        // always set the motor to the default direction so that our
        // state transition logic always works as intended
        armDeliveryMotor.setDirection(Direction.FORWARD);
    }

    // basically copy and paste of the linear slide code
    void moveDumper(ActuatorStates desiredState) {

        beltDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int desiredMMToMove = 0;


        // There are three possible states that the slide can be in
        // There are two possible transitions that can happen in each state
        switch (dumperState) {
            case ZERO:
                if (desiredState == ActuatorStates.ZERO) {
                    return;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    desiredMMToMove = 100;
                    this.dumperState = ActuatorStates.MIDDLE;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    desiredMMToMove = 200;
                    this.dumperState = ActuatorStates.HIGH;
                }
                break;
            case MIDDLE:
                if (desiredState == ActuatorStates.ZERO) {
                    desiredMMToMove = 100;
                    beltDriveMotor.setDirection(Direction.REVERSE);
                    this.dumperState = ActuatorStates.ZERO;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    return;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    desiredMMToMove = 100;
                    this.dumperState = ActuatorStates.HIGH;
                }
                break;
            case HIGH:
                if (desiredState == ActuatorStates.ZERO) {
                    desiredMMToMove = 200;
                    beltDriveMotor.setDirection(Direction.REVERSE);
                    this.dumperState = ActuatorStates.ZERO;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    desiredMMToMove = 100;
                    beltDriveMotor.setDirection(Direction.REVERSE);
                    this.dumperState = ActuatorStates.MIDDLE;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    return;
                }
                break;
            default:
                return;
        }


        final double COUNTS_PER_MOTOR_REV = 4.0;
        final double DRIVE_GEAR_REDUCTION = 72.1;
        final double WHEEL_CIRCUMFERENCE_MM = 32 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;


        int position = (int) COUNTS_PER_MM * desiredMMToMove;


        beltDriveMotor.setTargetPosition(position);


        beltDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        beltDriveMotor.setVelocity(250);

        // always set the motor to the default direction so that our
        // state transition logic always works as intended
        beltDriveMotor.setDirection(Direction.FORWARD);
    }
}
