package data;

public final class UserDataBuilder {
	
	public static UserData getUserData() {
	

     // Generating fake data
     String name = "name1";
     String email = "abc4@gmail.com";
     String pass = "P@ssword123";
     int mobileNumber = 1234567890;
     String address = "address 1234";
     String city = "New York";
     String state = "New York";
     String zipcode = "12345";
     String country = "United States";

     // Create an instance of UserData with the generated data
     UserData user = new UserData(name, email, pass, mobileNumber, address, city, state, zipcode, country);
     return user;
	}
	
//	public static String generateUniqueEmail() {
//        return "testemail" + System.currentTimeMillis() + "@example.com";
//    }

}