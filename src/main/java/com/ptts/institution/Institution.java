package com.ptts.institution;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Institution {

   
    private String institutionId; 

    @NotNull
    @Size(min = 1, max = 100)
    private String name;        

    @NotNull
    @Size(min = 1, max = 200)
    private String address;       

    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be a valid 10-digit number.")
    private String contactNumber; 

    @NotNull
    @Email(message = "Email must be in a valid format.")
    private String email;      
    

    // Getters and setters

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
