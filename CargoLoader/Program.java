package CargoLoader;

import java.util.Arrays;

import CargoLoader.AlgorithmX.ALXSolver;
import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.Utils.MatrixUtils;

public class Program {
    private final static int[] parcelAmounts = {2, 2, 2};
    private final static int[] parcelValues = {1, 1, 1};

    public static void main(String[] args) {
        int[][] matrix = MatrixCreator.create_matrix(parcelAmounts[0], parcelAmounts[1], parcelAmounts[2]);

        ALXSolver packer = new ALXSolver(parcelAmounts, parcelValues);
        int[] solutions = packer.algoX_loader(matrix);

        int[][][] finalContainer = MatrixUtils.build_container(matrix, solutions, parcelAmounts);
    }
}
