package io.pivotal.literx.repository;

import static io.pivotal.literx.domain.User.UNKNOWN_USER;

import io.pivotal.literx.domain.User;
import java.time.Duration;
import java.util.function.BiFunction;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveStarWarsUsersRepository implements ReactiveRepository<User> {

  private static final Duration DEFAULT_DELAY = Duration.ofMillis(500);

  public static final User OBIWAN = new User("obiwan", "Obi Wan", "Kenoby");
  public static final User ANAKIN = new User("askywalker", "Anakin", "Skywalker");
  public static final User LUKE = new User("lskywalker", "Luke", "Skywalker");
  public static final User JARJAR = new User("jbinks", "Jar Jar", "Binks");

  private final Flux<User> users = Flux.just(OBIWAN, ANAKIN, LUKE, JARJAR);

  @Override
  public Mono<Void> save(Publisher<User> publisher) {
    return Mono.error(new RuntimeException("Who do you think you are? George Lucas?"));
  }

  @Override
  public Mono<User> findFirst() {
    return users.take(0).single().delayElement(DEFAULT_DELAY);
  }

  @Override
  public Flux<User> findAll() {
    return users;
  }

  @Override
  public Mono<User> findById(String id) {
    return users.filter(user -> user.getUsername().equals(id))
        .single();
  }

  public Flux<User> loadUser(int page) {
    return Flux.empty();
  }

  /**
   * Transform a list of users into Star wars characters!
   * @param users
   * @return
   */
  public Flux<User> starWarsify(Flux<User> users) {
    return Flux.zip(users, findAll().<User>repeat(),
        (BiFunction<User, User, User>) this::starWarsify);
  }

  private User starWarsify(User user, User character) {
    if (user.getLastName() != null && character.getLastName() != null) {
      return new User(user.getUsername(), user.getFirstName(), mixLastname(user, character));
    }

    return UNKNOWN_USER;
  }

  private String mixLastname(User user, User character) {
    return user.getLastName().substring(0, 3) + character.getLastName().substring(0, 3);
  }
}
