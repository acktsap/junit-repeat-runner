/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import java.util.Objects;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * A non-repeating invoke method.
 *
 * @author acktsap
 * @since 0.1
 */
public class RepeatInvokeMethod extends Statement {

  protected final int count;

  protected final FrameworkMethod testMethod;

  protected final Object target;

  /**
   * Create a {@code RepeatInvokeMethod} instance.
   *
   * @param count      a count
   * @param testMethod a test method to invoke
   * @param target     an invocation target
   */
  public RepeatInvokeMethod(final int count, final FrameworkMethod testMethod,
      final Object target) {
    if (count <= 0) {
      throw new RuntimeException("Count must be > 0, but was " + count);
    }
    this.count = count;
    this.testMethod = Objects.requireNonNull(testMethod);
    this.target = Objects.requireNonNull(target);
  }

  @Override
  public void evaluate() throws Throwable {
    for (int i = 0; i < count; ++i) {
      testMethod.invokeExplosively(target);
    }
  }

  /**
   * Get repeat count.
   *
   * @return a repeat count
   */
  public int getCount() {
    return this.count;
  }

}