import java.util.Random;
import java.util.Scanner;

public class randomNumber {
	public static void main(String[] args) {
		
		/* This reads the input provided by user
         * using keyboard
         */
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a starting number to generate random numbers: ");
		int sNum = input.nextInt();
		System.out.println("Enter a Ending number to generate random numbers: ");
		int eNum = input.nextInt();
		
		// Closing Scanner after the use
		input.close();
		
		Random rnum = new Random();
		int counter;
		int[] numbers = new int[500]; // we want 500 numbers ; so size should reflect that.
		numbers[0] = rnum.nextInt(eNum-sNum) + sNum; // read First number
		int largest = numbers[0]; // Set it as largest
		int smallest = numbers[0]; // Set it as smallest
		
		for (counter = 1; counter < 500; counter++) {
			numbers[counter] = rnum.nextInt(eNum-sNum) + sNum; // Store numbers;
			System.out.println(numbers[counter]);
			largest = Math.max(largest, numbers[counter]); // Compare with previous largest
			smallest = Math.min(smallest, numbers[counter]); // Compare with previous smallest
		}
		
		System.out.println(smallest);
		System.out.println(largest);

	}

}
