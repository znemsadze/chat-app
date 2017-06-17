package ge.ssoft.chat.authentification;


import ge.ssoft.chat.core.model.UserAuthority;
import ge.ssoft.chat.core.model.Users;

public enum UserRole {
	USER, ADMIN;

	public UserAuthority asAuthorityFor(final Users user) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setUsersByUserId(user);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_USER":
			return USER;
		case "ROLE_ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}


}
