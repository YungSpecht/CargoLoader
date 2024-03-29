package CargoLoader.MatrixCreation;

import java.util.ArrayList;
import CargoLoader.MatrixCreation.Parcels.*;

/**
 * A class that contains methods for creating a two dimensional exact cover matrix, where each row
 * represents a parcel in a distinct orientation and placement inside the cargo space
 * @author Kai Kitagawa-Jones
 * @author Niklas Druba
 * @author Cui Qi
 * @author Yu Fei
 */


public class MatrixCreator {
    static Parcel aBlock = new Parcel(Database.A, 'A');
    static Parcel bBlock = new Parcel(Database.B, 'B');
    static Parcel cBlock = new Parcel(Database.C, 'C');
    static Parcel LPento = new Parcel(Database.L, 'L');
    static Parcel PPento = new Parcel(Database.P, 'P');
    static Parcel TPento = new Parcel(Database.T, 'T');


    /**
	 * Creates a 2D exact cover matrix, where each row represents a parcel in a distinct orientation and placement inside the cargo space
	 * @param mode Depending on whether you want ot create a matrix for box-parcels or pentominoes either 'b' or 'p'
	 * @return 2D int array, where each row represents a parcel in a distinct orientation and
     * placement inside the cargo space
	 */

    public static int[][] create_matrix(char mode){
        ArrayList<int[]> result = new ArrayList<int[]>();
        switch(mode){
            case 'b':
            simulate_placements(cBlock, result);
            simulate_placements(aBlock, result);
            simulate_placements(bBlock, result); break;
            case 'p':
            simulate_placements(TPento, result);
            simulate_placements(PPento, result);
            simulate_placements(LPento, result); break;
            default : System.out.println("Invalid Mode: enter either 'b' or 'p'");
        }
        return build_matrix(result);
    }

    //Call the add_rows() method for each distinct rotation
    private static void simulate_placements(Parcel parcel, ArrayList<int[]> result){
        ArrayList<int[][][]> orientations = new ArrayList<int[][][]>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    parcel.rotate_z();
                    if(orientation_is_distinct(parcel, orientations)){
                        add_rows(parcel, result);
                    }
                    orientations.add(parcel.get_shape());
                }
                parcel.rotate_x();
            }
            parcel.rotate_y();
        }
    }

    //Place the parcel in each possible position inside the container
    private static void add_rows(Parcel parcel, ArrayList<int[]> result){
        parcel.reset_coordinates();
        while(parcel.get_row_position() <= 8 - parcel.get_parcel_height()){
            while(parcel.get_col_position() <= 5 - parcel.get_parcel_width()){
                while(parcel.get_depth_position() <= 33 - parcel.get_parcel_depth()){
                    int[]row = parcel.place_parcel();
                    result.add(row);
                    parcel.increase_depth_position();
                }
                parcel.set_depth_position(0);
                parcel.increase_width_position();
            }
            parcel.set_width_position(0);
            parcel.increase_height_position();
        }
    }

    private static boolean orientation_is_distinct(Parcel parcel, ArrayList<int[][][]> orientations){
        for(int i = 0; i < orientations.size(); i++){
            int[][][] comparator = orientations.get(i);
            if(comparator.length == parcel.get_parcel_depth() && comparator[0].length == parcel.get_parcel_height() && comparator[0][0].length == parcel.get_parcel_width()){
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
    private static int[][] build_matrix(ArrayList<int[]> result){
        int[][] matrix = new int[result.size()][result.get(0).length];
        for(int r = 0; r < matrix.length; r++){
            matrix[r] = result.get(r);
        }
        return matrix;
    }
}
