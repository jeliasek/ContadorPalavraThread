package Testes;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import controller.ContadorController;

public class Versao250Threads {
	public static void main(String[] args) throws InterruptedException {
		
		AtomicInteger contador = new AtomicInteger();
		Scanner s = new Scanner(System.in);
        System.out.println("Qual palavra deseja contar?");
        ContadorController controller = new ContadorController("./src/dataset/", s.next());
        long tempoInicial = System.currentTimeMillis();
        int tamanho = controller.getArquivosDaPasta().length;

		int tamIntervalo = tamanho / tamanho;
		
		Thread t1 = new Thread(() -> {
            try {
                int a = controller.contadorPalavra(0, tamIntervalo);
                contador.addAndGet(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
		t1.start();

		for (int i = 2; i <= tamanho - 1; i++) {			
			int inicioIntervalo = ((i - 1) * tamIntervalo);
			int fimIntervalo = i * tamIntervalo;
			
			Thread tMeio = new Thread(() -> {
	            try {
	                int a = controller.contadorPalavra(inicioIntervalo, fimIntervalo);
	                contador.addAndGet(a);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        });
			
			tMeio.start();
			tMeio.join();
		}
		
		Thread tFim = new Thread(() -> {
            try {
                int a = controller.contadorPalavra((tamIntervalo * (tamanho - 1)), tamanho);
                contador.addAndGet(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
		
		t1.join();
		tFim.start();
		
		tFim.join();
		
		System.out.println("A palavra " + controller.getPalavra() + " aparece " + contador + " vezes");
        System.out.println("Executado em " + (System.currentTimeMillis() - tempoInicial) + " ms.");
        
	}
}
