
public enum Role {
	ADMIN,
	CUSTOMER;

	static Role getRole(String input) {
		input = input.toLowerCase();
		if(input.equals("admin")) {
			return Role.ADMIN;
		}else if(input.equals("customer")){
			return Role.CUSTOMER;
		}else {
			return null;
		}
	}
	
}
