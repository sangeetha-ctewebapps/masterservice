package com.lic.epgs.mst.exceptionhandling;

public class AnnuityPurchasedByServiceException extends Exception {
	
		private static final long serialVersionUID = -471180507998010368L;

		public AnnuityPurchasedByServiceException() {
			super();
		}

		public AnnuityPurchasedByServiceException(final String message) {
			super(message);
		}
	}

