package io.pivotal.literx;

import static org.assertj.core.api.Assertions.assertThat;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import java.time.Duration;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06RequestTest {

  Part06Request workshop = new Part06Request();
  ReactiveRepository<User> repository = new ReactiveUserRepository();

  // ========================================================================================

  @Test
  public void requestAll() {
    Flux<User> flux = repository.findAll();
    StepVerifier verifier = workshop.requestAllExpectFour(flux);
    verifier.verify();
  }

  // ========================================================================================

  @Test
  public void requestOneByOne() {
    Flux<User> flux = repository.findAll();
    StepVerifier verifier = workshop.requestOneExpectSkylerThenRequestOneExpectJesse(flux);
    verifier.verify();
  }

  @Test
  public void request4Elements() {
    assertThat(workshop.request4Integers()).isEqualTo(4);
  }


  @Test
  public void shouldBufferToAvoidOverflowException() {
    StepVerifier.withVirtualTime(() -> workshop.overflowExceptionBuffer())
        .thenAwait(Duration.ofSeconds(10))
        .expectNextCount(1000)
        .thenCancel()
        .verify();
  }

  @Test
  public void shouldDropToAvoidOverflowException() {
    StepVerifier.withVirtualTime(() -> workshop.overflowExceptionDrop())
        .thenAwait(Duration.ofSeconds(10))
        .expectNextCount(1000)
        .expectNextMatches(aLong -> aLong > 1000)
        .thenCancel()
        .verify();
  }

  @Test
  public void shouldKeepLatestToAvoidOverflowException() {
    StepVerifier.withVirtualTime(() -> workshop.overflowExceptionLatest())
        .thenAwait(Duration.ofSeconds(10))
        .expectNextCount(1000)
        .expectNextMatches(aLong -> aLong > 1000)
        .thenCancel()
        .verify();
  }

  @Test
  public void customPublisherShouldHandleBackpressure() {
    StepVerifier.create(workshop.customPublisher(), 0)
        .expectSubscription()
        .expectNoEvent(Duration.ofSeconds(1))
        .thenRequest(5)
        .expectNextCount(5)
        .thenRequest(5)
        .expectNextCount(5)
        .verifyComplete();
  }
}
