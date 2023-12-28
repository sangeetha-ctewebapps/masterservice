package com.lic.epgs.mst.usermgmt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptionModel;
import com.lic.epgs.mst.usermgmt.modal.TokenModel;
import com.lic.epgs.mst.usermgmt.modal.TokenUserModel;
import com.lic.epgs.mst.usermgmt.service.RedhatUserGenerationService;
import com.lic.epgs.rhssomodel.AccessTokenResponse;
import com.lic.epgs.rhssomodel.AddUserListModel;
import com.lic.epgs.rhssomodel.AddUserModel;
import com.lic.epgs.rhssomodel.Credential;
import com.lic.epgs.rhssomodel.RequestTokenModel;
import com.lic.epgs.rhssomodel.ResetPasswordRequestModel;
import com.lic.epgs.rhssomodel.ResponseModel;
import com.lic.epgs.rhssomodel.RoleResponseModel;
import com.lic.epgs.rhssomodel.SessionModel;
import com.lic.epgs.rhssomodel.TokenStatus;
import com.lic.epgs.rhssomodel.UserDetailsRequestModel;
import com.lic.epgs.rhssomodel.UserDetailsResponse;
import com.lic.epgs.rhssomodel.UserResponseModel;
import com.lic.epgs.rhssomodel.ValidateEmailMobileRequestModel;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/sso")
public class RedhatUserGenerationController {
	private static final Logger logger = LoggerFactory.getLogger(RedhatUserGenerationController.class);

	@Autowired
	RestTemplate rest;

	@Autowired
	private RedhatUserGenerationService redhatUserGenerationService;

	@PostMapping(value = "/checkActiveSessionsForUser")
	public ResponseEntity<Object> checkActiveSessionsForUser(@RequestParam String userName) throws Exception {
		ResponseEntity<Object> response = redhatUserGenerationService.checkActiveSessionsForUser(userName);

		return ResponseEntity.accepted().body(response);
	}
	
	@PostMapping(value = "/clearActiveSessionsForUser")
	public ResponseEntity<Object> clearActiveSessionsForUser(@RequestParam String userName) throws Exception {
		ResponseEntity<Object> response = redhatUserGenerationService.clearActiveSessionsForUser(userName);

		return ResponseEntity.accepted().body(response);
	}
	
	@PostMapping(value = "/loginAuthenticateRhsso")
	public ResponseEntity<Object> loginAuthenticateRhsso(@RequestBody TokenModel TokenModelRequest) throws Exception {
		ResponseEntity<Object> response = redhatUserGenerationService.loginAuthenticateRhsso(TokenModelRequest);

		return ResponseEntity.accepted().body(response);
	}

	@PostMapping(value = "/generateTokenForUser")
	public ResponseEntity<Object> generateTokenForUser(@RequestBody TokenModel TokenModelRequest) throws Exception {
		ResponseEntity<Object> response = redhatUserGenerationService.genetateAccessTokenForUser(TokenModelRequest);

		return ResponseEntity.accepted().body(response);
	}

	@PostMapping(value = "/getEmployeeDetailFromSsO")

	public ResponseEntity<Map<String, Object>> getEmployeeDetailFromSsO(
			@RequestBody TokenUserModel TokenUserModelRequest) throws ResourceNotFoundException {
		Map<String, Object> response = new HashMap<>();
		response = (Map<String, Object>) redhatUserGenerationService.getEmployeeDetailFromSsO(TokenUserModelRequest);

		return ResponseEntity.accepted().body(response);
	}

	@GetMapping(value = "/auth/getAllRolesByUsername/{username}")
	public ResponseEntity<?> getRoleByUsername(@RequestHeader("Authorization") String token,
			@PathVariable String username, @RequestParam String realm, @RequestParam String clientid) {
		logger.info("SSOController : getRoleByUsername : initiated");
		RoleResponseModel rrm = redhatUserGenerationService.getAllRolesByUsername(token, username, realm, clientid);

		return new ResponseEntity<Object>(rrm, HttpStatus.OK);

	}
	
	@PostMapping(value = "/auth/getAllSessionsLogout")
	public ResponseEntity<Object> getAllSessionsLogout(@RequestHeader("Authorization") String token,
			@RequestParam String realm,@RequestParam String userId,@RequestParam String userName) throws Exception {
		logger.debug("SSOController : getAllSessionsLogout : initiated");

		// Decryption technique
		
		ResponseModel rm = redhatUserGenerationService.getlogoutAllSessionsForUser(token, realm, userId,userName);

		return new ResponseEntity<>(rm, HttpStatus.OK);

	}

	@PostMapping(value = "/refreshTokenWithAccessToken")
	public ResponseEntity<?> getRefreshTokenWithAccessToken(@RequestBody RequestTokenModel refreshtokenmodel) {
		logger.info("SSOController : getRefreshTokenWithAccessToken : initiated");
		AccessTokenResponse response = redhatUserGenerationService.getRefreshTokenWithAccessToken(refreshtokenmodel);
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}
	
	@GetMapping(value = "/auth/searchUserByUsername/{userName}")
	public ResponseEntity<?> searchUserByUsername(@RequestHeader("Authorization") String token,
			@RequestParam String realm,@PathVariable String userName) throws Exception {
		logger.info("SSOController : getRoleByUsername : initiated");
		UserResponseModel rrm = redhatUserGenerationService.searchUserByUsername(token, realm, userName);

		return new ResponseEntity<Object>(rrm, HttpStatus.OK);

	}
	
	@GetMapping(value = "/auth/searchUserByUsernameForSuperAdmin/{userName}")
	public ResponseEntity<?> searchUserByUsernameForSuperAdmin(@RequestHeader("Authorization") String token,
			@RequestParam String realm,@PathVariable String userName) throws Exception {
		logger.info("SSOController : getRoleByUsername : initiated");
		ResponseEntity<String> rrm = redhatUserGenerationService.searchUserByUsernameForSuperAdmin(token, realm, userName);

		return new ResponseEntity<Object>(rrm, HttpStatus.OK);

	}
	
	

	@PostMapping(value = "/checkAccessTokenExpired")
	public ResponseEntity<?> checkAccessToken(@RequestBody RequestTokenModel refreshtokenmodel) {
		logger.info("SSOController : checkAccessToken : initiated");
		TokenStatus response = redhatUserGenerationService.checkAccessToken(refreshtokenmodel);
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}
	
	@PostMapping(value = "/getDetailsFromConcureciaApi")
	public ResponseEntity<?> getDetailsFromConcureciaApi(@RequestBody UserDetailsRequestModel userDetailsRequestModel) {
		logger.info("SSOController : getDetailsFromConcureciaApi : initiated");
	//	Map<String, Object> response = new HashMap<>();
		 ResponseEntity<Object> response =  redhatUserGenerationService.getDetailsFromConcureciaApi(userDetailsRequestModel);

		return ResponseEntity.accepted().body(response);

	}

	@PostMapping(value = "/getAllSessionDetailsFromRhsso")
	public ResponseEntity<?> getAllSessionDetailsFromRhsso(@RequestHeader("Authorization") String token,@RequestBody SessionModel sessionModelRequest) {
		logger.info("SSOController : getAllSessionDetailsFromRhsso : initiated");
	//	Map<String, Object> response = new HashMap<>();
		 ResponseEntity<Object> response =  redhatUserGenerationService.getAllSessionDetailsFromRhsso(sessionModelRequest);

		return ResponseEntity.accepted().body(response);

	}
	@PostMapping(value = "/auth/validateEmailMobile/{username}")
	public ResponseEntity<?> validateEmailMobile(@RequestHeader("Authorization") String token,
			@PathVariable String username, @RequestBody ValidateEmailMobileRequestModel validateRequestModel) {
		logger.info("SSOController : validateEmailMobile : initiated");
		ResponseModel rm = redhatUserGenerationService.validateEmailMobile(token, username, validateRequestModel);

		return new ResponseEntity<Object>(rm, HttpStatus.OK);
	}

	@PostMapping(value = "/auth/resetpassword/{username}")
	public ResponseEntity<?> resetPassword(@RequestHeader("Authorization") String token, @PathVariable String username,
			@RequestBody ResetPasswordRequestModel resetPasswordRequestModel) {
		logger.info("SSOController : resetPassword : initiated");
		ResponseModel rm = redhatUserGenerationService.resetPassword(token, username, resetPasswordRequestModel);
		return new ResponseEntity<Object>(rm, HttpStatus.OK);
	}

	/*
	 * @PostMapping(value = "/getAllRolesOfTokenUser") public
	 * ResponseEntity<Map<String, Object>> getAllRolesOfTokenUser(
	 * 
	 * @RequestBody TokenUserModel TokenUserModelRequest) throws
	 * ResourceNotFoundException { Map<String, Object> response = new HashMap<>();
	 * response = (Map<String, Object>)
	 * redhatUserGenerationService.getAllRolesOfTokenUser(TokenUserModelRequest);
	 * 
	 * return ResponseEntity.accepted().body(response); }
	 */
	@PostMapping(value = "/auth/adduser")
	public ResponseEntity<Object> addUser(@RequestHeader("Authorization") String token,String realm,@RequestBody String UserModel)  {
		logger.debug("Add user: initiated");
		Map<String, Object> response1 = new HashMap<String, Object>();
		
		JSONObject addUserModel = new JSONObject(UserModel);

	    	ArrayList<Credential> credentialList = new ArrayList();
		ArrayList<String> grouplist = new ArrayList();

		AddUserModel addUser = new AddUserModel();
		AddUserListModel addUserModel1 = new AddUserListModel();
		// Decryption technique
		addUser.setUsername(addUserModel.getString("username"));
		addUser.setEnabled(addUserModel.getBoolean("enabled"));
		addUser.setFirstName(addUserModel.getString("firstName"));
		addUser.setLastName(addUserModel.getString("lastName"));
		addUser.setEmail(addUserModel.getString("email"));
		addUser.setGroups(addUserModel.getString("group"));
		addUser.setPwdd(addUserModel.getString("username"));
		grouplist.add(addUser.getGroups());
		addUserModel1.setUsername(addUser.getUsername());
		addUserModel1.setEnabled(addUserModel.getBoolean("enabled"));
		addUserModel1.setFirstName(addUser.getFirstName());
		addUserModel1.setLastName(addUser.getLastName());
		addUserModel1.setEmail(addUser.getEmail());
		addUserModel1.setGroups(grouplist);
		Credential credentials = new Credential();
		credentials.setType("password");
		credentials.setValue(addUser.getPwdd());
		credentialList.add(credentials);

		addUserModel1.setCredentials(credentialList);

		ResponseEntity<Object> rm = redhatUserGenerationService.addUser(token, realm, addUserModel1);

		return new ResponseEntity<>(rm, HttpStatus.OK);
	}
}
