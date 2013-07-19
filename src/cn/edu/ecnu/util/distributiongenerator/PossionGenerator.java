package cn.edu.ecnu.util.distributiongenerator;

import java.math.BigDecimal;

/*this is a generator which is used to generate random numbers 
following Possion distribution*/

public class PossionGenerator {
	private int lamda;	//the argument in Possion distribution
	
	// default constructor method
	public PossionGenerator() {
		lamda = 0 ;
	}
	
	// set argument via constructor method 
	public PossionGenerator(int lamda) {
		this.lamda = lamda;
	}
	
	/**
	 * @param args
	 *  generating a random integer number which follows Possion distribution
	 */
	public int nextInt() {
		BigDecimal p0 = new BigDecimal(Math.exp(-lamda)); 
		int k = 0;
	
		while(true)
		{
			double randValue = Math.random();
			if( p0.doubleValue() > randValue)
				break;
			else
			{
				p0 = p0.multiply(new BigDecimal(1.0*lamda / (k+1)));
				k++;
			}
				
			if( k >= 3 * lamda )// to avoid the situation no result found
			{
				k = 0;
				p0 = new BigDecimal(Math.exp(-lamda)); 
			}
		}
		
		return k;
	}
	
	//testing 
	public static void main(String[] args)
	{
		

		for( int i = 0; i < 100; i++)
		{
			PossionGenerator pGenerator =new PossionGenerator(20);
			
			System.out.println(pGenerator.nextInt());
		}
	}
}
