package CargoLoader.MatrixCreation.Parcels;

/**
 * A class that contains 3D int arrays representing the different Parcel Types
 * @author Kai Kitagawa-Jones
 * @author Niklas Druba
 * @author Cui Qi
 * @author Yu Fei
 */
public class Database {
    public static int[][][] A = {
        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }},

        {{ 1 , 1 , 1 , 1 },
         { 1 , 1 , 1 , 1 }}
    };

    public static int[][][] B = {
        {{ 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 }},

        {{ 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 },
         { 2 , 2 , 2 , 2 }},
    };

    public static int[][][] C = {
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

    public static int[][][] L = {
        {{ 1 , 0 , 0 },
         { 1 , 0 , 0 },
         { 1 , 1 , 1 }}
    };

    public static int[][][] P = {
        {{ 2 , 2 },
         { 2 , 2 },
         { 2 , 0 }}
    };

    public static int[][][] T = {
        {{ 3 , 3 , 3 },
         { 0 , 3 , 0 },
         { 0 , 3 , 0 }}
    };
}
