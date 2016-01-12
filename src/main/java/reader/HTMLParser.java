/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Presko
 */
public class HTMLParser {

    public static List<String> getHeaders(Elements tableElements) {
        Elements tableHeaderEles = tableElements.select("thead tr th");
        List<String> colNames = new ArrayList<>();
        for (Element tableHeaderEle : tableHeaderEles) {
            colNames.add(tableHeaderEle.text());
        }
        return colNames;
    }

    public static String[][] getRowsData(Elements tableElements, Integer size) {
        Elements tableRowElements = tableElements.select(":not(thead) tr");
        String[][] data3d = new String[tableRowElements.size()][size];
        int rowIn = 0;
        int colIn;
        for (Element row : tableRowElements) {
            colIn = 0;
            Elements rowItems = row.select("td");
            for (Element rowItem : rowItems) {
                data3d[rowIn][colIn] = rowItem.text();
                colIn++;
            }
            rowIn++;
        }
        return data3d;
    }
}
