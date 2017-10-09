/* Purpose: 
 * --------------------------------------------------
 * This class represent a single email block that is
 * parsed within the provided file. It specifies
 * the message doc id and its state until it
 * enters a spam state or until the end of an email block.
 */

public class EmailInfo 
{
	private String docId;
	private States currentState = States.NOTSPAM;
	
	public void setState(char input) {
		this.currentState = States.getNextState(currentState, input);
	}
	
	public States getState() {
		return currentState;
	}
	
	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocId() {
		return docId;
	}

	public boolean isSpam() {
		return currentState.isSpam();
	}
}
