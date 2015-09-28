package Backend;

import Backend.helpers.Squares;

import java.util.*;

/**
 * Created by espence105 on 9/26/2015.
 */
public class Solver {
    private Integer[][] sudukoList;
    private Object[][]  cachePossibleValue;
    private boolean     fourSquare;
    private Squares squareSolver;
    private boolean solved;
    /**
     * For Testing will remove later
     * @return
     */
    public Object[][] getCachePossibleValue() {
        return cachePossibleValue;
    }
    private ArrayList<Integer> numbersToTen = new ArrayList<>();

    private int sizeOfBoard;
    /**
     * Constructor
     * Also creates the
     * @param sudukoList - creates the matrix for suduko
     */
    public Solver( Integer[][] sudukoList, int sizeOfBoard, boolean fourSquare) {
        this.sudukoList    = sudukoList;
        this.sizeOfBoard   = sizeOfBoard;
        this.solved        = false;
        this.fourSquare    = fourSquare;
        cachePossibleValue = new Object[sizeOfBoard][sizeOfBoard];
        for (int i = 1; i <= sizeOfBoard ; i++) {
            numbersToTen.add( i );
        }
        this.squareSolver   = new Squares(sudukoList, numbersToTen, sizeOfBoard);
        createCache();
    }

    /**
     * Getter for the Soduku list
     * @return
     */
    public  Integer[][] getSudukoList() {
        return sudukoList;
    }



    /**
     * Gets the values that are missing from the row
     * @param row
     * @return
     */
    public  ArrayList<Integer> getMissingRow(int row){
        ArrayList<Integer> temp = new ArrayList<>( numbersToTen );
        Arrays.stream(sudukoList[row])
               .forEach(m -> temp.remove(m));
        return temp;
    }

    /**
     * Gets the values that are missing from a selected column
     * @param column
     * @return
     */
    public  ArrayList<Integer>  getMissingColumn(int column){
        ArrayList<Integer> temp = new ArrayList<>( numbersToTen );
        for (int i = 0; i < sizeOfBoard ; i++) {
            temp.remove(sudukoList[i][column]);
        }
        return temp;
    }

    /**
     * Creates each individual cell
     * @param row
     * @param column
     */
    private Object[] createCell(int row, int column){
        ArrayList<Integer> cell = getMissingRow(row);
        getMissingColumn(column)
                .stream()
                .forEach(m -> cell.add(m));
        squareSolver.getMissingSquare(row, column, fourSquare)
                .stream()
                .forEach(m -> cell.add(m));
        return cell
                .stream()
                .filter(m-> Collections.frequency(cell, m) > 2)
                .distinct()
                .toArray();
    }

    /**
     * Creates the starting possible values
     */
    private void createCache(){
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard ; j++) {
                if ( sudukoList[i][j] == 99)
                {
                    cachePossibleValue[i][j] =  createCell(i, j);
                }
                else{
                    cachePossibleValue[i][j] = sudukoList[i][j];
                }
            }
        }
    }
    public void solvePuzzle(){
        while(!solved) {
            solved = true;
            for (int row = 0; row < sizeOfBoard; row++) {
                for (int column = 0; column < sizeOfBoard; column++) {
                    if (!cachePossibleValue[row][column].getClass().getName().equals("java.lang.Integer")) {
                        Object[] p = (Object[]) (cachePossibleValue[row][column]);
                        // If the length of p is one that is the correct value
                        if (p.length == 1) {
                            cachePossibleValue[row][column] = p[0];
                            sudukoList[row][column] = (Integer) p[0];
                            solved = false;
                        }

                        if (p.length >= 2) {
                            // Checks the rest of the square if it has a unique value
                            if(row==1 && column == 5)
                            {
                                int foo = 1;
                            }
                            p = squareSolver.checkRestOfSquare(
                                    p,
                                    cachePossibleValue,
                                    row,
                                    column,
                                    fourSquare);

                            if (p.length == 1) {
                                cachePossibleValue[row][column] = p[0];
                                sudukoList[row][column] = (Integer) p[0];
                                solved = false;
                            }
                        }
                    }
                }
            }createCache();

        }

    }

    public void displaySudoku(){
        for (int row = 0; row < sizeOfBoard ; row++) {
            for (int column = 0; column < sizeOfBoard ; column++) {
                System.out.print(sudukoList[row][column] + " | ");
                if (column==2 || column==5){
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (row==2 || row==5){
                System.out.println();
            }
        }
    }


    public static void main(String[] args){
        Integer[][] sudukoList4 = {
                {1  ,99 ,99 ,99},
                {99 ,3  ,99 ,99},
                {99 ,99 ,2  ,99},
                {3  ,99 ,99 ,4  }
        };
        Integer[][] sudukoListWorking = {
                {99 ,8  ,6    ,9  ,99 ,2    ,99 ,99 ,1 },
                {3  ,99 ,4    ,5  ,99 ,99   ,7  ,99 ,99},
                {5  ,99 ,99   ,7  ,8  ,99   ,3  ,9  ,99},

                {1  ,99 ,99   ,99 ,99 ,99   ,5  ,6  ,4 },
                {99 ,3  ,9    ,99 ,4  ,99   ,99 ,99 ,2 },
                {99 ,2  ,99   ,8  ,6  ,7    ,99 ,1  ,99},

                {9  ,99 ,99   ,2  ,99 ,1    ,6  ,8  ,99},
                {99 ,5  ,99   ,99 ,99 ,3    ,99 ,4  ,99},
                {2  ,6  ,7    ,99 ,5  ,99   ,99 ,3  ,9 }
        };
        Integer[][] sudukoList = {
                {99 ,99 ,99   ,99 ,99 ,4    ,5  ,7  ,99},
                {99 ,99 ,9    ,99 ,5  ,99   ,99 ,99 ,3 },
                {1  ,6  ,99   ,99 ,99 ,99   ,99 ,99 ,99},

                {99 ,99 ,99   ,1  ,4  ,3    ,99 ,99 ,99},
                {5  ,3  ,99   ,99 ,99 ,7    ,99 ,99 ,99},
                {99 ,99 ,99   ,99 ,99 ,99   ,99 ,99 ,2 },

                {99 ,99 ,99   ,99 ,99 ,99   ,8  ,3  ,99},
                {99 ,99 ,99   ,99 ,1  ,99   ,99 ,9  ,6 },
                {99 ,4  ,99   ,99 ,99 ,2    ,99 ,99 ,99}
        };
        Solver solver = new Solver(sudukoList, 9, false);
        solver.solvePuzzle();
        solver.displaySudoku();


    }
}
