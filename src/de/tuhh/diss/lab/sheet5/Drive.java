package de.tuhh.diss.lab.sheet5;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Drive implements Driver{
	
	private int angle;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;	
	
	public Drive(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		
		angle = (int) (35 * 60 / Math.PI * 5.4);
		this.leftMotor= leftMotor;
		this.rightMotor = rightMotor;
		
	}
	
	
	public void driveForward() {
		
		rightMotor.rotateTo(angle);
		leftMotor.rotateTo(angle);
	}

}
