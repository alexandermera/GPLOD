public class Author{
	
	String givenName ;
	String familyName ;
	String fullName ;
	String emailAddress ;
	String affiliationUnit ;
	String affiliation ;
	String postalAddress ;
	String telephoneNumber ;
	
	public Author(String givenName, String familyName, String fullName,
			String emailAddress) {
		super();
		this.givenName = givenName;
		this.familyName = familyName;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
	}
	
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAffiliationUnit() {
		return affiliationUnit;
	}

	public void setAffiliationUnit(String affiliationUnit) {
		this.affiliationUnit = affiliationUnit;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
}