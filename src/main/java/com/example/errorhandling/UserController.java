package com.example.errorhandling;

import java.io.EOFException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * https://github.com/spring-projects/spring-framework/issues/15819
 * 
 */
@Controller
public class UserController {

	@GetMapping("/user/{name}")
	@ResponseBody
	public User fetchUser(@PathVariable String name) throws Exception {
		if ("database".equals(name)) {
			// simply throwing an exception that will be handled by Spring Boot error controller
			// -> spring boot error response
			// recorded metric with DatabaseErrorException
			throw new DatabaseErrorException();
		}
		else if ("unknown".equals(name)) {
			// throwing an exception that's annotated with @ResponseStatus
			// -> spring boot error response with custom status
			// recorded metric with UnknownUserException
			throw new UnknownUserException(name);
		}
		else if ("unauthorized".equals(name)) {
			// using a @ResponseStatus annotated exception handler
			// -> blank page with custom status
			// recorded metric with UnauthorizedException
			throw new UnauthorizedException();
		}
		else if ("reason".equals(name)) {
			// using a @ResponseStatus+reason annotated exception handler
			// -> spring boot error response with custom status
			// recorded metric with ReasonException
			throw new ReasonException();
		}
		else if ("wrapping".equals(name)) {
			// using an exception handler that wraps and rethrows the exception
			// -> spring boot error response with original (not wrapped) exception
			// recorded metric with EOFException
			throw new EOFException();
		}
		return new User(name);
	}


	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	void handleUnauthorized(UnauthorizedException exc) {

	}

	@ExceptionHandler(ReasonException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "custom reason")
	void handleReason(ReasonException exc) {

	}

	@ExceptionHandler(EOFException.class)
	void handleEof(EOFException exc) {
		throw new WrappingException(exc);
	}

	@GetMapping("/view")
	public String fetchUserView() {
		// using an exception handler that renders an error view
		// -> custom error view
		// no recorded metric
		throw new ViewException();
	}

	@ExceptionHandler(ViewException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	String handleView(ViewException exc) {
		return "customError";
	}
}
