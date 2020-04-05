package acktsap.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A repeat annotation.
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
 * </pre>
 *
 * @author acktsap
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Repeat {

  /**
   * A repeat count. It should be &gt; 0.
   *
   * @return a repeat count
   * @since 0.1
   */
  int value();

}