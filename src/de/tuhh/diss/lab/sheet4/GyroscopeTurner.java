package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroscopeTurner implements Turner {

	private int degreesPerSecond;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	static final double E = 5; //in deg
	EV3GyroSensor gyrSens;

	

	public GyroscopeTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3GyroSensor gyrSens) {
		
		this.leftMotor= leftMotor;
		this.rightMotor = rightMotor;
		this.gyrSens = gyrSens;
		Delay.msDelay(750);
		this.gyrSens.reset();
		Delay.msDelay(750);		
		System.out.println("initVal: " + getAngle());									//debug

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
			rightMotor.backward();
			leftMotor.forward();
			while ((degrees-getAngle())>(-E)) { 
				Delay.msDelay(5);
				System.out.println("deltaP: " + (degrees-getAngle()));					//debug
				if((degrees+getAngle()) < 5*E ) {
					setSpeed((int)0.9*this.degreesPerSecond);
					System.out.println("speed: " + this.degreesPerSecond);				//debug
				}
				if ((degrees+getAngle())==0)break;
			}
			if ((degrees-getAngle())<E) {
				rightMotor.stop();
				leftMotor.stop();
				System.out.println("STOPPED MOTOR");									//debug
				}
			
		}else if(degrees<0){
			leftMotor.backward();
			rightMotor.forward();
			while ((degrees+getAngle())<E) {
				Delay.msDelay(5);
				System.out.println("deltaN: " + (degrees+getAngle())); 					//debug
				if((degrees+getAngle()) > -5*E ) {
					setSpeed((int)0.9*this.degreesPerSecond);
					System.out.println("speed: " + this.degreesPerSecond);				//debug
				}
				if ((degrees+getAngle())==0)break;
			}
			if ((degrees+getAngle())<E) {
				rightMotor.stop();
				leftMotor.stop();
				System.out.println("STOPPED MOTOR");									//debug
			}
		}
		
		System.out.println("final angle" + getAngle());
	}

	
	public void setSpeed(int degreesPerSecond) {
		
		this.degreesPerSecond = degreesPerSecond;
		rightMotor.setSpeed(this.degreesPerSecond);
		leftMotor.setSpeed(this.degreesPerSecond);

		
	}

}