package com.lic.epgs.mst.usermgmt.encryptdecrypt;

import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.lic.epgs.constant.Constant;


@Service
public class EncryptandDecryptAES {

    private static final String ALGO = "AES"; // Default uses ECB PKCS5Padding
    
    private static String secretKey = "UserMg_MPHPortal";
    

    public static String encrypt(String Data, String secret) throws Exception {
        Key key = generateKey(secret);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        return encryptedValue;
    }

    public static String decrypt(String strToDecrypt, String secret) {

        try {
            Key key = generateKey(secret);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private static Key generateKey(String secret) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
        Key key = new SecretKeySpec(decoded, ALGO);
        return key;
    }

    public static String decodeKey(String str) {
        byte[] decoded = Base64.getDecoder().decode(str.getBytes());
        return new String(decoded);
    }

    public static String encodeKey(String str) {
        byte[] encoded = Base64.getEncoder().encode(str.getBytes());
        return new String(encoded);
    }

    public static void main(String a[]) throws Exception {
        /*
         * Secret Key must be in the form of 16 byte like,
         *
         * private static final byte[] secretKey = new byte[] { �m�, �u�, �s�, �t�, �b�,
         * �e�, �1�, �6�, �b�, �y�, �t�,�e�, �s�, �k�, �e�, �y�};
         *
         * below is the direct 16byte string we can use
         */
        
//        System.out.println("EncodedBase64Key = " + encodedBase64Key); // This need to be share between client and server

        // To check actual key from encoded base 64 secretKey
        // String toDecodeBase64Key = decodeKey(encodedBase64Key);
        // System.out.println("toDecodeBase64Key = "+toDecodeBase64Key);
        
        // AES Encryption based on above secretKey
        
        String outputofAPI =
        		"{\r\n" + 
        		"    \"portalUserId\" : 9884,\r\n" + 
        		"    \"modifiedBy\" : \"sandeep\"\r\n" + 
        		"}";

        		

;
      /*  Map<String, Object> response = new HashMap<String, Object>();
        response.put(Constant.STATUS, 1);                 
		response.put(Constant.MESSAGE, Constant.SUCCESS);      
		
		Map<String, String> dataValue = new HashMap<String, String>();
		dataValue.put("portalUserId", "1");
		dataValue.put("username", "Vijay");
		response.put("Data", dataValue); */
		
		
        EncryptAESECBPKCS5Padding(outputofAPI);
     //   EncryptAESECBPKCS5Padding(response.toString());
        DecryptAESECBPKCS5Padding("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");
        
        
        String resposneDec = "PfeuRWNqsV+oFQadPkosc4/7fe8We2fr4lLI2Y/Qkzk7zAq2w70oIzOd360Z8KWo1fnVeTgb+FM8kFFFCSHYMo+BD27VfcGXz4uavZzjkQuPjvlWD0nStjyGBa6IueZpfO3/8cKbmqPqQXpzrY+OUaCNmo6P9ZKCs/QJ/1MiSD3RGETbN9I7DZzBtXbwFdgbwgonJXF6rKzoXKV+I5rB3zraMU38rVoZUBusXmy/IOrU9VL+rVqwJZ8JwGB2B++w";
        DecryptAESECBPKCS5PaddingForResponse(resposneDec);
    }
    
    
    
    public static JSONObject DecryptAESECBPKCS5Padding(String encStringValue) {
    	
    	String encodedBase64Key = encodeKey(secretKey);
    	
    	// AES Decryption based on above secretKey
        String decryptedString = encStringValue;
        JSONObject decrJSONObj = new JSONObject(EncryptandDecryptAES.decrypt(decryptedString, encodedBase64Key).toString());
        System.out.println("Decryption of str = " + decrJSONObj);
//        System.out.println("decrJSONObj.getStrin mphName of str = " + decrJSONObj.getString("mphName"));
        return decrJSONObj;
    }
    
    public static void DecryptAESECBPKCS5PaddingForResponse(String encStringValue) {
    	
    	String encodedBase64Key = encodeKey(secretKey);
    	
    	// AES Decryption based on above secretKey
        String decryptedString = encStringValue;
        String decrStr = EncryptandDecryptAES.decrypt(decryptedString, encodedBase64Key).toString();
        System.out.println("Decryption of response str = " + decrStr);
//        System.out.println("decrJSONObj.getStrin mphName of str = " + decrJSONObj.getString("mphName"));
//        return decrJSONObj;
    }
    
    
    public static String EncryptAESECBPKCS5Padding(String dcryptStringValue) throws Exception {
    	
    	String encodedBase64Key = encodeKey(secretKey);
    	
    	// AES Encryption based on above secretKey
    	String toEncrypt = dcryptStringValue;
        String encrStr = EncryptandDecryptAES.encrypt(toEncrypt, encodedBase64Key);
        System.out.println("Cipher Text: Encryption of str = " + encrStr);
        
        return encrStr;
    }
    
    
}