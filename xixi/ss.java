package xixi;

public class ss {

	private long b = 0;
	public void set1(){
		b = 0 ;
	}
	public void set2(){
		b = -1;
	}
	public void check(){
		System.out.println(b);
		if(0 != b && -1 != b){
			System.out.println("Error");
		}
	}
	public static void main(String[] args) {
		final ss s1 = new ss();
		
		//线程1 ：设置b =0 
		final Thread t1 = new Thread(){
			public void run(){
				while(true){
					s1.set1();
				}
			};
		};
		t1.start();
		
		//线程2 ： 设置 b = -1
		final Thread t2 = new Thread(){
			public void run(){
				while(true){
					s1.set2();
				}
			};
		};
		t2.start();
		
		
		//线程 3 : 检查 0 != b && -1 != b
		final Thread t3  = new Thread(){
			public void run(){
				while(true){
					s1.check();
				}
			};
		};
		t3.start();
	}
}
