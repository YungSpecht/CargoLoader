package CargoLoader.MatrixCreation.Parcels;

public class ABlock extends Parcel{

    private static int[][][] shape = {
        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }},

        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }}
    };

    public ABlock() {
        super(shape);
    }
    
}
