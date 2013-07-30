/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitorarquivode;

import java.io.*;
import java.text.NumberFormat;

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

    public processadorArquivoDE() {

    }

    public void processa(File filename) {
        double somaLinhas = 0.00;
        this.valorArquivo = 0;
        this.contaContratos = 0;

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("2")) {
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
