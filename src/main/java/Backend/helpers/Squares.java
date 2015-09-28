package Backend.helpers;

import groovy.util.GroovyCollections;
import groovyjarjarantlr.collections.List;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by espence105 on 9/26/2015.
 */
public class Squares {
    private int sizeOfBoard;
    private Integer[][] sudukoList;
    private ArrayList<Integer> numbersToTen = new ArrayList<>();

    public Squares(Integer[][] sudukoList, ArrayList<Integer> numbersToTen, int sizeOfBoard) {
        this.sudukoList   = sudukoList;
        this.numbersToTen = numbersToTen;
        this.sizeOfBoard  = sizeOfBoard;

    }
    private int[] getTopLeftCellFour(int x, int y){
        double sqrtBoard = Math.sqrt( sizeOfBoard );
        int[] topLeft;
        if(x < sqrtBoard){
            if(y < sqrtBoard){
                topLeft = new int[]{0,0};
            }
            else{
                topLeft = new int[]{0,2};
            }
        }
        else{
            if(y < sqrtBoard){
                topLeft = new int[]{2,0};
            }
            else{
                topLeft = new int[]{2,2};
            }
        }
        return topLeft;
    }

    /**
     * TODO: Finish this Gonna work on guessing with a 4x4
     * @param row
     * @param column
     * @return
     */
    private int[] getTopLeftCellNine(int row, int column){
        double sqrtBoard = Math.sqrt( sizeOfBoard );
        int[] topLeft;
        if(row==4 && column==0){
            int foo = 4;
        }
        if(row < sqrtBoard){
            if(column < sqrtBoard){
                topLeft = new int[]{0,0};
            }
            else if(column < sqrtBoard*2){
                topLeft = new int[]{0,3};
            }
            else {
                topLeft = new int[]{0,6};
            }
        }
        else if(row < sqrtBoard*2){
            if(column < sqrtBoard){
                topLeft = new int[]{3,0};
            }
            else if(column < sqrtBoard*2){
                topLeft = new int[]{3,3};
            }
            else {
                topLeft = new int[]{3,6};
            }
        }
        else{
            if(column < sqrtBoard){
                topLeft = new int[]{6,0};
            }
            else if(column < sqrtBoard*2){
                topLeft = new int[]{6,3};
            }
            else {
                topLeft = new int[]{6,6};
            }
        }
        return topLeft;
    }

    public int[] getTopLeftCell(int row, int column, boolean fourSquare){
        if(fourSquare){
            return getTopLeftCellFour(row,column);
        }
        else{
            //TODO: Come back to and implement later for the 9x9 grid
            return getTopLeftCellNine(row, column);
        }
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public ArrayList<Integer> getMissingSquare(int x, int y, boolean fourSquare){
        int[] coordinates = getTopLeftCell(x,y,fourSquare);
        int countX = 0;
        int countY = 0;
        ArrayList<Integer> temp = new ArrayList<>( numbersToTen );
        while ( countX < Math.sqrt(sizeOfBoard) ) {
            while ( countY < Math.sqrt(sizeOfBoard) ){
                temp.remove(sudukoList[coordinates[0] + countX][coordinates[1] + countY]);
                countY++;
            }
            countY = 0;
            countX++;
        }
        return temp;
    }


    private ArrayList<Object[]> createSquareOptions(Object[][]  cachePossibleValue, int[] coordinates, int row, int column){
        int countX = 0;
        int countY = 0;
        ArrayList<Object[]> allOptions = new ArrayList<>();
        while ( countX < Math.sqrt(sizeOfBoard) ) {
            while ( countY < Math.sqrt(sizeOfBoard) ){
                if (!cachePossibleValue[coordinates[0] + countX][coordinates[1] + countY].getClass().getName().equals("java.lang.Integer")) {
                    if (row != coordinates[0] + countX && column != coordinates[1] + countY){
                        allOptions.add ( (Object[]) cachePossibleValue[coordinates[0] + countX][coordinates[1] + countY]);
                    }

                }

                countY++;
            }
            countY = 0;
            countX++;
        }
        return allOptions;
    }

    public Object[] checkRestOfSquare(Object[] possibleValues,Object[][]  cachePossibleValue, int row, int column, boolean fourSquare ){
        int[] coordinates = getTopLeftCell(row, column,fourSquare);
        ArrayList<Object[]> possible = createSquareOptions(
                cachePossibleValue,
                coordinates,
                row,
                column);
        ArrayList<Object> toRemove = new ArrayList<>();
        for(Object p: possibleValues){
            for (Object[] z : possible){
                if(Arrays.asList(z).contains(p)){
                   toRemove.add(p);
                }
            }
        }
        ArrayList<Object> returnValue = new ArrayList<>();
        //TODO: GOT TO FIGURE OUT HOW TO REMOVE THE OTHER NUMBERS
        for(Object pos : possible){
            if(!toRemove.contains(pos)){
                returnValue.add(pos);
            }
        }

        return returnValue.toArray();
    }

}
