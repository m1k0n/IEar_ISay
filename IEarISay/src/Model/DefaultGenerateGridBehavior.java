package Model;

public class DefaultGenerateGridBehavior implements GenerateGridBehavior
{
	private int nbColumns;

	public DefaultGenerateGridBehavior(int nbColumns)
	{
		this.nbColumns = nbColumns;
	}

	public int generateNbButtons(int nbButtons)
	{
		if (this.nbColumns == 2)
		{
			return generate2NbButtons(nbButtons);
		}
		if (this.nbColumns == 4)
		{
			return generate4NbButtons(nbButtons);
		}
		if (this.nbColumns == 6)
		{
			return generate6NbButtons(nbButtons);
		}
		if (this.nbColumns == 8)
		{
			return generate8NbButtons(nbButtons);
		}
		if (this.nbColumns == 10)
		{
			return generate10NbButtons(nbButtons);
		}
		return -1;
	}

	public int generateNbLines(int nbButtons)
	{
		return nbButtons / nbColumns;
	}

	public int generateNbColumns()
	{
		return nbColumns;
	}

	public void toPrint()
	{
		System.out.println("I am a GenerateGridBehavior for 10 Columns !");
	}
	
	public int generate10NbButtons(int nbButtons)
	{
		if (nbButtons % 10 == 0)
		{
			return nbButtons;
		}
		else if ((nbButtons + 2) % 10 == 0)
		{
			return nbButtons + 2;
		}
		else if ((nbButtons + 4) % 10 == 0)
		{
			return nbButtons + 4;
		}
		else if ((nbButtons + 6) % 10 == 0)
		{
			return nbButtons + 6;
		}
		else
		{
			return nbButtons + 8;
		}
	}
	
	public int generate2NbButtons(int nbButtons)
	{
		return nbButtons;
	}
	
	public int generate4NbButtons(int nbButtons)
	{
		if (nbButtons % 4 == 0)
		{
			return nbButtons;
		}
		else
		{
			return nbButtons + 2;
		}
	}
	
	public int generate6NbButtons(int nbButtons)
	{
		if (nbButtons % 6 == 0)
		{
			return nbButtons;
		}
		else if ((nbButtons + 2) % 6 == 0)
		{
			return nbButtons + 2;
		}
		else
		{
			return nbButtons + 4;
		}
	}
	
	public int generate8NbButtons(int nbButtons)
	{
		if (nbButtons % 8 == 0)
		{
			return nbButtons;
		}
		else if ((nbButtons + 2) % 8 == 0)
		{
			return nbButtons + 2;
		}
		else if ((nbButtons + 4) % 8 == 0)
		{
			return nbButtons + 4;
		}
		else
		{
			return nbButtons + 6;
		}
	}
}
