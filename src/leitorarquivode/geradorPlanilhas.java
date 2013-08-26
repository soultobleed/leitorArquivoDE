/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitorarquivode;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author wga
 */
class geradorPlanilhas {
    File file;
    String FileName;
    WritableWorkbook workbook;
    WritableSheet sheet;

    public geradorPlanilhas(File f) throws IOException, FileNotFoundException, BiffException {
        this.file = f;
        this.FileName = setNomeDoArquivo();
        this.workbook = Workbook.createWorkbook(new File(FileName)); 
        this.sheet = workbook.createSheet("Plan 1", 0);
    } 
    
    public String getNomeDoArquivo() {
        return FileName;
    }

    private String setNomeDoArquivo() {
        FileName = "PLANILHA_DO_ARQUIVO_";
        FileName += file.getName().replace("ret", "xls");
        return FileName;
    }
    
    private void setHeader() throws WriteException, IOException {
     String[] cabecalhos = { "Contrato Gestão", "Assessoria", "Empresa", "Tipo Campanha", "Valor Pago Premiação" };
        for (int i = 0; i < cabecalhos.length; i++) {
         Label label = new Label(i, 0, cabecalhos[i]); 
         this.sheet.addCell(label);
        }
        workbook.write();
    }
    
    private void setBody() throws WriteException, IOException {
        processadorArquivoDE pa = new processadorArquivoDE();
        pa.processa(file);
        List listaContratos = pa.getListaContratos();
        List listaValores = pa.getListaValores();
        
        for (int i = 0; i < listaContratos.size(); i++) {
         Label label = new Label(1, i, listaContratos.get(i).toString()); 
         this.sheet.addCell(label);
         workbook.write();
        }
        
    }

    public void processa() throws IOException {
       try {
            this.setHeader();
            this.setBody();
            this.workbook.close();

        } catch (WriteException ex) {
            Logger.getLogger(geradorPlanilhas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    }
    
