package Logic;

import java.util.Scanner;

public class Driver {

	static public  void main(String[] args) {

		boolean again = true;
		Logic logic = new Logic();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the Grid Size: ");
		int gridSize = sc.nextInt();
		System.out.println("");
		System.out.print("Enter the percolation probability: ");
		float p = sc.nextFloat();
		System.out.println("");
		while (again) {
			logic.initializeGrid(gridSize);
			logic.fillBlocks(p);
			logic.printBlocks();
			System.out.println("Percolation: " + logic.isPercolating());

			System.out.print("Want to run again:(Y/N) ");
			if (sc.next().equalsIgnoreCase("n"))
				again = false;
			System.out.println("");
		}
		

	}

}
