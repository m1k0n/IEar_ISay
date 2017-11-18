package Model;

public class Domino
{
	private String firstWord;
	
	private String secondWord;
	
	public Domino()
	{
		super();
	}
	
	public Domino(String firstWord, String secondWord)
	{
		super();
		this.firstWord = firstWord;
		this.secondWord = secondWord;
	}
	
	public String getFirstWord()
	{
		return firstWord;
	}

	public void setFirstWord(String firstWord)
	{
		this.firstWord = firstWord;
	}

	public String getSecondWord()
	{
		return secondWord;
	}

	public void setSecondWord(String secondWord)
	{
		this.secondWord = secondWord;
	}
	
	@Override
	public String toString()
	{
		return firstWord + " / " + secondWord;
	}
}
