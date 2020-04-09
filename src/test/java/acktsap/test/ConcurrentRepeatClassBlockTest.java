/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static java.util.Collections.synchronizedSet;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.Statement;

@RunWith(JUnit4.class)
public class ConcurrentRepeatClassBlockTest {

  @Test
  public void testEvaluate() throws Throwable {
    // when
    final int parallelism = 5;
    final int expected = 10;
    final Set<String> usedThreads = synchronizedSet(new HashSet<>());
    final AtomicInteger count = new AtomicInteger(0);
    final ConcurrentRepeatClassBlock noRepeatClassBlock = new ConcurrentRepeatClassBlock(
        parallelism, expected, new Statement() {
      @Override
      public void evaluate() throws Throwable {
        usedThreads.add(Thread.currentThread().getName());
        count.incrementAndGet();
      }
    });
    noRepeatClassBlock.evaluate();

    // then
    assertEquals(parallelism, usedThreads.size());
    assertEquals(expected, count.get());
  }

  @Test
  public void shouldThrowExceptionOnInvalidParallelism() throws Throwable {
    try {
      // when
      new ConcurrentRepeatClassBlock(0, 1, new Statement() {
        @Override
        public void evaluate() throws Throwable {
        }
      });
    } catch (RuntimeException e) {
      // then
    }
  }

  @Test
  public void shouldThrowExceptionOnInvalidCount() throws Throwable {
    try {
      // when
      new ConcurrentRepeatClassBlock(1, 0, new Statement() {
        @Override
        public void evaluate() throws Throwable {
        }
      });
    } catch (RuntimeException e) {
      // then
    }
  }

}
