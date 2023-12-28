/*package com.lic.epgs.mst.exceptionhandling;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);

	}

	public ResourceNotFoundException(String message, Throwable throwable) {
		super(message, throwable);

	}
}*/
package com.lic.epgs.mst.exceptionhandling;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */

	private static final long serialVersionUID = 3955378912132768868L;

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public ResourceNotFoundException(String message) {
		super(message);

	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);

	}

}