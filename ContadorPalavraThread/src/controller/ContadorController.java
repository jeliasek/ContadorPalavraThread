package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ContadorController extends Thread{
    String caminho;
    File pasta;
    String palavra;

    public ContadorController(String caminho, String palavra) {
        this.caminho = caminho;
        this.pasta = new File(caminho);
        this.palavra = palavra;
    }

   

    public int contadorPalavra() throws IOException {
        int contador = 0;
        File[] arquivos = this.getArquivosDaPasta();
        for (File file : arquivos) {
            if (file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(this.caminho + file.getName()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals(this.palavra)) {
                    	contador++;
                    }
                }
            }
        }
        return contador;
    }

    public int contadorPalavra(int inicio, int fim) throws IOException {
        int contador = 0;
        File[] arquivos = this.getArquivosDaPasta();
        File[] arquivosSeparados = Arrays.copyOfRange(arquivos, inicio, fim);
        for (File file : arquivosSeparados) {
            if (file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(this.caminho + file.getName()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals(this.palavra)) {
                    	contador++;
                    }
                }
            }
        }
        return contador;
    }
    
    public String getPalavra() {
        return this.palavra;
    }
    
    public String getCaminho() {
        return this.caminho;
    }

    public File[] getArquivosDaPasta() {
        return this.pasta.listFiles();
    }

    @Override
    public void run() {
        System.out.println("Oieee");
    }
}