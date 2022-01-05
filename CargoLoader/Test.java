package CargoLoader;

import java.util.Arrays;
import CargoLoader.Utils.*;
import CargoLoader.MatrixCreation.Parcels.*;
import CargoLoader.MatrixCreation.*;

import CargoLoader.AlgorithmX.ALXSolver;
import CargoLoader.MatrixCreation.MatrixCreator;

public class Test {

    private final static int[] parcelAmounts = {1, 1, 1};
    private final static int[] parcelValues = {2, 3, 4};

    public static void main(String[] args) {
        int[] test = {1, 2, 3, 4, 5, 6, 7};

        test = ArrayUtils.cutoff_left(test, 2);
        System.out.println(Arrays.toString(test));
    }
}
