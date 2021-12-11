package microservies.book.socialmultiplication.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservies.book.socialmultiplication.domain.Multiplication;
import microservies.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservies.book.socialmultiplication.domain.User;
import microservies.book.socialmultiplication.service.MultiplicationService;

@WebMvcTest
class MultiplicationResultAttemptControllerTest {

	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void postResultReturnCorrect(boolean correct) throws Exception {
		//given
		given(multiplicationService.checkAttempt(
			any(MultiplicationResultAttempt.class))
		).willReturn(correct);

		User user = new User("john");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);

		//when
		MockHttpServletResponse response = mockMvc.perform(post("/results")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(attempt))
			)
			.andReturn()
			.getResponse();

		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
			objectMapper.writeValueAsString(
				new MultiplicationResultAttempt(
					attempt.getUser(),
					attempt.getMultiplication(),
					attempt.getResultAttempt(),
					correct
				)
			)
		);
	}

	@Test
	void checkCorrectAttemptTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");

		MultiplicationResultAttempt attempt =
			new MultiplicationResultAttempt(user, multiplication, 3000, false);

		//when
		boolean attemptResult = multiplicationService.checkAttempt(attempt);

		//then
		assertThat(attemptResult).isTrue();
	}

	@Test
	public void getUserStats() throws Exception {
		//given
		User user = new User("john_doe");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);

		given(multiplicationService.getStatsForUser("john_doe"))
			.willReturn(recentAttempts);

		//when
		MockHttpServletResponse response = mockMvc.perform(get("/results")
				.param("alias", "john_doe"))
			.andReturn()
			.getResponse();

		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString())
			.isEqualTo(objectMapper.writeValueAsString(recentAttempts));

	}
}