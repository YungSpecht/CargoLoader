package CargoLoader.MatrixCreation;

import java.util.ArrayList;
import CargoLoader.MatrixCreation.Parcels.*;

public class MatrixCreator {
    static ArrayList<int[]> result = new ArrayList<int[]>();
    static Parcel aBlock = new Parcel(Database.A);
    static Parcel bBlock = new Parcel(Database.B);
    static Parcel cBlock = new Parcel(Database.C);
    static Parcel LPento = new Parcel(Database.L);
    static Parcel PPento = new Parcel(Database.P);
    static Parcel TPento = new Parcel(Database.T);

    //Simulate placements for each parcel type
    public static int[][] create_matrix(char mode){
        switch(mode){
            case 'b': simulate_placements(cBlock); simulate_placements(aBlock); simulate_placements(bBlock); break;
            case 'p': simulate_placements(TPento); simulate_placements(PPento); simulate_placements(LPento); break;
            default : System.out.println("Invalid Mode: enter either 'b' or 'p'");
        }
        return build_matrix();
    }

    //Call the add_rows() method for each distinct rotation
    private static void simulate_placements(Parcel parcel){
        ArrayList<int[][][]> orientations = new ArrayList<int[][][]>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    parcel.rotate_y();
                    if(orientation_is_distinct(parcel, orientations)){
                        add_rows(parcel);
                    }
                    orientations.add(parcel.get_shape());
                }
                parcel.rotate_z();
            }
            parcel.rotate_x();
        }
    }

    //Place the parcel in each possible position inside the container
    private static void add_rows(Parcel parcel){
        parcel.reset_coordinates();
        while(parcel.get_row_position() <= 8 - parcel.get_parcel_rows()){
            while(parcel.get_col_position() <= 5 - parcel.get_parcel_cols()){
                while(parcel.get_length_position() <= 33 - parcel.get_parcel_length()){
                    int[]row = parcel.place_parcel();
                    result.add(row);
                    parcel.increase_length_position();
                }
                parcel.set_length_position(0);
                parcel.increase_col_position();
            }
            parcel.set_col_position(0);
            parcel.increase_row_position();
        }
    }

    private static boolean orientation_is_distinct(Parcel parcel, ArrayList<int[][][]> orientations){
        for(int i = 0; i < orientations.size(); i++){
            int[][][] comparator = orientations.get(i);
            if(comparator.length == parcel.get_parcel_length() && comparator[0].length == parcel.get_parcel_rows() && comparator[0][0].length == parcel.get_parcel_cols()){
                boolean check = true;
                for(int j = 0; j < comparator.length; j++){
                    for(int k = 0; k < comparator[0].length; k++){
                        for(int l = 0; l < comparator[0][0].length; l++){
                            if(parcel.get_shape()[j][k][l] != comparator[j][k][l]){
                                check = false;
                            }
                        }
                    }
                }
                if(check){
                    return false;
                }
            }
        }
        return true;
    }

    //Convert the ArrayList of rows into a 2D int array
    private static int[][] build_matrix(){
        int[][] matrix = new int[result.size()][result.get(0).length];
        for(int r = 0; r < matrix.length; r++){
            matrix[r] = result.get(r);
        }
        return matrix;
    }
}
