package alarm;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Alarm alarm = new Alarm();
		Runnable r = () -> {
			for (int i = 20000; i < 30000; i = i + 5000) {
				alarm.scheduleAlarm(i);
			}
		};
		for (int i = 0; i <= 1; i++) {
			Thread t1 = new Thread(r);
			t1.start();
		}
		// Thread t2 = new Thread(r);

		// t2.start();
		// t1.start();

	}

}
