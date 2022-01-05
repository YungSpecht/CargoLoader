package CargoLoader.MatrixCreation.Parcels;

public class BBlock extends Parcel{
    
    private static int[][][] shape = {
        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }},

        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }},
    };

    public BBlock(){
        super(shape);
    }
}
