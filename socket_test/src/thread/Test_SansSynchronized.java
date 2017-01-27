package thread;

public class Test_SansSynchronized extends Thread {

	private static double taux = 0.05;
	static Double solde = new Double(0);

	public void crediter(double val) {
		print("solde avant crédit : " + solde);
		try {
			print("Fait un gros Calcul et va créditer le compte de : " + val);
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		solde += val;
		print("Solde après crédit : " + solde);
	}

	public void calculInterests() {
		synchronized (solde) {
			print("Calcul des interets sur solde a" + solde);
			solde = solde + solde * taux;
			print("Solde après interet : " + solde);
		}
	}

	public static void print(String msg) {
		System.out.println(Thread.currentThread().getName() + " : " + msg);
	}

	public static void main(String[] args) {
		final Test_SansSynchronized t = new Test_SansSynchronized();

		Runnable runA = new Runnable() {

			@Override
			public void run() {
				t.crediter(1000);

			}
		};
		Thread ta = new Thread(runA, "Site Internet");
		System.out.println("Lancement du crédit par internet");
		ta.start();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Runnable runB = new Runnable() {

			@Override
			public void run() {
				t.calculInterests();

			}
		};
		Thread tb = new Thread(runB, "Agence B");
		System.out.println("Lancement calcul des interets");
		tb.start();
	}

}
