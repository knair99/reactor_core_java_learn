package io.pivotal.literx;

import java.time.Duration;
import java.util.function.Consumer;
import reactor.core.publisher.Flux;

/**
 * Learn how to create custom publisher
 *
 * @author Julien Hoarau
 * @see Flux#create(Consumer)
 * @see Flux#generate(Consumer)
 * @see Flux#push(Consumer)
 */
public class Part15CustomPublisher {

  /**
   * TODO Reimplement Flux.range()
   *
   * @see Flux#range(int, int)
   */
  public Flux<Integer> range(int start, int count) {
    return Flux.empty();
  }

  /**
   * TODO Reimplement Flux.interval(Duration.ofMillis(100))
   *
   * @see Flux#interval(Duration)
   */
  public Flux<Integer> interval() {
    return Flux.empty();
  }
}
