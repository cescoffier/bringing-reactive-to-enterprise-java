package me.escoffier.reactive_summit.demo1;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class Neo {

  public Publisher<String> state() {
    return Flowable.fromArray("sleeping", "awake")
      .zipWith(Flowable.interval(3, TimeUnit.SECONDS), (a, b) -> a)
      .repeat();
  }

}
