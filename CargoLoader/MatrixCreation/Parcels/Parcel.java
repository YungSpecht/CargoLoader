package CargoLoader.MatrixCreation.Parcels;

public class Parcel {
    private int lenPos;
    private int rowPos;
    private int colPos;
    private int[][][] shape;
    private int id;

    public Parcel(int[][][] shape, int id){
        lenPos = 0;
        rowPos = 0;
        colPos = 0;
        this.shape = shape;
        this.id = id;
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

    /* returns a 3d array representation of the cargo container with the parcel placed inside of it
     * according to it's coordinates 
     */
    public int[][][] place_parcel(){
        int[][][] result = new int[35][8][5];
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape[i].length; j++){
                for(int k = 0; k < shape[i][j].length; k++){
                    result[i + lenPos][j + rowPos][k + colPos] = id;
                }
            }
        }
        return  result;
    }

    //rotate parcel once around the y-axis
    public void rotate_parcel(){
        int[][][] result = new int[shape[0][0].length][shape[0].length][shape.length];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[i].length; j++){
                for(int k = 0; k < result[i][j].length; k++){
                    result[i][j][k] = id;
                }
            }
        }
        shape = result;
    }
   
    //rotate parcel around the x-axis
    public void tip_parcel_right(int times){
        for(int y = 0; y < times; y++){
            int[][][] result = new int[shape.length][shape[0][0].length][shape[0].length];
            for(int i = 0; i < result.length; i++){
                for(int j = 0; j < result[i].length; j++){
                    for(int k = 0; k < result[i][j].length; k++){
                        result[i][j][k] = id;
                    }
                }
            }
            shape = result;
        }
    }

    //rotate parcel around the y-axis
    public void tip_parcel_forward(int times){
        for(int y = 0; y < times; y++){
            int[][][] result = new int[shape[0].length][shape.length][shape[0][0].length];
            for(int i = 0; i < result.length; i++){
                for(int j = 0; j < result[i].length; j++){
                    for(int k = 0; k < result[i][j].length; k++){
                        result[i][j][k] = id;
                    }
                }
            }
            shape = result;
        }
    }
}
