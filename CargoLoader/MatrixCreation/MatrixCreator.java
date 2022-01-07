package CargoLoader.MatrixCreation;

import CargoLoader.Utils.*;
import CargoLoader.MatrixCreation.Parcels.*;

public class MatrixCreator {
    public static int[][] create_matrix(){
        System.out.println("\nCreating 2D exact cover matrix ...");
        int[][] result = new int[0][0];

        //create an instance of each parcel type
        ABlock aBlock = new ABlock();
        BBlock bBlock = new BBlock();
        CBlock cBlock = new CBlock();

        //for each parcel type, simulate all placements insde the container
        int[][] simA = simulate_placements(aBlock);
        //System.out.println("simA " + simA.length);
        int[][] simB = simulate_placements(bBlock);
        //System.out.println("simB " + simB.length);
        int[][] simC = simulate_placements(cBlock);
        //System.out.println("simC " + simC.length);

        result = ArrayUtils.add(result, simA);
        result = ArrayUtils.add(result, simB);
        result = ArrayUtils.add(result, simC);

        System.out.println("Done");
        System.out.println("Exact cover matrix size: " + result.length + "x" + result[0].length);
        return result;
    }

    private static int[][] simulate_placements(Parcel parcel){
        int[][] result = new int[0][0];

        //depending on the parcel type, call the create_matrix() method for every possible orientation
        if(parcel instanceof CBlock){
            result = ArrayUtils.add(result, create_matrix(parcel));
            return result;
        }
        else if(parcel instanceof ABlock){
            result = ArrayUtils.add(result, create_matrix(parcel)); 
            parcel.rotate_parcel();
            result = ArrayUtils.add(result, create_matrix(parcel));
            parcel.tip_parcel_forward(1);
            result = ArrayUtils.add(result, create_matrix(parcel));
            return result;
        }
        else{
            result = ArrayUtils.add(result, create_matrix(parcel)); 
            parcel.tip_parcel_right(1);
            result = ArrayUtils.add(result, create_matrix(parcel));
            parcel.tip_parcel_right(3);
            parcel.tip_parcel_forward(1);
            result = ArrayUtils.add(result, create_matrix(parcel));
            parcel.tip_parcel_forward(1);
            parcel.rotate_parcel();
            result = ArrayUtils.add(result, create_matrix(parcel)); 
            parcel.tip_parcel_right(1);
            result = ArrayUtils.add(result, create_matrix(parcel));
            parcel.tip_parcel_right(3);
            parcel.tip_parcel_forward(1);
            result = ArrayUtils.add(result, create_matrix(parcel));

            return result;
        }
    }


    private static int[][] create_matrix(Parcel parcel){
        int[][] result = new int[0][0];
        parcel.set_length_position(0);
        parcel.set_col_position(0);
        parcel.set_row_position(0);

        //place the parcel inside the contatainer in every possible position
        while(parcel.get_length_position() <= 33 - parcel.get_parcel_length()){
            while(parcel.get_row_position() <= 8 - parcel.get_parcel_rows()){
                while(parcel.get_col_position() <= 5 - parcel.get_parcel_cols()){
                    int[][][] temp = parcel.place_parcel();
                    //convert the resulting 3d container array into a 1d array and append the new row to the matrix
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
