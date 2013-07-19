package cn.edu.ecnu.util.distributiongenerator;

import java.util.Random;

/**
 * @author uqcxu2
 * this is a generator which is used to generate random numbers 
following Uniform distribution
 */

public class UniformGenerator {
//	private int low_bound;
//	private int high_bound;
//	
//	public UniformGenerator() {
//		low_bound = 0;
//		high_bound = 0;
//	}
//	
//	public UniformGenerator(int low_bound, int high_bound) {
//		this.low_bound = low_bound;
//		this.high_bound = high_bound;
//	}
    private static Random r = new Random(System.currentTimeMillis());
	//generate integer data randomly in range [low_bound, high_bound] 
	/**
	 * @param low_bound
	 * @param high_bound
	 * @return
	 */
	public static int nextInt(int low_bound, int high_bound){
		//return (int)low_bound+(int)(Math.random()*(high_bound-low_bound+1));
		return low_bound + r.nextInt(high_bound - low_bound + 1);
	}
	
	//generate double data randomly in range [low_bound, high_bound) 
	/**
	 * @param low_bound
	 * @param high_bound
	 * @return
	 */
	public static double nextDouble(double low_bound, double high_bound) {
		return low_bound + new Random(System.currentTimeMillis()).nextDouble()*(high_bound - low_bound);
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {
			System.out.println(UniformGenerator.nextInt(1,6));
		}
	}
}
