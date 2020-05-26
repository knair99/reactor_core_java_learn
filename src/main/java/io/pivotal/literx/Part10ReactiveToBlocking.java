package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to turn Reactive API to blocking one.
 *
 * @author Sebastien Deleuze
 */
public class Part10ReactiveToBlocking {

//========================================================================================

  // TODO Return the user contained in that Mono
  User monoToValue(Mono<User> mono) {
    return mono.block(); //this will block until the user becomes available
  }

//========================================================================================

  // TODO Return the users contained in that Flux
  Iterable<User> fluxToValues(Flux<User> flux) {
    return flux.toIterable(); //this will also block
  }
}
