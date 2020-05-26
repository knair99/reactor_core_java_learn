package io.pivotal.literx;

import java.time.Duration;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

public class Part18HotColdTest {

  private Part18HotCold workshop = new Part18HotCold();

  @Test
  public void coldToHot() {

    final Flux<Long> tickProducer1 = workshop.getTicks();
    final Flux<Long> tickProducer2 = workshop.getTicks();

    StepVerifier.create(Flux.zip(tickProducer1, tickProducer2.delaySubscription(Duration.ofMillis(100))))
        .expectNext(Tuples.of(0L, 1L))
        .expectNext(Tuples.of(1L, 2L))
        .thenCancel()
        .verify();
  }


}