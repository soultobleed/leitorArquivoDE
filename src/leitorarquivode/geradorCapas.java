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
import java.text.NumberFormat;

/**
 * Gerador de Capas a partir de um layout de arquivo DE
 * @author canti
 * TODO: Verificar o nome da empresa e jogar o nome correto no arquivo. 
 */
public class geradorCapas {
    File file;
    FileWriter fw;
    BufferedWriter bw;
    String FileName;
    String NomeEmpresa;
    StringBuilder sb;
    
    public geradorCapas(File f) throws IOException, FileNotFoundException {
        this.NomeEmpresa = "Empresa BV Financeira";
        this.sb = new StringBuilder();
        if (f.getName().startsWith("DE") && f.getName().endsWith("ret")) {
            //Arquivo de entrada
            this.file = f;
            //Objetos do arquivo de saída
            this.FileName = setNomeDoArquivo();
            //separatorChar pela compatibilidade entre plataformas
            this.fw = new FileWriter(f.getParent() + File.separatorChar + FileName);
            this.bw = new BufferedWriter(fw);
            
        }
        
        else {
        throw new FileNotFoundException("Arquivo Inválido!");
        }
                
    }
    
    private String setNomeDoArquivo() {
        FileName = "CAPA_DO_ARQUIVO_";
        FileName += file.getName().replace("ret", "doc");
        return FileName;
        
    }
    
    public String getNomeDoArquivo() {
        return FileName;
    }
    
    private void montaHeaderModelo(String data, String codAss) throws IOException {
        /**
         * Montador de cabeçalho
         * Espera a data no formato ddmmyy e o código da praça para escrever na segunda linha do arquivo
         *
         **/         
           
        sb.append("Nome da assessoria: PASQUALI PARISE E GASPARINI JUNIOR ADVOGADOS \n");
        sb.append(data);
        sb.append(codAss);
        sb.append("\n");
        sb.append("$EMPRESA"); //Esse campo vai ser substituido depois pelo nome da empresa correto
        sb.append("\n");
        sb.append("A/C: Wendy Pereira / Jaqueline Lima \n");
        sb.append("Ref.: Honorários - Comprovante Anexo \n");
        sb.append("N° da NF -");
        sb.append("\n\n\n");
        sb.append("FINANCIADO"
                + "\t\t\t\t\t\t"
                + "CONTRATO"
                + "\t\tTIPO"
                + "\t\t\t\t"
                + "VALOR\n");
        }
    
   
    public void writeCapa() throws IOException {
        /**
         * Método que escreve toda a capa do arquivo, incluindo o cabeçalho e o rodapé
         **/
        double valorArquivo = 0;
        try {
            //Objetos do arquivo de entrada
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
        
        while (line != null) {
                double valorLinha;
             if (line.startsWith("1")) {
                 //Data + Código da Assessoria no arquivo
                 this.montaHeaderModelo(line.substring(2, 5), line.substring(4, 10)); 
            }
            if (line.startsWith("2")) {
                valorLinha = Double.valueOf(line.substring(58, 67)) / 100;
                valorArquivo += valorLinha;
                verificaEmpresaPorCodContrato(line.substring(67, 81));
                sb.append(line.substring(12, 52)); //Nome
                sb.append(line.substring(67, 81)); //Contrato Gestão
                sb.append("Honorários de Vendas\t\t");
                sb.append(NumeroFormatado(valorLinha));
                sb.append("\n");
            }

            line = br.readLine();
            
            
        }
        sb.replace(sb.indexOf("$EMPRESA"), sb.indexOf("$EMPRESA")+8, NomeEmpresa);
        fw.append(sb); //Escreve o conteúdo no arquivo
        this.writeTailCapa(valorArquivo); //Escreve o rodapé do arquivo
        fw.flush();
        fw.close();
                
        } catch(IOException ex)
                {   
                    fw.close();
                    
        } finally {
              }
                       
        
    }
    
    private String NumeroFormatado(double n) {
        /**
         * Pelo bem da sanidade mental, devolve o numero formatado no formato R$ 1.000,00
         */
        
        return NumberFormat.getCurrencyInstance().format(n);
    }
    
    
   private void writeTailCapa(double valorArquivo) throws IOException{
       /**
        * Escreve o rodapé da capa, recebe o valor total do arquivo para o cálculo dos impostos
        * Cálculo de IR = 1,5%
        * Cálculo de PIS = 0,6%
        * Cálculo de COFINS = 0.3%
        * Cálculo de Contribuição Social = 0.1%
        */
       double vlrIR = 0.015 * valorArquivo;
       double vlrPIS = 0.0065 * valorArquivo;
       double vlrCOFINS = 0.03 * valorArquivo;
       double vlrCSocial = 0.01 * valorArquivo;
       
       fw.append("\n");
       fw.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
       fw.append("-----------");
       fw.append("\n");
       fw.append("TOTAL BRUTO\t\t\t\t\t\t\t\t\t\t\t\t\t");
       fw.append(NumeroFormatado(valorArquivo));
       fw.append("\n");
       fw.append("\t\t\t\t\t\t\t1,5\t\tIR 1,50%\t\t\t\t");
       fw.append(NumeroFormatado(vlrIR));
       fw.append("\n");
       fw.append("\t\t\t\t\t\t\t0,65\t\tPIS\t0,65%\t\t\t\t");
       fw.append(NumeroFormatado(vlrPIS));
       fw.append("\n");
       fw.append("\t\t\t\t\t\t\t3\t\tCOFINS 3,00%\t\t\t");
       fw.append(NumeroFormatado(vlrCOFINS));
       fw.append("\n");
       fw.append("\t\t\t\t\t\t\t1\t\tC.SOCIAL 1,00%\t\t\t");
       fw.append(NumeroFormatado(vlrCSocial));
       fw.append("\n");
       fw.append("\t\tPasquali Parise e Gasparini Junior Advogados");
       
   }

    private void verificaEmpresaPorCodContrato(String codContrato) {
        if (codContrato.startsWith("14")) { 
            NomeEmpresa = "BV Leasing Arrendamento Mercantil S/A";
        }
        else NomeEmpresa = "Empresa BV Financeira";
    }
}


