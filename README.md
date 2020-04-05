# JUnit Repeat Runner

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
  implementation "acktsap:junit-repeat-runner:${version}"
}
```

## Usage

```java
@RunWith(RepeatRunner.class)
public class SomeTest {
  @Test
  @Repeat(5)
  public void testRepeat5() {
    // repeat 5 times
  }

  @Test
  @Repeat(10)
  public void testRepeat10() {
    // repeat 10 times
  }
}
```
