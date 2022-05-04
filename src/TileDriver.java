package de.tuhh.diss.lab.sheet5;


import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TileDriver implements Driver{
	
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private EV3UltrasonicSensor distSens;
	private final int WHEELDIAMETER = 54; 
	private final int ANGULAR_VELOCITY = 760;
	private final double TILE_LENGTH = 350;
	private double distanceToWall;
	
	
	public TileDriver(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.distSens = new EV3UltrasonicSensor(SensorPort.S4);
	}
	

	public void driveTileForward() {
		
		runMotors(-TILE_LENGTH);
	
	}
	
	public void approachTileEdge(boolean towards) {
		
		if (towards) {
			distanceToWall = -65;
		} else {
			distanceToWall = 65;
		}
		
		runMotors(distanceToWall);
		
	}
		
	public void adjustTileEdgeDist() {
		
		double actualDistToWall = checkDistance();
		int distanceAdjustment = 55;
		
		distanceToWall = ((TILE_LENGTH/2) - (actualDistToWall + distanceAdjustment));
				
		runMotors(distanceToWall);
	}
	
	private void runMotors(double distanceToWall) {
		
		int motorDegree = setMotorDegree(distanceToWall);
		setSpeed();
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,false);

		Delay.msDelay(250);
		
	}
	
	
	private double checkDistance() {
		
		float distValue[];
		distValue = new float[1];
		double distValueMm;
		
		SampleProvider dist = this.distSens.getDistanceMode();

		dist.fetchSample(distValue, 0);
		
		distValueMm = distValue[0] * 1000;
		
		distSens.close();
		
		return distValueMm;
		
	}
	
	
	private int setMotorDegree(double length) {
		
		double alphaWheel = (length/(Math.PI * WHEELDIAMETER)) *360;
		double alphaMotor = 3*alphaWheel;
		int alphaMotorPerMotor = (int)alphaMotor;
		
		return alphaMotorPerMotor;
	}
	
	
	private void setSpeed() {
		this.rightMotor.setSpeed(ANGULAR_VELOCITY);
		this.leftMotor.setSpeed(ANGULAR_VELOCITY);
	}
}


