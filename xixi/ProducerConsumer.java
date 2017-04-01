package xixi;


/*    范例名称：生产者--消费者问题
 *     源文件名称：ProducerConsumer.java
 *    要 ：
 *        1. 共享数据的不一致性/临界资源的保护
 *        2. Java对象锁的概念
 *        3. synchronized关键字/wait()及notify()方法
 */

public class ProducerConsumer {
    public static void main(String args[]){
            SyncStack stack = new SyncStack();
            Runnable p=new Producer(stack);
            Runnable c = new Consumer(stack);
            Thread p1 = new Thread(p);
            Thread c1 = new Thread(c);
            
            p1.start();
            c1.start();
    }
}


class SyncStack{  //支持多线程同步操作的堆栈的实现
    private int index = 0;//篮子里面还有多少个包子
    private char []data = new char[6];    
    public synchronized void push(char c){
        if(index == data.length){
        try{
                this.wait();
        }catch(InterruptedException e){}
        }
        this.notify();
        data[index] = c;
        index++;
    }
    public synchronized char pop(){
    	
    	if(index ==0){
            try{
                this.wait();
            }catch(InterruptedException e){}
        }
    	System.out.println("index是包子，现在还有："+ index+"个");
        this.notify();
        index--;
        return data[index];
    }
}


class  Producer implements Runnable{
    SyncStack stack;    
    public Producer(SyncStack s){
        stack = s;
    }
    public void run(){
        for(int i=0; i<20; i++){
            char c =(char)(Math.random()*26+'A');
            stack.push(c);
            System.out.println("produced："+i+"  "+c);
            try{                                    
                Thread.sleep((int)(Math.random()*1000)); 
            }catch(InterruptedException e){
            }
        }
    }
}


class Consumer implements Runnable{
    SyncStack stack;    
    public Consumer(SyncStack s){
        stack = s;
    }
    public void run(){
        for(int i=0;i<20;i++){
        	
            char c = stack.pop();
            System.out.println("消费："+c);
            try{                                       
                Thread.sleep((int)(Math.random()*1000));
            }catch(InterruptedException e){
            }
        }
    }
}
