/*=============================================================================
|   Assignment:  Program #6:  Demonstrating FToA
|       Author:  Mason Holter
|
|       Course:  CSc 245
|   Instructor:  L. M.
|     Due Date:  April 8
|
|  Description:  The purpose of this function is to demonstrate a direct proof
|				 of the Fundamental Theorem of Arithmetic. Achieved through 
|			     recursion, each iteration of the factorize function will 
|				 discover an integers makeup; either prime or the two numbers
|				 that result in its product. This continues until each iteration
|				 is proven to be prime or a product of primes.
|
|     Language:  Java
| Ex. Packages:  N/A
|                
| Deficiencies:  This program cannot find the prime value or divisors of any
|				 integer that is not found in or divisible by the first 22
|				 prime numbers. This can be fixed by adding further prime
|				 numbers to the primeArray integer array.
*===========================================================================*/
public class FTOA {

		public static void main(String[] args) { 
			int product = Integer.valueOf(args[0]);
			System.out.println("This program will demonstrate that " + product + " is either "
					+ "prime");
			System.out.println("or is the product of two or more prime numbers");
			System.out.println();
			factorize(product, 0);
			System.out.println();
			System.out.println("As this output shows, the Fundamental Theorem of Arithmetic " +
					"holds for " + product);
		}
		
	      /*---------------------------------------------------------------------
        |  Method factorize
        |
        |  Purpose:  The main driving function of the program, factorize uses 
        |			 remainder division to discover two integers whose product
        |			 results in the input integer, parameterized as 'product'.
        |			 Once these two integers are discovered, they are handed to
       	| 		     two helper functions that format correct printing of proof, 
        |			 as well as recurse factorize with these two divisors, to
        |			 discover any further possible division. In cases of a square
        |			 result, or the two divisors being the same, a special print 
        |			 function is called.If the 'product' is already prime, then 
        |			 it is printed as such.
        |
        |  Pre-condition:  'product' must be divisible by the first 22 primes.
        |
        |  Post-condition: Series of strings printed to console demonstrating
        |				   factorization of 'product'.
        |
        |  Parameters:
        |      product -- Input integer that is either broken down into its divisors,
        |				  or in the case of primes, simply explained as such.
        |
        |	   tabLength -- For formatting purposes, this integer tracks the depth
        |	   				to which the next description of an integer should be
        |	   				printed
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
		public static void factorize(int product, int tabLength) { 
			int[] primeArray = new int[] {
					2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
					71, 73, 79, 83, 89, 97 };
			for (int i = 0; i < 22; i++) { 
				if (product % primeArray[i] == 0 && product != primeArray[i]) { 
					int divisor = primeArray[i];
					int quotient = product / primeArray[i];
					if (quotient == divisor) { // if square 
						handleSquare(product, quotient, divisor, tabLength);
					} else { // if not square
						handleNonSquare(product, quotient, divisor, tabLength, primeArray);
					}
						return;
				} else if (product == primeArray[i]) {
					printTab(tabLength);
					System.out.println(product + " is prime.");
					return;
				}
			}
		}
		
	        /*---------------------------------------------------------------------
        |  Method handleSquare
        |
        |  Purpose:  This helper function takes an integer discovered to be
        |			 a square, and prints the correct description. It then
        |			 recurses factorize on its square root.
        |
        |  Pre-condition: None
        |
        |  Post-condition: Correctly formatted strings printed to console
        |
        |  Parameters:
        |      product -- Input integer that is broken down into its divisors.
        |	   quotient -- Quotient result of product / divisor
        |	   divisor -- Prime divisor of product
        |	   tabLength -- For formatting purposes, this integer tracks the depth
        |	   				to which the next description of an integer should be
        |	   				printed
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
		private static void handleSquare(int product, int quotient, int divisor, int tabLength) { 
			printTab(tabLength-1);
			System.out.println(product + " = " + quotient + " squared; is this"
					+ " factor either prime of the product of primes?");
			factorize(divisor, tabLength+1);
			printTab(tabLength);
			System.out.println(product + " is the square of " + divisor + ", which "
					+ "is prime or the product of primes");
		}
		
		    /*---------------------------------------------------------------------
        |  Method handleSquare
        |
        |  Purpose:  This helper function takes an integer and its prime 
        |			 divisor, and prints the correct description. It then
        |			 recurses factorize on the quotient of this divison
        |			 if it is not a prime.
        |
        |  Pre-condition: None
        |
        |  Post-condition: Correctly formatted strings printed to console
        |
        |  Parameters:
        |      product -- Input integer that is broken down into its divisors.
        |	   quotient -- Quotient result of product / divisor
        |	   divisor -- Prime divisor of product
        |	   tabLength -- For formatting purposes, this integer tracks the depth
        |	   				to which the next description of an integer should be
        |	   				printed
        |	   primeArray -- Array object containing the first 22 prime numbers
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
		private static void handleNonSquare(int product, int quotient, int divisor, int tabLength, int[] primeArray) {
			printTab(tabLength);
			System.out.println(product + " = " + quotient + " * " + divisor + 
					"; are these factors either prime or the products of primes?");
			boolean quotientPrime = false;
			for (int x = 0; x < 22; x++) {
				if (quotient == primeArray[x]) { 
					quotientPrime = true;
				}
			}
			printTab(tabLength);
			if (quotientPrime == false) { 
				factorize(quotient, tabLength+1);
			}
			
			if (quotientPrime == true) { 
				printTab(tabLength+1);
				System.out.println(quotient + " is prime.");
				printTab(tabLength+1);
				System.out.println(divisor + " is prime.");
			} else {
				printTab(tabLength);
				System.out.println(divisor + " is prime.");
			}
			printTab(tabLength);
			System.out.println(product + " is the product of primes (" + quotient +
					" and " + divisor + " are prime of prime products).");
		}
		
		    /*---------------------------------------------------------------------
        |  Method printTab
        |
        |  Purpose:  This helper function takes the tabLength integer and 
        |			 prints the same amount of 'tabs' (four spaces) to assist
        |			 in correctly formatting the proof strings. 
        |
        |  Pre-condition: None
        |
        |  Post-condition: Nones
        |
        |  Parameters:
        |	   tabLength -- For formatting purposes, this integer tracks the depth
        |	   				to which the next description of an integer should be
        |	   				printed
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
		private static void printTab(int tabLength) { 
			if (tabLength > 0) { 
				System.out.print(new String(new char[tabLength]).replace("\0", "    "));
			}
		}
}
