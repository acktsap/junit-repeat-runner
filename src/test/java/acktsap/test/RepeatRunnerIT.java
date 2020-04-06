/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.test;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RepeatRunner.class)
@Repeat(3)
public class RepeatRunnerIT {

  protected static Map<String, AtomicInteger> expected;
  protected static Map<String, AtomicInteger> actual;

  @BeforeClass
  public static void setUpBeforeClass() {
    final Map<String, AtomicInteger> expectedMap = new HashMap<>();
    expectedMap.put("shouldRun1TimesOnNoRepeat", new AtomicInteger(1));
    expectedMap.put("shouldRun1Times", new AtomicInteger(1));
    expectedMap.put("shouldRun5Times", new AtomicInteger(5));
    expectedMap.put("shouldRun10Times", new AtomicInteger(10));
    expected = Collections.unmodifiableMap(expectedMap);

    final Map<String, AtomicInteger> actualMap = new HashMap<>();
    expectedMap.keySet().stream()
        .forEach(k -> actualMap.put(k, new AtomicInteger(0)));
    actual = new ConcurrentHashMap<>(actualMap);
  }

  @Test
  public void shouldRun1TimesOnNoRepeat() {
    actual.get("shouldRun1TimesOnNoRepeat").incrementAndGet();
  }

  @Test
  @Repeat(1)
  public void shouldRun1Times() {
    actual.get("shouldRun1Times").incrementAndGet();
  }

  @Test
  @Repeat(5)
  public void shouldRun5Times() {
    actual.get("shouldRun5Times").incrementAndGet();
  }

  @Test
  @Repeat(10)
  public void shouldRun10Times() {
    actual.get("shouldRun10Times").incrementAndGet();
  }

  @AfterClass
  public static void tearDownAfterClass() {
    final boolean matchResult = actual.entrySet().stream()
        .allMatch(e -> expected.get(e.getKey()).get() == e.getValue().get());
    assertTrue("Expected: " + expected + " but was: " + actual, matchResult);
  }

}