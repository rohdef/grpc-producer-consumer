package dk.rohdef.danske_bank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("A message service")
class MessagesServiceTest {
    @Nested
    @DisplayName("sub component A")
    public final class LogicA {
        @Test
        @DisplayName("should have sub logic 1")
        public void testFoo() {
            assertThat("a").isEqualTo("a");
        }

        @Test
        @DisplayName("should have sub logic 2")
        public void testFoo2() {
            assertThat(3).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("sub component B")
    public final class LogicB                {
        @Test
        @DisplayName("should have sub logic 1")
        public void testFoo() {
            assertThat("a").isEqualTo("a");
        }

        @Test
        @DisplayName("should have sub logic 2")
        public void testFoo2() {
            assertThat(3).isEqualTo(3);
        }
    }
}