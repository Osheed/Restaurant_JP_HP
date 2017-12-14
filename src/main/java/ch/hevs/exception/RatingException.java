package ch.hevs.exception;

public class RatingException extends RuntimeException {

	public RatingException() {
		super();
	}

	public RatingException(String arg0) {
		super(arg0);
	}

	public RatingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RatingException(Throwable arg0) {
		super(arg0);
	}

}
