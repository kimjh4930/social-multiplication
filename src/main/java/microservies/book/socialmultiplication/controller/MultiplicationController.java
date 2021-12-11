package microservies.book.socialmultiplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservies.book.socialmultiplication.domain.Multiplication;
import microservies.book.socialmultiplication.service.MultiplicationService;

@RestController
@RequestMapping("/multiplications")
public class MultiplicationController {

	private final MultiplicationService multiplicationService;

	public MultiplicationController(
		MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}

	@GetMapping("/random")
	public Multiplication getRandomMultiplication() {
		return multiplicationService.createRandomMultiplication();
	}
}
