package com.lic.epgs.mst.utils;

import java.security.SecureRandom;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class CodeUtil {

	public static final String VALUE = "000000000";

	public String generateChallanNumber() {
		return "C" + new DecimalFormat(VALUE).format(new SecureRandom().nextInt(999999999));

	}

	public String generateTransactionNumber() {
		return "T" + new DecimalFormat(VALUE).format(new SecureRandom().nextInt(999999999));

	}

	public String generateVendorCode() {
		return "VND" + new DecimalFormat(VALUE).format(new SecureRandom().nextInt(999999999));

	}

}
