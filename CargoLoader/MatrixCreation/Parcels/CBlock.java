package CargoLoader.MatrixCreation.Parcels;

public class CBlock extends Parcel{

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
