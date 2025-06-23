package com.one.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Author Fox
 */
public interface Lock {

   void lock();


   void unlock();
}
