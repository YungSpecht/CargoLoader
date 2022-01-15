package CargoLoader;

import CargoLoader.MatrixCreation.MatrixCreator;
import CargoLoader.MatrixCreation.Parcels.Parcel;
import java.util.ArrayList;

public class Program {
	public static int[] parcelAmounts;
	public static char parcelMode;
	public static char coverMode = 'p';
    public static final int maxResults = 1000;
    public static int[][] boxMatrix;
    public static int[][] pentoMatrix;

    public static void main(String[] args) {
        boxMatrix = MatrixCreator.create_matrix('b');
        pentoMatrix = MatrixCreator.create_matrix('p');
        GUI.call();
    }

	public static int[][][] solveBox() {
		ArrayList<Integer> result = new ArrayList<Integer>();
        LinkedListBoxes list = new LinkedListBoxes(boxMatrix);
        result = list.exactCover(maxResults);
        return Parcel.build_container(boxMatrix, result);
    }

    public static int[][][] solvePento() {
		ArrayList<Integer> result = new ArrayList<Integer>();
        LinkedListPentos list = new LinkedListPentos(pentoMatrix);
        result = list.exactCover(maxResults);
        return Parcel.build_container(pentoMatrix, result);
    }
}
