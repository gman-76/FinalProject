package com.udacity.gradle.builditbigger;

import android.util.Log;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Gerhard on 09/11/2015.
 */
public class GCETest extends TestCase implements GCEListener{
    private String joke = null;
    private CountDownLatch cdl;
    public void testGCE(){
        cdl = new CountDownLatch(1);
        GCE gce = new GCE(this);
        gce.execute();
        try {
            cdl.await();
            assertTrue(joke != null);
        }catch(InterruptedException e){
            assertTrue(false);
        }
    }
    @Override
    public void callComplete(String returnValue) {
        Log.d("GSETEST","received " + returnValue + " from GCE");
        joke = returnValue;
        cdl.countDown();
    }
}
