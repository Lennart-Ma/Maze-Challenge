package de.tuhh.diss.lab.sheet5;


import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Drive implements Driver{
	
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private EV3UltrasonicSensor distSens;
	private final int WHEELDIAMETER = 54; //in mm
	private final int ANGULAR_VELOCITY = 760;
	private final int TILELENGTH = 350; //in mm
	private final int DISTANCE_TO_WALL = 65;
	private final int OFFSET_TILE_ADJUSTMENT = 55;
	private int distanceToWall;
	
	
	public Drive(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3UltrasonicSensor distSens) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.distSens = distSens;
	}
	

	public void driveTileForward() {
		
		int motorDegree = setMotorDegree(-TILELENGTH);
		setSpeed();
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,false);
		
		System.out.println("Got here");
		
		Delay.msDelay(250);
	}
	
	public void approachTileEdge(boolean towards) {
		
		if (towards) {
			distanceToWall = -DISTANCE_TO_WALL;
		} else {
			distanceToWall = DISTANCE_TO_WALL;
		}
		
		int motorDegree = setMotorDegree(distanceToWall);
		setSpeed();
		
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,false);
		
		Delay.msDelay(250);
		
	}
	
	
	public void adjustTileEdgeDist() {
		
		double actualDistToWall = checkDistance();
		
		double distanceToWallDouble = ((TILELENGTH/2) - (actualDistToWall + OFFSET_TILE_ADJUSTMENT));
				
		distanceToWall = (int)distanceToWallDouble;
				
		int motorDegree = setMotorDegree(distanceToWall);
		setSpeed();
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,false);

		Delay.msDelay(250);
		
	}
	
	
	public double checkDistance() {
		
		float distValue[];
		distValue = new float[1];
		double distValueMm;
		
		SampleProvider dist = this.distSens.getDistanceMode();

		dist.fetchSample(distValue, 0);
		
		distValueMm = distValue[0] * 1000;
		
		distSens.close(); 
		
		return distValueMm;
		
	}
	
	
	private int setMotorDegree(int length) {
		
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


