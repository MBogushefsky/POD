
package com.bogaware.controller;
 
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bogaware.global.BankInstitutionManager;
import com.bogaware.global.RestManager;
import com.bogaware.global.UserManager;
import com.bogaware.messaging.TwilioMessageManager;
import com.bogaware.plugins.PluginHandler;
import com.bogaware.service.Application;
import com.bogaware.service.BankAccountManager;
import com.bogaware.service.User;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.BankInstitution;
import com.bogaware.util.SettingsManager;
 
//http://crunchify.com/simplest-spring-mvc-hello-world-example-tutorial-spring-model-view-controller-tips/
@Controller
public class ApplicationController {
 
	@RequestMapping("/home")
	public ModelAndView homePage() {
		String message = "Homepage is in development...";
		return new ModelAndView("home", "message", message);
	}
	
	@RequestMapping("/")
	public ModelAndView loginPage() {
		String message = "Login is in development...";
		return new ModelAndView("login", "message", message);
	}
	
	@RequestMapping("/app")
	public ModelAndView applicationPage() {
		String message = "Application is in development...";
		return new ModelAndView("index", "message", message);
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getVersion() {
		return "{ \"version\":\"1.0.1\"}";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/call/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String login(@RequestBody String requestString) {
		JSONObject requestJson = RestManager.stringToJson(requestString);
		JSONObject responseJson = new JSONObject();
		User loggedInUser = Application.login((String)requestJson.get("username"), (String)requestJson.get("password"));
		if(loggedInUser != null) {
			responseJson.put("authorized", true);
			responseJson.put("user_id", loggedInUser.getUserId());
		} else {
			responseJson.put("authorized", false);
			responseJson.put("user_id", null);
		}
		return responseJson.toJSONString();
	}
	
	@RequestMapping(value = "/call/getName", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getName() {
		return "{ \"name\":\"ThisIsAName\"}";
	}
	
	//TODO Convert POST to have Authorizations in the header for gets (and contain gets for users for example by passing the user id in the URL)
	@CrossOrigin
	@RequestMapping(value = "/call/get_current_user", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getCurrentUser(@RequestBody String requestString) {
		JSONObject requestJson = RestManager.stringToJson(requestString);
		JSONObject responseJson = new JSONObject();
		User loggedInUser = Application.login((String)requestJson.get("username"), (String)requestJson.get("password"));
		if(loggedInUser != null) {
			int userId = loggedInUser.getUserId();
			responseJson.put("authorized", true);
			responseJson.put("user_id", userId);
			responseJson.put("username", loggedInUser.getUsername());
			responseJson.put("first_name", loggedInUser.getFirstName());
			responseJson.put("last_name", loggedInUser.getLastName());
			responseJson.put("email", loggedInUser.getEmail());
			responseJson.put("phone_number", loggedInUser.getPhoneNumber());
		} else {
			responseJson.put("authorized", false);
			responseJson.put("user_id", null);
		}
		return responseJson.toJSONString();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/call/register", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String register(@RequestBody String requestString) {
		JSONObject requestJson = RestManager.stringToJson(requestString);
		JSONObject responseJson = new JSONObject();
		UserManager userMgr = new UserManager();
		String registerOutput = userMgr.addUserToDatabase((String)requestJson.get("username"), (String)requestJson.get("password"), (String)requestJson.get("first_name"), (String)requestJson.get("last_name"), (String)requestJson.get("email"), (String)requestJson.get("phone_number"));
		if(registerOutput.equalsIgnoreCase("Success")) {
			responseJson.put("success", true);
		} else {
			responseJson.put("success", false);
		}
		responseJson.put("message", registerOutput);
		return responseJson.toJSONString();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/call/get_institutions", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getInstitutions(@RequestBody String requestString) {
		JSONObject requestJson = RestManager.stringToJson(requestString);
		JSONObject responseJson = new JSONObject();
		User loggedInUser = Application.login((String)requestJson.get("username"), (String)requestJson.get("password"));
		if(loggedInUser != null) {
			int userId = loggedInUser.getUserId();
			responseJson.put("user_id", userId);
			BankInstitutionManager bankInstitMgr = new BankInstitutionManager();
			ArrayList<BankInstitution> bankInstits = bankInstitMgr.getBankInstitutions(userId);
			JSONArray jsonBankInstitutions = new JSONArray();
			for(BankInstitution bankInstit : bankInstits) {
				JSONObject tempJsonInstitution = new JSONObject();
				tempJsonInstitution.put("id", bankInstit.getId());
				tempJsonInstitution.put("name", bankInstit.getName());
				tempJsonInstitution.put("token", bankInstit.getAccessToken());
				jsonBankInstitutions.add(tempJsonInstitution);
			}
			responseJson.put("bank_institutions", jsonBankInstitutions);
		} else {
			responseJson.put("user_id", null);
		}
		return responseJson.toJSONString();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/call/get_bank_accounts", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getBankAccounts(@RequestBody String requestString) {
		JSONObject requestJson = RestManager.stringToJson(requestString);
		JSONObject responseJson = new JSONObject();
		User loggedInUser = Application.login((String)requestJson.get("username"), (String)requestJson.get("password"));
		if(loggedInUser != null) {
			int userId = loggedInUser.getUserId();
			responseJson.put("user_id", userId);
			BankAccountManager bankAccMgr = new BankAccountManager(userId, (int)((Long)requestJson.get("institution_id")).intValue(), (String)requestJson.get("institution_token"));
			String bankAccounts = bankAccMgr.getInstitutionBankAccounts();
			//TODO NEED TO IMPLEMENT BANKACCOUNTS AGAIN (NEED TO MASSAGE)
			/*JSONArray jsonBankInstitutions = new JSONArray();
			for(BankInstitution bankInstit : bankInstits) {
				JSONObject tempJsonInstitution = new JSONObject();
				tempJsonInstitution.put("id", bankInstit.getId());
				tempJsonInstitution.put("name", bankInstit.getName());
				jsonBankInstitutions.add(tempJsonInstitution);
			}*/
			responseJson.put("bank_accounts", bankAccounts);
		} else {
			responseJson.put("user_id", null);
		}
		return responseJson.toJSONString();
	}
	
	@RequestMapping(value = "/twilio/message", method = RequestMethod.POST)
	@ResponseBody
	public String receivedTwilioMessage(@RequestBody String requestString) {
		/*CommandCenter command = new CommandCenter();
		return "{ \"message\":\"success: "+messageMap.get("Body")+" plus "+ Functions.getCurrentBalanceFromPhoneNumber("4808885436") +"\"}";*/
		String textMessage = TwilioMessageManager.getFieldOfTwilioMessage(requestString, "Body");
		String fromPhoneNumber = TwilioMessageManager.getFieldOfTwilioMessage(requestString, "From");
		//CommandCenter command = new CommandCenter();
		PluginHandler handler = new PluginHandler(fromPhoneNumber, textMessage);
		/*PrintWriter pw;
		try {
			pw = new PrintWriter(new File("output.txt"));
	        pw.write(textMessage);
	        pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return handler.getTextResponse();
	}
	
}
