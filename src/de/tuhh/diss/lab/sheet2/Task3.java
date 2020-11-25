package de.tuhh.diss.lab.sheet2;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Task3 {

	public static void main(String[] args) {
		
		double pi = Math.PI;
		double d_r = 20; 
		int angle = (int) (d_r * 60 / pi * 5.4); 
		
		var rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		var leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);

		
		rightMotor.setSpeed(100);
		leftMotor.setSpeed(100);

		//rightMotor.forward();
		//leftMotor.forward();
		
		//Delay.msDelay(200000);
		
		do {
			rightMotor.rotate(angle, true);
		} while (rightMotor.isMoving() == false);
		
		
		do {
			leftMotor.rotate(angle, true);
		} while (leftMotor.isMoving() == true);
		
		
	}

}
