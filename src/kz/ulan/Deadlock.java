package kz.ulan;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Ulan on 1/16/2023
 */

public class Deadlock {

    public static void main(String[] args) {
        var a = new Object();
        var b = new Object();

        var barrier = new CyclicBarrier(2);

        new Thread(() -> {
            synchronized (a) {
                System.out.println("Catch A");
                try {
                    barrier.await(); // sync
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    System.out.println("Now catch B");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println("Catch B");
                try {
                    barrier.await(); // sync
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                synchronized (a) {
                    System.out.println("Now catch A");
                }
            }
        }).start();
    }
}
