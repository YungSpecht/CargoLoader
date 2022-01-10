package CargoLoader;

import java.util.ArrayList;
import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.MatrixCreation.Parcels.Parcel;

public class Program {
    public static final int[] parcelAmounts = {100, 100, 100};

    public static void main(String[] args) {
        //create 2d cover matrix
        int[][] matrix = MatrixCreator.create_matrix();
        
        //create linked list from matrix and run DLX on it
        LinkedList list = new LinkedList(matrix);
        ArrayList<Integer> result = list.exactCover(500);

        //build the resulting cargo container
        int[][][] finalContainer = Parcel.build_container(matrix, result);
        GUI.call(finalContainer);
        
        System.out.println("amount of packed parcels: " + result.size());
        System.out.println("value of parcels: " + list.get_value());
    }
}
