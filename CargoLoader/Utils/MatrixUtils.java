package CargoLoader.Utils;

import java.util.ArrayList;
import java.util.Random;

public class MatrixUtils {
    public static int[] create_matrix_row(int[][][] container){
        int[] result = new int[0];
        for(int i = 0; i < container.length; i++){
            for(int j = 0; j < container[i].length; j++){
                result = ArrayUtils.add(result, container[i][j]);
            }
        }
        return result;
    }

    public static int[][] create_pre_matrix(int length, int width, int col){
        int[][] result = new int[length][width];
        for(int i = 0; i < length; i++){
            result[i][col] = 1;
        }
        return result;
    }

    public static void random_sort_parcels(int [][] temp){
        Random rand_1 = new Random();

        for (int i = 0; i < temp.length; i++) {
            int swap= rand_1.nextInt(temp.length);
            int [] temp_1 = temp[swap];
            temp[swap] = temp[i];
            temp[i] = temp_1;
        }
    }

    public static int[][][] build_container(int[][] matrix, ArrayList<Integer> solution){
        int[][][] result = new int[33][8][5];
        for(int i = 0; i < solution.size(); i++){
            int[] temp = matrix[solution.get(i)];
            int[][][] temp2 = reassemble_container(temp);
            result = merge_containers(result, temp2);
        }
        return result;
    }

    private static int[][][] reassemble_container(int[] in){
        int[][][] result = new int[33][8][5];
        int count = 0;
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                for(int k = 0; k < result[0][0].length; k++){
                    result[i][j][k] = in[count];
                    count++;
                }
            }
        }
        return result;
    }

    private static int[][][] merge_containers(int[][][] A, int[][][] B){
        int[][][] result = new int[33][8][5];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                for(int k = 0; k < result[0][0].length; k++){
                    if(B[i][j][k] != 0){
                        result[i][j][k] = B[i][j][k];
                    }
                    else{
                        result[i][j][k] = A[i][j][k];
                    }
                }
            }
        }
        return result;
    }
}
