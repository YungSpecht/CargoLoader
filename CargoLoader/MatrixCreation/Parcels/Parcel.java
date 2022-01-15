package CargoLoader.MatrixCreation.Parcels;

import java.util.ArrayList;

public class Parcel {
    private int lenPos;
    private int rowPos;
    private int colPos;
    private int[][][] shape;

    public Parcel(int[][][] shape){
        lenPos = 0;
        rowPos = 0;
        colPos = 0;
        this.shape = shape;
    }

    public int[][][] get_shape(){
        return shape;
    }

    public int get_length_position(){
        return lenPos;
    }

    public int get_row_position(){
        return rowPos;
    }

    public int get_col_position(){
        return colPos;
    }

    public int get_parcel_length(){
        return shape.length;
    }

    public  int get_parcel_rows(){
        return shape[0].length;
    }

    public int get_parcel_cols(){
        return shape[0][0].length;
    }
    
    public void increase_length_position(){
        lenPos++;
    }

    public void increase_row_position(){
        rowPos++;
    }

    public void increase_col_position(){
        colPos++;
    }

    public void set_length_position(int Pos){
        lenPos = Pos;
    }

    public void set_row_position(int Pos){
        rowPos = Pos;
    }

    public void set_col_position(int Pos){
        colPos = Pos;
    }

    public void reset_coordinates(){
        lenPos = 0;
        rowPos = 0;
        colPos = 0;
    }

    /* returns a 3d array representation of the cargo container with the parcel placed inside of it
     * according to it's coordinates 
     */
    public int[] place_parcel(){
        int[][][] result = new int[33][8][5];
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape[i].length; j++){
                for(int k = 0; k < shape[i][j].length; k++){
                    result[i + lenPos][j + rowPos][k + colPos] = shape[i][j][k];
                }
            }
        }
        return transform_container(result);
    }

    //rotate parcel once around the z-axis
    public void rotate_z(){
        int[][][] result = new int[shape[0][0].length][shape[0].length][shape.length];
        for(int i = 0; i < shape[0].length; i++){
            for(int j = 0; j < shape[0][0].length; j++){
                for(int k = 0; k < shape.length; k++){
                    result[j][i][result[0][0].length - 1 - k] = shape[k][i][j];
                }
            }
        }
        shape = result;
    }
   
    //rotate parcel around the x-axis
    public void rotate_x(){
        int[][][] result = new int[shape.length][shape[0][0].length][shape[0].length];
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape[0][0].length; j++){
                for(int k = 0; k < shape[0].length; k++){
                    result[i][j][result[0][0].length - 1 - k] = shape[i][k][j];
                }
            }
        }
        shape = result;
    }

    //rotate parcel around the y-axis
    public void rotate_y(){
        int[][][] result = new int[shape[0].length][shape.length][shape[0][0].length];
        for(int i = 0; i < shape[0][0].length; i++){
            for(int j = 0; j < shape.length; j++){
                for(int k = 0; k < shape[0].length; k++){
                    result[result.length - 1 - k][j][i] = shape[j][k][i];
                }
            }
        }
        shape = result;
    }
    

    //Turn a 3D array into a 1D array
    private int[] transform_container(int[][][] container){
        int[] result = new int[container.length * container[0].length * container[0][0].length];
        int counter = 0;
        for(int i = 0; i < container.length; i++){
            for(int j = 0; j < container[0].length; j++){
                for(int k = 0; k < container[0][0].length; k++){
                    result[counter] = container[i][j][k];
                    counter++;
                }
            }
        }
        return result;
    }

    //Assemble the resulting cargo container based on the matrix and result from the Algorithm
    public static int[][][] build_container(int[][] matrix, ArrayList<Integer> solution){
        int[][][] result = new int[33][8][5];
        for(int i = 0; i < solution.size(); i++){
            int[] row = matrix[solution.get(i)];
            int count = 0;
            int[][][] temp = new int[33][8][5];
            for(int j = 0; j < result.length; j++){
                for(int k = 0; k < result[0].length; k++){
                    for(int l = 0; l < result[0][0].length; l++){
                        temp[j][k][l] = row[count];
                        count++;
                    }
                }
            }
            for(int j = 0; j < result.length; j++){
                for(int k = 0; k < result[0].length; k++){
                    for(int l = 0; l < result[0][0].length; l++){
                        if(temp[j][k][l] != 0){
                            result[j][k][l] = temp[j][k][l];
                        }
                    }
                }
            }

        }
        return result;
    }
}
