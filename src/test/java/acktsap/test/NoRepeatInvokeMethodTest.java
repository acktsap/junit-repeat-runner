/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.FrameworkMethod;

@RunWith(JUnit4.class)
public class NoRepeatInvokeMethodTest {

  @Test
  public void testEvaluate() throws Throwable {
    // given
    final AtomicInteger count = new AtomicInteger(0);
    final Class<NoRepeatStatementTestClass> klass = NoRepeatStatementTestClass.class;
    final FrameworkMethod method = new FrameworkMethod(klass.getMethod("test"));
    final NoRepeatStatementTestClass instance = klass.getConstructor(AtomicInteger.class)
        .newInstance(count);

    // then
    final int expected = 1;
    final NoRepeatInvokeMethod noRepeatInvokeMethod = new NoRepeatInvokeMethod(method, instance);
    noRepeatInvokeMethod.evaluate();
    assertEquals(expected, count.get());
  }

  public static class NoRepeatStatementTestClass {

    protected final AtomicInteger count;

    public NoRepeatStatementTestClass(final AtomicInteger count) {
      this.count = count;
    }

    public void test() {
      count.incrementAndGet();
    }

  }
}