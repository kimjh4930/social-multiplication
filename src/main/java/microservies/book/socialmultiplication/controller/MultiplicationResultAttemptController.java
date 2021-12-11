package microservies.book.socialmultiplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import microservies.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservies.book.socialmultiplication.service.MultiplicationService;

@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {
	private final MultiplicationService multiplicationService;

	public MultiplicationResultAttemptController(
		MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}

	@PostMapping
	public ResponseEntity<MultiplicationResultAttempt> postResult (
		@RequestBody MultiplicationResultAttempt multiplicationResultAttempt){

		boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);

		MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(
			multiplicationResultAttempt.getUser(),
			multiplicationResultAttempt.getMultiplication(),
			multiplicationResultAttempt.getResultAttempt(),
			isCorrect
		);

		return ResponseEntity.ok(attemptCopy);
	}

	@GetMapping
	public ResponseEntity<List<MultiplicationResultAttempt>> getStatics(@RequestParam("alias") String alias){
		return ResponseEntity
			.ok(multiplicationService.getStatsForUser(alias));
	}

	@RequiredArgsConstructor
	@NoArgsConstructor(force = true)
	@Getter
	static final class ResultResponse {
		private final boolean correct;
	}
}
