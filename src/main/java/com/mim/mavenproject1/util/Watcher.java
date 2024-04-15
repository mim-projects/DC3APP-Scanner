/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mim.mavenproject1.util;


import com.mim.mavenproject1.application.Main;
import com.mim.mavenproject1.util.interfaces.WatcherCommand;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author robb
 */
public class Watcher implements Runnable, WatcherCommand {

    private WatcherListener mListener;
    private Instant start;

    public Watcher(WatcherListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void run() {
        boolean wait = true;
        start = Instant.now();
        while (wait) {
            if (!Singleton.getInstance().isControl()) {
                //Singleton.getInstance().setControl(false);
                wait = false;
            }
            Instant finish = Instant.now();
            Long elapsed = Duration.between(start, finish).toMillis();
            if (elapsed > 40) {
                wait = false;
                if (mListener != null) {
                    mListener.timedOut();
                }
            }
        }
        System.out.println("TERMINEEEE.......!!!!");

    }

    @Override
    public void restart() {
        System.out.println("updating.... start time...");
        start = Instant.now();
    }

    public interface WatcherListener {

        public void timedOut();
    }
}
