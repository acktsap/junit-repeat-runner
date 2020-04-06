# JUnit Repeat Runner

A custom junit runner for junit 4.

## Build

- Prerequisite
  - jdk8
- Lint: `./gradlew check`
- Test: `./gradlew test`
- Build (also lint, test): `./gradlew clean build`
- Install to local: `./gradlew clean publishToMavenLocal`

## Install

Git clone

```shell script
> git clone https://github.com/acktsap/junit-repeat-runner.git
```

Install to maven local

```shell script
> ./gradlew clean publishToMavenLocal
```

Gradle

```
dependencies {
  testImplementation "junit:junit:4.12"  // need junit
  testImplementation "acktsap:junit-repeat-runner:${version}"
}
```

## Usage

Repeat a test method.

```java
@RunWith(RepeatRunner.class)
public class SomeTest {
  @Test
  @Repeat(5)
  public void testRepeat5() {
    // repeat 5 times
  }
}
```

Repeat a test method in parallel.

```java
@RunWith(RepeatRunner.class)
public class SomeTest {
  @Test
  @Repeat(value = 5, parallelism = 3)
  public void testRepeat5Parallelism3() {
    // repeat 5 times on 3 threads
  }
}
```

Repeat all test methods in a class.

```java
@RunWith(RepeatRunner.class)
@Repeat(3)
public class SomeTest {
  // repeat 3 times

  @Test
  public void test1() {
  }

  @Test
  public void test2() {
  }
}
```

Repeat all test methods in a class in parallel.

```java
@RunWith(RepeatRunner.class)
@Repeat(value = 5, parallelism = 3)
public class SomeTest {
  // repeat 5 times on 3 threads

  @Test
  public void test1() {
  }

  @Test
  public void test2() {
  }
}
```
