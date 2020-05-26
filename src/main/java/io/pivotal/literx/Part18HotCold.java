package io.pivotal.literx;

import java.time.Duration;
import reactor.core.publisher.Flux;

/**
 * Learn how to transform a cold stream to a hot one
 *
 * @author Julien Hoarau
 * @see Flux#share()
 * @see Flux#publish()
 */
public class Part18HotCold {

  private Flux<Long> tickProducer;

  // TODO We want to share tickProducer between multiple subscribers and
  // transform the stream to a hot stream.
  public Part18HotCold() {
    this.tickProducer = Flux.interval(Duration.ofMillis(100));
  }

  public Flux<Long> getTicks() {
    return tickProducer;
  }
}
