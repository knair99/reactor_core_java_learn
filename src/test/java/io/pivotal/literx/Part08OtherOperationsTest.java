package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

/**
 * Learn how to use various other operators.
 *
 * @author Sebastien Deleuze
 */
public class Part08OtherOperationsTest {

  static final User MARIE = new User("mschrader", "Marie", "Schrader");
  static final User MIKE = new User("mehrmantraut", "Mike", "Ehrmantraut");
  Part08OtherOperations workshop = new Part08OtherOperations();

  // ========================================================================================

  @Test
  public void zipFirstNameAndLastName() {
    Flux<String> usernameFlux =
        Flux.just(
            User.SKYLER.getUsername(),
            User.JESSE.getUsername(),
            User.WALTER.getUsername(),
            User.SAUL.getUsername());
    Flux<String> firstNameFlux =
        Flux.just(
            User.SKYLER.getFirstName(),
            User.JESSE.getFirstName(),
            User.WALTER.getFirstName(),
            User.SAUL.getFirstName());
    Flux<String> lastNameFlux =
        Flux.just(
            User.SKYLER.getLastName(),
            User.JESSE.getLastName(),
            User.WALTER.getLastName(),
            User.SAUL.getLastName());
    Flux<User> userFlux =
        workshop.userFluxFromStringFlux(usernameFlux, firstNameFlux, lastNameFlux);
    StepVerifier.create(userFlux)
        .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
        .verifyComplete();
  }

  // ========================================================================================

  @Test
  public void fastestMono() {
    ReactiveRepository<User> repository = new ReactiveUserRepository(MARIE);
    ReactiveRepository<User> repositoryWithDelay = new ReactiveUserRepository(250, MIKE);
    Mono<User> mono =
        workshop.useFastestMono(repository.findFirst(), repositoryWithDelay.findFirst());
    StepVerifier.create(mono).expectNext(MARIE).verifyComplete();

    repository = new ReactiveUserRepository(250, MARIE);
    repositoryWithDelay = new ReactiveUserRepository(MIKE);
    mono = workshop.useFastestMono(repository.findFirst(), repositoryWithDelay.findFirst());
    StepVerifier.create(mono).expectNext(MIKE).verifyComplete();
  }

  // ========================================================================================

  @Test
  public void fastestFlux() {
    ReactiveRepository<User> repository = new ReactiveUserRepository(MARIE, MIKE);
    ReactiveRepository<User> repositoryWithDelay = new ReactiveUserRepository(250);
    Flux<User> flux = workshop.useFastestFlux(repository.findAll(), repositoryWithDelay.findAll());
    StepVerifier.create(flux).expectNext(MARIE, MIKE).verifyComplete();

    repository = new ReactiveUserRepository(250, MARIE, MIKE);
    repositoryWithDelay = new ReactiveUserRepository();
    flux = workshop.useFastestFlux(repository.findAll(), repositoryWithDelay.findAll());
    StepVerifier.create(flux)
        .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
        .verifyComplete();
  }

  // ========================================================================================

  @Test
  public void complete() {
    ReactiveRepository<User> repository = new ReactiveUserRepository();
    PublisherProbe<User> probe = PublisherProbe.of(repository.findAll());
    Mono<Void> completion = workshop.fluxCompletion(probe.flux());
    StepVerifier.create(completion).verifyComplete();
    probe.assertWasRequested();
  }

  // ========================================================================================

  @Test
  public void nullHandling() {
    Mono<User> mono = workshop.nullAwareUserToMono(User.SKYLER);
    StepVerifier.create(mono).expectNext(User.SKYLER).verifyComplete();
    mono = workshop.nullAwareUserToMono(null);
    StepVerifier.create(mono).verifyComplete();
  }

  // ========================================================================================

  @Test
  public void emptyHandling() {
    Mono<User> mono = workshop.emptyToSkyler(Mono.just(User.WALTER));
    StepVerifier.create(mono).expectNext(User.WALTER).verifyComplete();
    mono = workshop.emptyToSkyler(Mono.empty());
    StepVerifier.create(mono).expectNext(User.SKYLER).verifyComplete();
  }
}
