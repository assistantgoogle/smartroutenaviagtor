package com.ptts.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Profile {
	
    @NotNull(message = "Organization ID cannot be null")
    @Size(min = 1, max = 50)
    private String orgId;
    public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	
	

	@NotNull
    @Size(min = 1, max = 50)
    private String institutionId; 

    @NotNull(message = "Profile ID cannot be null")
    @Size(min = 1, max = 50)
    
    private String profileId;
    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 1, max = 50)
    private String fName;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(min = 1, max = 50)
    private String lName;

    @NotBlank(message = "Primary phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Primary phone number must be a valid 10-digit number")
    private String phno1;

    @NotBlank(message = "Email cannot be blank")
    @Email(regexp= "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email should be valid")
    private String profileEmailOne;

    @NotBlank(message = "Date of Birth cannot be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String dob;

    @Size(max = 50, message = "Reference ID must be at most 50 characters")
    private String reference_Id;

    @NotBlank
    @Pattern(regexp = "^(Male|Female|Other)$")
    private String gender;

    @Pattern(regexp = "^\\d{10}$", message = "Emergency contact number must be a valid 10-digit number")
    private String emergency_no;

    @NotBlank(message = "Address Type cannot be blank")
    @Size(min = 1, max = 50, message = "Address Type must be between 1 and 20 characters")
    private String adressType;

    @NotBlank(message = "Address Line 1 cannot be blank")
    @Size(min = 1, max = 100, message = "Address Line 1 must be between 1 and 100 characters")
    private String adress1;

    @Size(max = 100, message = "Address Line 2 must be at most 100 characters")
    private String adress2;

    @NotBlank(message = "Pincode cannot be blank")
    @Pattern(regexp = "^\\d{6}$", message = "Pincode must be a valid 6-digit number")
    private String profilepincode;

    @NotBlank(message = "City cannot be blank")
    @Size(min = 1, max = 50, message = "City must be between 1 and 50 characters")
    private String profilecity;

    @NotBlank(message = "State cannot be blank")
    @Size(min = 1, max = 50, message = "State must be between 1 and 50 characters")
    private String profilestate;

    @NotBlank(message = "Country cannot be blank")
    @Size(min = 1, max = 50, message = "Country must be between 1 and 50 characters")
    private String profilecountry;

    @Size(max = 100, message = "Landmark must be at most 100 characters")
    private String landmark;

    @Pattern(regexp = "^\\d{10}$", message = "Secondary phone number must be a valid 10-digit number")
    private String phno2;

   
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
        
    }

    

    public String getProfileId() {
        return profileId;
    }
    

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhno1() {
        return phno1;
    }

    public void setPhno1(String phno1) {
        this.phno1 = phno1;
    }

    public String getProfileEmailOne() {
        return profileEmailOne;
    }

    public void setProfileEmailOne(String profileEmailOne) {
        this.profileEmailOne = profileEmailOne;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getReference_Id() {
        return reference_Id;
    }

    public void setReference_Id(String reference_Id) {
        this.reference_Id = reference_Id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmergency_no() {
        return emergency_no;
    }

    public void setEmergency_no(String emergency_no) {
        this.emergency_no = emergency_no;
    }

    public String getAdressType() {
        return adressType;
    }

    public void setAdressType(String adressType) {
        this.adressType = adressType;
    }

    public String getAdress1() {
        return adress1;
    }

    public void setAdress1(String adress1) {
        this.adress1 = adress1;
    }

    public String getAdress2() {
        return adress2;
    }

    public void setAdress2(String adress2) {
        this.adress2 = adress2;
    }

    public String getProfilepincode() {
        return profilepincode;
    }

    public void setProfilepincode(String profilepincode) {
        this.profilepincode = profilepincode;
    }

    public String getProfilecity() {
        return profilecity;
    }

    public void setProfilecity(String profilecity) {
        this.profilecity = profilecity;
    }

    public String getProfilestate() {
        return profilestate;
    }

    public void setProfilestate(String profilestate) {
        this.profilestate = profilestate;
    }

    public String getProfilecountry() {
        return profilecountry;
    }

    public void setProfilecountry(String profilecountry) {
        this.profilecountry = profilecountry;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPhno2() {
        return phno2;
    }

    public void setPhno2(String phno2) {
        this.phno2 = phno2;
    }
}
