package CargoLoader;

import java.util.ArrayList;
import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.Utils.MatrixUtils;

public class Program {
    public static final int A = 1;
    public static final int B = 1;
    public static final int C = 1;

    public static void main(String[] args) {
        //create 2d cover matrix
        int[][] matrix = MatrixCreator.create_matrix();
        
        //create linked list from matrix and run DLX on it
        LinkedList list = new LinkedList(matrix);
        ArrayList<Integer> result = list.exactCover(5);

        //build the resulting cargo container
        int[][][] finalContainer = MatrixUtils.build_container(matrix, result);
        
        //print the cargo container
        for(int r = 0; r < finalContainer[0].length; r++){
            System.out.println("Layer: " + (r + 1));
            for(int c = 0; c < finalContainer[0][0].length; c++){
                for(int l = 0; l < finalContainer.length; l++){
                    System.out.print(finalContainer[l][r][c]);
                }
                System.out.println();
            }
            System.out.println();
        }
        
        System.out.println("amount of packed parcels: " + result.size());
        System.out.println("value of parcels: " + list.get_max_value());
        
    }
}
