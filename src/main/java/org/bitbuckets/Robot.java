package org.bitbuckets;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {

    static float TICK_LENGTH_MS = 20;
    static float TICK_LENGTH_SECONDS = TICK_LENGTH_MS / 1000;

    //final variables [correct]
    DifferentialDrivetrainSim sim = new DifferentialDrivetrainSim(
            DCMotor.getNeo550(1),
            150,
            30,
            5,
            1,
            0.5,
            null
    );
    final XboxController driverController = new XboxController(0);

    //state variables

    double lastLeftVoltageCommand_volts = 0f;
    double lastRightVoltageCommand_volts = 0f;

    public void robotInit() {

    }

    public void robotPeriodic() {

        sim.setInputs(lastLeftVoltageCommand_volts, lastRightVoltageCommand_volts);
        sim.update(TICK_LENGTH_SECONDS);



        Pose2d estimatedPose = sim.getPose();
        double[] poseArray = new double[] {estimatedPose.getX(), estimatedPose.getY(), estimatedPose.getRotation().getRadians()};

        SmartDashboard.getEntry("robot-pose").setDoubleArray(poseArray);
    }



    public void teleopPeriodic() {
        lastLeftVoltageCommand_volts = driverController.getRawAxis(1) * 12;
        lastRightVoltageCommand_volts = driverController.getRawAxis(0) * 12;
    }



}
