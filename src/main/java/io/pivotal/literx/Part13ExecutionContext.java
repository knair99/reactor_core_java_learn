package io.pivotal.literx;

import java.util.ArrayList;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Learn about execution context.
 *
 * Exercise is in Part13ExecutionContextTest
 *
 * @author Julien Hoarau
 */
public class Part13ExecutionContext {
  private final Scheduler blue = Schedulers.newSingle("blue");
  private final Scheduler red = Schedulers.newSingle("red");
  private final Scheduler green = Schedulers.newSingle("green");
  private final Scheduler yellow = Schedulers.newSingle("yellow");

  // TODO Find on which thread is each operator executing
  Flux<Integer> findWhichExecutionContext() {
    return Flux.range(0, 10)            // thread: blue
        .doOnComplete(() -> System.out.println(Thread.currentThread().getName()))
        .map(integer -> integer * 2)                // thread: blue
        .doOnComplete(() -> System.out.println(Thread.currentThread().getName()))
        .subscribeOn(blue)                          // thread: blue
        .doOnComplete(() -> System.out.println(Thread.currentThread().getName()))
        .publishOn(green)                           // thread: green
        .doOnComplete(() -> System.out.println(Thread.currentThread().getName()))
        .map(integer -> integer * 4)                // thread: green
        .doOnComplete(() -> System.out.println(Thread.currentThread().getName()))
        .flatMap(this::zeroIfEven)                  // thread: YELLOW - because there was a subscribe on in there
        .doOnComplete(() -> System.out.println(Thread.currentThread().getName()))
        .subscribeOn(red)                           // thread: YELLOW - finishes on yellow because of above
        .doOnComplete(()-> System.out.println(Thread.currentThread().getName()));                          // thread: green
  }

  // This Flux emits the thread being used at each step
  // The expected result is ["blue-*", "red-*", "yellow-*", "green-*"]
  // TODO Make the test pass by sprinkling publishOn/subscribeOn and the blue/red/green/yellow schedulers.
  Flux<String> usePublishSubscribe() {
    return Mono.defer(() -> Mono.just(registerCurrentThread(new ArrayList<>())))
    .publishOn(red)
    .doOnNext(this::registerCurrentThread)
    .publishOn(yellow)
    .flatMap(strings -> {
      registerCurrentThread(strings);
      return Mono.just(strings)
          .doOnNext(this::registerCurrentThread)
          .subscribeOn(green);
    })
    .flatMapIterable(strings -> strings)
    .subscribeOn(blue);
  }

  private List<String> registerCurrentThread(List<String> strings) {
    strings.add(Thread.currentThread().getName());
    return strings;
  }

  private Mono<Integer> zeroIfEven(int number) {
    return Mono.just(number % 2 == 0 ? 0 : number)
        .subscribeOn(yellow);
  }
}
