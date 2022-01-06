package CargoLoader.MatrixCreation.Parcels;

public class BBlock extends Parcel{
    
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
