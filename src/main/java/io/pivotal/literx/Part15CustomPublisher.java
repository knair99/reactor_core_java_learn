package io.pivotal.literx;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import reactor.core.publisher.Flux;

/**
 * Learn how to create custom publisher
 *
 * @author Julien Hoarau
 * @see Flux#create(Consumer)
 * @see Flux#generate(Consumer)
 * @see Flux#push(Consumer)
 */
public class Part15CustomPublisher {

  /**
   * TODO Reimplement Flux.range()
   *
   * @see Flux#range(int, int)
   */
  public Flux<Integer> range(int start, int count) {
    return Flux.generate(
            () -> start, (state, sink) -> { //state is the same type as Flux<T> - Integer here
              sink.next(state);
              if (state == start + count - 1) sink.complete();
              return state + 1;
            });
  }

  /**
   * TODO Reimplement Flux.interval(Duration.ofMillis(100))
   *
   * @see Flux#interval(Duration)
   */
  public Flux<Integer> interval() {
    return Flux.create( sink -> {
        AtomicInteger count = new AtomicInteger();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sink.next(count.getAndIncrement());
            }
        }, 0, 100);
        });
    };
}
