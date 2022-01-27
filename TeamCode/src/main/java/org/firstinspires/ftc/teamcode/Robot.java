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

    public enum LinearSlideStates {
        ZERO,
        LOW,
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

    private  LinearSlideStates slideState = LinearSlideStates.ZERO;
    private DumpStates dumperState = DumpStates.NO_DUMP;

    private boolean duckToggle = true;
    private boolean intakeToggle = true;
    private boolean dumpToggle = true;

    private DuckDetector duckDetector;


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

        duckRotator.setDirection(Direction.REVERSE);

        dumperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.duckDetector = new DuckDetector(this);

    }

    public LinearSlideStates detectDuck() {
        return this.duckDetector.detect();
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

    void intakeMotorToggleReverse(boolean button) {
        if (intakeToggle && button) {
            intakeToggle = false;
            if (intakeMotor.getPower() == 0) {
                intakeMotor.setPower(-1);
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
        telemetry.addData("dumper power", dumperMotor.getPower());
        telemetry.addData("dumper position", dumperMotor.getCurrentPosition());
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

        frontLeftMotor.setVelocity(1250);
        frontRightMotor.setVelocity(1250);
        backLeftMotor.setVelocity(1250);
        backRightMotor.setVelocity(1250);

        while(opModeIsActive() && motorsAreBusy()) {
            updateTelemetry();
        }
        doNothing(100);
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

        frontLeftMotor.setVelocity(1250);
        frontRightMotor.setVelocity(1250);
        backLeftMotor.setVelocity(1250);
        backRightMotor.setVelocity(1250);
        while(opModeIsActive() && frontLeftMotor.isBusy()) {
            updateTelemetry();
        }
        resetEncoders();
        doNothing(100);
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

        int target = (int) (DRIVE_TRAIN_COUNTS_PER_MM * mm);
        setTargetPosition(target);
        setRunToPosition();

        frontLeftMotor.setVelocity(1250);
        frontRightMotor.setVelocity(1250);
        backLeftMotor.setVelocity(1250);
        backRightMotor.setVelocity(1250);
        while(opModeIsActive() && motorsAreBusy()) {
            updateTelemetry();
        }
        resetEncoders();
        doNothing(100);
    }

    public void driveForwardsInMillimeters(int mm) {
        frontLeftMotor.setDirection(Direction.REVERSE);
        backRightMotor.setDirection(Direction.FORWARD);
        driveStraightInMillimeters(mm);
        resetMotorDirection();
    }

    void driveBackwardsInMillimeters(int mm) {
        frontRightMotor.setDirection(Direction.REVERSE);
        backLeftMotor.setDirection(Direction.FORWARD);
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

//        timer.reset();
//        int rotationTime2 = 1000;
//        duckRotator.setPower(1);
//
//        while (opModeIsActive() && timer.milliseconds() <= rotationTime2) {
//            updateTelemetry();
//        }

        duckRotator.setPower(0);
    }



    // TODO: complete the state machine
    public void setLinearSlideState(LinearSlideStates desiredState) {

        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int desiredMMToMove = 0;
        int position = 0;
        LinearSlideStates pendingState = LinearSlideStates.ZERO;

        // 39 position units per inch
        // set the position variable to (39 x the number of inches to travel)


        // There are three possible states that the slide can be in
        // There are two possible transitions that can happen in each state
        switch (slideState) {
            case ZERO:
                if (desiredState == LinearSlideStates.ZERO) {
                    return;
                }
                if (desiredState == LinearSlideStates.MIDDLE) {
//                    desiredMMToMove = 200;
                    position = 527;
                    pendingState = LinearSlideStates.MIDDLE;
                }
                if (desiredState == LinearSlideStates.HIGH) {
//                    desiredMMToMove = 400;
                    position = 741;
                    pendingState = LinearSlideStates.HIGH;
                }
                if (desiredState == LinearSlideStates.LOW) {
                    position = 351;
                    pendingState = LinearSlideStates.LOW;
                }
                break;
            case MIDDLE:
                if (desiredState == LinearSlideStates.ZERO) {
//                    desiredMMToMove = 200;
                    position = 527;
                    linearSlideMotor.setDirection(Direction.REVERSE);
                    pendingState = LinearSlideStates.ZERO;
                }
                if (desiredState == LinearSlideStates.MIDDLE) {
                    return;
                }
                if (desiredState == LinearSlideStates.HIGH) {
//                    desiredMMToMove = 200;
                    position = 214;
                    pendingState = LinearSlideStates.HIGH;
                }
                break;
            case HIGH:
                if (desiredState == LinearSlideStates.ZERO) {
//                    desiredMMToMove = 400;
                    position = 741;
                    linearSlideMotor.setDirection(Direction.REVERSE);
                    pendingState = LinearSlideStates.ZERO;
                }
                if (desiredState == LinearSlideStates.MIDDLE) {
//                    desiredMMToMove = 200;
                    position = 214;
                    linearSlideMotor.setDirection(Direction.REVERSE);
                    pendingState = LinearSlideStates.MIDDLE;
                }
                if (desiredState == LinearSlideStates.HIGH) {
                    return;
                }
                break;
            default:
                return;
        }


        // this wasn't working for some reason so we switched to a hard-coded position
        // in other words, a hard-coded encoder value.
//        final double COUNTS_PER_MOTOR_REV = 4.0;
//        final double DRIVE_GEAR_REDUCTION = 72.0;
//        final double WHEEL_CIRCUMFERENCE_MM = 28 * Math.PI;
//
//        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
//        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;


//        position = (int) Math.round(COUNTS_PER_MM * desiredMMToMove);


        linearSlideMotor.setTargetPosition(position);


        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        linearSlideMotor.setVelocity(350);

        // uncomment this loop to block other autonomous operations
        // while the linear slide is moving
        while(opModeIsActive() && linearSlideMotor.isBusy()) {
            updateTelemetry();
        }
        this.slideState = pendingState;
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // always set the motor to the default direction so that our
        // state transition logic always works as intended
        linearSlideMotor.setDirection(Direction.FORWARD);
    }


    @Deprecated
    public void setDumperState(DumpStates desiredState) {

        dumperMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int position = 0;

        // pendingState was added as a lock to prevent concurrency issues when using buttons
        // this function will most likely just be used for autonomous so
        // we can likely remove them after testing
        DumpStates pendingState = DumpStates.NO_DUMP;

        int waitTime = 0;

        if (desiredState == DumpStates.DUMP) {
            if(dumperState == DumpStates.NO_DUMP) {
                pendingState = DumpStates.DUMP;
                waitTime = 250;
            } else {
                // Already in the state that we want. Return and do nothing
                return;
            }
        }

        if (desiredState == DumpStates.NO_DUMP) {
            if(dumperState == DumpStates.DUMP) {
                dumperMotor.setDirection(Direction.REVERSE);
                pendingState = DumpStates.NO_DUMP;
                waitTime = 400;
            } else {
                // Already in the state that we want. Return and do nothing
                return;
            }
        }

        position = 80; // this value changes how far the dumper moves

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

        doNothing(waitTime);
    }

    // refactored to time based because I was not getting encoder values
    // did not have the tools, time, or spare motors to try and troubleshoot.
    public void setDumperState2(DumpStates desiredState) {

        // pendingState was added as a lock to prevent concurrency issues when using buttons
        // this function will most likely just be used for autonomous so
        // we can likely remove them after testing
        DumpStates pendingState = DumpStates.NO_DUMP;
        int waitTime = 0;

        if (desiredState == DumpStates.DUMP) {
            if(dumperState == DumpStates.NO_DUMP) {
                waitTime = 250;
                pendingState = DumpStates.DUMP;
            } else {
                // Already in the state that we want. Return and do nothing
                return;
            }
        }

        if (desiredState == DumpStates.NO_DUMP) {
            if(dumperState == DumpStates.DUMP) {
                dumperMotor.setDirection(Direction.REVERSE);
                waitTime = 400;
            } else {
                // Already in the state that we want. Return and do nothing
                return;
            }
        }

        ElapsedTime timer = new ElapsedTime();

        dumperMotor.setPower(0.4);
        while(timer.milliseconds() < waitTime && opModeIsActive()) {
            updateTelemetry();
        }
        dumperMotor.setPower(0);
        dumperState = pendingState;

        // always set the motor to the default direction so that our
        // state transition logic always works as intended
        dumperMotor.setDirection(Direction.FORWARD);


        timer.reset();
        // wait until the freight is dumped
        while(timer.milliseconds() < 1000 && opModeIsActive()) {
            updateTelemetry();
        }
    }
}