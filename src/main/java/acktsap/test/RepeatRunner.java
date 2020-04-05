/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

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
  protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
    return super.methodInvoker(method, test);
  }

}