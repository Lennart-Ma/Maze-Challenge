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
	private final int ANGULAR_VELOCITY = 1500;
	private final int TILELENGTH = 350; //in mm
	private double distanceToWallDouble;
	private int distanceToWall;
	private double angularVelocity;
	private double actualDistToWall;
	private double TILE_LENGTH = 350;
	
	
	public Drive(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3UltrasonicSensor distSens) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.distSens = distSens;
	}
	

	public void driveTileForward() {
		
		int motorDegree = setMotorDegree(-TILELENGTH);
		setSpeed();
		double angularVelocity = ANGULAR_VELOCITY;
		double delayTime = (Math.abs(motorDegree)/Math.abs(angularVelocity))*1000;
		int delayTimeInt = (int)delayTime;
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,true);

		Delay.msDelay(3 * delayTimeInt);
		System.out.println("END: driveTileForward()");

	}
	
	public void approachTileEdge(boolean towards) {
		
		if (towards) {
			distanceToWall = -65;
		} else {
			distanceToWall = 65;
		}
		
		int motorDegree = setMotorDegree(distanceToWall);
		setSpeed();
		double angularVelocity = ANGULAR_VELOCITY;
		double delayTime = (Math.abs(motorDegree)/Math.abs(angularVelocity))*1000;
		int delayTimeInt = (int)delayTime;
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,true);

			Delay.msDelay(5 * delayTimeInt);

	}
	
	
	public void adjustTileEdgeDist() {
		
		actualDistToWall = checkDistance();

		System.out.println("before Adjustment: "+ actualDistToWall);
		
		distanceToWallDouble = ((TILE_LENGTH/2) - (actualDistToWall + 55));
				
		distanceToWall = (int)distanceToWallDouble;
				
		
		int motorDegree = setMotorDegree(distanceToWall);
		setSpeed();
		double angularVelocity = ANGULAR_VELOCITY;
		double delayTime = (Math.abs(motorDegree)/Math.abs(angularVelocity))*1000;
		int delayTimeInt = (int)delayTime;
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,true);


		Delay.msDelay(5 * delayTimeInt);
		
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


