package CargoLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class LinkedList {
	ColumnObject header;
	int[][] matrix;

	ArrayList<Integer> partialSolution;
	ArrayList<Integer> bestSolution;
	int[] parcelCount;

	int maxValue;
	int solutionsFound;
	int maxResults;

	public LinkedList(int[][] matrix) {
		partialSolution = new ArrayList<Integer>();
		bestSolution = new ArrayList<Integer>();
		parcelCount = new int[3];
		maxValue = 0;
		this.matrix = matrix;
		createList();
	}

	public ArrayList<Integer> exactCover(int maxResults, char mode) {
		this.maxResults = maxResults;
		switch(mode){
			case 'b': search(); break;
			case 'p': search(); break;
		}
		return bestSolution;
	}

	//Create a Linked List from the 2D exact-cover matrix
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

	//Recursive function that finds the solution providing most packed value
	int search() {
		//The three base cases
		if(header.R == header){
			if (valueOf(partialSolution) > valueOf(bestSolution)){
				bestSolution = new ArrayList<>(partialSolution);
				maxValue = valueOf(partialSolution);
			}
			System.out.print("\r[" + (solutionsFound + 1) + "/" + maxResults + "] solutions found");
			solutionsFound++;
			return valueOf(partialSolution);
		}
		if (solutionsFound >= maxResults) return 0;
		if (allColumnsEmpty() || Arrays.stream(parcelCount).sum() == Arrays.stream(Program.parcelAmounts).sum()) {
			if (valueOf(partialSolution) > valueOf(bestSolution)){
				bestSolution = new ArrayList<>(partialSolution);
				maxValue = valueOf(partialSolution);
			}
			System.out.print("\r[" + (solutionsFound + 1) + "/" + maxResults + "] solutions found");
			solutionsFound++;
			return valueOf(partialSolution);
		}

		//finding the column that contains the least amount of nodes and covering it
		ColumnObject c = smallestColumn();
		c.cover();

		int bestValue = -1;
		//for each node in that column:
		for (DataObject i = c.D; i != c; i = i.D) {
			int parcelType = get_parcel_type(i.row);
			//add the corersponding row to the partial solution if specified amount hasn't been reached yet
			if(parcelCount[parcelType - 1] < Program.parcelAmounts[parcelType - 1]){
				partialSolution.add(i.row);
				parcelCount[parcelType - 1]++;
				for (DataObject j = i.R; j != i; j = j.R) j.C.cover();
				//recursive call on reduced linked list
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

	//Returns the column containing the least amount of nodes
	ColumnObject smallestColumn() {
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

	//Returns the parcel identifier of a matrix row
	int get_parcel_type(int row){
		for(int i = 0; i < matrix[0].length; i++){
			if(matrix[row][i] != 0){
				return matrix[row][i];
			}
		}
		return 0;
	}

	//Checks wether all remaining columns contain no nodes
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

	//Evaluates the value of the current partial solution
	int valueOf(ArrayList<Integer> rows) {
		int result = 0;
		for(int i = 0; i < rows.size(); i++){
			result += get_parcel_type(rows.get(i)) + 2;
		}
		return result;
	}

	int get_value(){
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
