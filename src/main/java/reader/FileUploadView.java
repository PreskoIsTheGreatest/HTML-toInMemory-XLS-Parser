/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;
 
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

 
import org.primefaces.model.UploadedFile;
 
@Named(value = "fileUploadView")
@SessionScoped
public class FileUploadView implements Serializable{
     
    @Inject
    private TableRenderer renderer;
    
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    boolean canInputExpr=false;

    public boolean isCanInputExpr() {
        return canInputExpr;
    }

    public void setCanInputExpr(boolean canInputExpr) {
        this.canInputExpr = canInputExpr;
    }
     
    public void upload() throws UnsupportedEncodingException, IOException {
        if(file != null) {
            File newFile = new File("temp.html");
            Files.write(file.getContents(), newFile);
            Document doc = Jsoup.parse(newFile, "UTF-8", "");
            Elements tableElements = doc.select("table");
            List<String> colNames = HTMLParser.getHeaders(tableElements);
            String[][] data3d = HTMLParser.getRowsData(tableElements, colNames.size());
            renderer.setColNames(colNames);
            renderer.setData3d(data3d);
            renderer.setRowNames(data3d.length);
            System.out.println(Arrays.hashCode(data3d));
            renderer.setParser(colNames, data3d);
            canInputExpr=true;
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
