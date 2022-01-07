package CargoLoader;

import java.util.ArrayList;

public class LinkedList {
	ColumnObject header;
	int[][] matrix;

	ArrayList<Integer> partialSolution;
	ArrayList<Integer> bestSolution;

	int maxValue;
	int solutionsFound;
	int maxResults;

	public LinkedList(int[][] matrix) {
		partialSolution = new ArrayList<Integer>();
		bestSolution = new ArrayList<Integer>();
		maxValue = 0;
		this.matrix = matrix;
		createList();
	}

	public ArrayList<Integer> exactCover(int maxResults) {
		System.out.println("searching for " + maxResults + " solutions...");
		this.maxResults = maxResults;
		search();
		System.out.println("\ndone\n");
		return bestSolution;
	}

	void createList() {
		System.out.println("creating exact cover linked list...");
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
		System.out.println("done");
	}

	int search() {
		prune();
		if (solutionsFound >= maxResults) return 0;
		if (header.R == header) {
			if (valueOf(partialSolution) > valueOf(bestSolution)){
				bestSolution = new ArrayList<>(partialSolution);
				maxValue = valueOf(bestSolution);
			}
			System.out.print("\r[" + (solutionsFound + 1) + "/" + maxResults + "] solutions found");
			solutionsFound++;
			return valueOf(partialSolution);
		}

		ColumnObject c = smallestColumn();
		c.cover();

		int prevValue = -1;
		for (DataObject i = c.D; i != c; i = i.D) {
			partialSolution.add(i.row);
			for (DataObject j = i.R; j != i; j = j.R) j.C.cover();
			int newValue = search();
			partialSolution.remove(partialSolution.size() - 1);
			for (DataObject j = i.L; j != i; j = j.L) j.C.uncover();
			if (newValue <= prevValue) break;
			else prevValue = newValue;
		}

		c.uncover();
		return 0;
	}

	void prune(){
        for (ColumnObject i = (ColumnObject)header.R; i != header; i = (ColumnObject)i.R) {
            if(i.size == 0){
                i.cover();
            }
		}
    }

	ColumnObject smallestColumn() {
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

	ColumnObject getCol(int id) {
		for (ColumnObject i = (ColumnObject)header.R; i != header; i = (ColumnObject)i.R) {
			if (i.id == id) return i;
		}
		return null;
	}

	int matrix() {
		return valueOf(partialSolution);
	}

	int get_max_value(){
		return maxValue;
	}

	int valueOf(ArrayList<Integer> rows) {
		int result = 0;
		for(int i = 0; i < rows.size(); i++){
			for(int c = 0; c < matrix[0].length; c++){
				if(matrix[rows.get(i)][c] != 0){
					if(matrix[rows.get(i)][c] == 1){
						result += 3;
					}
					else if(matrix[rows.get(i)][c] == 2){
						result += 4;
					}
					else{
						result += 5;
					}
					break;
				}
			}
		}
		return result;
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

