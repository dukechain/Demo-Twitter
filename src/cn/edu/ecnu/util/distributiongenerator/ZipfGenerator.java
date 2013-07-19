package cn.edu.ecnu.util.distributiongenerator;

import java.util.Random;

/*Zipf Distribution Generator in Java
 see details @ http://diveintodata.org/2009/09/zipf-distribution-generator-in-java*/

public class ZipfGenerator {
	private Random rnd = new Random(System.currentTimeMillis());
	private int size;
	private double skew;
	private double bottom = 0;

	public ZipfGenerator(int size, double skew) {
		this.size = size;
		this.skew = skew;

		for (int i = 1; i <= size; i++) {
			this.bottom += (1 / Math.pow(i, this.skew));
		}
	}

	// the next() method returns an rank id. The frequency of returned rank ids
	// are follows Zipf distribution. rank id [1, size]
	public int nextInt() {
		int rank;
		double friquency = 0;
		double dice;

		rank = rnd.nextInt(size) + 1;
		friquency = (1.0d / Math.pow(rank, this.skew)) / this.bottom;
		dice = rnd.nextDouble();

		while (!(dice < friquency)) {
			rank = rnd.nextInt(size) + 1;
			friquency = (1.0d / Math.pow(rank, this.skew)) / this.bottom;
			dice = rnd.nextDouble();
		}

		return rank;
	}
	
	public double nextDouble() {
		double candidate = nextInt()-1;
		//from [1, size] to [0, size)
		
		candidate = candidate / (double)size;
		//from [0,size) to [0,1)
		
		candidate = 1 - candidate;
		//from [0,1) to [1,0)
//		if (candidate<0||candidate) {
//			
//		}
		return candidate;
	}
	
	//just return the value in the range [low, high]
	public int nextInt(int low, int high) {
		int candidate = nextInt() + low -1;
		//from [1,size] to [low,high]
		candidate = low + high - candidate;
		//from [low,high] to [high,low]
		if (candidate > high) {
			System.out.println("error!");
		}
		
		return candidate;
	}

	// This method returns a probability that the given rank occurs.
	public double getProbability(int rank) {
		return (1.0d / Math.pow(rank, this.skew)) / this.bottom;
	}

	
	//testing
	public static void main(String[] args) {
//		if (args.length != 2) {
//			System.out.println("usage: ./zipf size skew");
//			System.exit(-1);
//		}

		ZipfGenerator zipf = new ZipfGenerator(100, 0.8);
		
		for (int i = 1; i <= 100; i++)
			System.out.println(zipf.getProbability(i));
			//System.out.println(zipf.nextInt(10,100));
//			System.out.println(zipf.nextDouble());
	}
}