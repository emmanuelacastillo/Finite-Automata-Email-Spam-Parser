/* Purpose: 
 * --------------------------------------------------
 * This class is used to assist
 * in determine the different components of
 * an email block within the file being parsed.
 * It determines the following of an email block:
 * 1) Start and end elements of the full email
 * 2) Start and end elements of the email message id
 * 3) The subject line
 */

public class EmailTracker 
{
	public static final String EMAIL_START_TAG = "<DOC>";
	public static final String ID_START_TAG = "<DOCID>";
	public static final String ID_END_TAG = "</DOCID>";
	public static final String SUBJ_IND = "Subject:";
	public static final String EMAIL_END_TAG = "</DOC>";
	
	private boolean isFullSubjectLineFound = false;
	
	private StringBuilder emailPortiontracker = new StringBuilder();
	
	public boolean isEmailStartFound(char input) 
	{
		return isElementFound(EMAIL_START_TAG, input);
	}
	
	public boolean isIdStartFound(char input) 
	{
		return isElementFound(ID_START_TAG, input);
	}
	
	public boolean isIdEndFound(char input) 
	{
		return isElementFound(ID_END_TAG, input);
	}
	
	public boolean isFullSubjectLineParsed(char input)
	{
		if(isFullSubjectLineFound)
			return true;
		
		String subStringAfterSubject = emailPortiontracker.substring(emailPortiontracker.length() - SUBJ_IND.length(), emailPortiontracker.length());
		if(emailPortiontracker.toString().contains(SUBJ_IND) && subStringAfterSubject.contains("\n"))
		{
			isFullSubjectLineFound = true;
			return true;
		}
		else
		{
			emailPortiontracker.append(Character.toString(input));
			return false;
		}
	}
	
	public boolean isEmailEndFound(char input) 
	{
		return isElementFound(EMAIL_END_TAG, input);
	}
	
	private boolean isElementFound(String element, char input)
	{
		if(emailPortiontracker.toString().contains(element))
			return true;
		else
		{
			emailPortiontracker.append(Character.toString(input));
			return false;
		}
	}
}
