package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import org.junit.Test;
import reactor.test.StepVerifier;

public class Part17ContextTest {

  Part17Context workshop = new Part17Context();

  @Test
  public void testContext() {
    StepVerifier.create(workshop.addContext())
        .expectNext(User.SKYLER)
        .expectAccessibleContext()
        .hasKey("cloudId")
        .then()
        .verifyComplete();
  }


}