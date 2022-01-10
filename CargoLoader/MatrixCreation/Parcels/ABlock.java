package CargoLoader.MatrixCreation.Parcels;

public class ABlock extends Parcel{
    /* Parcel of the Type A
     * Dimensions: 2m x 1m x 1m
     * Identifier: 1
     */
    private static int[][][] shape = {
        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }},

        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }}
    };

    public ABlock() {
        super(shape, 1);
    }
}
