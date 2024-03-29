package CargoLoader;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a doubly-linked list, and contains methods to find the exact cover to the list.
 * @author Kai Kitagawa-Jones
 * @author Niklas Druba
 * @author Yu Fei
 * @author Cui Qi
 */

public class LinkedList{
	ColumnObject header;
	int[][] matrix;
	ArrayList<Integer> specialCase;
	ArrayList<Integer> partialSolution;
	ArrayList<Integer> bestSolution;
	int[] parcelCount;

	int maxValue;
	int solutionsFound;
	int maxResults;
	double timeCheckPoint;
	boolean check;

	/**
	 * Creates a LinkedListPentos object. A doubly-linked list is created based on the provided matrix, and the
	 * exactCover() method can be called immediately.
	 * @param matrix An 2D integer array that Algorithm X should be applied to. 0 represents an empty cell, and a non-0
	 *     number represents a occupied cell.
	 * @return A fully initialized LinkedListPentos object
	 */

	public LinkedList(int[][] matrix) {
		partialSolution = new ArrayList<Integer>();
		bestSolution = new ArrayList<Integer>();
		specialCase = new ArrayList<Integer>();
		parcelCount = new int[3];
		maxValue = 0;
		this.matrix = matrix;
		createList();
	}

	/**
	 * Returns the best solution to the doubly-linked list created in the constructor method within a specified number
	 * of solutions.
	 * @param maxResults The number of results to be processed before terminating.
	 * @return An ArrayList containing the row numbers of the rows in the array provided in the constructor that make up
	 *     the best solution.
	 * @throws Exception
	 */
	public ArrayList<Integer> exactCover(int maxResults) throws Exception {
		this.maxResults = maxResults;
		timeCheckPoint = System.currentTimeMillis();
		check = false;
		search();
		return bestSolution;
	}

	void createList() {
		header = new ColumnObject(-1);

		// add column objects
		ColumnObject prevCol = header;
		for (int i = 0; i < matrix[0].length; i++) {
			ColumnObject currCol = new ColumnObject(i);
			prevCol.addRight(currCol);
			prevCol = currCol;
		}

		// add data objects
		for (int i = 0; i < matrix.length; i++) {
			DataObject prev = null;
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] != 0) {
					ColumnObject col = getCol(j);
					DataObject newNode = new DataObject(col, i);
					if (prev == null) prev = newNode;
					col.U.addBelow(newNode);
					prev.addRight(newNode);
					col.size++;
				}
			}
		}
	}

	int search() throws Exception {
		if(valueOf(partialSolution) > valueOf(specialCase)){
			specialCase = new ArrayList<>(partialSolution);
		}
		if (solutionsFound >= maxResults) return 0;
		switch (Program.coverMode) {
			case 'e':
				if (header.R == header) {
					if (valueOf(partialSolution) > valueOf(bestSolution)) {
						bestSolution = new ArrayList<>(partialSolution);
						maxValue = valueOf(partialSolution);
					}
					System.out.print("\r[" + (solutionsFound + 1) + "/" + maxResults + "] solutions found");
					solutionsFound++;
					return valueOf(partialSolution);
				}
				break;
			case 'p':
				if (header.R == header || Arrays.stream(parcelCount).sum() == Arrays.stream(Program.parcelAmounts).sum() || allColumnsEmpty()) {
					if (valueOf(partialSolution) > valueOf(bestSolution)) {
						bestSolution = new ArrayList<>(partialSolution);
						maxValue = valueOf(partialSolution);
					}
					System.out.print("\r[" + (solutionsFound + 1) + "/" + maxResults + "] solutions found");
					solutionsFound++;
					return valueOf(partialSolution);
				}
				if(Program.parcelMode == 'p' && check){
					if (valueOf(partialSolution) > valueOf(bestSolution)) {
						bestSolution = specialCase;
						maxValue = valueOf(specialCase);
					}
					System.out.print("\r[" + (solutionsFound + 1) + "/" + maxResults + "] solutions found");
					solutionsFound++;
					return valueOf(specialCase);
				}
				break;
			default : System.out.println("Invalid mode");
		}
		if(System.currentTimeMillis() - timeCheckPoint > 4000 && GUI.exactCover && solutionsFound == 0){
			Exception noSolutionFound = new Exception();
			throw noSolutionFound;
		}else if(System.currentTimeMillis() - timeCheckPoint > 10000 && solutionsFound == 0){
			check = true;
		}

		ColumnObject c;
		switch(Program.parcelMode){
			case 'p' : c = smallestColumnPento(); break;
			case 'b' : c = smallestColumnBox(); break;
			default : c = null;
		}
		c.cover();

		int bestValue = -1;
		for (DataObject i = c.D; i != c; i = i.D) {
			// parcel Type can be 1(A Box/L Pento), 2(B Box/P Pento) or 3(C Box/T Pento)
			int parcelType = get_parcel_type(i.row);
			// this if-statement checks wether the desired amount for the parcel type of the current row has already
			// been reached. If so it will not add the row to the partial solution and go on to the next one.
			if (parcelCount[parcelType - 1] < Program.parcelAmounts[parcelType - 1]) {
				partialSolution.add(i.row);
				parcelCount[parcelType - 1]++;
				for (DataObject j = i.R; j != i; j = j.R) j.C.cover();
				int newValue = search();
				partialSolution.remove(partialSolution.size() - 1);
				parcelCount[parcelType - 1]--;
				for (DataObject j = i.L; j != i; j = j.L) j.C.uncover();
				if (newValue <= bestValue) break;
				else bestValue = newValue;
			}
		}

		c.uncover();
		return bestValue != -1 ? bestValue : 0;
	}

	ColumnObject smallestColumnPento() {
		int minSize = Integer.MAX_VALUE;
		ColumnObject minCol = null;
		for (ColumnObject i = (ColumnObject)header.R; i != header; i = (ColumnObject)i.R) {
			if (i.size < minSize) {
				minSize = i.size;
				minCol = i;
			}
		}
		return minCol;
	}

	ColumnObject smallestColumnBox() {
		int minSize = Integer.MAX_VALUE;
		ColumnObject minCol = null;
		for (ColumnObject i = (ColumnObject)header.R; i != header; i = (ColumnObject)i.R) {
			if (i.size < minSize && i.size != 0) {
				minSize = i.size;
				minCol = i;
			}
		}
		return minCol;
	}

	boolean allColumnsEmpty() {
		for (ColumnObject i = (ColumnObject)header.R; i != header; i = (ColumnObject)i.R) {
			if (i.size != 0) return false;
		}
		return true;
	}

	ColumnObject getCol(int id) {
		for (ColumnObject i = (ColumnObject)header.R; i != header; i = (ColumnObject)i.R) {
			if (i.id == id) return i;
		}
		return null;
	}

	int matrix() {
		return valueOf(partialSolution);
	}

	// Evaluates the value of the current partial solution
	int valueOf(ArrayList<Integer> rows) {
		int result = 0;
		for (int i = 0; i < rows.size(); i++) {
			result += get_parcel_type(rows.get(i)) + 2;
		}
		return result;
	}

	// Returns the parcel identifier of a matrix row
	int get_parcel_type(int row) {
		for (int i = 0; i < matrix[0].length; i++) {
			if (matrix[row][i] != 0) {
				return matrix[row][i];
			}
		}
		return 0;
	}

	int get_value() {
		return maxValue;
	}
}

class DataObject {
	DataObject L, R, U, D;
	ColumnObject C;
	int row;

	public DataObject(int row) {
		L = R = U = D = this;
		this.row = row;
	}

	public DataObject(ColumnObject c, int row) {
		this(row);
		C = c;
	}

	void addBelow(DataObject other) {
		other.D = D;
		other.U = this;
		other.D.U = other;
		D = other;
	}

	void addRight(DataObject other) {
		other.R = R;
		other.L = this;
		R.L = other;
		R = other;
	}
}

class ColumnObject extends DataObject {
	int size, id;

	public ColumnObject(int id) {
		super(-1);
		size = 0;
		this.id = id;
	}

	void cover() {
		L.R = R;
		R.L = L;

		for (DataObject i = D; i != this; i = i.D) {
			for (DataObject j = i.R; j != i; j = j.R) {
				j.U.D = j.D;
				j.D.U = j.U;
				j.C.size--;
			}
		}
	}

	void uncover() {
		for (DataObject i = U; i != this; i = i.U) {
			for (DataObject j = i.L; j != i; j = j.L) {
				j.C.size++;
				j.U.D = j;
				j.D.U = j;
			}
		}

		L.R = this;
		R.L = this;
	}
}