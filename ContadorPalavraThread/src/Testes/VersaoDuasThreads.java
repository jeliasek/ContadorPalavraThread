package Testes;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import controller.ContadorController;

public class VersaoDuasThreads {
	public static void main(String[] args) throws InterruptedException {
		
		AtomicInteger contador = new AtomicInteger();
        Scanner s = new Scanner(System.in);
        System.out.println("Qual palavra deseja contar?");
        ContadorController controller = new ContadorController("./src/dataset/", s.next());
        long tempoInicial = System.currentTimeMillis();
        int tamanho = controller.getArquivosDaPasta().length;
        Thread t1 = new Thread(() -> {
            try {
                int a = controller.contadorPalavra(0, tamanho / 2);
                contador.addAndGet(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                int a = controller.contadorPalavra((tamanho / 2), tamanho);
                contador.addAndGet(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("A palavra " + controller.getPalavra() + " aparece " + contador + " vezes");
        System.out.println("Executado em " + (System.currentTimeMillis() - tempoInicial) + " ms.");
	}
}
