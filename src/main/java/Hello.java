import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.*;
import com.launchdarkly.sdk.server.integrations.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Hello {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "YOUR SDK KEY";

  // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
  static final String FEATURE_FLAG_KEY = "YOUR FLAG KEY";

  // Set RELAY_PROXY to the URI of the LaunchDarkly Relay Proxy.
  static final String RELAY_PROXY ="YOUR RELAY PROXY URI";

  // Set REDIS to the URI of the Redis persistent datastore.
  static final String REDIS = "YOUR REDIS URI";

  // Set REDIS_PREFIX to the prefix name of the cache for your environment.
  static final String REDIS_PREFIX ="YOUR REDIS PREFIX";

  private static void showMessage(String s) {
    System.out.println("*** " + s);
    System.out.println();
  }

  public static void main(String... args) throws IOException {
    if (SDK_KEY.equals("")) {
      showMessage("Please edit Hello.java to set SDK_KEY to your LaunchDarkly SDK key first");
      System.exit(1);
    }

    URI uri;
    try {
      uri = new URI(RELAY_PROXY);
      LDConfig config  = new LDConfig.Builder()
        .events(Components.sendEvents().baseURI(uri)
        )
        .dataStore(Components.persistentDataStore(Redis.dataStore()
          .uri(URI.create(REDIS))
          .prefix(REDIS_PREFIX))
          .cacheSeconds(30)
        )
        .build();
      System.out.println("URI parsed successfully!");

    LDClient client = new LDClient(SDK_KEY, config);

    if (client.isInitialized()) {
      showMessage("SDK successfully initialized!");
    } else {
      showMessage("SDK failed to initialize");
      System.exit(1);
    }
    
    // Set up the user properties. This user should appear on your LaunchDarkly users dashboard
    // soon after you run the demo.
    LDUser user = new LDUser.Builder("USER KEY VALUE")
                            .name("USER NAME")
                            .firstName("USER FIRSTNAME")
                            .lastName("USER LASTNAME")
                            .email("USER EMAIL ADDRESS")
                            .custom("Cell", "USER CELL NUMBER")
                            .custom("Company", "USER COMPANY")
                            .custom("Group", "USER GROUP")
                            .custom("Country", "USER COUNTRY")
                            .custom("State", "USER STATE")
                            .custom("City", "USER CITY")
                            .build();

    boolean flagValue = client.boolVariation(FEATURE_FLAG_KEY, user, false);

    showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValue + " for this user");

    // Here we ensure that the SDK shuts down cleanly and has a chance to deliver analytics
    // events to LaunchDarkly before the program exits. If analytics events are not delivered,
    // the user properties and flag usage statistics will not appear on your dashboard. In a
    // normal long-running application, the SDK would continue running and events would be
    // delivered automatically in the background.
    client.close();
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
  }
}
