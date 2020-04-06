/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.Statement;

@RunWith(JUnit4.class)
public class RepeatClassBlockTest {

  @Test
  public void testEvaluate() throws Throwable {
    final int expected = 5;
    final AtomicInteger count = new AtomicInteger(0);
    final RepeatClassBlock noRepeatClassBlock = new RepeatClassBlock(expected, new Statement() {
      @Override
      public void evaluate() throws Throwable {
        count.incrementAndGet();
      }
    });
    noRepeatClassBlock.evaluate();
    assertEquals(expected, count.get());
  }

  @Test
  public void shouldThrowExceptionOnInvalidCount() throws Throwable {
    try {
      // when
      new RepeatClassBlock(0, new Statement() {
        @Override
        public void evaluate() throws Throwable {
        }
      });
    } catch (RuntimeException e) {
      // then
    }
  }

}
