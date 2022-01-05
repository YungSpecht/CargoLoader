package CargoLoader.MatrixCreation.Parcels;

public class CBlock extends Parcel{

    private static int[][][] shape = {
        {{ 1 , 1 , 1 },
         { 1 , 1 , 1 },
         { 1 , 1 , 1 }},

        {{ 1 , 1 , 1 },
         { 1 , 1 , 1 },
         { 1 , 1 , 1 }},

        {{ 1 , 1 , 1 },
         { 1 , 1 , 1 },
         { 1 , 1 , 1 }},
    };

    public CBlock() {
        super(shape);
    }
    
}
