/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@RunWith(JUnit4.class)
public class RepeatRunnerTest {

  @SuppressWarnings("unchecked")
  @Test
  public void testMethodInvoker() throws Throwable {
    final Object[][] parameters = {
        {"repeat1", 1},
        {"repeat5", 5},
        {"repeat10", 10},
    };

    final Class<RepeatRunnerTestClass> klass = RepeatRunnerTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    for (final Object[] parameter : parameters) {
      // when
      final String name = (String) parameter[0];
      final int count = (int) parameter[1];
      final Method method = klass.getMethod(name);
      final RepeatRunnerTestClass instance = klass.newInstance();

      // then
      final Statement statement = repeatRunner
          .methodInvoker(new FrameworkMethod(method), instance);
      assertEquals(RepeatInvokeMethod.class, statement.getClass());
      assertEquals(count, ((RepeatInvokeMethod) statement).getCount());
    }
  }

  @Test
  public void shouldReturnNoRepeatInvokeMethodOnNoRepeat() throws Throwable {
    // given
    final Class<RepeatRunnerTestClass> klass = RepeatRunnerTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    final Method method = klass.getMethod("noRepeat");
    final RepeatRunnerTestClass instance = klass.newInstance();

    // then
    final Statement statement = repeatRunner
        .methodInvoker(new FrameworkMethod(method), instance);
    assertEquals(NoRepeatInvokeMethod.class, statement.getClass());
  }

  @Test
  public void shouldThrowErrorOnInvalidRepeat() throws Throwable {
    // given
    final Class<RepeatRunnerTestClass> klass = RepeatRunnerTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    final Method method = klass.getMethod("invalidRepeat");
    final RepeatRunnerTestClass instance = klass.newInstance();

    try {
      // when
      repeatRunner.methodInvoker(new FrameworkMethod(method), instance);
    } catch (RuntimeException e) {
      // then
    }
  }

  public static class RepeatRunnerTestClass {

    @Test
    @Repeat(1)
    public void repeat1() {
    }

    @Test
    @Repeat(5)
    public void repeat5() {

    }

    @Test
    @Repeat(10)
    public void repeat10() {
    }

    @Test
    public void noRepeat() {
    }

    @Test
    @Repeat(0)
    public void invalidRepeat() {
    }

  }

}