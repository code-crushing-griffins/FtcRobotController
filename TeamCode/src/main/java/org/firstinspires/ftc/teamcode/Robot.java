package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Robot extends LinearOpMode {
    DcMotorEx frontLeftMotor; // 0
    DcMotorEx frontRightMotor; // 1
    DcMotorEx backLeftMotor; // 2
    DcMotorEx backRightMotor; // 3
    DcMotorEx dumperMotor;
    DcMotor intakeMotor;
    DcMotorEx linearSlideMotor;
    DcMotor duckRotator;

    public enum ActuatorStates {
        ZERO,
        MIDDLE,
        HIGH
    }

    public enum DumpStates {
        NO_DUMP,
        DUMP
    }

    private final double DRIVE_TRAIN_COUNTS_PER_MOTOR_REV = 28.0;
    private final double DRIVE_TRAIN_GEAR_REDUCTION = 26.9;
    private final double DRIVE_TRAIN_WHEEL_CIRCUMFERENCE_MM = 75 * Math.PI;

    private final double DRIVE_TRAIN_COUNTS_PER_WHEEL_REV = DRIVE_TRAIN_COUNTS_PER_MOTOR_REV * DRIVE_TRAIN_GEAR_REDUCTION;
    private final double DRIVE_TRAIN_COUNTS_PER_MM = DRIVE_TRAIN_COUNTS_PER_WHEEL_REV / DRIVE_TRAIN_WHEEL_CIRCUMFERENCE_MM;

    private  ActuatorStates slideState = ActuatorStates.ZERO;
    private DumpStates dumperState = DumpStates.NO_DUMP;

    private boolean duckToggle = true;
    private boolean intakeToggle = true;
    private boolean dumpToggle = true;


    @Override
    public void runOpMode() {
        frontLeftMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "backRightMotor");
        dumperMotor =  (DcMotorEx) hardwareMap.get(DcMotor.class, "freightDumperMotor");
        intakeMotor =  hardwareMap.get(DcMotor.class, "intakeMotor");
        linearSlideMotor =  (DcMotorEx) hardwareMap.get(DcMotor.class, "linearSlideMotor");
        duckRotator = hardwareMap.get(DcMotor.class, "duckRotatorMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

//        dumperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    void strafeRight(double power) {
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(-power);
    }

    void stopMotors() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);//
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

    void intakeMotorToggle(boolean button) {
        if (intakeToggle && button) {
            intakeToggle = false;
            if (intakeMotor.getPower() == 0) {
                intakeMotor.setPower(1);
            } else {
                intakeMotor.setPower(0);
            }
        }

        if (!button) {
            intakeToggle = true;
        }
    }


    void updateTelemetry() {
        telemetry.addData("frontLeftMotor", this.frontLeftMotor.getPower());
        telemetry.addData("backLeftMotor", this.backLeftMotor.getPower());
        telemetry.addData("frontRightMotor", this.frontRightMotor.getPower());
        telemetry.addData("backRightMotor", this.backRightMotor.getPower());
        telemetry.addData("slide state", slideState);
//        telemetry.addData("dumper state", dumperState);
        telemetry.addData("linear slide motor busy?", linearSlideMotor.isBusy());
        telemetry.addData("linear slide motor velocity", linearSlideMotor.getVelocity());
        telemetry.addData("linear slide position", linearSlideMotor.getCurrentPosition());
        telemetry.addData("frontLeftMotor busy", frontLeftMotor.isBusy());
        telemetry.addData("frontRightMotor busy", frontLeftMotor.isBusy());
        telemetry.addData("backLeftMotor busy", backLeftMotor.isBusy());
        telemetry.addData("backRightMotor busy", backRightMotor.isBusy());
        telemetry.addData("duckRotator power", duckRotator.getPower());
        telemetry.update();
    }

    private void doNothing(int ms) {
        ElapsedTime timer = new ElapsedTime();
        while(opModeIsActive() && timer.milliseconds() < ms) {
            updateTelemetry();
        }
    }

    public void strafeLeftInMillimeters(int mm) {
        strafeInMillimeters(mm);

    }

    public void strafeRightInMillimeters(int mm) {
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

    void resetEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    boolean motorsAreBusy() {
        return frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy() || backRightMotor.isBusy();
    }

    void setTargetPosition(int target) {
        frontLeftMotor.setTargetPosition(target);
        frontRightMotor.setTargetPosition(target);
        backLeftMotor.setTargetPosition(target);
        backRightMotor.setTargetPosition(target);
    }

    void setRunToPosition() {
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void strafeInMillimeters(int mm) {
        resetEncoders();

        int target = (int) (DRIVE_TRAIN_COUNTS_PER_MM * mm);

        setTargetPosition(target);

        setRunToPosition();

        frontLeftMotor.setVelocity(1000);
        frontRightMotor.setVelocity(1000);
        backLeftMotor.setVelocity(1000);
        backRightMotor.setVelocity(1000);

        while(opModeIsActive() && motorsAreBusy()) {
            updateTelemetry();
        }
        doNothing(250);
        resetEncoders();
    }

    private void turnInDegrees(double degrees) {
        resetEncoders();
        // trial and error to find the number of rotations to get to 90 degrees
        // A constant can be derived from this measurement to approximate the number of
        // rotations for a turn of arbitrary degrees.
//        double rotationScaler = (7.0/300.0) * degrees;
//        double rotationScaler = (1.0/45.0) * degrees;
        double rotationScaler = (41.0/1800.0) * degrees;
        int target = (int) (DRIVE_TRAIN_COUNTS_PER_WHEEL_REV * rotationScaler); // 90 degrees
        setTargetPosition(target);
        setRunToPosition();

        frontLeftMotor.setVelocity(600);
        frontRightMotor.setVelocity(600);
        backLeftMotor.setVelocity(600);
        backRightMotor.setVelocity(600);
        while(opModeIsActive() && frontLeftMotor.isBusy()) {
            updateTelemetry();
        }
        resetEncoders();
        doNothing(250);
    }

    public void turnLeftInDegrees(double degrees) {
        backLeftMotor.setDirection(Direction.FORWARD);
        backRightMotor.setDirection(Direction.FORWARD);


        turnInDegrees(degrees);
        resetMotorDirection();
    }

    void turnRightInDegrees(double degrees) {
        frontLeftMotor.setDirection(Direction.REVERSE);
        frontRightMotor.setDirection(Direction.REVERSE);
        turnInDegrees(degrees);
        resetMotorDirection();
    }

    private void driveStraightInMillimeters(int mm) {
        resetEncoders();

        int target = (int) (DRIVE_TRAIN_COUNTS_PER_WHEEL_REV); // 90 degrees
        setTargetPosition(target);
        setRunToPosition();

        frontLeftMotor.setVelocity(600);
        frontRightMotor.setVelocity(600);
        backLeftMotor.setVelocity(600);
        backRightMotor.setVelocity(600);
        while(opModeIsActive() && frontLeftMotor.isBusy()) {
            updateTelemetry();
        }
        resetEncoders();
        doNothing(250);
    }

    public void driveForwardsInMillimeters(int mm) {
        frontLeftMotor.setDirection(Direction.REVERSE);
        backRightMotor.setDirection(Direction.FORWARD);
        driveStraightInMillimeters(mm);
        resetMotorDirection();
    }







    void deliverDuck() {
        ElapsedTime timer = new ElapsedTime();
        int initialRotationTime = 3000;
        duckRotator.setPower(.5);

        while (opModeIsActive() && timer.milliseconds() <= initialRotationTime) {
            updateTelemetry();
        }

        timer.reset();
        int rotationTime2 = 1000;
        duckRotator.setPower(1);

        while (opModeIsActive() && timer.milliseconds() <= rotationTime2) {
            updateTelemetry();
        }

        duckRotator.setPower(0);
    }









    void goStraightInMilliseconds(int ms) {
        ElapsedTime timer = new ElapsedTime();
        leftSideGoForwards(1.0);
        rightSideGoForwards(1.0);
        while(opModeIsActive() && timer.milliseconds() < ms) {
            updateTelemetry();
        }
        stopMotors();
    }

    void goBackwardsInMilliseconds(int ms) {
        ElapsedTime timer = new ElapsedTime();
        leftSideGoForwards(-1.0);
        rightSideGoForwards(-


                1.0);
        while(opModeIsActive() && timer.milliseconds() < ms) {
            updateTelemetry();
        }
        stopMotors();
    }


    public void setLinearSlideState(ActuatorStates desiredState) {

        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int desiredMMToMove = 0;
        ActuatorStates pendingState = ActuatorStates.ZERO;


        // There are three possible states that the slide can be in
        // There are two possible transitions that can happen in each state
        switch (slideState) {
            case ZERO:
                if (desiredState == ActuatorStates.ZERO) {
                    return;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    desiredMMToMove = 200;
                    pendingState = ActuatorStates.MIDDLE;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    desiredMMToMove = 400;
                    pendingState = ActuatorStates.HIGH;
                }
                break;
            case MIDDLE:
                if (desiredState == ActuatorStates.ZERO) {
                    desiredMMToMove = 200;
                    linearSlideMotor.setDirection(Direction.REVERSE);
                    pendingState = ActuatorStates.ZERO;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    return;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    desiredMMToMove = 200;
                    pendingState = ActuatorStates.HIGH;
                }
                break;
            case HIGH:
                if (desiredState == ActuatorStates.ZERO) {
                    desiredMMToMove = 400;
                    linearSlideMotor.setDirection(Direction.REVERSE);
                    pendingState = ActuatorStates.ZERO;
                }
                if (desiredState == ActuatorStates.MIDDLE) {
                    desiredMMToMove = 200;
                    linearSlideMotor.setDirection(Direction.REVERSE);
                    pendingState = ActuatorStates.MIDDLE;
                }
                if (desiredState == ActuatorStates.HIGH) {
                    return;
                }
                break;
            default:
                return;
        }


        final double COUNTS_PER_MOTOR_REV = 4.0;
        final double DRIVE_GEAR_REDUCTION = 72.0;
        final double WHEEL_CIRCUMFERENCE_MM = 48 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;


        int position = (int) COUNTS_PER_MM * desiredMMToMove;


        linearSlideMotor.setTargetPosition(position);


        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        linearSlideMotor.setVelocity(200);
        while(opModeIsActive() && linearSlideMotor.isBusy()) {
            updateTelemetry();
        }
        this.slideState = pendingState;
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // always set the motor to the default direction so that our
        // state transition logic always works as intended
        linearSlideMotor.setDirection(Direction.FORWARD);
    }


    public void setDumperState(DumpStates desiredState) {

        dumperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int desiredMMToMove = 0;
        // pendingState was added as a lock to prevent concurrency issues when using buttons
        // this function will most likely just be used for autonomous so
        // we can likely remove them after testing
        DumpStates pendingState = DumpStates.NO_DUMP;

        if (desiredState == DumpStates.DUMP) {
            if(dumperState == DumpStates.NO_DUMP) {
                desiredMMToMove = 3;
                pendingState = DumpStates.DUMP;
            } else {
                // Already in the state that we want. Return and do nothing
                return;
            }
        }

        if (desiredState == DumpStates.NO_DUMP) {
            if(dumperState == DumpStates.DUMP) {
                dumperMotor.setDirection(Direction.REVERSE);
                desiredMMToMove = 3;
            } else {
                // Already in the state that we want. Return and do nothing
                return;
            }
        }

        final double COUNTS_PER_MOTOR_REV = 4.0;
        final double DRIVE_GEAR_REDUCTION = 72.0; // TODO: is this right?
        final double WHEEL_CIRCUMFERENCE_MM =  5 * Math.PI;

        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;


        int position = (int) COUNTS_PER_MM * desiredMMToMove;

        dumperMotor.setTargetPosition(position);

        dumperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dumperMotor.setVelocity(200);
        while(opModeIsActive() && dumperMotor.isBusy()) {
            updateTelemetry();
        }
        this.dumperState = pendingState;
        dumperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // always set the motor to the default direction so that our
        // state transition logic always works as intended
        dumperMotor.setDirection(Direction.FORWARD);

    }
}