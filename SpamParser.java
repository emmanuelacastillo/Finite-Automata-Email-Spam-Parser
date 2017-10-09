import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SpamParser
{	
	/* File information variables */
	private File file;
	private List<EmailInfo> emailInfos;
	
	/* File parsing variables */
	private EmailTracker emailTracker;
	private int input;
	private char charInput;
	
	/* Individual email variables */
	private EmailInfo emailInfo;
	private StringBuilder docId;
	
	public SpamParser(String filePath) throws FileNotFoundException
	{
		File fileToParse = new File(filePath);
		if(!fileToParse.exists()) {
			throw new FileNotFoundException("File " + filePath + " does not exist");
		}
		
		this.file = fileToParse;
		emailInfos = new ArrayList<EmailInfo>();
		initVarsForIndividualEmail();
	}
	
	private void initVarsForIndividualEmail()
	{
		emailTracker = new EmailTracker();
		emailInfo = new EmailInfo();
		docId = new StringBuilder();
	}
		
	public List<EmailInfo> parse() throws FileNotFoundException, IOException
	{
		InputStream in = null;
		Reader reader = null;
				
		try
		{	
			in = new FileInputStream(file);
			reader = new InputStreamReader(in, Charset.defaultCharset());
					
			/* Read file until end of file, reading through each email block */
			while((input = reader.read()) != -1)
			{
				charInput = (char) input;
				
				/* Find start of email (<DOC>) */
				if(!emailTracker.isEmailStartFound(charInput)){ continue; }
				
				/* Find doc id start of email (<DOCID>) */
				if(!emailTracker.isIdStartFound(charInput)){ continue; }
				
				/* Find doc id end of email (</DOCID>) */
				if(!emailTracker.isIdEndFound(charInput)){ 
					docId.append(String.valueOf(charInput));
					continue; 
				}
				
				/* Find full subject line of email to determine when to start parsing email body (Subject:..../n)*/
				if(!emailTracker.isFullSubjectLineParsed(charInput)){ continue; } 
				
				/* Parse email until end of email found (</DOC>) */
				if(!emailTracker.isEmailEndFound(charInput))
				{
					/* Set email state until spam state detected */
					if(!emailInfo.isSpam())
					{
						emailInfo.setState(charInput);
						System.out.println("Current state: " + emailInfo.getState().getStateName());
					}
					continue;
				}
				
				/* Add email information */
				docId.replace(docId.length() - EmailTracker.ID_END_TAG.length(), docId.length(), ""); // Remove end tag of doc id
				emailInfo.setDocId(docId.toString().trim()); // Remove leading and trailing white spaces
				emailInfos.add(emailInfo);
				
				/* Output message if email identified as spam */
				if(emailInfo.isSpam())
				{
					System.out.println("---------------------------------");
					System.out.println("Spam Detected:");
					System.out.println("---------------------------------");
					System.out.println("DocId: " + emailInfo.getDocId());
					System.out.println("State: " + emailInfo.getState().getStateName());
					System.out.println("---------------------------------");
				}
				else
				{
					System.out.println("---------------------------------");
					System.out.println("No Spam Detected for DocId: " + emailInfo.getDocId());
					System.out.println("---------------------------------");
				}
				
				/* Reinitialize for next email to parse */
				initVarsForIndividualEmail();
			}
		}
		/* Close I/O resources */
		finally
		{
			if(in != null)
				in.close();
			
			if(reader != null)
				reader.close();
		}
		
		/* Print parsing results */
		System.out.println("----------------------------------------------------");
		System.out.println("Spam Report:");
		System.out.println("----------------------------------------------------");
		
		if(emailInfos.isEmpty())
			System.out.println("No spam detected.");
		else
		{
			System.out.println("The following emails are identified as spam:");
			
			for(EmailInfo info : emailInfos)
				if(info.isSpam())
					System.out.println(info.getDocId() + " at state: " + info.getState().getStateName());
		}
		System.out.println("----------------------------------------------------");

		return emailInfos;
	}
}
