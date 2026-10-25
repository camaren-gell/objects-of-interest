package chess.utils;

public class InputValidator{

	public static boolean isValidMoveFormat(String moveInput){
	
		if (moveInput.length() != 5){ // validates length
			return false;
}
		if (moveInput.charAt(2) != ' ' ){ // validates space
			return false;	
}
		if (moveInput.charAt(0) < 'A' || moveInput.charAt(0) > 'H' ){ // validates first action letter
			return false;
}
		if (moveInput.charAt(3) < 'A' || moveInput.charAt(3) > 'H' ){ //validates second action letter
			return false;
}
		if (moveInput.charAt(1) < '1' || moveInput.charAt(1) > '8'){ // validate the first row
			return false;
}
		if (moveInput.charAt(4) < '1' || moveInput.charAt(4) > '8'){ // validate the second row
			return false;
}
	return true;	

		}
	}