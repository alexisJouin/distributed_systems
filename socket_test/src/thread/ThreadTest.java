package thread;

public class ThreadTest extends Thread {
	private long delay;

	public ThreadTest(long delay){
		this.delay = delay;
	}

	public void run() {
		try {
			for (int i = 1; i <= 10; i++) {
				System.out.println(getName() + " : " + i);

				// Ralendir boucle
				Thread.sleep(delay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t1 = new ThreadTest(200), t2 = new ThreadTest(500);
		t1.start();
		t2.start();
	}

}


