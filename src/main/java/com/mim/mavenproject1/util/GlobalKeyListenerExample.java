/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mim.mavenproject1.util;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;



/**
 *
 * @author robb
 */
public class GlobalKeyListenerExample implements NativeKeyListener {

    private ReporterListener mListener;

    public GlobalKeyListenerExample(ReporterListener mListener) {
        this.mListener = mListener;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if (mListener == null) {
            return;
        }

        final String key = e.getKeyText(e.getKeyCode());

        System.out.println("Key Typed: " + key);
        if (!Singleton.getInstance().isControl()) {
            Singleton.getInstance().setControl(true);
            mListener.launchWatcher();
        }

        Singleton.getInstance().addElement(key);
        mListener.restartCount();
        if (Singleton.getInstance().isControl()) {
            if (Singleton.getInstance().getKeyList().size() == 9) {
                mListener.validCodeResult();
            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

        /*if (mListener == null) {
            return;
        }

        final String key = e.getKeyText(e.getKeyCode());

        System.out.println("Key Typed: " + key);
        if (!Singleton.getInstance().isControl()) {
            Singleton.getInstance().setControl(true);
            mListener.launchWatcher();
        }

        Singleton.getInstance().addElement(key);
        mListener.restartCount();
        if (Singleton.getInstance().isControl()) {
            if (Singleton.getInstance().getKeyList().size() == 6) {
                mListener.validCodeResult();
            }
        }*/
    }

    /*public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
    }*/
    public interface ReporterListener {

        public void launchWatcher();

        public void validCodeResult();

        public void restartCount();
    }
}
