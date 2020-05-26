package io.pivotal.literx;

import org.junit.Test;
import reactor.test.StepVerifier;

/**
 * Learn about publishOn/subscribeOn.
 *
 * @author Julien Hoarau
 */
public class Part13ExecutionContextTest {
  private Part13ExecutionContext workshop = new Part13ExecutionContext();

  @Test
  public void findExecutionContextForEachOperator() {
    StepVerifier.create(workshop.findWhichExecutionContext())
        .expectNextCount(10)
        .verifyComplete();
  }

  @Test
  public void shouldEmitThreadNames() {
    StepVerifier.create(workshop.usePublishSubscribe())
        .expectNextMatches(s -> s.startsWith("blue-"))
        .expectNextMatches(s -> s.startsWith("red-"))
        .expectNextMatches(s -> s.startsWith("yellow-"))
        .expectNextMatches(s -> s.startsWith("green-"))
        .verifyComplete();
  }
}
