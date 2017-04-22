package com.matthewtamlin.spyglass.library.core;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

/**
 * Exception to indicate that a method has been called on a illegal thread.
 */
public class IllegalThreadException extends RuntimeException {
	public IllegalThreadException() {
		super();
	}

	public IllegalThreadException(final String message) {
		super(message);
	}

	public IllegalThreadException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public IllegalThreadException(final Throwable cause) {
		super(cause);
	}

	@RequiresApi(24) // For caller
	@TargetApi(24) // For lint
	protected IllegalThreadException(final String message, final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}