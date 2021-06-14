# LaunchDarkly Sample Java Application 

This is a simple console application that demonstrates how LaunchDarkly's Java SDK works with the LaunchDarkly Relay Proxy in Daemon Mode with Redis.

 Below, you'll find the basic build procedure, but for more comprehensive instructions, you can visit your [Quickstart page](https://app.launchdarkly.com/quickstart#/) or the [Java SDK reference guide](https://docs.launchdarkly.com/sdk/server-side/java).

## Build instructions 

This project uses [Gradle](https://gradle.org/). It requires that Java is already installed on your system (version 8 or higher). It will automatically use the latest release of the LaunchDarkly SDK with major version 5.

1. Edit `src/main/java/Hello.java` and set the value of `SDK_KEY` to your LaunchDarkly SDK key. If there is an existing boolean feature flag in your LaunchDarkly project that you want to evaluate, set `FEATURE_FLAG_KEY` to the flag key.  You will also need to set the value of `REDIS` to the URI of your Redis persistent datastore.

```java
  static final String SDK_KEY = "sdk-1234567890abcdef";

  static final String FEATURE_FLAG_KEY = "my-flag";

  static final String REDIS = "http://your-redis-URI";
```

2. On the command line, run `./gradlew run` (or, on Windows, `gradlew run`).

You should see the message `"Feature flag '<flag key>' is <true/false> for this user"`.
