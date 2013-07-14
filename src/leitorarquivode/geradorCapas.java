/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitorarquivode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author canti
 */
public class geradorCapas {
    File file;
    FileWriter fw;
    BufferedWriter bw;
    String FileName;
    
    public geradorCapas(File f) throws IOException, FileNotFoundException {
        if (f.getName().startsWith("DE") && f.getName().endsWith("ret")) {
            this.file = f;
            this.fw = new FileWriter(file);
            this.bw = new BufferedWriter(fw);
            
        }
        
        else {
        throw new FileNotFoundException("Arquivo Inválido!");
        }
                
    }
    
    public void setNomeDoArquivo() {
        FileName = "CAPA_DO_ARQUIVO_";
        FileName.concat(file.getName().replace("ret", "doc"));
    }
    
    public String getNomeDoArquivo() {
        return FileName;
    }
    
    public void writeCapa() {
        try {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        
        
        
        while (line != null) {
            //TODO: Escrever as linhas dos contratos! 
        }
        
        } catch(IOException ex)
                {
                    
        } finally {
            
        }
                       
        
    }
}
