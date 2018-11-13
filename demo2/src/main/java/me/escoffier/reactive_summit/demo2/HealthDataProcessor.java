package me.escoffier.reactive_summit.demo2;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HealthDataProcessor {

  private static final Logger LOGGER = LogManager.getLogger(HealthDataProcessor.class);


  @Incoming("health")
  @Outgoing("heartbeat")
  @Broadcast
  public JsonObject filtered(JsonObject input) {
    LOGGER.info("Received {}", input.encode());
    return input.getJsonObject("heartbeat");
  }

}
