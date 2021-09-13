package xyz.blaklinten.joggl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** Unit test for simple App. */
@SpringBootTest
public class AppTest {
  @Autowired private WebController controller;
  /** Rigorous Test :-) */
  @Test
  public void sanityCheck() throws Exception {
    assertThat(controller).isNotNull();
  }
}
