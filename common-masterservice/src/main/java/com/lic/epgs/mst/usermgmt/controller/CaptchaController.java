package com.lic.epgs.mst.usermgmt.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptionModel;
import com.lic.epgs.mst.usermgmt.entity.CommonConfig;
import com.lic.epgs.mst.usermgmt.modal.TokenModel;
import com.lic.epgs.mst.usermgmt.repository.CommonConfigRepository;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.mst.usermgmt.service.RedhatUserGenerationService;
import com.lic.epgs.rhssomodel.AccessTokenResponse;
import com.lic.epgs.rhssomodel.RequestTokenModel;
import com.lic.epgs.rhssomodel.TokenStatus;
import com.lic.epgs.rhssomodel.UserCaptchaModel;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@RequestMapping("/umgmt")
public class CaptchaController {

	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	
	 @Autowired
	private RedhatUserGenerationService redhatUserGenerationService;
	 
	 @Autowired
	 private EncryptandDecryptAES encryptandDecryptAES;
	 
	
		
	 @Autowired
	 private PortalMasterRepository portalMasterRepository;
	 
	 @Autowired
	 private CommonConfigRepository commonConfigRepository;
	
	@GetMapping("/captcha")
	public Map<String, Object> getCaptchaValue()  {
	
		Map<String, Object> response1 = new HashMap<>();
		int iTotalChars = 6;
		int iHeight = 40;
		int iWidth = 150;
		Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
		Random randChars = new Random();
		long value=randChars.nextLong();
		String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, iTotalChars);
		BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
		int iCircle = 15;
		for (int i = 0; i < iCircle; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
		}
		g2dImage.setFont(fntStyle1);
		for (int i = 0; i < iTotalChars; i++) {
			g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
			if (i % 2 == 0) {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
			} else {
				g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
			}
		}
		
		g2dImage.dispose();
		
		response1.put(Constant.STATUS, 200);                 
		response1.put(Constant.MESSAGE, Constant.SUCCESS);                
		response1.put("Data",sImageCode ); 

		
		return response1;
	}
	
	@PostMapping("/validateCaptcha")
	public ResponseEntity< Object> validateCaptcha(@RequestBody UserCaptchaModel userDatail) {
		
		Map<String, Object> response1 = new HashMap<>();
		response1.put(Constant.STATUS, 401);
		response1.put(Constant.MESSAGE, Constant.INVALIDCAPTCHA);
		response1.put("Data", userDatail);
		String captcha = userDatail.getCaptcha();
		String verifyCaptcha = userDatail.getVerifyCaptcha();
		String username = userDatail.getUsername();
		List<String> testUsersList = new ArrayList<String>();
		CommonConfig commonConfig = commonConfigRepository.getDataFromCommonConfig(username);
		List<CommonConfig> commonConfigs = commonConfigRepository.getCommonConfigDetailsBasedOnModule("EPGSCAPTCHA");
		testUsersList = commonConfigs.stream().map(CommonConfig::getKey2).collect(Collectors.toList());
		/*if(username != null && username.equalsIgnoreCase("epgstools")) {
			if(verifyCaptcha != null && verifyCaptcha.equals("a1b2c3")) {
			response1.put(Constant.STATUS, 200);                 
			response1.put(Constant.MESSAGE, Constant.SUCCESS);                
			response1.put("Data",userDatail ); 
			}
		}else*/ if(username != null && testUsersList.contains(username)) {
			if(verifyCaptcha != null && verifyCaptcha.equals(commonConfig.getValue())) {
				response1.put(Constant.STATUS, 200);                 
				response1.put(Constant.MESSAGE, Constant.SUCCESS);                
				response1.put("Data",userDatail ); 
			}
		}
		else {
			if (captcha.equals(verifyCaptcha)) {
				
				response1.put(Constant.STATUS, 200);                 
				response1.put(Constant.MESSAGE, Constant.SUCCESS);                
				response1.put("Data",userDatail ); 
				
			} else {
				
				response1.put(Constant.STATUS, 401);
				response1.put(Constant.MESSAGE, Constant.INVALIDCAPTCHA);
				response1.put("Data", userDatail);
			}
		}

		return new ResponseEntity<>(response1, HttpStatus.OK);
	}
	
	@PostMapping(value = "/generateTokenForUser")
	public ResponseEntity<Object> generateTokenForUser(@RequestBody TokenModel TokenModelRequest)  {
		ResponseEntity<Object> response = redhatUserGenerationService.genetateAccessTokenForUser(TokenModelRequest);

		return ResponseEntity.accepted().body(response);
	}
	
	@PostMapping(value = "/refreshTokenWithAccessToken")
	public ResponseEntity<Object> getRefreshTokenWithAccessToken(@RequestBody RequestTokenModel refreshtokenmodel) {
		logger.debug("SSOController : getRefreshTokenWithAccessToken : initiated");
		AccessTokenResponse response = redhatUserGenerationService.getRefreshTokenWithAccessToken(refreshtokenmodel);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	

	@PostMapping(value = "/checkAccessTokenExpired")
    public ResponseEntity<Object> checkAccessToken(@RequestBody EncryptionModel encryptionModel) throws Exception {
        logger.debug("SSOController : checkAccessToken : initiated");
        Map<String, Object> response1 = new HashMap<>();
     // Decryption technique
     		JSONObject plainJSONObject = encryptandDecryptAES
     				.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
     		RequestTokenModel refreshtokenmodel = new RequestTokenModel();
     		refreshtokenmodel.setAccessToken(plainJSONObject.getString("accessToken"));
     		refreshtokenmodel.setClient_id(plainJSONObject.getString("clientId"));
     		refreshtokenmodel.setClient_secret(plainJSONObject.getString("clientSecret"));
     		refreshtokenmodel.setRealms(plainJSONObject.getString("realms"));
        TokenStatus response = redhatUserGenerationService.checkAccessToken(refreshtokenmodel);
        ObjectMapper obj = new ObjectMapper();
        String jsonStr = obj.writeValueAsString(response);
       String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
       response1.put(Constant.STATUS, 200);
		response1.put(Constant.MESSAGE, Constant.SUCCESS);
		response1.put("body", encaccessResponse);
        return new ResponseEntity<>(response1, HttpStatus.OK);

    }
	
	@GetMapping("/fetchCaptcha/{module}")
	public ResponseEntity< Object> getCaptcha(@PathVariable String module) {
		
		Map<String, Object> response1 = new HashMap<>();
		List<CommonConfig> commonConfigsList = commonConfigRepository.getCommonConfigDetailsBasedOnModule(module);
		if(commonConfigsList.size() > 0)
		{
			response1.put(Constant.STATUS, 200);                 
			response1.put(Constant.MESSAGE, Constant.SUCCESS);                
			response1.put("Data",commonConfigsList);
		}
		else
		{
			response1.put(Constant.STATUS, 401);
			response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		}
		return new ResponseEntity<>(response1, HttpStatus.OK);
	}
	
	
}
