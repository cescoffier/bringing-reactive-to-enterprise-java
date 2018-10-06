package me.escoffier.reactive_summit.demo3;

import io.reactivex.Flowable;
import me.escoffier.reactive_summit.simulator.measures.BloodPressureGenerator;
import me.escoffier.reactive_summit.simulator.measures.BodyTemperatureGenerator;
import me.escoffier.reactive_summit.simulator.measures.HeartBeatGenerator;
import me.escoffier.reactive_summit.simulator.measures.Patient;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class Neo {

  @Produces
  Patient getPatient() {
    return new Patient(
      "neo",
      new BloodPressureGenerator("pressure", 70, 170, 3, 48, 78, 3),
      new HeartBeatGenerator("heartbeat", 120, 150, 5),
      new BodyTemperatureGenerator("temperature", 38.0, 39.9, 0.5)
    );
  }


  public Publisher<String> state() {
    return Flowable.fromArray("sleeping", "awake")
      .zipWith(Flowable.interval(10, TimeUnit.SECONDS), (a, b) -> a)
      .repeat();
  }



}