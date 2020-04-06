/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import java.util.Objects;
import org.junit.runners.model.Statement;

/**
 * A non-repeating class block.
 *
 * @author acktsap
 * @since 0.2
 */
public class NoRepeatClassBlock extends Statement {

  protected final Statement classBlock;

  /**
   * Create an {@code NoRepeatClassBlock} instance.
   *
   * @param classBlock a class block
   */
  public NoRepeatClassBlock(final Statement classBlock) {
    this.classBlock = Objects.requireNonNull(classBlock);
  }

  @Override
  public void evaluate() throws Throwable {
    classBlock.evaluate();
  }

}
