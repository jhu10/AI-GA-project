import java.util.Random;
import java.util.Vector;

public class GeneticAlgorithm {
	

	public static void main(String[] args) {
		int M[][] = new int[][] { { 1, 0, 0, 0, 0, 0, 0, 0, 2 }, { 0, 0, 8, 0, 0, 9, 0, 3, 7 },
				{ 7, 0, 0, 5, 3, 0, 0, 8, 0 }, { 0, 8, 0, 0, 7, 3, 0, 5, 4 }, { 0, 0, 6, 4, 0, 2, 7, 0, 0 },
				{ 9, 7, 0, 8, 5, 0, 0, 1, 0 }, { 0, 1, 0, 0, 8, 7, 0, 0, 9 }, { 3, 4, 0, 6, 0, 0, 8, 0, 0 },
				{ 8, 0, 0, 0, 0, 0, 0, 0, 1 } };

		int E[][] = { { 0, 2, 0, 4 }, { 3, 0, 0, 0 }, { 0, 0, 4, 0 }, { 0, 0, 0, 0 } };
		int V[][] = { { 1, 0, 0, 4 }, { 0, 0, 0, 0 }, { 0, 3, 2, 0 }, { 0, 0, 0, 0 } };

		int[][] H = { { 0, 0, 0, 0, 0, 0, 6, 8, 0 }, { 0, 0, 0, 0, 7, 3, 0, 0, 9 }, { 3, 0, 9, 0, 0, 0, 0, 4, 5 },
				{ 4, 9, 0, 0, 0, 0, 0, 0, 0 }, { 8, 0, 3, 0, 5, 0, 9, 0, 2 }, { 0, 0, 0, 0, 0, 0, 0, 3, 6 },
				{ 9, 6, 0, 0, 0, 0, 3, 0, 8 }, { 7, 0, 0, 6, 8, 0, 0, 0, 0 }, { 0, 2, 8, 0, 0, 0, 0, 0, 0 } };


		int populationSize = 1000;
		int maxIteration = 500000;
		int iteration = 0;
		int[] sudokuArray = SudokuGenetic.convertTwoDtoOneD(M.clone());
		
		Vector<SudokuGenetic> population = new Vector<>();
		SudokuGenetic []currentSudoku = new SudokuGenetic [populationSize];

		
		SudokuGenetic.setInitialGene(sudokuArray.clone());
		for (int i = 0; i < populationSize; i++) {
			currentSudoku[i] = new SudokuGenetic(initializeSudoku(sudokuArray.clone()));
			population.add(new SudokuGenetic(currentSudoku[i].getGene()));
		}
		
		SudokuGenetic solutionSudoku = geneticAlgorithm(population, iteration, maxIteration, populationSize);		
		print(SudokuGenetic.ConvertOneDToTwoD(solutionSudoku.getGene()));

	}
	
	static SudokuGenetic geneticAlgorithm(Vector<SudokuGenetic> population, int iteration, int maxIteration, int populationSize) {
		
		Random rand = new Random();
		
		SudokuGenetic currentSudoku = SudokuGenetic.bestSelection(population);
		while(currentSudoku.getFitness() != 0){

			for(int i = 0; i < population.size(); i++){
				if(rand.nextBoolean())
					population.get(i).mutation();
				else
					population.get(i).crossover(population.get(rand.nextInt(population.size())));		
			}
			
			if(rand.nextBoolean()){
				currentSudoku = SudokuGenetic.bestSelection(population);
			}
			else
				currentSudoku = SudokuGenetic.randomSelection(population);
			iteration++;


			population = new Vector<>();
			for(int i = 0; i < populationSize; i++)
				population.add(new SudokuGenetic(currentSudoku.getGene().clone()));
			System.out.println(currentSudoku.getFitness());

		}
		System.out.println("Number of iterations is: " + iteration);
		return currentSudoku;
	}

	static int[] initializeSudoku(int[] sudoku) {
		Random rand = new Random();
		int boxSize = (int) Math.sqrt(sudoku.length);

		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[i] == 0) {
				sudoku[i] = rand.nextInt(boxSize) + 1;
			}
		}

		return sudoku;
	}


	public static void print(int[][] M) {
		int l = M.length;
		int s = (int) Math.sqrt(l);

		for (int i = 0; i < l; i++) {
			if (i % s == 0) {
				for (int m = 0; m < M.length + 2; m++)
					System.out.print("--");
				System.out.println();
			}
			for (int j = 0; j < l; j++) {
				if (j % s == 0)
					System.out.print("|");
				System.out.print(M[i][j] + " ");
			}
			System.out.print("|");
			System.out.println();

		}
		for (int m = 0; m < M.length + 2; m++)
			System.out.print("--");
		System.out.println("");
	}

}
