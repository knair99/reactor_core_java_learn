package io.pivotal.literx;

import static org.assertj.core.api.Assertions.assertThat;

import io.pivotal.literx.Part14CodeReuse.MetricRegister;
import io.pivotal.literx.domain.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

public class Part14CodeReuseTest {

  private final MockMeterRegister metricRegister = new MockMeterRegister();
  Part14CodeReuse workshop = new Part14CodeReuse(metricRegister);

  @Before
  public void setUp() throws Exception {
    metricRegister.reset();
  }

  @Test
  public void testFindFirst() {
    StepVerifier.create(workshop.instrumentedFindFirst())
        .expectNextCount(1)
        .verifyComplete();

    assertThat(metricRegister.getTime("findFirst")).isGreaterThanOrEqualTo(100);
  }

  @Test
  public void testFind() {
    StepVerifier.create(workshop.instrumentedFindUser(User.SKYLER.getUsername())
        .repeat(2))
        .expectNextCount(2)
        .verifyComplete();

    assertThat(metricRegister.getTime("findUser")).isGreaterThanOrEqualTo(100);
    assertThat(metricRegister.getTime("findUser")).isLessThan(300);
  }

  private static class MockMeterRegister implements MetricRegister {

    private final Map<String, Long> timeMetrics = new HashMap<>();

    @Override
    public void timed(String metricName, long durationInMilliseconds) {
      timeMetrics.put(metricName, durationInMilliseconds);
    }

    public long getTime(String metricName) {
      return timeMetrics.getOrDefault(metricName, -1L);
    }

    public void reset() {
      timeMetrics.clear();
    }
  }
}