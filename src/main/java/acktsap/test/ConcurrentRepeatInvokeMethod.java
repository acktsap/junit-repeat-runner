/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * A concurrent repeating invoke method.
 *
 * @author acktsap
 */
public class ConcurrentRepeatInvokeMethod extends Statement {

  protected final int parallelism;

  protected final int count;

  protected final FrameworkMethod testMethod;

  protected final Object target;

  /**
   * Create a {@code ConcurrentRepeatInvokeMethod} instance.
   *
   * @param parallelism a parallelism level
   * @param count       a count
   * @param testMethod  a test method to invoke
   * @param target      an invocation target
   */
  public ConcurrentRepeatInvokeMethod(final int parallelism, final int count,
      final FrameworkMethod testMethod, final Object target) {
    if (parallelism <= 0) {
      throw new RuntimeException("Parallelism level must be > 0, but was " + parallelism);
    }
    if (count <= 0) {
      throw new RuntimeException("Count must be > 0, but was " + count);
    }
    this.parallelism = parallelism;
    this.count = count;
    this.testMethod = Objects.requireNonNull(testMethod);
    this.target = Objects.requireNonNull(target);
  }

  @Override
  public void evaluate() throws Throwable {
    final ExecutorService executorService = Executors.newFixedThreadPool(parallelism);
    try {
      final List<Callable<Object>> callableList = new ArrayList<>(count);
      final Callable<Object> callable = () -> {
        try {
          testMethod.invokeExplosively(target);
          return 0;
        } catch (Throwable e) {
          throw new RuntimeException(e);
        }
      };
      for (int i = 0; i < count; ++i) {
        callableList.add(callable);
      }
      final List<Future<Object>> futures = executorService.invokeAll(callableList);
      for (final Future<Object> future : futures) {
        future.get();
      }
    } finally {
      executorService.shutdownNow();
    }
  }

  /**
   * Get parallelism level.
   *
   * @return a parallelism level
   */
  public int getParallelism() {
    return this.parallelism;
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