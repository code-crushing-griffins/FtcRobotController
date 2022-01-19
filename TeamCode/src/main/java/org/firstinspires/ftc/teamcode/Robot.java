package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Robot extends LinearOpMode {
    DcMotorEx frontLeftMotor; // 0
    DcMotorEx frontRightMotor; // 1
    DcMotorEx backLeftMotor; // 2
    DcMotorEx backRightMotor; // 3
    // DcMotorEx armDeliveryMotor;
    DcMotor intakeMotor;
    DcMotorEx linearSlideMotor;
    DcMotor duckRotator;
//    CRServo freightFlipperServo;
    Servo freightFlipperServo;
    Servo freightDumperServo;

    public enum ActuatorStates {
        ZERO,
        MIDDLE,
        HIGH
    }

    private final double DRIVE_TRAIN_COUNTS_PER_MOTOR_REV = 28.0;
    private final double DRIVE_TRAIN_GEAR_REDUCTION = 26.9;
    private final double DRIVE_TRAIN_WHEEL_CIRCUMFERENCE_MM = 75 * Math.PI;

    private final double DRIVE_TRAIN_COUNTS_PER_WHEEL_REV = DRIVE_TRAIN_COUNTS_PER_MOTOR_REV * DRIVE_TRAIN_GEAR_REDUCTION;
    private final double DRIVE_TRAIN_COUNTS_PER_MM = DRIVE_TRAIN_COUNTS_PER_WHEEL_REV / DRIVE_TRAIN_WHEEL_CIRCUMFERENCE_MM;

//    private ActuatorStates  slideState = ActuatorStates.ZERO;
    private  ActuatorStates slideState = ActuatorStates.ZERO;

    private boolean duckToggle = true;
    private boolean intakeToggle = true;
    private boolean dumpToggle = true;


    @Override
    public void runOpMode() {
        frontLeftMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = (DcMotorEx) hardwareMap.get(DcMotor.class, "backRightMotor");
        // armDeliveryMotor =  (DcMotorEx) hardwareMap.get(DcMotor.class, "armDeliveryMotor");
        intakeMotor =  hardwareMap.get(DcMotor.class, "intakeMotor");
        linearSlideMotor =  (DcMotorEx) hardwareMap.get(DcMotor.class, "linearSlideMotor");
        duckRotator = hardwareMap.get(DcMotor.class, "duckRotatorMotor");
//        freightFlipperServo = hardwareMap.get(CRServo.class, "freightFlipperServo");
        freightFlipperServo = hardwareMap.get(Servo.class, "freightFlipperServo");
        freightDumperServo = hardwareMap.get(Servo.class, "freightDumperServo");




        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

//        armDeliveryMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        freightFlipperServo.setPosition(0.0);

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

    // this will set the power of the continuous rotation servo
    // that is used to flip the freight off of the side of
    // our freight loader. The intention is that it is used
    // to place freight on the shared shipping hub
    void setCRServoPower(double power) {
//        freightFlipperServo.setPower(power);
        if (power == 0) {
            return;
        }
        double sign = power / Math.abs(power);
        double position = freightFlipperServo.getPosition();
        freightFlipperServo.setPosition(position + (sign * 0.01));
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

    void dumpToggle(boolean button) {
        if (dumpToggle && button) {
            dumpToggle = false;
            if (freightDumperServo.getPosition() < 0.6) {
                freightDumperServo.setPosition(0.64);
            } else {
                freightDumperServo.setPosition(0.209);
            }
        }

        if (!button) {
            dumpToggle = true;
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
        telemetry.addData("freightDumperServo position", freightDumperServo.getPosition());
        telemetry.addData("flipperServoPosition", freightFlipperServo.getPosition());
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



//    void moveFreightArmToTarget(ActuatorStates desiredState) {
//
//        armDeliveryMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        int desiredMMToMove = 0;
//
//
//        // There are three possible states that the slide can be in
//        // There are two possible transitions that can happen in each state
//        switch (slideState) {
//            case ZERO:
//                if (desiredState == ActuatorStates.ZERO) {
//                    return;
//                }
//                if (desiredState == ActuatorStates.MIDDLE) {
//                    desiredMMToMove = 100;
//                    this.slideState = ActuatorStates.MIDDLE;
//                }
//                if (desiredState == ActuatorStates.HIGH) {
//                    desiredMMToMove = 225;
//                    this.slideState = ActuatorStates.HIGH;
//                }
//                break;
//            case MIDDLE:
//                if (desiredState == ActuatorStates.ZERO) {
//                    desiredMMToMove = 100;
//                    armDeliveryMotor.setDirection(Direction.REVERSE);
//                    this.slideState = ActuatorStates.ZERO;
//                }
//                if (desiredState == ActuatorStates.MIDDLE) {
//                    return;
//                }
//                if (desiredState == ActuatorStates.HIGH) {
//                    desiredMMToMove = 125;
//                    this.slideState = ActuatorStates.HIGH;
//                }
//                break;
//            case HIGH:
//                if (desiredState == ActuatorStates.ZERO) {
//                    desiredMMToMove = 225;
//                    armDeliveryMotor.setDirection(Direction.REVERSE);
//                    this.slideState = ActuatorStates.ZERO;
//                }
//                if (desiredState == ActuatorStates.MIDDLE) {
//                    desiredMMToMove = 125;
//                    armDeliveryMotor.setDirection(Direction.REVERSE);
//                    this.slideState = ActuatorStates.MIDDLE;
//                }
//                if (desiredState == ActuatorStates.HIGH) {
//                    return;
//                }
//                break;
//            default:
//                return;
//        }
//
//
//        final double COUNTS_PER_MOTOR_REV = 4.0;
//        final double DRIVE_GEAR_REDUCTION = 72.1;
//        final double WHEEL_CIRCUMFERENCE_MM = 14.4 * Math.PI;
//
//        final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
//        final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE_MM;
//
//
//        int position = (int) COUNTS_PER_MM * desiredMMToMove;
//
//
//        armDeliveryMotor.setTargetPosition(position);
//
//
//        armDeliveryMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        armDeliveryMotor.setVelocity(250);
//
//        // always set the motor to the default direction so that our
//        // state transition logic always works as intended
//        armDeliveryMotor.setDirection(Direction.FORWARD);
//    }
//
    // basically copy and paste of the linear slide code
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
        final double DRIVE_GEAR_REDUCTION = 72.1;
        final double WHEEL_CIRCUMFERENCE_MM = 24 * Math.PI;

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
}
