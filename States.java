/* Purpose: 
 * --------------------------------------------------
 * This is an enum that defines the possible states
 * a parsed email may go through. It defines the state
 * based on the provided inputs and determines the next state
 * based on the current state and the current symbol (char) input.
 * Essentially, this enum defines the finite automaton for spam emails.
 */

public enum States 
{
	NOTSPAM("NotSpam", false),
	F("f", false),
	FR("fr", false),
	FRE("fre", false),
	FREE("free", false),
	FREESPACE("free ", false),
	FREESPACEA("free a", false),
	FREESPACEAC("free ac", false),
	FREESPACEACC("free acc", false),
	FREESPACEACCE("free acce", false),
	FREESPACEACCES("free acces", false),
	FREESPACEACCESS("free access", false),
	FREESPACEACCESSFINAL("free access ", true),
	FREESPACES("free s", false),
	FREESPACESO("free so", false),
	FREESPACESOF("free sof", false),
	FREESPACESOFT("free soft", false),
	FREESPACESOFTW("free softw", false),
	FREESPACESOFTWA("free softwa", false),
	FREESPACESOFTWAR("free softwar", false),
	FREESPACESOFTWARE("free software", false),
	FREESPACESOFTWAREFINAL("free software ", true),
	FREESPACEV("free v", false),
	FREESPACEVA("free va", false),
	FREESPACEVAC("free vac", false),
	FREESPACEVACA("free vaca", false),
	FREESPACEVACAT("free vacat", false),
	FREESPACEVACATI("free vacati", false),
	FREESPACEVACATIO("free vacatio", false),
	FREESPACEVACATION("free vacation", false),
	FREESPACEVACATIONFINAL("free vacation ", true),
	FREESPACET("free t", false),
	FREESPACETR("free tr", false),
	FREESPACETRI("free tri", false),
	FREESPACETRIA("free tria", false),
	FREESPACETRIAL("free trial", false),
	FREESPACETRIALS("free trials", false),
	FREESPACETRIALSFINAL("free trials ", true),
	W("w", false),
	WI("wi", false),
	WIN("win", false),
	WINFINAL("win ", true),
	WINN("winn", false),
	WINNE("winne", false),
	WINNER("winner", false),
	WINNERFINAL("winner ", true),
	WINNERS("winners", false),
	WINNERSFINAL("winners ", true),
	WINNI("winni", false),
	WINNIN("winnin", false),
	WINNING("winning", false),
	WINNINGS("winnings", false),
	WINNINGSFINAL("winnings ", true);
	
	private String stateString;
	private boolean isSpam;
	
	States(String stateString, boolean isSpam)
	{
		this.stateString = stateString;
		this.isSpam = isSpam;
	}
	
	/* Determines the next state based on the current state and the current input. */
	public static States getNextState(States current, char input)
	{
		String currentState = current.getName();
		if(currentState.equals(States.NOTSPAM.getName()))
			currentState = "";
		
		String nextStateName = currentState + input;
		
		for(States state : States.values())
			if(state.getName().equals(nextStateName))
				return state;
		
		return States.NOTSPAM;
	}
	
	private String getName()
	{
		return this.stateString;
	}
	
	public String getStateName()
	{
		return this.name();
	}
	
	public boolean isSpam()
	{
		return this.isSpam;
	}
}
