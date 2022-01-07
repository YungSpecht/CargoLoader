package CargoLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.Utils.MatrixUtils;

public class Program {
    public static ArrayList<Integer> bestSolution;
    public static int maxValue = 0;

    public static void main(String[] args) {
        //create 2d cover matrix
        int[][] matrix = MatrixCreator.create_matrix();
        for(int i = 0; i < 50; i++){
            MatrixUtils.random_sort_parcels(matrix);
        
            //create linked list from matrix and run DLX on it
            LinkedList list = new LinkedList(matrix);
            ArrayList<Integer> result = list.exactCover(10);
            if(list.get_max_value() > maxValue){
                bestSolution = result;
                maxValue = list.get_max_value();
            }

        }

        //build the resulting cargo container
        int[][][] finalContainer = MatrixUtils.build_container(matrix, bestSolution);
        
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
        
        System.out.println("amount of packed parcels: " + bestSolution.size());
        System.out.println("value of parcels: " + maxValue);
        
    }
}
