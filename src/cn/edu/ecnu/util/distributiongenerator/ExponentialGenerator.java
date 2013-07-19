package cn.edu.ecnu.util.distributiongenerator;

import java.util.ArrayList;

public class ExponentialGenerator {
	
	/**
	 * e为期望值 1/lamada，row为需要生成的随机数的个数
	 * @param e
	 * @param row
	 * @return
	 */
	public ArrayList<Double> expntl(double lamada, int row) {
		double t,temp;
		ArrayList<Double> a = new ArrayList<Double>();
		
		for(int i = 0; i < row; i++) {
			t = (double) Math.random();
			temp = (double) (-1*Math.log(t)/lamada);
			a.add(temp);
		}
		
		return a;
	}
	
	/**
	 * 
	 * @param lamada
	 * @return
	 */
	public static int nextInt(double lamada) {
		return (int) nextDouble(lamada);
	}
	
	/**
	 * 
	 * @param lamada
	 * @return
	 */
	public static double nextDouble(double lamada) {
		if (lamada<=0d) {
			return 0d;
		}
		
		double t = (double) Math.random();
		
		return (double) (-1*Math.log(t)/lamada);
	}
	
	
	public static void main(String[] args) {
		float b = 0;
		ExponentialGenerator gen = new ExponentialGenerator();
		
		ArrayList<Double> a = gen.expntl(40d/1000d, 20);
		for (int i=0;i<a.size();i++) {
		   b += a.get(i);
		   System.out.println(a.get(i));
		}
		  
		System.out.println("*************");
		  
		System.out.println(b/a.size());
	}
}
