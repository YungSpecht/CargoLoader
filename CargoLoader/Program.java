package CargoLoader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.MatrixCreation.Parcels.Parcel;

public class Program {
    public static final int[] parcelAmounts = {1000, 1000, 1000};
    public static final char mode = 'p';

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter("output.txt");
        
        //create 2d cover matrix
        int[][] matrix = MatrixCreator.create_matrix(mode);
        System.out.println(matrix.length);
        
    
        //create linked list from matrix and run DLX on it
        LinkedList list = new LinkedList(matrix);
        ArrayList<Integer> result = list.exactCover(1000, mode);

        //build the resulting cargo container
        int[][][] finalContainer = Parcel.build_container(matrix, result);

        for(int i = 0; i < finalContainer[0].length; i++){
            for(int j = 0; j < finalContainer[0][0].length; j++){
                for(int k = 0; k < finalContainer.length; k++){
                    out.print(finalContainer[k][i][j]);
                }
                out.println();
            }
            out.println();
            out.println();
        }   
        out.println("amount of packed parcels: " + result.size());
        out.println("value of parcels: " + list.get_value());
        System.out.println("amount of packed parcels: " + result.size());
        System.out.println("value of parcels: " + list.get_value());
        out.flush();
        out.close(); 
        GUI.call(finalContainer);
    }
}
