import java.util.Random;
import java.util.Vector;

public class SudokuGenetic {

	private static int[] initialGene;
	private int[] gene;
	private int fitnessValue;

	Random rand = new Random();

	public SudokuGenetic(int[] gene) {
		this.gene = gene;
		fitnessValue = getFitnessValue(this.gene);
	}

	static int getFitnessValue(int[] gene) {
		int fitness = 0;
		int[][] newGene = ConvertOneDToTwoD(gene);
		int[][] newInitialGene = ConvertOneDToTwoD(initialGene);

		for (int i = 0; i < newGene.length; i++) {
			boolean[] rowError = new boolean[newGene.length + 1];
			boolean[] colError = new boolean[newGene.length + 1];

			for (int j = 0; j < newGene.length; j++) {
				if (rowError[newGene[i][j]])
					fitness++;

				if (colError[newGene[j][i]])
					fitness++;

				if ((newInitialGene[i][j] != 0 && newInitialGene[i][j] != newGene[i][j]) || newGene[i][j] == 0) {
					fitness += 1000;
				}

				rowError[newGene[i][j]] = true;
				colError[newGene[j][i]] = true;
			}
		}

		int boxSize = (int) Math.sqrt(newGene.length);
		for (int i = 0; i < newGene.length; i += boxSize) {
			for (int j = 0; j < newGene.length; j += boxSize) {
				boolean[] boxError = new boolean[newGene.length + 1];

				for (int k = 0; k < boxSize; k++) {
					for (int l = 0; l < boxSize; l++) {
						if (boxError[newGene[i + k][j + l]])
							fitness++;
						boxError[newGene[i + k][j + l]] = true;
					}
				}
			}
		}

		return fitness;

	}

	public void mutation() {
		int[] mutation = this.gene;
		int index = rand.nextInt(gene.length);
		int value = rand.nextInt((int) Math.sqrt(gene.length)) + 1;

		mutation[index] = value;

		this.setGene(mutation);
	}

	public void crossover(SudokuGenetic sudoku1) {
		int[] gene1 = this.gene;
		int[] gene2 = sudoku1.gene;

		int start = rand.nextInt(gene1.length);
		int end = rand.nextInt(gene1.length - start) + start;

		int[] newGene1 = new int[gene1.length];
		int[] newGene2 = new int[gene1.length];
		for (int i = 0; i < gene1.length; i++) {
			newGene1[i] = (i >= start && i <= end) ? gene2[i] : gene1[i];
			newGene2[i] = (i >= start && i <= end) ? gene1[i] : gene2[i];
		}

		this.setGene(newGene1);
		sudoku1.setGene(newGene2);
	}

	public static SudokuGenetic bestSelection(Vector<SudokuGenetic> sudokus) {
		SudokuGenetic min = sudokus.firstElement();
		for (SudokuGenetic sudoku : sudokus)
			if (sudoku.getFitness() < min.fitnessValue)
				min = sudoku;

		return min;
	}

	public static SudokuGenetic randomSelection(Vector<SudokuGenetic> population) {
		int max = 0;
		for (SudokuGenetic sudoku : population)
			if (sudoku.fitnessValue > max)
				max = sudoku.fitnessValue;

		int sum = 0;
		for (SudokuGenetic sudoku : population)
			sum += max - sudoku.fitnessValue;

		double random = Math.random() * sum;
		int i;
		for (i = 0; i < population.size() && random > 0; i++) {
			random -= max - population.get(i).fitnessValue;
		}
		
		if (i == 0) 
			return population.get(i);

		return population.get(i - 1);

	}

	public static int[][] ConvertOneDToTwoD(int[] oneD) {
		int[][] twoD = new int[(int) Math.sqrt(oneD.length)][(int) Math.sqrt(oneD.length)];
		for (int i = 0; i < oneD.length; i++)
			twoD[i / twoD.length][i % twoD.length] = oneD[i];

		return twoD;
	}

	static int[] convertTwoDtoOneD(int[][] twoD) {
		int[] oneD = new int[twoD.length * twoD.length];

		for (int i = 0; i < oneD.length; i++)
			oneD[i] = twoD[i / twoD.length][i % twoD.length];

		return oneD;
	}

	public int[] getInitial() {
		return initialGene;
	}

	public static void setInitialGene(int[] initialGene) {
		SudokuGenetic.initialGene = initialGene;
	}

	public int[] getGene() {
		return gene;
	}

	public void setGene(int[] gene) {
		this.gene = gene;
		fitnessValue = getFitnessValue(gene);
	}

	public int getFitness() {
		return fitnessValue;
	}

	public void setFitness(int fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

}
