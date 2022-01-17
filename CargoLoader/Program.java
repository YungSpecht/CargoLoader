package CargoLoader;

import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.MatrixCreation.Parcels.Parcel;
import java.util.ArrayList;

/**
 * Class that starts the Program and contains methods that can be called by the GUI
 * @author Kai Kitagawa-Jones
 * @author Niklas Druba
 * @author Yu Fei
 * @author Cui Qi
 * @author Anton Zegelaar
 * @author Irena Shaleva
 * @author Guilherme Pereira Sequeira
 */

public class Program {
	public static int[] parcelAmounts;
	public static char parcelMode;
	public static char coverMode;
    public static final int maxResults = 10000;
    public static final int[][] boxMatrix = MatrixCreator.create_matrix('b');
    public static final int[][] pentoMatrix = MatrixCreator.create_matrix('p');
    public static int finalAmount;
    public static int finalValue;

    public static void main(String[] args) {
        GUI.call();
    }

     /**
	 * A method that calls the required methods based on the input entered in the GUI by the user
	 * @return A 3D representation of the cargo space resulting from the users input
	 */
        public static int[][][] solve(){
        ArrayList<Integer> result = new ArrayList<Integer>();
        LinkedList list;
        switch(parcelMode){
            case 'b' : list = new LinkedList(boxMatrix); break;
            case 'p' : list = new LinkedList(pentoMatrix); break;
            default: list = null;
        }
        try {
            result = list.exactCover(maxResults);
        } catch (Exception noSolutionFound) {
            System.out.println("No solution found in time");
            return new int[33][8][5];
        }
        finalAmount = result.size();
        finalValue = list.get_value();
        switch(parcelMode){
            case 'b' : return Parcel.build_container(boxMatrix, result);
            case 'p' : return Parcel.build_container(pentoMatrix, result);
            default: return new int[0][0][0];
        }
    }
}
