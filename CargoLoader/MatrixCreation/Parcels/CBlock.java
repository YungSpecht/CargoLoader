package CargoLoader.MatrixCreation.Parcels;

public class CBlock extends Parcel{
    /* Parcel of the Type C
     * Dimensions: 1,5m x 1,5m x 1,5m
     * Identifier: 3
     */
    private static int[][][] shape = {
        {{ 3 , 3 , 3 },
         { 3 , 3 , 3 },
         { 3 , 3 , 3 }},

        {{ 3 , 3 , 3 },
         { 3 , 3 , 3 },
         { 3 , 3 , 3 }},

        {{ 3 , 3 , 3 },
         { 3 , 3 , 3 },
         { 3 , 3 , 3 }},
    };

    public CBlock() {
        super(shape, 3);
    }
}
