import java.util.Random;
import java.util.Scanner;

public final class MaxSumTest
{
	//Smallest possible random integer.
	private final static int MIN = -9999;
	//Largest possible random integer.
	private final static int MAX = 9999;
    static private int seqStart = 0;
    static private int seqEnd = -1;
    private static Random random;

    /**
     * Quadratic maximum contiguous subsequence sum algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     * http://users.cis.fiu.edu/~weiss/dsaajava3/code/MaxSumTest.java
     */
    public static int maxSubSum2( int [ ] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
        {
            int thisSum = 0;
            for( int j = i; j < a.length; j++ )
            {
                thisSum += a[ j ];

                if( thisSum > maxSum )
                {
                    maxSum = thisSum;
                    seqStart = i;
                    seqEnd   = j;
                }
            }
        }

        return maxSum;
    }

    /**
     * Linear-time maximum contiguous subsequence sum algorithm.
     * seqStart and seqEnd represent the actual best sequence.
     * http://users.cis.fiu.edu/~weiss/dsaajava3/code/MaxSumTest.java
     */
    public static int maxSubSum4( int [ ] a )
    {
        int maxSum = 0;
        int thisSum = 0;

        for( int i = 0, j = 0; j < a.length; j++ )
        {
            thisSum += a[ j ];

            if( thisSum > maxSum )
            {
                maxSum = thisSum;
                seqStart = i;
                seqEnd   = j;
            }
            else if( thisSum < 0 )
            {
                i = j + 1;
                thisSum = 0;
            }
        }

        return maxSum;
    }


    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     * http://users.cis.fiu.edu/~weiss/dsaajava3/code/MaxSumTest.java
     */
    private static int maxSumRec( int [ ] a, int left, int right )
    {
        int maxLeftBorderSum = 0, maxRightBorderSum = 0;
        int leftBorderSum = 0, rightBorderSum = 0;
        int center = ( left + right ) / 2;

        if( left == right )  // Base case
            return a[ left ] > 0 ? a[ left ] : 0;

        int maxLeftSum  = maxSumRec( a, left, center );
        int maxRightSum = maxSumRec( a, center + 1, right );
        
        
        for( int i = center; i >= left; i-- )
        {
            leftBorderSum += a[ i ];
            if( leftBorderSum > maxLeftBorderSum )
                maxLeftBorderSum = leftBorderSum;
        }

        for( int i = center + 1; i <= right; i++ )
        {
            rightBorderSum += a[ i ];
            if( rightBorderSum > maxRightBorderSum )
                maxRightBorderSum = rightBorderSum;
        }

        return max3( maxLeftSum, maxRightSum,
                     maxLeftBorderSum + maxRightBorderSum );
    }

    /**
     * Return maximum of three integers.
     * http://users.cis.fiu.edu/~weiss/dsaajava3/code/MaxSumTest.java
     */
    private static int max3( int a, int b, int c )
    {
        return a > b ? a > c ? a : c : b > c ? b : c;
    }

    /**
     * Driver for divide-and-conquer maximum contiguous
     * subsequence sum algorithm.
     * http://users.cis.fiu.edu/~weiss/dsaajava3/code/MaxSumTest.java
     */
    public static int maxSubSum3( int [ ] a )
    {
        return a.length > 0 ? maxSumRec( a, 0, a.length - 1 ) : 0;
    }
    
    private static Random rand = new Random( );
    
    /**
     * Driver program and user interface. 
     */
    public static void main( String [ ] args )
    {
    	long startTime;
    	long endTime;
    	int size = 0;
        int maxSum;
        int a[];
        boolean validInput = false;
        
        Scanner kbd = new Scanner(System.in);
        
        //Ensures valid input is given for size. 
        while (!validInput)
        {
        	System.out.println("Please enter the size of the problem (N):");
        	size = kbd.nextInt();
        	if (size > 0)
        	{
        		validInput = true;
        	}
        }
        
        a = randomArray(size);
        if (a.length < 50)
        {
        	for (int num : a)
        	{
        		System.out.print(num + " ");
        	}
        }
        
        startTime = System.nanoTime();
        maxSum = maxSubSum2( a );
        endTime = System.nanoTime();
        System.out.println( "\nAlgorithm 2: \nMaxSum: " + maxSum + ", S_index: "
                       + seqStart + " E_index: " + seqEnd + "\nExecution Time: " + (endTime - startTime));
        startTime = System.nanoTime();
        maxSum = maxSubSum3( a );
        endTime = System.nanoTime();
        System.out.println( "\nAlgorithm 3: \nMaxSum: " + maxSum + ", S_index: "
                       + seqStart + " E_index: " + seqEnd + "\nExecution Time: " + (endTime - startTime));
        startTime = System.nanoTime();
        maxSum = maxSubSum4( a );
        endTime = System.nanoTime();
        System.out.println( "\nAlgorithm 4: \nMaxSum: " + maxSum + ", S_index: "
                       + seqStart + " E_index: " + seqEnd + "\nExecution Time: " + (endTime - startTime));

    }

    /*
     *Helper method to populate random array. 
     */
	private static int[] randomArray(int size) 
	{
		random = new Random();
		int[] randArray = new int[size];
		for (int i = 0; i < size; i++)
		{
			randArray[i] = random.nextInt(MAX - MIN) + MIN;
		}
		
		return randArray;
	}
}
