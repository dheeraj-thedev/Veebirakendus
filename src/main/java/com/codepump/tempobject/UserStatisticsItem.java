package com.codepump.tempobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

/**
 * Class to transport values from both tables to the user. <br>
 * Used to create statistics about the user.<br>
 * 
 * Future plans include statistics about languages used, but for now TKasekamp doesn't know how to make that query.
 * 
 * @author TKasekamp
 * 
 */
@Entity
@NamedNativeQuery(name = "thisUserStatistics", query = "SELECT w.user_id, w.user_name, count(*) AS created_Items FROM webapp_user as w, codeitem as c WHERE w.user_id = c.user_id AND w.user_id = :t_id GROUP BY w.user_id ", resultClass = UserStatisticsItem.class)
public class UserStatisticsItem implements Serializable{
	/**
	 * Has to be here
	 */
	private static final long serialVersionUID = -5921914751621125681L;
	private int userID;
	private String username;
	private int createdItems;
//	private String favouriteLanguage;
	
	public UserStatisticsItem() {
	}

	public UserStatisticsItem(int userID, String username, int createdItems,
			String favouriteLanguage) {
		super();
		this.userID = userID;
		this.username = username;
		this.createdItems = createdItems;
//		this.favouriteLanguage = favouriteLanguage;
	}

	@Id
	@Column(name = "USER_ID")
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Column(name = "USER_NAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "CREATED_ITEMS")
	public int getCreatedItems() {
		return createdItems;
	}

	public void setCreatedItems(int createdItems) {
		this.createdItems = createdItems;
	}
//	@Column(name = "FAVOURITE")
//	public String getFavouriteLanguage() {
//		return favouriteLanguage;
//	}
//
//	public void setFavouriteLanguage(String favouriteLanguage) {
//		this.favouriteLanguage = favouriteLanguage;
//	}
//
//	@Override
//	public String toString() {
//		return "UserStatisticsItem [userID=" + userID + ", username="
//				+ username + ", createdItems=" + createdItems
//				+ ", favouriteLanguage=" + favouriteLanguage + "]";
//	}

}
