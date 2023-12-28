package com.lic.epgs.constant;

import java.util.Calendar;
import java.util.Date;

public class Constant {

	public static final String TRANSACTION_SUCCESS = "Success";
	public static final String TRANSACTION_ERROR = "Error";
	public static final String DUPLICATE_ERROR = "Duplication Error Occurred";
	public static final String DUPLICATE_CODE = "code is already present";
	public static final String DUPLICATE_NOT_FOUND = "No duplicate  detail found";
	public static final String NO_DATA_FOUND = "No data found";
	public static final String ERROR = "Error";
	public static final String NOT_FOUND = "sr number details are not found";
	public static final String FAILED = "Failed";
	public static final String SUCCESS = "Success";
	public static final String MESSAGE = "Message";
	public static final String STATUS = "status";
	public static final String DATA = "Data";
	public static final String DATA_RETRIEVAL_ERROR = "Errors occurs while in retrieving data due to ";
	public static final String SOMETHING_WENT_WRONG = "Please Try Again, Something Went Wrong";
	public static final String DUPLICATE_ENTRY = "Duplicate Entry Found";
	public static final String INVALID_PAYLOAD = "Constraint Violated in Payload";
	public static final Object ALLOWED = null;
	public static final Object USERNAME_NOT_FOUND = null;
	public static final Object SRNUMBER_FOUND = "User is already active in your unit";
    public static final Object SRNUMBER_NOT_FOUND = "User is Not Active in this unit";
    public static final Object UNIT_FOUND ="cannot be added as he belong s to other unit";
    public static final String INVALIDCAPTCHA = "Invalid Captcha";
    public static final Object DEACTIVE_USER_FOUND = "can be added into your unit and set all the roles releated to concern unit by epgs admin";
    
    public static final String RHSSO_REALM_ADMIN_ID = "b8ca0f3b-d1b9-48aa-946a-37a402972592";
	public static final String RHSSO_REALM_ADMIN_NAME = "realm-admin";
	public static final String RHSSO_REALM_CLIENT = "838a84e4-f14f-41e6-834d-8df2be65abb1";
	public static final String RHSSO_PORTAL_ADMIN_ID = "34906b31-71a9-434f-b703-5559ee541f35";
	public static final String RHSSO_PORTAL_ADMIN_NAME = "Portal_Admin";
	public static final String RHSSO_CLAIM_PORTAL_ID = null;
	public static final String RHSSO_CLAIM_PORTAL_NAME = null;
	public static final String RHSSO_USRMGMT_CLIENT = "01176b2b-85d6-4be9-b669-88ece139f0da";
	public static final String RHSSO_REALM = "usermgmt_claim_portal";
	private Constant() {
	}

	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getTomorrowDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
}
