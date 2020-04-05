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
public class RepeatInvokeMethodTest {

  @Test
  public void testEvaluate() throws Throwable {
    // given
    final AtomicInteger count = new AtomicInteger(0);
    final Class<RepeatStatementTestClass> klass = RepeatStatementTestClass.class;
    final FrameworkMethod method = new FrameworkMethod(klass.getMethod("test"));
    final RepeatStatementTestClass instance = klass.getConstructor(AtomicInteger.class)
        .newInstance(count);

    // then
    final int expected = 5;
    final RepeatInvokeMethod repeatInvokeMethod = new RepeatInvokeMethod(expected, method,
        instance);
    repeatInvokeMethod.evaluate();
    assertEquals(expected, count.get());
  }

  @Test
  public void shouldThrowExceptionOnInvalidCount() throws Throwable {
    // given
    final Class<RepeatStatementTestClass> klass = RepeatStatementTestClass.class;
    final FrameworkMethod method = new FrameworkMethod(klass.getMethod("test"));
    final RepeatStatementTestClass instance = klass.getConstructor(AtomicInteger.class)
        .newInstance(new AtomicInteger(0));

    // when
    try {
      new RepeatInvokeMethod(0, method, instance);
    } catch (RuntimeException e) {
      // then
    }
  }

  public static class RepeatStatementTestClass {

    protected final AtomicInteger count;

    public RepeatStatementTestClass(final AtomicInteger count) {
      this.count = count;
    }

    public void test() {
      count.incrementAndGet();
    }

  }
}