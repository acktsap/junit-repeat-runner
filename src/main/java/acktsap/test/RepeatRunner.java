/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static java.util.Optional.ofNullable;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * A custom {@link org.junit.runner.Runner} for repeating test.
 *
 * <p>Usage:
 *
 * <pre>
 *
 * &#064;RunWith(RepeatRunner.class)
 * public class SomeTest {
 *
 *   &#064;Test
 *   &#064;Repeat(10)
 *   public void repeat10Times() {
 *     // repeat 10 times
 *   }
 * }
 *
 * &#064;RunWith(RepeatRunner.class)
 * public class SomeTest {
 *
 *   &#064;Test
 *   &#064;Repeat(value = 10, parallelism = 3)
 *   public void repeat10TimesParallelism3() {
 *     // repeat 10 times in a 3 thread
 *   }
 * }
 *
 * &#064;RunWith(RepeatRunner.class)
 * &#064;Repeat(3)
 * public class SomeTest {
 *
 *   // repeat 3 times
 *
 *   &#064;Test
 *   public void test1() {
 *   }
 *
 *   &#064;Test
 *   public void test2() {
 *   }
 * }
 *
 * &#064;RunWith(RepeatRunner.class)
 * &#064;Repeat(value = 10, parallelism = 3)
 * public class SomeTest {
 *
 *   // repeat 10 times in a 3 thread
 *
 *   &#064;Test
 *   public void test1() {
 *   }
 *
 *   &#064;Test
 *   public void test2() {
 *   }
 * }
 * </pre>
 *
 * @author acktsap
 * @since 0.1
 */
public class RepeatRunner extends BlockJUnit4ClassRunner {

  /**
   * Creates a RepeatRunner to run {@code klass}.
   *
   * @param klass a class to run
   * @throws InitializationError if the test class is malformed.
   */
  public RepeatRunner(final Class<?> klass) throws InitializationError {
    super(klass);
  }

  @Override
  protected Statement classBlock(final RunNotifier notifier) {
    final Statement classBlock = super.classBlock(notifier);
    return ofNullable(getTestClass().getAnnotation(Repeat.class))
        .map(r -> r.parallelism() > 1
            ? new ConcurrentRepeatClassBlock(r.parallelism(), r.value(), classBlock)
            : new RepeatClassBlock(r.value(), classBlock))
        .map(Statement.class::cast)
        .orElse(new NoRepeatClassBlock(classBlock));
  }

  @Override
  protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
    return ofNullable(method.getAnnotation(Repeat.class))
        .map(r -> r.parallelism() > 1
            ? new ConcurrentRepeatInvokeMethod(r.parallelism(), r.value(), method, test)
            : new RepeatInvokeMethod(r.value(), method, test))
        .map(Statement.class::cast)
        .orElse(new NoRepeatInvokeMethod(method, test));
  }

}
