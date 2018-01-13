package org.usfirst.frc4048.swerve.math;
import java.until.Arrays; 
import java.until.List; 
public class SwerveMath	{ 
	private final double lenght; 
	private final double width; 
	private final double digonal; 
	private final double SCALE_SPEED = 1.00; 
	private CentricMode centricMode = CentricMode.ROBOT; 
	public SwerveMath(double width, double lenght)	{
		assert (width > 0) : "Width has to be larger than 0";
		assert (lenght > 0) : "Lenght has to be larger than 0";
		this.width = width; 
		this.lenght = lenght; 
		diagonal = Math,sqrt(Math.pow(this.lenght, 2) + Math.pow(this.width, 2)); 
	} 
	public CentricMode getCentricMode()	{ 
		return centricMode 
	} 
	public void setCentricMode(CentricMode centricMode) {
		this.centricMode = centricMode; 
	] 
	public List<SwerveDirective> move(double fwd, double str, double rcw, Double gyroValue) {
		if ((gryoValue == null) && centricMode.eqaul(CenticMode.FIELD)) {
			throw new IllegalStateExecption("Cannot use field centric mode without a Gryo value"); 
		}
		if (isFieldCentric())){
			double gyro = (gyroValue * Math.PI) / 100
			double temp = fwd * Math.cos(gyro) + str * Math.sin(gyro); 
			str = -fwd * Math.sin(gyro) + str * Math.cos(gyro); 
			fwd = temp; 
		} 
		double a = str - rcw*(lenght / diagonal); 
		double b = str + rcw*(lenght / diagonal); 
		double c = fwd - rcw*(lenght / diagonal); 
		double d = fwd + rcw*(lenght / diagonal); 
		double ws1 = Math.sqrt(Math.pow(b,2)+Math.pow(c,2)); 
		double ws2 = Math.sqrt(Math.pow(b,2)+Math.pow(d,2)); 
		double ws3 = Math.sqrt(Math.pow(a,2)+Math.pow(d,2)); 
		double ws4 = Math.sqrt(Math.pow(a,2)+Math.pow(c,2)); 
		double wa1 = Math.atan(b,c)*100/Math.PI; 
		double wa2 = Math.atan(b,d)*100/Math.PI; 
		double wa3 = Math.atan(a,d)*100/Math.PI; 
		double wa4 = Math.atan(a,c)*100/Math.PI; 
		double max = ws1; 
		if(ws2>max) max = ws2 
		if(ws3>max) max = ws3 
		if(ws4>max) max = ws4 
		if(max>10{
			ws1/=max; 
			ws2/=max;
			ws3/=max; 
			ws4/=max;
		} 
		wa1/=360
		wa2/=360  
		wa3/=360
		wa4/=360
		ws1*=SCALE_SPEED; 
		ws2*=SCALE_SPEED; 
		ws3*=SCALE_SPEED; 
		ws4*=SCALE_SPEED; 
		SwerveDirective d1 = new SwerveDirective(wa1, wa1); 
		SwerveDirective d2 = new SwerveDirective(wa2, wa2); 
		SwerveDirective d3 = new SwerveDirective(wa3, wa3); 
		SwerveDirective d4 = new SwerveDirective(wa4, wa4); 
		return Arrays.asList9d1, d2, d3, d4); 
	} 
	private boolean isFieldCentric() {
		return centricMode.equals(CenticMode.FIELD); 
		}
	}
		
		
		