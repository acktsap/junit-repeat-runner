/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static java.util.Collections.synchronizedSet;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.FrameworkMethod;

@RunWith(JUnit4.class)
public class ConcurrentRepeatInvokeMethodTest {

  @Test
  public void testEvaluate() throws Throwable {
    // given
    final int parallelism = 5;
    final int expectedCount = 10;
    final Set<String> usedThreads = new HashSet<>();
    final Class<ConcurrentRepeatStatementTestClass> klass =
        ConcurrentRepeatStatementTestClass.class;

    // when
    final CountDownLatch latch = new CountDownLatch(parallelism);
    final AtomicInteger count = new AtomicInteger(0);
    final FrameworkMethod method = new FrameworkMethod(klass.getMethod("test"));
    final ConcurrentRepeatStatementTestClass instance = klass
        .getConstructor(Set.class, CountDownLatch.class, AtomicInteger.class)
        .newInstance(usedThreads, latch, count);
    final ConcurrentRepeatInvokeMethod repeatInvokeMethod = new ConcurrentRepeatInvokeMethod(
        parallelism, expectedCount, method, instance);
    repeatInvokeMethod.evaluate();
    latch.await();

    // then
    assertEquals(parallelism, usedThreads.size());
    assertEquals(expectedCount, count.get());
  }

  @Test
  public void shouldThrowExceptionOnInvalidParalleism() throws Throwable {
    // given
    final Class<ConcurrentRepeatStatementTestClass> klass =
        ConcurrentRepeatStatementTestClass.class;
    final FrameworkMethod method = new FrameworkMethod(klass.getMethod("test"));
    final ConcurrentRepeatStatementTestClass instance = klass.newInstance();

    // when
    try {
      new ConcurrentRepeatInvokeMethod(0, 1, method, instance);
    } catch (RuntimeException e) {
      // then
    }
  }

  @Test
  public void shouldThrowExceptionOnInvalidCount() throws Throwable {
    // given
    final Class<ConcurrentRepeatStatementTestClass> klass =
        ConcurrentRepeatStatementTestClass.class;
    final FrameworkMethod method = new FrameworkMethod(klass.getMethod("test"));
    final ConcurrentRepeatStatementTestClass instance = klass.newInstance();

    // when
    try {
      new ConcurrentRepeatInvokeMethod(1, 0, method, instance);
    } catch (RuntimeException e) {
      // then
    }
  }

  public static class ConcurrentRepeatStatementTestClass {

    protected final Set<String> usedThreads;

    protected final CountDownLatch countDownLatch;

    protected final AtomicInteger count;

    public ConcurrentRepeatStatementTestClass() {
      this.usedThreads = null;
      this.countDownLatch = null;
      this.count = null;
    }

    public ConcurrentRepeatStatementTestClass(final Set<String> usedThreads,
        final CountDownLatch countDownLatch, final AtomicInteger count) {
      this.usedThreads = synchronizedSet(usedThreads);
      this.countDownLatch = countDownLatch;
      this.count = count;
    }

    public void test() {
      usedThreads.add(Thread.currentThread().getName());
      countDownLatch.countDown();
      count.incrementAndGet();
    }

  }

}