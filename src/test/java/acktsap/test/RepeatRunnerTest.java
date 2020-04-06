/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.JUnit4;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@RunWith(JUnit4.class)
public class RepeatRunnerTest {

  @Test
  public void testClassBlock() throws Throwable {
    final Object[][] parameters = {
        {ClassRepeat1TestClass.class, 1},
        {ClassRepeat5TestClass.class, 5},
    };

    for (final Object[] parameter : parameters) {
      // when
      final Class<?> klass = (Class<?>) parameter[0];
      final int count = (int) parameter[1];
      final RepeatRunner repeatRunner = new RepeatRunner(klass);

      // then
      final Statement statement = repeatRunner.classBlock(new RunNotifier());
      assertEquals(RepeatClassBlock.class, statement.getClass());
      assertEquals(count, ((RepeatClassBlock) statement).getCount());
    }
  }

  @Test
  public void shouldReturnNoRepeatClassBlockOnNoRepeat() throws Throwable {
    // when
    final Class<?> klass = NoRepeatTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);

    // then
    final Statement statement = repeatRunner.classBlock(new RunNotifier());
    assertEquals(NoRepeatClassBlock.class, statement.getClass());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testMethodInvoker() throws Throwable {
    final Object[][] parameters = {
        {"repeat1", 1},
        {"repeat5", 5},
        {"repeat10", 10},
    };

    final Class<MethodRepeatRunnerTestClass> klass = MethodRepeatRunnerTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    for (final Object[] parameter : parameters) {
      // when
      final String name = (String) parameter[0];
      final int count = (int) parameter[1];
      final Method method = klass.getMethod(name);
      final MethodRepeatRunnerTestClass instance = klass.newInstance();

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
    final Class<MethodRepeatRunnerTestClass> klass = MethodRepeatRunnerTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    final Method method = klass.getMethod("noRepeat");
    final MethodRepeatRunnerTestClass instance = klass.newInstance();

    // then
    final Statement statement = repeatRunner
        .methodInvoker(new FrameworkMethod(method), instance);
    assertEquals(NoRepeatInvokeMethod.class, statement.getClass());
  }

  @Test
  public void shouldThrowErrorOnInvalidRepeat() throws Throwable {
    // given
    final Class<MethodRepeatRunnerTestClass> klass = MethodRepeatRunnerTestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    final Method method = klass.getMethod("invalidRepeat");
    final MethodRepeatRunnerTestClass instance = klass.newInstance();

    try {
      // when
      repeatRunner.methodInvoker(new FrameworkMethod(method), instance);
    } catch (RuntimeException e) {
      // then
    }
  }

  public static class MethodRepeatRunnerTestClass {

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

  @Repeat(1)
  public static class ClassRepeat1TestClass {

    @Test
    public void test() {
    }

  }

  @Repeat(5)
  public static class ClassRepeat5TestClass {

    @Test
    public void test() {
    }

  }

  public static class NoRepeatTestClass {

    @Test
    public void test() {
    }

  }

}