package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import java.util.function.Consumer;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

/**
 * Learn how to debug Reactor code.
 *
 * @author Julien Hoarau
 * @see Hooks#onOperatorDebug()
 * @see Flux#checkpoint()
 * @see Flux#doOnNext(Consumer)
 */
public class Part12DebuggingTest {

  Part12Debugging workshop = new Part12Debugging();


  @Test
  public void readingTeaLeaves() {
    StepVerifier.create(workshop.starWarsifyUsers())
        .expectNextCount(6)
        .verifyComplete();
  }

  // ========================================================================================

  @Test
  public void experimentWithLog() {
    Flux<User> flux = workshop.fluxWithLog();
    StepVerifier.create(flux, 0)
        .thenRequest(1)
        .expectNextMatches(u -> true)
        .thenRequest(1)
        .expectNextMatches(u -> true)
        .thenRequest(2)
        .expectNextMatches(u -> true)
        .expectNextMatches(u -> true)
        .verifyComplete();
  }

  // ========================================================================================

  @Test
  public void experimentWithDoOn() {
    Flux<User> flux = workshop.fluxWithDoOnPrintln();
    StepVerifier.create(flux).expectNextCount(4).verifyComplete();
  }

}
