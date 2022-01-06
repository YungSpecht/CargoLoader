package CargoLoader.Utils;

import java.util.Arrays;

public class AlgoXUtils {
    public static int get_min_column(int[][] matrix){
        int[] tracker = {99999, 0};
        for(int c = 1; c < matrix[0].length; c++){
            int count = 0;
            for(int r = 1; r < matrix.length; r++){
                if(matrix[r][c] != 0){
                    count++;
                }
            }
            if(count < tracker[0]){
                tracker[0] = count;
                tracker[1] = c;
            }
        }
        return tracker[1];
    }

    public static int[] get_relevant_columns(int[] row) {
        int[] relevantCols = new int[0];
        for(int i = 1; i < row.length; i++){
            if(row[i] != 0){
                relevantCols = ArrayUtils.add_distinct_element(relevantCols, i);
            }
        }
        return relevantCols;
    }

    public static int[] get_relevant_rows(int[][] matrix, int[]cols2check){
        int[]relevantRows = new int[0];
        for(int i = 0; i < cols2check.length; i++){
            for(int r = 1; r < matrix.length; r++){
                if(matrix[r][cols2check[i]] != 0){
                    relevantRows = ArrayUtils.add_distinct_element(relevantRows, r);
                }
            }
        }
        return relevantRows;
    }

    public static int[][] prepare_matrix(int[][] matrix) {
        int[][] columnHeader = new int[1][matrix[0].length + 1];
        for (int i = 0; i < matrix[0].length + 1; i++){
            columnHeader[0][i] = i;
        }
        int[][] newMatrix = new int[matrix.length][0];
		for (int i = 0; i < matrix.length; i++) {
			newMatrix[i] = ArrayUtils.add_element_to_start(matrix[i], i + 1);
		}
		return ArrayUtils.add(columnHeader, newMatrix);
	}

    public static int calculate_score(int[] partialSolution, int[] parcelAmounts, int[] parcelValues){
        int score = 0;
        for(int i = 0; i < partialSolution.length; i++){
            if(partialSolution[i] <= parcelAmounts[0]){
                score += parcelValues[0];
            }
            else if(partialSolution[i] <= parcelAmounts[0] + parcelAmounts[1]){
                score += parcelValues[1];
            }
            else{
                score += parcelValues[2];
            }
        }
        return score;
    }

    public static int[][] prune(int[][] matrix){
        int[][] result = ArrayUtils.copy(matrix);
        int[] cols2del = new int[0];
        for(int c = 1; c < matrix[0].length; c++){
            boolean check = true;
            for(int r = 1; r < matrix.length; r++){
                if(matrix[r][c] != 0){
                    check = false;
                }
            }
            if(check){
                cols2del = ArrayUtils.add_distinct_element(cols2del, c);
            }
        }
        result = ArrayUtils.remove_col(result, cols2del);
        return result;
    }
}
