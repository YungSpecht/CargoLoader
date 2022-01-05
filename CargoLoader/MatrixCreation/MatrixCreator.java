package CargoLoader.MatrixCreation;

import CargoLoader.Utils.*;
import CargoLoader.MatrixCreation.Parcels.*;

public class MatrixCreator {
    public static int[][] create_matrix(int A, int B, int C){
        int[][] result = new int[0][0];
        int width = A + B + C;

        ABlock aBlock = new ABlock();
        BBlock bBlock = new BBlock();
        CBlock cBlock = new CBlock();

        int[][] simA = simulate_placements(aBlock);
        int[][] simB = simulate_placements(bBlock);
        int[][] simC = simulate_placements(cBlock);

        for(int i = 0; i < width; i++){
            if(i < A){
                int[][] preA = MatrixUtils.create_pre_matrix(simA.length, width, i);
                result = ArrayUtils.add(result, ArrayUtils.add_perpendicular(preA, simA));
            }
            else if(i < A + B){
                int[][] preB = MatrixUtils.create_pre_matrix(simB.length, width, i);
                result = ArrayUtils.add(result, ArrayUtils.add_perpendicular(preB, simB));
            }
            else{
                int[][] preC = MatrixUtils.create_pre_matrix(simC.length, width, i);
                result = ArrayUtils.add(result, ArrayUtils.add_perpendicular(preC, simC));
            }
        }
        return result;
    }

    private static int[][] simulate_placements(Parcel parcel){
        int[][] result = new int[0][0];
        if(parcel.get_parcel_length() == parcel.get_parcel_cols() && parcel.get_parcel_cols() == parcel.get_parcel_rows()){
            result = ArrayUtils.add(result, matrix_action(parcel));
            return result;
        }
        else if(parcel.get_parcel_length() == parcel.get_parcel_rows()){
            result = ArrayUtils.add(result, matrix_action(parcel)); 
            parcel.rotate_parcel_right(1);
            result = ArrayUtils.add(result, matrix_action(parcel));
            return result;
        }
        else{
            result = ArrayUtils.add(result, matrix_action(parcel)); 
            parcel.rotate_parcel_right(1);
            result = ArrayUtils.add(result, matrix_action(parcel));
            parcel.rotate_parcel_right(3);
            parcel.rotate_parcel_forward();;
            result = ArrayUtils.add(result, matrix_action(parcel));
            return result;
        }
    }


    private static int[][] matrix_action(Parcel parcel){
        int[][] result = new int[0][0];
        parcel.set_length_position(0);
        parcel.set_col_position(0);
        parcel.set_row_position(0);
        while(parcel.get_length_position() <= 35 - parcel.get_parcel_length()){
            while(parcel.get_row_position() <= 8 - parcel.get_parcel_rows()){
                while(parcel.get_col_position() <= 5 - parcel.get_parcel_cols()){
                    int[][][] temp = parcel.place_parcel();
                    int[] row = MatrixUtils.create_matrix_row(temp);
                    result = ArrayUtils.add_element(result, row);
                    parcel.increase_col_position();
                }
                parcel.set_col_position(0);
                parcel.increase_row_position();
            }
            
            parcel.set_row_position(0);
            parcel.increase_length_position();
        }
        return result;
    }
}
