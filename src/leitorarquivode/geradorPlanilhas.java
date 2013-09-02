/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitorarquivode;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
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
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;

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
     String[] cabecalhos = { "Contrato Gestão", "Assessoria", "Empresa", "Tipo Campanha", "Valor Pago",  "Premiação" };
        for (int i = 0; i < cabecalhos.length; i++) {
         Label label = new Label(i, 0, cabecalhos[i]); 
         this.sheet.addCell(label);
        }
    }
    
    private void setBody() throws WriteException, IOException {
        WritableCellFormat integerFormat = new WritableCellFormat (NumberFormats.INTEGER); 
        processadorArquivoDE pa = new processadorArquivoDE();
        pa.processa(file);
        List listaContratos = pa.getListaContratos();
        List listaValores = pa.getListaValores();
        
        for (int i = 0; i < listaContratos.size(); i++) {
         Number nrContrato = new Number(0, i+1, Double.valueOf(listaContratos.get(i).toString()), integerFormat); //Contrato Gestão
         this.sheet.addCell(nrContrato);
         Label label = new Label(2, i+1, "PASQUALI"); //Assessoria
         this.sheet.addCell(label);
         label = new Label(5, i+1, NumeroFormatado(Double.valueOf(listaValores.get(i).toString()))); //Valor Pago
         this.sheet.addCell(label);
	 //label = new Label(5, i+1, listaValores.get(i).toString());
         //this.sheet.addCell(label);
        }
        
        
            
        
        
    }
        private String NumeroFormatado(double n) {
        /**
         * Pelo bem da sanidade mental, devolve o numero formatado no formato R$ 1.000,00
         */
        
        return NumberFormat.getCurrencyInstance().format(n);
    }
    

    public void processa() throws IOException {
       try {
            this.setHeader();
            this.setBody();
            this.workbook.write();
            this.workbook.close();

        } catch (WriteException ex) {
            Logger.getLogger(geradorPlanilhas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    }
    
