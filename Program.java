import java.io.FileNotFoundException;

/* Purpose: 
 * --------------------------------------------------
 * This program parses a file that contains information
 * of a set of email and determines which is spam based
 * on a defined finite automaton.
 */

public class Program 
{
	private static final String DEFAULT_FILE = "messagefile.txt";
	
	public static void main(String[] args) 
	{
		String fileToParse = DEFAULT_FILE;
		
		/* If an input file is provided as the
		 first argument, it will be used
		 as the file that will be parsed. */
		if(args.length > 0) {
			fileToParse = args[0];
		}
		
		try
		{
			SpamParser parser = new SpamParser(fileToParse);
			parser.parse();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("File " + fileToParse + " not found.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Unknown error occured while trying to parse file " + fileToParse);
		}
	}

}
