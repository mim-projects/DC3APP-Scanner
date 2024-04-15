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
public class IndicatorWatcher implements Runnable, WatcherCommand {

    private WatcherListener mListener;
    private Instant start;

    public IndicatorWatcher(WatcherListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void run() {
        boolean wait = true;
        start = Instant.now();
        while (wait) {
            if (!Singleton.getInstance().isIndicatorControl()) {
                //Singleton.getInstance().setControl(false);
                wait = false;
            }
            Instant finish = Instant.now();
            Long elapsed = Duration.between(start, finish).toSeconds();
            if (elapsed > 10) {
                wait = false;
                if (mListener != null) {
                    mListener.timedOutIndicators();
                }
            }
        }
        System.out.println("TERMINEEEE....... FOR INDICATORS............!!!!");

    }

    @Override
    public void restart() {
        System.out.println("updating.... start time... for indicators");
        start = Instant.now();
    }

    public interface WatcherListener {

        public void timedOutIndicators();
    }
}
