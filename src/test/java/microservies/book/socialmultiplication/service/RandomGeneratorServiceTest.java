package microservies.book.socialmultiplication.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RandomGeneratorServiceTest {

	private RandomGeneratorService randomGeneratorService;

	@BeforeEach
	public void setUp() {
		randomGeneratorService = new RandomGeneratorServiceImpl();
	}

	@Test
	void generateRandomFactorIsBetweenExpectedLimits() {
		//given

		//when
		List<Integer> randomFactors = IntStream.range(0, 1000)
			.map(i -> randomGeneratorService.generateRandomFactor())
			.boxed()
			.collect(Collectors.toList());

		//then
		assertThat(randomFactors).isSubsetOf(
			IntStream.range(11, 100)
				.boxed()
				.collect(Collectors.toList())
		);
	}
}
