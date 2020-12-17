package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroscopeTurner extends SimpleTurner implements Turner {

	private int degreesPerSecond;
	static final double E = 70; //in deg
	EV3GyroSensor gyrSens;

	

	public GyroscopeTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3GyroSensor gyrSens) {
		
		super(leftMotor, rightMotor);
		this.gyrSens = gyrSens;
		Delay.msDelay(750);
		this.gyrSens.reset();
		Delay.msDelay(750);

		
		System.out.println("initVal: " + getAngle());		//debug

		
		
	}

	public void setSpeed(int degreesPerSecond) {			//might be usable without declaration -> inherited from superclass
		this.degreesPerSecond = 1000*degreesPerSecond;
		getRightMotor().setSpeed(this.degreesPerSecond);
		getLeftMotor().setSpeed(this.degreesPerSecond);
	}
	
	
	public float getAngle () {
		
		float angleValue[] = new float[1];
		SampleProvider angle = gyrSens.getAngleMode();
		angle.fetchSample(angleValue, 0);
		System.out.println(angleValue[0]);												//debug
		return Math.abs(angleValue[0]);	 
	}
	
	
	public void turn(int degrees) {
		
		System.out.println("deg: " + degrees); 											//debug

		
		if (degrees>0) {			
				getRightMotor().backward();
				getLeftMotor().forward();
				while (degrees-getAngle()>E) {
					System.out.println("delta: " + (degrees-getAngle()));				//debug
					Delay.msDelay(50);
				}
		}else if(degrees<0){
			getRightMotor().forward();
			getLeftMotor().backward();
			System.out.println("delta: " + (degrees+getAngle())); 						//debug
			while (degrees+getAngle()>E) {
				Delay.msDelay(50);
				System.out.println("delta: " + (degrees+getAngle())); 					//debug
			}
		}
		getRightMotor().stop();
		getLeftMotor().stop();
		System.out.println("final angle" + getAngle());
	}
}
