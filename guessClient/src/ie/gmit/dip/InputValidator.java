package ie.gmit.dip;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The InputValidator interface for basic validation of input.
 * 
 */
public interface InputValidator {
	
	/**
	 * Validates if String is not empty or null
	 * 
	 * @return return String that is not empty or null
	 */
	public String validateStringNotEmpty();
	
	/**
	 * Returns an integer as an chosen option by the User.
	 * <p>
	 * It displays a range of options and asks User to select one from the range.
	 * It accepts only integers.
	 * The range of options is specified by the min and max parameters.
	 * 
	 * @param min a minimum value for option
	 * @param max a maximum value for option
	 * @return a selected option
	 */
    public int validateIntOfRange(int min, int max);
    
    /**
     * Returns the whole input as a string
     * 
     * @return a whole input as a string
     */
    public String validateString();
    
	/**
	 * Closes resources
	 */
	public void close();
    
}
