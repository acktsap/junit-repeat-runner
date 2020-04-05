/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@RunWith(JUnit4.class)
public class RepeatRunnerTest {

  @Test
  public void testMethodInvoker() throws Throwable {
    final Class<TestClass> klass = TestClass.class;
    final RepeatRunner repeatRunner = new RepeatRunner(klass);
    final Method method = klass.getMethod("test");
    final TestClass instance = klass.newInstance();
    final Statement statement = repeatRunner
        .methodInvoker(new FrameworkMethod(method), instance);
    assertNotNull(statement);
  }

  public static class TestClass {

    @Test
    public void test() {
    }

  }

}
