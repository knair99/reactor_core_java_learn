package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveUserRepository;
import java.util.function.Function;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to reuse operators
 *
 * @author Julien Hoarau
 * @see Flux#transform(Function)
 * @see Flux#compose(Function)
 */
public class Part14CodeReuse {

  private ReactiveUserRepository reactiveUserRepository = new ReactiveUserRepository(100);

  private final MetricRegister metricRegister;

  public Part14CodeReuse(MetricRegister metricRegister) {
    this.metricRegister = metricRegister;
  }

  // TODO we want to record the `findFirst` duration and add it to MetricRegister
  public Mono<User> instrumentedFindFirst() {
    return reactiveUserRepository.findFirst();

  }

  // TODO extract previous logic in a separate function in order to be able
  // to instrument this stream as well
  public Mono<User> instrumentedFindUser(String username) {
    return reactiveUserRepository.findById(username);
  }

  /**
   * Poor man stopwatch.
   * Can be used to record execution time in milliseconds
   */
  private static class StopWatch {
    private long start = -1;

    public void start() {
      if (start == -1) {
        start = System.currentTimeMillis();
      }
    }

    public long stop() {
      return start != -1L ? System.currentTimeMillis() - start : -1L;
    }
  }

  interface MetricRegister {
    void timed(String metricName, long durationInMilliseconds);
  }
}
