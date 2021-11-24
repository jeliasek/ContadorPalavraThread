package Testes;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import controller.ContadorController;

public class VersaoQtdNucleosThreads {
	public static void main(String[] args) throws InterruptedException {
		
		AtomicInteger contador = new AtomicInteger();
		Scanner s = new Scanner(System.in);
        System.out.println("Qual palavra deseja contar?");
        ContadorController controller = new ContadorController("./src/dataset/", s.next());
		int cores = Runtime.getRuntime().availableProcessors();
		long tempoInicial = System.currentTimeMillis();
		int tamanho = controller.getArquivosDaPasta().length;
		System.out.println("O dispositivo possui " + cores + " núcleos lógicos");
		int tamIntervalo = tamanho / cores;
		
		Thread t1 = new Thread(() -> {
            try {
                int a = controller.contadorPalavra(0, tamIntervalo);
                contador.addAndGet(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
		t1.start();

		for (int i = 2; i <= cores - 1; i++) {			
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
            	
                int a = controller.contadorPalavra((tamIntervalo * (cores - 1)), tamanho);
                contador.addAndGet(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
		
		
		tFim.start();
		tFim.join();
		t1.join();
		
		System.out.println("A palavra " + controller.getPalavra() + " aparece " + contador + " vezes");
        System.out.println("Executado em " + (System.currentTimeMillis() - tempoInicial) + " ms.");
        
	}
}
