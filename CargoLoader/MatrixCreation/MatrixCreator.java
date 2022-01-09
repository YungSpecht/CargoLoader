package CargoLoader.MatrixCreation;

import java.util.ArrayList;
import CargoLoader.MatrixCreation.Parcels.*;

public class MatrixCreator {
    static ArrayList<int[]> result = new ArrayList<int[]>();

    static ABlock aBlock = new ABlock();
    static BBlock bBlock = new BBlock();
    static CBlock cBlock = new CBlock();

    public static int[][] create_matrix(){
        System.out.println("Creating 2D exact cover matrix ...");
        //for each parcel type, simulate all placements insde the container
        simulate_placements(cBlock);
        simulate_placements(aBlock);
        simulate_placements(bBlock);
        return build_matrix();
    }

    private static void simulate_placements(Parcel parcel){
        if(parcel instanceof ABlock){
            add_rows(parcel); parcel.rotate_z();
            add_rows(parcel); parcel.rotate_y();
            add_rows(parcel);
        }
        else if(parcel instanceof BBlock){
            add_rows(parcel); parcel.rotate_y();
            add_rows(parcel); parcel.rotate_z();
            add_rows(parcel); parcel.rotate_x();
            add_rows(parcel); parcel.rotate_y();
            add_rows(parcel); parcel.rotate_z();
            add_rows(parcel);
        }
        else{
            add_rows(parcel);
        }
    }

    private static void add_rows(Parcel parcel){
        parcel.reset_coordinates();
        while(parcel.get_length_position() <= 33 - parcel.get_parcel_length()){
            while(parcel.get_row_position() <= 8 - parcel.get_parcel_rows()){
                while(parcel.get_col_position() <= 5 - parcel.get_parcel_cols()){
                    int[]row = parcel.place_parcel();
                    result.add(row);
                    parcel.increase_col_position();
                }
                parcel.set_col_position(0);
                parcel.increase_row_position();
            }
            parcel.set_row_position(0);
            parcel.increase_length_position();
        }
    }

    private static int[][] build_matrix(){
        int[][] matrix = new int[result.size()][result.get(0).length];
        for(int r = 0; r < matrix.length; r++){
            matrix[r] = result.get(r);
        }
        return matrix;
    }
}
