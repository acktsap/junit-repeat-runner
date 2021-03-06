/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import java.util.Objects;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * An non-repeating invoke method.
 *
 * @author acktsap
 * @since 0.1
 */
public class NoRepeatInvokeMethod extends Statement {

  protected final FrameworkMethod testMethod;

  protected final Object target;

  /**
   * Create an {@code NoRepeatInvokeMethod} instance.
   *
   * @param testMethod a test method to invoke
   * @param target     an invocation target
   */
  public NoRepeatInvokeMethod(final FrameworkMethod testMethod,
      final Object target) {
    this.testMethod = Objects.requireNonNull(testMethod);
    this.target = Objects.requireNonNull(target);
  }

  @Override
  public void evaluate() throws Throwable {
    testMethod.invokeExplosively(target);
  }

}