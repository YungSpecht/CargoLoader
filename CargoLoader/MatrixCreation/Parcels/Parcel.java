package CargoLoader.MatrixCreation.Parcels;

import java.util.ArrayList;

/**
 * Represents a Parcel that can be placed inside the cargo space
 * @author Kai Kitagawa-Jones
 * @author Niklas Druba
 * @author Cui Qi
 * @author Yu Fei
 */
public class Parcel {
    private int depPos;
    private int rowPos;
    private int colPos;
    private int[][][] shape;
    private char id;

     /**
	 * Creates a Parcel object. A parcel is created based on the provided shape matrix and a parcel-type identifier
	 * @param shape a 3D int array that servers as a representation of the shape of the parcel
     * @param id a char that serves as an identifier of the parcel type
	 * @return A fully initialized parcel object
	 */
    public Parcel(int[][][] shape, char id){
        depPos = 0;
        rowPos = 0;
        colPos = 0;
        this.shape = shape;
        this.id = id;
    }

    public char get_id(){
        return id;
    }

    public int[][][] get_shape(){
        return shape;
    }

    public int get_depth_position(){
        return depPos;
    }

    public int get_row_position(){
        return rowPos;
    }

    public int get_col_position(){
        return colPos;
    }

    public int get_parcel_depth(){
        return shape.length;
    }

    public  int get_parcel_height(){
        return shape[0].length;
    }

    public int get_parcel_width(){
        return shape[0][0].length;
    }
    
    public void increase_depth_position(){
        depPos++;
    }

    public void increase_height_position(){
        rowPos++;
    }

    public void increase_width_position(){
        colPos++;
    }

    public void set_depth_position(int Pos){
        depPos = Pos;
    }

    public void set_height_position(int Pos){
        rowPos = Pos;
    }

    public void set_width_position(int Pos){
        colPos = Pos;
    }

    /**
	 * Resets the depth, height and width coordinates of the parcel inside the cargo space
	 */
    public void reset_coordinates(){
        depPos = 0;
        rowPos = 0;
        colPos = 0;
    }

    /**
	 * Places the cargo inside the cargo space according to the parcels current coordinates
	 * @return a 3D-int array of the cargo space with the parcel placed inside of according to its coordinates
	 */
    public int[] place_parcel(){
        int[][][] result = new int[33][8][5];
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape[i].length; j++){
                for(int k = 0; k < shape[i][j].length; k++){
                    result[i + depPos][j + rowPos][k + colPos] = shape[i][j][k];
                }
            }
        }
        return transform_container(result);
    }

    /**
	 * Rotates the shape instance field of the parcel object once along the z-axis
	 */
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
   
    /**
	 * Rotates the shape instance field of the parcel object once along the x-axis
	 */
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

    /**
	 * Rotates the shape instance field of the parcel object once along the y-axis
	 */
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

    /**
	 * Builds the 3D representation of the cargo space according to the rows of the 2D exact cover matrix returned by Algorithm X
	 * @param matrix The 2D exact cover matrix initially created by the create_matrix() method
     * @param solution ArrayList of rows of the exact cover matrix that have been selected by ALgorithm X
	 * @return A 3D int array representing the cargo space with all the desired parcel placed inside of it
	 */
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
