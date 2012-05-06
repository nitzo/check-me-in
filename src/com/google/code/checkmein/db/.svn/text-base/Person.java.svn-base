package com.google.code.checkmein.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable; 
import javax.jdo.annotations.Persistent; 
import javax.jdo.annotations.PrimaryKey;  
import com.restfb.types.User;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class Person {

	@PrimaryKey     
	@Persistent   
	private String id;       
	@Persistent
	private String firstName;
	@Persistent
	private String lastName;
	@Persistent
	private String gender;
	@Persistent
	private String email;
	@Persistent
	private String access_token;
	@Persistent
	private String cell_phone;
	@Persistent
	private String locale;
	@Persistent
	private Date creationDate;
	@Persistent
	private Set<String> FBfriends;
	@Persistent
	private String birthday;
	@Persistent
	private String pic;
	
	@SuppressWarnings("deprecation")
	public Person(User facebookUser,String access_token) { 
		updatePerson(facebookUser, access_token);
		Date temp = new Date();
		temp.setHours(temp.getHours()+ DatabaseLogic.GMT);
		this.creationDate = temp;
		this.FBfriends = new HashSet<String>();
	}
	
	public void setFaceboofParams(User facebookUser){
		this.id = facebookUser.getId();
		this.firstName = facebookUser.getFirstName();
		this.lastName = facebookUser.getLastName();
		this.email = facebookUser.getEmail();
		this.gender = facebookUser.getGender();
		this.locale = facebookUser.getLocale();
		this.pic = facebookUser.getPicture();
		this.birthday = facebookUser.getBirthday();
	}
	
	public void updatePerson(User facebookUser,String access_token){
		setFaceboofParams(facebookUser);
		this.access_token=access_token;
	}
	
	public String getId() {
		return id;
	}

	public String getAccessToken(){
		return this.access_token;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAccessToken(String access_token){
		this.access_token = access_token;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getLocale() {
		return locale;
	}
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Set<String> getFriends() {
		return FBfriends;
	}

	public void setFriends(Set<String> friends) {
		this.FBfriends = friends;
	}

	public String toString(){
		
		StringBuilder s;
		
		s = new StringBuilder();
		
		s.append("FB Id: " + this.getId() + "<br>\n");
		s.append("Name: " + this.getFirstName() + " " + this.getLastName() + "<br>\n");
		s.append("Email: " + this.getEmail() + "<br>\n");
		s.append("<br>\n");
		
		
		return s.toString();
	}

	public Date getCreationDate() {
		return creationDate;
	}

}
