package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.gampad;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.packet;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.time;
import static java.lang.Math.min;
import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robots.BradBot;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class RR20 {
    boolean logi=false, isRight = false;
    LinearOpMode op;
    BradBot robot;
    int bark = 0, delaySec = 0;
    TrajectorySequence[] preload = new TrajectorySequence[3];
    TrajectorySequence[] preToStack = new TrajectorySequence[3];
//    TrajectorySequence[] droppy = new TrajectorySequence[3];

    TrajectorySequence[] park = new TrajectorySequence[3], parkLeft = new TrajectorySequence[3];



    public RR20(LinearOpMode op, boolean isLogi){
        logi = isLogi;
        this.op=op;
        robot = new BradBot(op, false, isLogi);
        robot.roadrun.setPoseEstimate(new Pose2d(17, -61, Math.toRadians(-90)));
        robot.update();

        preload[0] = robot.roadrun.trajectorySequenceBuilder(new Pose2d(17,-61, toRadians(-90)))
                .lineToLinearHeading(new Pose2d(16.5, -43, toRadians(-60)))
                .lineToLinearHeading(new Pose2d(7.5, -40, toRadians(-40)))
                .lineToLinearHeading(new Pose2d(11.5, -40, toRadians(-40)))
                .build();
        preload[1] = robot.roadrun.trajectorySequenceBuilder(new Pose2d(17,-61,toRadians(-90)))
                .lineToLinearHeading(new Pose2d(16.5, -36.5, toRadians(-91))).build();
        preload[2] = robot.roadrun.trajectorySequenceBuilder(new Pose2d(17,-61, toRadians(-90)))
                .lineToLinearHeading(new Pose2d(24.5,-43, toRadians(-90))).build();
        if(!isLogi){
        preToStack[0] = robot.roadrun.trajectorySequenceBuilder(preload[0].end())
                .lineToLinearHeading(new Pose2d(46.4, -29, toRadians(-180))).build();
        preToStack[1] = robot.roadrun.trajectorySequenceBuilder(preload[1].end())
                .lineToLinearHeading(new Pose2d(46.4, -35.25, toRadians(-180))).build();
        preToStack[2] = robot.roadrun.trajectorySequenceBuilder(preload[2].end())
                .lineToLinearHeading(new Pose2d(46.4, -41.5, toRadians(-180))).build();
        }
        else{
            preToStack[0] = robot.roadrun.trajectorySequenceBuilder(preload[0].end())
                    .lineToLinearHeading(new Pose2d(40, -35, toRadians(-180)))
                    .waitSeconds(2.0)
                    .lineToLinearHeading(new Pose2d(47, -29, toRadians(-180))).build();
            preToStack[1] = robot.roadrun.trajectorySequenceBuilder(preload[1].end())
                    .lineToLinearHeading(new Pose2d(16.5, -39.5, toRadians(-91)))
                    .lineToLinearHeading(new Pose2d(40.4, -37, toRadians(-180)))
                    .waitSeconds(2.0)
                    .lineToLinearHeading(new Pose2d(47, -35.25, toRadians(-180))).build();
            preToStack[2] = robot.roadrun.trajectorySequenceBuilder(preload[2].end())
                    .lineToLinearHeading(new Pose2d(42.5, -39.5, toRadians(-180)))
                    .waitSeconds(2.0)
                    .lineToLinearHeading(new Pose2d(47, -41.5, toRadians(-180))).build();
        }
        park[0] = robot.roadrun.trajectorySequenceBuilder(preToStack[0].end())
                .lineToLinearHeading(new Pose2d(43, -29, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(43, -58, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(58, -58, toRadians(-180)))
                .build();
        park[1] = robot.roadrun.trajectorySequenceBuilder(preToStack[1].end())
                .lineToLinearHeading(new Pose2d(43, -35.25, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(43, -58, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(58, -58, toRadians(-180)))
                .build();
        park[2] = robot.roadrun.trajectorySequenceBuilder(preToStack[2].end())
                .lineToLinearHeading(new Pose2d(43, -41.5, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(43, -58, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(58, -58, toRadians(-180)))
                .build();
        parkLeft[0] = robot.roadrun.trajectorySequenceBuilder(preToStack[0].end())
                .lineToLinearHeading(new Pose2d(43, -29, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(43, -8, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(58, -8, toRadians(-180)))
                .build();
        parkLeft[1] = robot.roadrun.trajectorySequenceBuilder(preToStack[1].end())
                .lineToLinearHeading(new Pose2d(43, -35.25, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(43, -8, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(58, -8, toRadians(-180)))
                .build();
        parkLeft[2] = robot.roadrun.trajectorySequenceBuilder(preToStack[2].end())
                .lineToLinearHeading(new Pose2d(43, -41.5, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(43, -8, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(58, -8, toRadians(-180)))
                .build();


        robot.setRight(true);
        robot.setBlue(false);
        robot.observeSpike();
    }
    public void waitForStart(){
        while (!op.isStarted() || op.isStopRequested()) {
            bark = robot.getSpikePos();
            op.telemetry.addData("pixel", bark);
            packet.put("spike", bark);
            op.telemetry.addData("delaySec", delaySec);
            op.telemetry.addData("isRight", isRight);
            if (gampad.readGamepad(op.gamepad1.dpad_up, "gamepad1_dpad_up", "addSecs")) {
                delaySec++;
            }
            if (gampad.readGamepad(op.gamepad1.dpad_down, "gamepad1_dpad_down", "minusSecs")) {
                delaySec = min(0, delaySec - 1);
            }
            if (gampad.readGamepad(op.gamepad1.dpad_right, "gamepad1_dpad_right", "parkRight")) {
                isRight = true;
            }
            if (gampad.readGamepad(op.gamepad1.dpad_left, "gamepad1_dpad_left", "parkLeft")) {
                isRight = false;
            }
            robot.update();
        }
        op.resetRuntime();
        time=0;
    }
    public void purp()
    {
        robot.queuer.queue(false, true);
        robot.queuer.queue(false, true);
        robot.upAuto();
        robot.purpurAuto();
        robot.queuer.addDelay(2.0+delaySec);
        robot.followTrajSeq(preload[bark]);
        robot.queuer.addDelay(0.9);
        robot.dropAuto(0);

    }
    public void pre(){
        robot.followTrajSeq(preToStack[bark]);
        robot.veryLowAuto();
        robot.drop();
//            robot.queuer.addDelay(0.2);

    }

    public void park(){
        if(isRight){
      robot.followTrajSeq(park[bark]);
    }
        else{
            robot.followTrajSeq(parkLeft[bark]);
        }
        robot.queuer.addDelay(0.8);
        robot.resetAuto();
        robot.queuer.waitForFinish();
        robot.queuer.addDelay(0.8);
        robot.queuer.queue(false,true);
    }

    public void update(){
        robot.update();
        robot.queuer.setFirstLoop(false);
    }

    public boolean isAutDone(){
        return !robot.queuer.isFullfilled()&&time<29.8;
    }}
