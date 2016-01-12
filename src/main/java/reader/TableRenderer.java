/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Presko
 */
@Named(value = "tableRenderer")
@SessionScoped
public class TableRenderer implements Serializable {

    List<Integer> rowNames = new ArrayList<>();
    List<String> colNames = new ArrayList<>();
    ExpParser parser;

    String[][] data3d;

    boolean renderIframeColumn = false;

    String expression = "";

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean getRenderIframeColumn() {
        return renderIframeColumn;
    }

    public void setRenderIframeColumn(boolean renderIframeColumn) {
        this.renderIframeColumn = renderIframeColumn;
    }

    public List<Integer> getRowNames() {
        return rowNames;
    }

    public void setRowNames(List<Integer> rowNames) {
        this.rowNames = rowNames;
    }

    public void setRowNames(Integer rowNum) {
        this.rowNames = new ArrayList<>();
        for (Integer i = 1; i <= rowNum; i++) {
            this.rowNames.add(i);
        }
    }

    public List<String> getColNames() {
        return colNames;
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }

    public String[][] getData3d() {
        return data3d;
    }

    public void setData3d(String[][] data3d) {
        this.data3d = data3d;
    }

    public void evaluateExp() {
        int[] values = this.parser.parse(expression);
        if (values[2] == -1) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure!", expression + " is out of table");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            try {
                this.data3d[values[0]][values[1]] = "" + values[2];
                FacesMessage message = new FacesMessage("Succesful", expression + " Executed");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (ArrayIndexOutOfBoundsException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure!", expression + " is out of table");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public ExpParser getParser() {
        return parser;
    }

    public void setParser(List<String> colNames, String[][] data3d) {
        this.parser = new ExpParser(colNames, data3d);
    }

}
