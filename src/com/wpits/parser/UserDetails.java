
package com.wpits.parser;

import java.io.Serializable;


public class UserDetails  implements Serializable{

	private String userName;
	private String userId;
	private String distributerId;
	private String userImage;
	private String userDob;
	private String userGender;
	private String userMobile;
	private String userWalletbalance;
	private String useridNumber;
	private String userAddress;
	private String reserveAmmount;
	private Loan loan;
	private String isloanrequested;
	private String user_email_id;
	private String isloanapproved;
	private String unreadnotifications;

	public String getUnreadnotifications() {
		return unreadnotifications;
	}

	public void setUnreadnotifications(String unreadnotifications) {
		this.unreadnotifications = unreadnotifications;
	}

	public String getIsloanapproved() {
		return isloanapproved;
	}

	public void setIsloanapproved(String isloanapproved) {
		this.isloanapproved = isloanapproved;
	}

	public String getUser_email_id() {
		return user_email_id;
	}

	public void setUser_email_id(String user_email_id) {
		this.user_email_id = user_email_id;
	}

	public String getIsloanrequested() {
		return isloanrequested;
	}

	public void setIsloanrequested(String isloanrequested) {
		this.isloanrequested = isloanrequested;
	}

	/**
	 * 
	 * @return
	 *     The userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 *     The user_name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return
	 *     The userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 *     The user_id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * @return
	 *     The distributerId
	 */
	public String getDistributerId() {
		return distributerId;
	}

	/**
	 * 
	 * @param distributerId
	 *     The distributer_id
	 */
	public void setDistributerId(String distributerId) {
		this.distributerId = distributerId;
	}

	/**
	 * 
	 * @return
	 *     The userImage
	 */
	public String getUserImage() {
		return userImage;
	}

	/**
	 * 
	 * @param userImage
	 *     The user_image
	 */
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	/**
	 * 
	 * @return
	 *     The userDob
	 */
	public String getUserDob() {
		return userDob;
	}

	/**
	 * 
	 * @param userDob
	 *     The user_dob
	 */
	public void setUserDob(String userDob) {
		this.userDob = userDob;
	}

	/**
	 * 
	 * @return
	 *     The userGender
	 */
	public String getUserGender() {
		return userGender;
	}

	/**
	 * 
	 * @param userGender
	 *     The user_gender
	 */
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	/**
	 * 
	 * @return
	 *     The userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * 
	 * @param userMobile
	 *     The user_mobile
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * 
	 * @return
	 *     The userWalletbalance
	 */
	public String getUserWalletbalance() {
		return userWalletbalance;
	}

	/**
	 * 
	 * @param userWalletbalance
	 *     The user_walletbalance
	 */
	public void setUserWalletbalance(String userWalletbalance) {
		this.userWalletbalance = userWalletbalance;
	}

	/**
	 * 
	 * @return
	 *     The useridNumber
	 */
	public String getUseridNumber() {
		return useridNumber;
	}

	/**
	 * 
	 * @param useridNumber
	 *     The userid_number
	 */
	public void setUseridNumber(String useridNumber) {
		this.useridNumber = useridNumber;
	}

	/**
	 * 
	 * @return
	 *     The userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * 
	 * @param userAddress
	 *     The user_address
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * 
	 * @return
	 *     The reserveAmmount
	 */
	public String getReserveAmmount() {
		return reserveAmmount;
	}

	/**
	 * 
	 * @param reserveAmmount
	 *     The reserve_ammount
	 */
	public void setReserveAmmount(String reserveAmmount) {
		this.reserveAmmount = reserveAmmount;
	}

	/**
	 * 
	 * @return
	 *     The loan
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * 
	 * @param loan
	 *     The loan
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

}
