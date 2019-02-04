package midTerm;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class HangmanApp {

	public static void main(String[] args) {

		 EasyWordsTextFile.createDirectory("WordLists");
		 EasyWordsTextFile.createBlankFile("WordLists/easywords.txt");
		 EasyWordsTextFile.createBlankFile("WordLists/mediumwords.txt");
		 EasyWordsTextFile.createBlankFile("WordLists/hardwords.txt");
		printWelcome();
		String name = askName();
		int diff = getDiff();
		int sessionWins = 0;
		int sessionLosses = 0;
		String keepPlaying = "y";
		while (keepPlaying.equalsIgnoreCase("y")) {
			String word = getWord(diff);
			if (runGame(name, word, diff)) {
				sessionWins++;
			} else {
				sessionLosses++;
			}
			keepPlaying = keepPlaying();
		}
		System.out.println(name + ", your record this session was " + sessionWins + "-" + sessionLosses);

		printGoodbye();

	}

	public static boolean runGame(String userName, String word, int diff) {
		String wordStatus = wordStart(word);
		int misses = 0;
		int hits = 0;
		int missMax = 6;
		String guessList = "";
		while (misses < missMax && hits != word.length()) { // loops while they havent hit the max misses or won

			for (int j = 0; j < word.length(); j++) {
				System.out.print(wordStatus.charAt(j) + " "); // adds separation to the underscores
			}
			System.out.println();

			System.out.println("Misses: " + misses); // tracks misses
			char guess = getGuess().toLowerCase().charAt(0); // gets the guess
			guessList += guess + " "; // updates the guess list

			if (!(word.indexOf(guess) == -1)) { // if the word contains the letter, this replaces the underscores with
												// that letter in the appropriate position(s) and ticks up the hit count
												// for each instance
				for (int i = 0; i < word.length(); i++) {
					char currChar = word.charAt(i);
					if (currChar == guess) {
						wordStatus = wordStatus.substring(0, i) + guess + wordStatus.substring(i + 1);
						hits++;
					}
				}

			} else { // ticks up misses and alerts the user
				System.out.println("Miss!");
				misses++;
			}
			if (misses < missMax) { // added this so the guess list didn't print out again if they had already lost
				System.out.println("Guesses so far: " + guessList);
			}
		}
		if (hits == word.length()) { // ends the current game and prints out a message, returns the result of the
										// game
			System.out.println(word);
			System.out.println("You Win!!!");
			return true;
		} else {
			System.out.println("You Lose :(");
			System.out.println("The word was: " + word);
			return false;
		}
	}

	public static String getGuess() { // gets a letter guess from the user
		Scanner scnr = new Scanner(System.in);
		String guess = "0";
		String regex = ("[a-zA-z]{1}");
		while (!guess.matches(regex)) {
			System.out.println("Please enter a guess - remember, use a single a-z character");
			guess = scnr.nextLine();
		}
		return guess;

	}

	public static String wordStart(String word) { // returns a string with a number of underscores equal to the letters
													// in the word
		String blankWord = "";
		for (int i = 0; i < word.length(); i++) {
			blankWord += "_";
		}
		blankWord += " ";
		return blankWord;
	}

	public static void printWelcome() { // prints out a welcome message
		System.out.println("Welcome to the Hangman Game!");
	}

	public static String askName() { // gets the user's name
		Scanner scnr = new Scanner(System.in);
		String name;
		name = Validator.getString(scnr, "Please enter your name:");
		return name;

	}

	public static String keepPlaying() { // asks if the user wants to continue
		Scanner scnr = new Scanner(System.in);
		String keepPlaying = Validator.getString(scnr, "Press \"y\" to keep playing, any other key to exit");
		return keepPlaying;
	}

	public static void printGoodbye() { // prints out a goodbye message
		System.out.println("Thank you for playing!");
	}

	public static int getDiff() { // allows the user to choose a difficulty
		Scanner scnr = new Scanner(System.in);
		int diff = Validator.getInt(scnr, "Please choose a difficulty:\n1: Easy\n2: Medium\n3: Hard", 1, 3);
		return diff;
	}

	public static String getWord(int diff) { // gets a random word from the selected list
		int wordIndex = 0;
		Random rand = new Random();
		String word = "";
		ArrayList<String> wordList = new ArrayList<>();
		if (diff == 1) {
			wordList = EasyWordsTextFile.readFile();
			wordIndex = rand.nextInt(wordList.size());
			word = wordList.get(wordIndex);

		}
		if (diff == 2) {

			wordList = MediumWordsTextFile.readFile();
			wordIndex = rand.nextInt(wordList.size());
			word = wordList.get(wordIndex);
		}
		if (diff == 3) {

			wordList = HardWordsTextFile.readFile();
			wordIndex = rand.nextInt(wordList.size());
			word = wordList.get(wordIndex);
		}

		return word;
	}
}