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
public class NoRepeatClassBlockTest {

  @Test
  public void testEvaluate() throws Throwable {
    final AtomicInteger count = new AtomicInteger(0);
    final NoRepeatClassBlock noRepeatClassBlock = new NoRepeatClassBlock(new Statement() {
      @Override
      public void evaluate() throws Throwable {
        count.incrementAndGet();
      }
    });
    noRepeatClassBlock.evaluate();
    assertEquals(1, count.get());
  }

}
