/*

	Deadlock = 

		Example:
			traffic di na jud mulihok
			https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
	
	Mutex = if naa na gani nag gunit ana nga lock kelangan maghuwat ang uban na i-release ang thread para magamit na sa uban
		
		Example:
			CR - 1 person at a time 



	Critical Sections = part sa code na daghan kaau nag access na threads imo na i-inclose of synchronize na blocks to prevent ConcurrentModificationExceptio
		= murag protektahan nimo cya daan...isud ba nimo ug synchronize block

		Examples:
			Cross roads ... intersection 
			different traffic lights per way 
			mag huwat sa ilang turn



	Semaphore = multiple mutex.
		Ex: 
			5 locks ====>> 5 ra ang maka access
			Carenderia... maghuwat ka na mahuman ug gamit ng previous batch mara makagamit ang user 
		

Sources:
http://tutorials.jenkov.com/java-concurrency/race-conditions-and-critical-sections.html


*/


// DEADLOCK

public class Deadlock {
	static class Friend {
		private final String name;
		public Friend(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
		public synchronized void bow(Friend bower) {
			System.out.format("%s: %s"
					+ "  has bowed to me!%n", 
					this.name, bower.getName());
			bower.bowBack(this);
		}
		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s"
					+ " has bowed back to me!%n",
					this.name, bower.getName());
		}
	}

	public static void main(String[] args) {
		final Friend alphonse =
				new Friend("Alphonse");
		final Friend gaston =
				new Friend("Gaston");
		new Thread(new Runnable() {
			public void run() { alphonse.bow(gaston); }
		}).start();
		new Thread(new Runnable() {
			public void run() { gaston.bow(alphonse); }
		}).start();
	}
}

package .com.tutorial;
 
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
 
public class SemaphoreMutexExample {
	static Object Lock = new Object();
	static LinkedList<String> List = new LinkedList<String>();
	
	static Semaphore semaphore = new Semaphore(0);
	static Semaphore mutex = new Semaphore(1);
		
	
	static class Producer extends Thread {
		
		public void run() {
			int counter = 1;
			try {
				while (true) {
					String threadName = 
							Thread.currentThread().getName() + counter++;
					
					mutex.acquire();
					List.add(threadName);
					System.out.println("Producer is prdoucing new value: 
							" + threadName);
					mutex.release();
					semaphore.release();
					Thread.sleep(500);
				}
			} catch (Exception x) {
					x.printStackTrace();
			}
		}
	}
		
	static class Consumer extends Thread {
		String consumerName;
		
		public Consumer(String name) {
				this.consumerName = name;
		}
		
		public void run() {
			try {
				
				while (true) {
					semaphore.acquire();
					mutex.acquire();
					String result = "";
					for (String value : List) {
							result = value + ",";
					}
					System.out.println(consumerName + 
							" consumes value: " + result + "List.size(): "
							+ List.size() + "\n");
					mutex.release();
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}
		
	public static void main(String[] args) {
			new Producer().start();
			new Consumer("").start();
			new Consumer("Google").start();
			new Consumer("Yahoo").start();
	}
}

Producer is prdoucing new value: Thread-01
Crunchify consumes value: Thread-01, List.size(): 1
 
Producer is prdoucing new value: Thread-02
Google consumes value: Thread-02, List.size(): 2
 
Producer is prdoucing new value: Thread-03
Yahoo consumes value: Thread-03, List.size(): 3
 
Producer is prdoucing new value: Thread-04
Crunchify consumes value: Thread-04, List.size(): 4


import java.util.concurrent.Semaphore;

public class SemaphoreExample {
  private static final int MAX_CONCURRENT_THREADS = 2;
  private final Semaphore AdminLOCK = 
  		new Semaphore(MAX_CONCURRENT_THREADS, true);
  
  public void StartTest() {
    for (int i = 0; i < 10; i++) {
      Person person = new Person();
      person.start();
    }
  }
  
  public class Person extends Thread {
    @Override
    public void run() {
      try {
        AdminLOCK.acquire();
      } catch (InterruptedException e) {
        System.out.println("received InterruptedException");
        return;
      }
      System.out.println("Thread " + this.getId() 
      		+ " start using 's car - Acquire()");
      try {
        sleep(1000);
      } catch (Exception e) { 
      } finally {
        AdminLOCK.release();
      }
      System.out.println("Thread " + this.getId() 
      		+ " stops using 's car -  Release()\n");
    }
  }
  
  public static void main(String[] args) {
    SemaphoreExample test = new SemaphoreExample();
    test.StartTest(); 
  }
}



Thread 11 start using Crunchify's car - Acquire()
Thread 10 start using Crunchify's car - Acquire()
Thread 10 stops using Crunchify's car -  Release()
 
Thread 12 start using Crunchify's car - Acquire()
Thread 13 start using Crunchify's car - Acquire()
Thread 11 stops using Crunchify's car -  Release()
 
Thread 13 stops using Crunchify's car -  Release()
 
Thread 15 start using Crunchify's car - Acquire()
Thread 14 start using Crunchify's car - Acquire()
Thread 12 stops using Crunchify's car -  Release()
 
Thread 14 stops using Crunchify's car -  Release()
 
Thread 16 start using Crunchify's car - Acquire()
Thread 15 stops using Crunchify's car -  Release()
 
Thread 17 start using Crunchify's car - Acquire()
Thread 17 stops using Crunchify's car -  Release()
 
Thread 18 start using Crunchify's car - Acquire()
Thread 19 start using Crunchify's car - Acquire()
Thread 16 stops using Crunchify's car -  Release()
 
Thread 18 stops using Crunchify's car -  Release()
 
Thread 19 stops using Crunchify's car -  Release()


class Table {  

	void printTable(int n){  
		synchronized(this) {//synchronized block  
			for(int i=1;i<=5;i++) {  
				System.out.println(n*i);  
				try{  
				 Thread.sleep(400);  
				} catch(Exception e){ System.out.println(e); }  
			}  
		}  
	}//end of the method  
}  
	
class MyThread1 extends Thread {  
	Table t;  
	MyThread1(Table t) {  
		this.t=t;  
	}  
	public void run() {  
		t.printTable(5);  
	}  
		
}  
class MyThread2 extends Thread {  
	Table t;  
	MyThread2(Table t){  
		this.t=t;  
	}  
	public void run(){  
		t.printTable(100);  
	}  
}  
	
public class SynchronizedBlockExample{  
	public static void main(String args[]){  
		Table obj = new Table();//only one object  
		MyThread1 t1 = new MyThread1(obj);  
		MyThread2 t2 = new MyThread2(obj);  
		t1.start();  
		t2.start();  
	}  
}  

5
10
15
20
25
100
200
300
400
500


==========================================================================================================================================================================

class Counter {
	int value = 0;

	public void increment() {
		int temp = value;	// read
		// this is just to randomly force a thread switch
		try {  
    	Thread.sleep(1000);  
    } catch(Exception e) { System.out.println(e); }
		temp++;						// add 
		value = temp; 		// write 
		System.out.println(value);
	}

}

class Door extends Thread {
	Counter people_;
  String name; 
  Door(Counter c, String n) {
    people_ = c;
    name = n;
  }

	public void run() {
		while (true) {
			System.out.println(name);
			synchronized(people_) {
				people_.increment(); 
			}
		}
	}
}

public class CriticalSectionExample {
	public static void main (String[] args) {
		Counter people_ = new Counter();
		Door west =  new Door(people_, "W:");
    Door east = new Door(people_, "E:");
		west.start();
		east.start();
	}
}





W:
E:
	1
W:
	2
E:
	3
W:
	4
E:
	5
W:
	6
E:
	7
W:
	8
E:
	9
W:
	10


W:
E:
	1
E:
	1
W:
	2
	2
W:
E:
	3
W:
	3
E:
	4
E:
	4
W:
	5
E:
	5
W:
	6
W:
	6
E:
	7
W:
	7
E:
	8
E:
	8
W:
	9
W:
	9
E:
	10


multithreading discussion


by default java applications are single threaded 

using other cores at the same time is possible using mutli threading 

use multithreading to speed up our applications 

common example of multithreading
	games 
		threads for
			- graphic
			- audio
			- algos 
			- etc.


Thread  in java is a running process

we need threading because there would be times when a certain collection is accessed by many application

data concurrency = making sure that ur data is consistent and updated

it should be able to update the changes u have made by other threads 

Non-concurrent collections
	- arraylist
	- hashset
	- treeset

synchronized collections
	= it locks an object so that no multiple threads can acces it 

thread monitor 
	= something that a thread obtains so that it can lock an object 

synchronized vs. concurrent collections

	synchronized = one thread can access a resource/object at a time
		synchronized collections:
			vector
			hashtable


	concurrent = many threads can access the resource; only locks the specific portion of code 
		multiple threads can access it 
		concurrent hashmap
		copyonwritearraylist
		simultaneous accessing

how to synchronized a non-synchronize object
	using synchronize keyword 
		synchronize block
		synchronize method

		// callable? search for this performs asychronization 


ExecutorService ExServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
other way to do threading, like implementing the runnable 


limited number of threads = semaphore
one = mutex

unlimited = concurrent collections


static synchronize can lock the entire class


Thread vs. implements Runnable 
	implemeting runnable is better because 
	http://stackoverflow.com/questions/541487/implements-runnable-vs-extends-thread
	In practical terms, it means you can implement Runnable and extend from another class as well.

how to start a thread?
	use start();

	nganung dili ang run nalang? ang .start() ang magpabuhat sa other thread to run iya i allow ang concurrency
	start() - // javadoc sa thread.start() ni 
	/**
     * Causes this thread to begin execution; the Java Virtual Machine
     * calls the <code>run</code> method of this thread.
     * <p>
     * The result is that two threads are running concurrently: the
     * current thread (which returns from the call to the
     * <code>start</code> method) and the other thread (which executes its
     * <code>run</code> method).
     * <p>
     * It is never legal to start a thread more than once.
     * In particular, a thread may not be restarted once it has completed
     * execution.*/

Threads


	.sleep()
		temporarily ceases threads eecution given the time 
		"i have to continue after the given amount of time"

	.yield()
		voluntarily gives up control of the resource
		only continues its task when u want it to
		CPU has a choice to grant the control back to it
		// in java mu line up ka balik sa thread "queue" 

	.join()
		i allow ang pag una ug proces sa lain na thread and then continue proccessing after the other thread has finished 

	.wait()
		same as sleep but other method in another thread can wake it up
		how does it wake it up?
			it notifies it by 
		waiting for another thread to finish using the resource 


deadlock

	two threads infinitely waiting for the resource


	// sample of Observer pattern design principle

	synchronized void bow(Friend bower) {
		System.out.format("%s: %s " + "has bowed to me!%n", this.name, bower.getName());
		bower.bowBack(this);
	}


	how to prevent deadlock?
		- dont hoard resources
		- dont wait inside a synchronized block 


semaphores

	in java u can assign priorities in ur thread 

	starvation happens when one thread uses a resource forever starving other threads 


using join u need to make sure that the incoming thread has higher priority 

synchronized : bathroom example 



STUDY ABOUT threading

