package org.example;

import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 *  * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 *  * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 *  * 写出你的方法，越多越好，提交到github。
 *  *
 */
public class Homework03 {

    private static Integer result = 0;
    //设置锁,闭锁
    static CountDownLatch countDownLatch = new CountDownLatch(1);
    //栅栏
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    public static void main1(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();


        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        FutureTask futureTask = FutureTask();
//        futureTask.run();
        System.out.println("开始继续之后的逻辑");



        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 确保  拿到result 并输出
//        System.out.println("异步计算结果为："+futureTask.get());
        // 然后退出main线程


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sumResult = sum();
                result = sumResult;
            }
        });
        thread.run();
        while(result==0){

        }
        System.out.println("第二种方法，while死循环，sumResult=" + result);


    }
    public static void main2(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("开始");

        Integer sumRes = new Integer(0);
        MyWork myWork = new MyWork(result,countDownLatch);
        myWork.start();
        countDownLatch.await();
        System.out.println("结束,result="+result);
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {

        System.out.println("开始");

        Integer sumRes = new Integer(0);
        MyWork myWork = new MyWork(result,countDownLatch);
        myWork.start();
        cyclicBarrier.await();
        System.out.println("结束,result="+result);
    }

    static class MyWork extends Thread {
        private Integer sum;
        private CountDownLatch countDownLatch;
        MyWork(Integer sum,CountDownLatch downLatch) {
            this.sum = sum;
            this.countDownLatch = downLatch;
        }
        @Override
        public void run() {
            result = sum();
            try {
                cyclicBarrier.await();
                System.out.println("子线程准备好了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//            countDownLatch.countDown();
        }
    }

    public static FutureTask FutureTask(){
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return sum();
            }
        });
        futureTask.run();
        return futureTask;
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
