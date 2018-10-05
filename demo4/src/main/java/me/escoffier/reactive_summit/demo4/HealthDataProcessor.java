package me.escoffier.reactive_summit.demo4;

import io.smallrye.reactive.messaging.annotations.Multicast;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.PublisherBuilder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class HealthDataProcessor {

  private static final Logger LOGGER = LogManager.getLogger(HealthDataProcessor.class);

  @Inject
  private Vertx vertx;
  private WebClient client;

  @PostConstruct
  public void init() {
    client = WebClient.create(vertx, new WebClientOptions().setDefaultHost("localhost").setDefaultPort(9000));
  }

  @Incoming("health")
  @Outgoing("heartbeat")
  @Multicast
  public PublisherBuilder<JsonObject> process(PublisherBuilder<byte[]> input) {
    return input
      .map(bytes -> new JsonObject(Buffer.buffer(bytes)))
      .flatMapCompletionStage(json -> invokeStoreService(json).thenApply(x -> {
        LOGGER.info("The snapshot has been sent to the store service");
        return json;
      }))
      .map(json -> json.getJsonObject("heartbeat"));
  }

  private CompletionStage<Void> invokeStoreService(JsonObject data) {
    CompletableFuture<Void> future = new CompletableFuture<>();
    client.post("/store").rxSendJsonObject(data)
      .ignoreElement()
      .subscribe(
        () -> future.complete(null),
        future::completeExceptionally
      );
    return future;
  }

}
