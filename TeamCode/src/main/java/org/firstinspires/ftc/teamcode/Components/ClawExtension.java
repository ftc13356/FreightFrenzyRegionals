package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFMotor;
import org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFServo;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.logger;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.op;

//TODO: capitalize c in ClawExtension
public class ClawExtension {
    public static int ClawExtended = 0; // 0 = retracted, 1 = custom extended, 2 = completely extended
    RFServo clawExtendServo;
    final double INCHES_PER_POS = 10/1;
    public ClawExtension(){
        clawExtendServo = new RFServo("clawExtendServo", 1);
        ClawExtended = 0;
        logger.createFile("clawExtensionLog", "Time Desc Value");
    }
    public void extendToPosition(double inches){
        //use rfservo setPosition for variable distance
        //input is inches that claw should extend in inches
        //set servo range & extend to it
        //log when & what range is set to
        clawExtendServo.setPosition(inches/INCHES_PER_POS);
        ClawExtended = 1;
        logger.log("clawExtensionLog", " Claw Extended to: " + inches + " in.");
    }
    public void extendClaw(){
        //no input TODO: maybe a seperate function for setPosition so you have variable distance
        //state of extendClaw has to be false(retracted state)
        //set servo position
        //set state of claw extended to true
        //log when extension starts & when movement ends
        if(ClawExtended == 1 || ClawExtended == 0){
            clawExtendServo.setPosition(1);
            ClawExtended = 2;
            logger.log("clawExtensionLog", " Claw Extended to: " + INCHES_PER_POS + " in.");
        }
        else{
            logger.log("clawExtensionLog", "Action not done: Tried to fully extend claw when already fully extended");
        }
    }
    public void retractClaw(){
        //no input
        //state of extendClaw has to be true(extended state)
        //set servo position
        //set state of claw extended to false
        //log when extension starts & when movement ends
        if(ClawExtended == 1 || ClawExtended == 2){
            clawExtendServo.setPosition(0);
            logger.log("clawExtension", " Claw Retracted");
        }
        else{
            logger.log("clawExtension", " Action not done: Tried to retract claw when already fully retracted");
        }
    }
}
