package CargoLoader;

import java.util.ArrayList;
import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.Utils.MatrixUtils;

public class Program {
    private final static int[] parcelAmounts = {3, 3, 3};
    private final static int[] parcelValues = {1, 1, 1};

    public static void main(String[] args) {
        int[][] matrix = MatrixCreator.create_matrix(parcelAmounts[0], parcelAmounts[1], parcelAmounts[2]);
        //System.out.println(matrix.length);
        
        LinkedList list = new LinkedList(matrix, parcelAmounts, parcelValues);
        ArrayList<Integer> result = list.exactCover(1000);

        int[][][] finalContainer = MatrixUtils.build_container(matrix, result, parcelAmounts);

        for(int r = 0; r < finalContainer[0].length; r++){
            for(int c = 0; c < finalContainer[0][0].length; c++){
                for(int l = 0; l < finalContainer.length; l++){
                    System.out.print(finalContainer[l][r][c]);
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("amount of packed parcels: " + result.size());
        System.out.println("value of parcels: " + list.get_score());
        
    }
}
