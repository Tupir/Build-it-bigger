package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.*;


@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest{

    // from https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
    MainActivityFragment mainActivityFragment = new MainActivityFragment();
    @Test
    public void testVerifyJoke() throws InterruptedException {
        assertTrue(true);
        final CountDownLatch latch = new CountDownLatch(1);
        EndpointAsyncTask endpointAsyncTask = new EndpointAsyncTask() {
            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                assertTrue(!result.isEmpty());
                assertTrue(result != null);
                System.out.println(result);
                latch.countDown();
            }
        };
        endpointAsyncTask.execute(mainActivityFragment);
        latch.await();
    }

}