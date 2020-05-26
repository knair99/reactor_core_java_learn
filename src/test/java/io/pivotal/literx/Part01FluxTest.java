package io.pivotal.literx;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a
 *     href="http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux
 *     Javadoc</a>
 */
public class Part01FluxTest {

  Part01Flux workshop = new Part01Flux();

  // ========================================================================================

  @Test
  public void empty() {
    Flux<String> flux = workshop.emptyFlux();
    StepVerifier.create(flux).verifyComplete();
  }

  // ========================================================================================

  @Test
  public void fromValues() {
    Flux<String> flux = workshop.fooBarFluxFromValues();
    StepVerifier.create(flux).expectNext("foo", "bar").verifyComplete();
  }

  // ========================================================================================

  @Test
  public void fromList() {
    Flux<String> flux = workshop.fooBarFluxFromList();
    StepVerifier.create(flux).expectNext("foo", "bar").verifyComplete();
  }

  // ========================================================================================

  @Test
  public void error() {
    Flux<String> flux = workshop.errorFlux();
    StepVerifier.create(flux).verifyError(IllegalStateException.class);
  }

  // ========================================================================================

  @Test
  public void countEach() {
    Flux<Integer> flux = workshop.counter();
    StepVerifier.create(flux).expectNext(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).verifyComplete();
  }

	// ========================================================================================

	@Test
	public void countEachFromStream() {
		Flux<Integer> flux = workshop.counterFromStream();
		StepVerifier.create(flux).expectNext(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).verifyComplete();
	}
}
