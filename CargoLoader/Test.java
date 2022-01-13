package CargoLoader;

import java.util.ArrayList;
import java.util.Arrays;

import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.MatrixCreation.Parcels.Parcel;
import CargoLoader.MatrixCreation.Parcels.Representations;

public class Test {
    public static void main(String[] args) {
        
        //create 2d cover matrix
        int[][] matrix = MatrixCreator.create_matrix('p');
        System.out.println(matrix.length);
        
    
        //create linked list from matrix and run DLX on it
        LinkedList2 list = new LinkedList2(matrix);
        ArrayList<Integer> result = list.exactCover(10000);

        //build the resulting cargo container
        int[][][] finalContainer = Parcel.build_container(matrix, result);
        GUI.call(finalContainer);

    }

    static void print(Parcel parcel){
        for(int i = 0; i < parcel.get_parcel_length(); i++){
            for(int  j = 0; j < parcel.get_parcel_rows(); j++){
                System.out.println(Arrays.toString(parcel.get_shape()[i][j]));
            }
            System.out.println();
        }
    }
}
