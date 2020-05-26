package io.pivotal.literx;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.Test;
import reactor.test.StepVerifier;

public class Part15CustomPublisherTest {

  Part15CustomPublisher workshop = new Part15CustomPublisher();

  @Test
  public void testRange() {
    StepVerifier.create(workshop.range(0, 5))
        .expectNext(0)
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4)
        .verifyComplete();
  }

  @Test
  public void testInterval() {
    final Duration duration = StepVerifier.create(workshop.interval())
        .expectNext(0)
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4)
        .thenCancel()
        .verify();

    assertThat(duration.toMillis()).isGreaterThan(400);
  }


}