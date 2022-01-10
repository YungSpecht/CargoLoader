package CargoLoader.MatrixCreation.Parcels;

public class BBlock extends Parcel{
    /* Parcel of the Type B
     * Dimensions: 2m x 1m x 1,5m
     * Identifier: 2
     */
    private static int[][][] shape = {
        {{ 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 }},

        {{ 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 }},
    };

    public BBlock(){
        super(shape, 2);
    }
}
