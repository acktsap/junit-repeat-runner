/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import java.util.Objects;
import org.junit.runners.model.Statement;

/**
 * A repeating class block.
 *
 * @author acktsap
 */
public class RepeatClassBlock extends Statement {

  protected final int count;

  protected final Statement classBlock;

  /**
   * Create a {@code RepeatClassBlock} instance.
   *
   * @param count      a count
   * @param classBlock a class block
   */
  public RepeatClassBlock(final int count, final Statement classBlock) {
    if (count <= 0) {
      throw new RuntimeException("Count must be > 0, but was " + count);
    }
    this.count = count;
    this.classBlock = Objects.requireNonNull(classBlock);
  }

  @Override
  public void evaluate() throws Throwable {
    for (int i = 0; i < count; ++i) {
      classBlock.evaluate();
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
