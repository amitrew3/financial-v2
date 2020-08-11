package com.rew3.common.application;

import com.rew3.billing.user.model.User;

public class Authentication {

	private Authentication() {

	}

	public synchronized static String getUserId() {
		System.out.println("++ Get User Id");
		/*HttpServletRequest request = ServletActionContext.getRequest();
//		String userId = request.getHeader("REW3-UserId");
		User u = (User) request.getAttribute("user");
		//return u.get_id();*/
		return "1111111";
	}

	public synchronized static User getUser() {
//		System.out.println("++ Get User");
//		HttpServletRequest request = ServletActionContext.getRequest();
//		User u = (User) request.getAttribute("user");
		return null;
	}
	public synchronized static String getRew3UserId() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String userId = request.getHeader("REW3-UserId");
		return "userId";
	}
	public synchronized static String getRew3GroupId() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String userId = request.getHeader("REW3-GroupId");
		return "userId";
	}

    public synchronized static AuthenticatedUser getAuthenticatedUser() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//
//		String id = request.getHeader("userId");
//		String userFirstName = request.getHeader("userFirstName");
//		String userLastName = request.getHeader("userLastName");

		return null;
    }

	public synchronized static String getMemberId() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String memberId = request.getHeader("memberId");
		return "memberId";
	}
}
