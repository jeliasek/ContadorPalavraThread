package Testes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VersaoSemThread {

	public static void main(String[] args) throws IOException {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Qual palavra deseja contar?");
		long tempoInicial = System.currentTimeMillis();
		String palavra = s.next();
		String caminho = "./src/dataset/";
		File pasta = new File(caminho);
		int contador = 0;
        File[] arquivos = pasta.listFiles();
        for (File file : arquivos) {
            if (file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(caminho + file.getName()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.equals(palavra)) {
                    	contador++;
                    }
                }
            }
        }
        
        System.out.println("A palavra " + palavra + " aparece " + contador + " vezes");
        System.out.println("Executado em " + (System.currentTimeMillis() - tempoInicial) + " ms.");

	}

}
