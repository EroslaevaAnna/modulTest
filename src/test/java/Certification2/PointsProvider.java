package Certification2;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class PointsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(10, 10),
                Arguments.of(100, 100),
                Arguments.of(-50, -50),
                Arguments.of(0, 0),
                Arguments.of(100, 100),
                Arguments.of(-5000000, -5000000)
        );
    }
}
