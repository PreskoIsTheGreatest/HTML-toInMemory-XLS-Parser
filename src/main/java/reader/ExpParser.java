/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Presko
 */
public class ExpParser {

    List<String> headers;
    String[][] data3d;
    private static final String GTE = ">=";
    private static final String GT = ">";
    private static final String LTE = "<=";
    private static final String LT = "<";
    private static final String EQ = "=";
    private static final String IF = "IF";
    private static final String COMMA = ",";
    private static final Integer ARR_XLS_CORRECTION = 1;

    public ExpParser(List<String> headers, String[][] data3d) {
        this.headers = headers;
        this.data3d = data3d;
    }

    public int[] parse(String expression) {
        Integer iOfEq = expression.indexOf(EQ);
        String toBeAssStr = expression.substring(0, iOfEq);
        String toBeAssValueStr = expression.substring(iOfEq + 1, expression.length());
        Integer value = null;
        int[] assignable=new int[]{-1,-1};
        try{
            assignable = getAssignableCell(toBeAssStr);
        }catch(NumberFormatException ex){
            value =-1;
        }
        try {
            value = new Integer(toBeAssValueStr);
        } catch (NumberFormatException ex) {
            if (toBeAssValueStr.startsWith(IF)) {
                value = executeConditionalExpr(toBeAssValueStr);
            }
        }
        return new int[]{assignable[0], assignable[1], value};
    }

    private int[] getAssignableCell(String cellStr) {
        String colName = cellStr.substring(0, 1);
        String rowName = cellStr.substring(1, cellStr.length());
        int colIn = this.headers.indexOf(colName);
        int rowIn = new Integer(rowName);
        return new int[]{rowIn - ARR_XLS_CORRECTION, colIn};
    }

    private int executeConditionalExpr(String toBeAssValueStr) {
        System.out.println("It is a boolean condition");
        //Remove unnesesery signs
        toBeAssValueStr = toBeAssValueStr.substring(3, toBeAssValueStr.length() - 1);
        //Define range of the sing
        int[] rangeOfSign = indexOfConditionalSign(toBeAssValueStr);
        //Get the sign
        String sign = toBeAssValueStr.substring(rangeOfSign[0], rangeOfSign[1]);
        //Find index of the first coma
        int comma = toBeAssValueStr.indexOf(COMMA);
        //Extract the values string which you want to be assigned in true/false
        String valuesToAssigned = toBeAssValueStr.substring(comma + 1, toBeAssValueStr.length());
        //Extract the values which you want to be assigned in true/false
        String[] values = valuesToAssigned.split(COMMA);
        //Get the condition string
        toBeAssValueStr = toBeAssValueStr.substring(0, comma);
        //Split the condition string from the sign to determine the tow cells
        String[] cells = toBeAssValueStr.split(sign);
        //Find out which is the first cell
        try {
            int[] cellFirst = getAssignableCell(cells[0]);
            //Find out which is the second celll
            int[] cellSecond = getAssignableCell(cells[1]);
            String firstVal = data3d[cellFirst[0]][cellFirst[1]];
            String secondVal = data3d[cellSecond[0]][cellSecond[1]];
            if (this.evalueteCondition(sign, new Integer(firstVal), new Integer(secondVal))) {
                return new Integer(values[0]);
            }
            return new Integer(values[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.err.println("Грешни стойности за клетка");
        }
        return -1;
    }

    private boolean evalueteCondition(String sign, Integer first, Integer second) {
        switch (sign) {
            case LTE:
                return first <= second;
            case LT:
                return first < second;
            case GTE:
                return first >= second;
            case GT:
                return first > second;
            case EQ:
                return Objects.equals(first, second);
        }
        return false;
    }

    private int[] indexOfConditionalSign(String ch) {
        Integer gte = ch.indexOf(GTE);
        Integer lte = ch.indexOf(LTE);
        Integer gt = ch.indexOf(GT);
        Integer lt = ch.indexOf(LT);
        Integer eq = ch.indexOf(EQ);
        Integer[] values = {gte, lte, gt, lt, eq};
        int[] range = new int[2];
        for (int i = 0; i < values.length; i++) {
            if (values[i] != -1) {
                if (i == 0 || i == 1) {
                    range = new int[]{values[i], values[i] + 2};
                } else {
                    range = new int[]{values[i], values[i] + 1};
                }
                break;
            }
        }
        return range;
    }
}
