package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Model.Domino;

public class DominosUtils
{
	public static List<Domino> createDominos(int nbButtons)
	{
		List<Domino> dominos = new ArrayList<Domino>();
		
		for (int idx = 0 ; idx < nbButtons; idx++)
		{
			dominos.add(new Domino());
		}
		return dominos;
	}
	
	
	public static List<Domino> shuffleDominos(List<Domino> dominos, int nbCol)
	{
//		List<Domino> dominos = genererDominos(words);
		List<List<Domino>> shuffledDominos = distribuerDominos(dominos, nbCol);
		
		return nettoyerDominos(shuffledDominos, nbCol);
	}
	
	public static List<Domino> genererDominos(List<String> words)
	{
		List<Domino> dominos = new ArrayList<Domino>();
		String lastUsedWord = null;
		for (String string : words)
		{
			if (dominos.isEmpty())
			{
				dominos.add(new Domino(ApplicationConstants.START_LABEL, string));
			}
			else
			{
				dominos.add(new Domino(lastUsedWord, string));
			}
			lastUsedWord = string;
		}
		dominos.add(new Domino(lastUsedWord, ApplicationConstants.END_LABEL));
		return dominos;
	}

	private static List<List<Domino>> distribuerDominos(List<Domino> dominos, int nbCol)
	{
		int nbDominos = dominos.size();
		List<List<Domino>> result = new ArrayList<List<Domino>>();
		int nbJoueurs = nbCol / 2;
		for (int i = 0; i < nbJoueurs; i++)
		{
			result.add(new ArrayList<Domino>());
		}

		result.get(0).add(dominos.remove(0));
		int lastJoueur = 0;

		Iterator<Domino> iter = dominos.iterator();
		while (iter.hasNext())
		{
			Domino next = iter.next();
			int idxToAdd = -1;
			int nbMinDominosDejaAjoutes = 10000;
			List<Integer> sau = new ArrayList<>();
			for (int i = 0; i < nbJoueurs; i++)
			{
				if (i == lastJoueur)
				{
					continue;
				}
				if (result.get(i).size() == nbMinDominosDejaAjoutes)
				{
					nbMinDominosDejaAjoutes = result.get(i).size();
					sau.add(i);
				}
				else if (result.get(i).size() < nbMinDominosDejaAjoutes)
				{
					nbMinDominosDejaAjoutes = result.get(i).size();
					sau.clear();
					sau.add(i);
				}
			}
			int sauSize = sau.size();
			if (sauSize == 1)
			{
				idxToAdd = sau.get(0);
			}
			else
			{
				Random rand = new Random();
				idxToAdd = sau.get(rand.nextInt(sauSize));
			}

			if (idxToAdd != -1)
			{
				result.get(idxToAdd).add(next);
				lastJoueur = idxToAdd;
			}

		}

		return result;
	}

	private static List<Domino> nettoyerDominos(List<List<Domino>> dominos, int nbCol)
	{
		List<Domino> listeFinale = new ArrayList<Domino>();

		int nbJoueurs = nbCol / 2;
		int nbDominosParJoueurs = dominos.get(0).size();
		int nbDominoAjoutes = 0;

		while (nbDominoAjoutes < nbDominosParJoueurs)
		{
			for (int idx = 0; idx < nbJoueurs; idx++)
			{
				listeFinale.add(dominos.get(idx).get(nbDominoAjoutes));
			}
			nbDominoAjoutes++;
		}

		return listeFinale;
	}
}
