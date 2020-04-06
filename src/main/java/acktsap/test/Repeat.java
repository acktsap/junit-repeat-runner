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
 * &#064;RunWith(RepeatRunner.class)
 * public class SomeTest {
 *
 *   &#064;Test
 *   &#064;Repeat(value = 10, parallelism = 3)
 *   public void repeat10TimesParallelism3() {
 *     // repeat 10 times on 3 threads
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
 *   // repeat 10 times on 3 threads
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Repeat {

  /**
   * A repeat count. It should be &gt; 0.
   *
   * @return a repeat count
   * @since 0.1
   */
  int value();

  /**
   * A repeat parallelism. It should be &gt; 0. Default is 1.
   *
   * @return a parallelism
   * @since 0.2
   */
  int parallelism() default 1;

}