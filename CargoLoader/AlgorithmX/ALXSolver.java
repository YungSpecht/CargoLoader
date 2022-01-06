package CargoLoader.AlgorithmX;

import java.util.Arrays;

import CargoLoader.Utils.*;


public class ALXSolver {
    private int[] parcelAmounts;
    private int[] parcelValues;
    private int[] solutionTracker;
    private int[] bestSolution;
    private int maxScore;

    public ALXSolver(int[] parcelAmounts, int[] parcelValues){
        this.parcelAmounts = parcelAmounts;
        this.parcelValues = parcelValues;
        solutionTracker = new int[0];
        bestSolution = new int[0];
        maxScore = 0;
    }

    public int[] algoX_loader(int[][] in){
        int[][] data = AlgoXUtils.prepare_matrix(in);
        int[] answers = algo_x(data);

        return bestSolution;
    }


    private int[] algo_x(int[][] in){
        int[][] matrix = ArrayUtils.copy(in);
        int[] partialSolution = new int[0];
        matrix = AlgoXUtils.prune(matrix);

        //BASE CASE: If the matrix is empty the current partial solutin is valid
        if(matrix[0].length == 1){
            int check = AlgoXUtils.calculate_score(solutionTracker, parcelAmounts, parcelValues);
            if(check > maxScore){
                bestSolution = solutionTracker;
                maxScore = check;
            }
            return new int[0];
        }

        //Select the first colum containing the least amount of 1's
        int minColumn = AlgoXUtils.get_min_column(matrix);

        //If any column contains no 1's at all the branch gets terminated
        if(minColumn == -1){
            int check = AlgoXUtils.calculate_score(solutionTracker, parcelAmounts, parcelValues);
                if(check > maxScore){
                    bestSolution = solutionTracker;
                    maxScore = check;
                }
            int[] terminateBranch = {-666};
            return terminateBranch;
        }
        
        for(int r = 1; r < matrix.length; r++){
            //A row is selected such that matrix[r][selectedColumn] == 1
            if(matrix[r][minColumn] != 0){
                //The current row get's added to the partial solution
                partialSolution = ArrayUtils.add_element(partialSolution, matrix[r][0]);
                solutionTracker = ArrayUtils.add_element(solutionTracker, matrix[r][0]);
                
                //The rows and columns that have to be deleted get stored in seperate arrays 
                int[]columns2delete = AlgoXUtils.get_relevant_columns(matrix[r]);
                int[]rows2delete = AlgoXUtils.get_relevant_rows(matrix, columns2delete);

                //The reduced matrix is created
                matrix = ArrayUtils.remove_row(matrix, rows2delete);
                matrix = ArrayUtils.remove_col(matrix, columns2delete);

                //Recursive call on the reduced matrix
                int[] newPartialSolution = algo_x(matrix);
                
                //Add the new partial solution to the current one if successful
                if(newPartialSolution.length == 0){
                    return partialSolution;
                }
                if(newPartialSolution[0] != -666){
                    partialSolution = ArrayUtils.add(partialSolution, newPartialSolution);
                    return partialSolution;
                }


                //Remove current row from partial solution and move on to next row if unsuccessful
                partialSolution = ArrayUtils.remove_last_element(partialSolution);
                solutionTracker = ArrayUtils.remove_last_element(solutionTracker);
            }
        }
        return new int[0];
    }

    public int get_score(){
        return maxScore;
    }
}
