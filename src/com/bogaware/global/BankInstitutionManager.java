package com.bogaware.global;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.bogaware.service.accounts.BankInstitution;

public class BankInstitutionManager {
	public ArrayList<BankInstitution> getBankInstitutions(int userId){
		ArrayList<BankInstitution> result = new ArrayList<BankInstitution>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM bank_institutions WHERE ACCOUNT_OWNER=" + userId + ";");
			while (rs.next())
			{
				BankInstitution tempInstitution = new BankInstitution(rs.getInt("ID"), rs.getInt("ACCOUNT_OWNER"), rs.getString("BANK_NAME"), rs.getInt("INSTITUTION_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("ACCESS_TOKEN"), rs.getDate("LAST_UPDATED"));
				result.add(tempInstitution);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
