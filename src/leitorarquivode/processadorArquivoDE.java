/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitorarquivode;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author canti
 */
class OnlyExt implements FilenameFilter {

    String ext;

    public OnlyExt(String ext) {
        this.ext = ("." + ext).toLowerCase();
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ext);
    }
}

public class processadorArquivoDE {

    private double valorArquivo;
    private int contaContratos;
    private ArrayList listaClientes;
    private ArrayList listaContratos;
    private ArrayList listaValores;
    


    public processadorArquivoDE() {
        this.listaValores = new ArrayList();
        this.listaContratos = new ArrayList();
        this.listaClientes = new ArrayList();

   
    }    

    public void processa(File filename) {
        double somaLinhas = 0.00;
        this.valorArquivo = 0;
        this.contaContratos = 0;
        
        
        

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            System.out.println(listaContratos.toString());
            while ((line = br.readLine()) != null) {
                System.out.println(listaContratos.toString());
                if (line.startsWith("2")) {
                    listaContratos.add(line.substring(67, 81));
                    listaContratos.add(line.substring(67, 81));
                    listaValores.add(String.valueOf(line.substring(57, 67)));
                    somaLinhas += Double.valueOf(line.substring(57, 67));
                    contaContratos++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setValorArquivo(somaLinhas);
        }
    }
    
    public List getListaClientes() {
        return listaClientes;
    }
    
    public List getListaValores() {
        return listaValores;
    }
    
    public List getListaContratos() { 
        return listaContratos;
    }
    
    public int getTotalContratos() {
        return contaContratos;
    }

    public String getValorArquivoFormatado() {
        return NumberFormat.getCurrencyInstance().format(valorArquivo);
    }

    public double getValorArquivoAsDouble() {
        return valorArquivo;
    }

    public void setValorArquivo(double somaLinhas) {
        this.valorArquivo = Double.valueOf(somaLinhas / 100);
    }
}
