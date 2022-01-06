package CargoLoader;

import java.util.ArrayList;
import java.util.Arrays;
import CargoLoader.Utils.*;
import CargoLoader.MatrixCreation.Parcels.*;
import CargoLoader.MatrixCreation.*;
import CargoLoader.MatrixCreation.MatrixCreator;

public class Test {

    private final static int[] parcelAmounts = {1, 1, 1};
    private final static int[] parcelValues = {1, 1, 1};

    public static void main(String[] args) {
        int[][] matrix = MatrixCreator.create_matrix(1, 1, 1);
        
        LinkedList list = new LinkedList(matrix, parcelAmounts, parcelValues);
        ArrayList<Integer> result = list.exactCover(1000);
        System.out.println(result.toString());
    }
}
