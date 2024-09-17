package alarm;

import java.util.PriorityQueue;
import java.util.Queue;

public class Alarm {
	Queue<Long> q;

	public Alarm() {
		q = new PriorityQueue<Long>();
		init();
	}

	private void init() {
		Runnable r = () -> {
			while (true) {
				synchronized (q) {
					// If q is empty wait for an insertion
					if (q.isEmpty()) {
						try {
							System.out.println("waiting for insertion");
							q.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// if q has a element check if the alarm time has come
					if (q.peek() - System.currentTimeMillis() <= 0) {
						long t = q.poll();
						System.out.println("Current Time: " + System.currentTimeMillis() + "Scheduled Time: " + t);
					}
					// if alarm time is not passed put a wait on the top element
					// Note: I have added notifyAll when Insertion just to
					else {
						try {
							System.out.println("waiting for timing out");

							q.wait(q.peek() - System.currentTimeMillis());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		Thread t1 = new Thread(r);
		t1.start();

	}

	public void scheduleAlarm(int delay) {
		synchronized (q) {
			long t = System.currentTimeMillis() + delay;
			// We should Notify Only if the new scheduled Time is less than the top time
			boolean shouldNotify = (q.peek() == null) || q.peek() > t;
			q.offer(t);
			System.out.println(t + " added to queue");
			if (shouldNotify) {
				System.out.println("Notifying all");
				q.notifyAll();
			}
		}
	}

}
