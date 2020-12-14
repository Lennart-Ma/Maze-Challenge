package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class SimpleTurner implements Turner {
	
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private double degreesPerSecond;
	private static final int WHEELDIAMETER = 54/2; //in mm
	private static final double TURNINGCIRCLE = 385.41; //in mm
	
	public SimpleTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
	
	
	public void setSpeed(int degreesPerSecond) {
		this.degreesPerSecond = degreesPerSecond;
		rightMotor.setSpeed(degreesPerSecond);
		leftMotor.setSpeed(degreesPerSecond);	
	}

	public void turn(int circleDegree) {

		int motorDegree = getMotorDegree(circleDegree);
			
		int delay_time = (int)((motorDegree/degreesPerSecond)*1000);
		System.out.println(delay_time);
		
		Delay.msDelay(2000);
	
		rightMotor.rotate(motorDegree,true);
		leftMotor.rotate(-motorDegree,true);
		Delay.msDelay(delay_time);
		System.out.println("Done");

	}
	
	private int getMotorDegree(int circleDegree) {
		
		System.out.println(circleDegree);
		double distanceWheel = ((circleDegree* TURNINGCIRCLE)/360);
		System.out.println(distanceWheel);
		
		double alphaWheel = (distanceWheel/(2*Math.PI*WHEELDIAMETER))*360;
		System.out.println(alphaWheel);
		
		double alphaMotor = 3*alphaWheel;
		System.out.println(alphaMotor);
		
		// wieso komme ich hier noch auf eine *2? Wenn ich das per Hand berechne komme ich nicht drauf?
		int alphaMotorPerMotor = (int)alphaMotor*2;
		System.out.println(alphaMotorPerMotor);
		
		
		return alphaMotorPerMotor;
		
	}

}
