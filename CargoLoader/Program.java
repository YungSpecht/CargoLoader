package CargoLoader;

import java.util.Arrays;

import CargoLoader.AlgorithmX.ALXSolver;
import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.Utils.MatrixUtils;

public class Program {
    private final static int[] parcelAmounts = {18, 18, 16};
    private final static int[] parcelValues = {3, 4, 5};

    public static void main(String[] args) {
        int[][] matrix = MatrixCreator.create_matrix(parcelAmounts[0], parcelAmounts[1], parcelAmounts[2]);

        ALXSolver packer = new ALXSolver(parcelAmounts, parcelValues);
        int[] solutions = packer.algoX_loader(matrix);

        int[][][] finalContainer = MatrixUtils.build_container(matrix, solutions, parcelAmounts);

        for(int r = 0; r < finalContainer[0].length; r++){
            for(int l = 0; l < finalContainer.length; l++){
                System.out.println(Arrays.toString(finalContainer[l][r]));
            }
            System.out.println();
        }
        System.out.println("amount of packed parcels: " + solutions.length);
        System.out.println("score: " + packer.get_score());
    }
}
