///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           WordSearch
// Course:          CS 200, Spring 2020
//
// Author:          Joe Rafferty
// Email:           jrrafferty@wisc.edu
// Lecturer's Name: Jim Williams
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class WordSearch {
    public static void main(String[] args) {
	}
    /**
     * Opens and reads a dictionary file returning a list of words.
     * Example:
     *  dog
     *  cat
     *  turtle
     *  elephant
     * 
     * If there is an error reading the file, such as the file cannot be found,
     * then the following message is shown: 
     *     Error: Unable to read file <dictionaryFilename>
     * with <dictionaryFilename> replaced with the parameter value.
     * 
     * @param dictionaryFilename The dictionary file to read.
     * @return An ArrayList of words.
     */
    public static ArrayList<String> readDictionary(String dictionaryFilename) {
    	ArrayList<String> animals = new ArrayList<>();
    	FileInputStream filestream;
    	BufferedReader buffRead;
    	
    	try {
    	    filestream = new FileInputStream(dictionaryFilename);
    	    buffRead = new BufferedReader(new InputStreamReader(filestream));
    	    String word = buffRead.readLine();
    	    
    	    while (word != null) {
    	        animals.add(word);
    	        word = buffRead.readLine();
    	    }
    	    buffRead.close();
    	}
    	catch (FileNotFoundException e) {
    	    System.out.println("Error: Unable to read file " + dictionaryFilename);
    	}
    	catch (IOException e) {
    	    e.printStackTrace();
    	}
        return animals;
    }
	
    /**
     * Opens and reads a wordSearchFileName file returning a block of characters.
     * Example:
     *  jwufyhsinf
     *  agzucneqpo
     *  majeurnfyt
     * 
     * If there is an error reading the file, such as the file cannot be found,
     * then the following message is shown: 
     *     Error: Unable to read file <wordSearchFileName>
     * with <wordSearchFileName> replaced with the parameter value.
     * 
     * @param wordSearchFileName The dictionary file to read.
     * @return A 2d-array of characters representing the block of letters.
     */
    public static char[][] readWordSearch(String wordSearchFileName) {
        ArrayList<String> charBlocks = new ArrayList<>();
        FileInputStream filestream;
        BufferedReader buffRead;
        
        try {
            filestream = new FileInputStream(wordSearchFileName);
            buffRead = new BufferedReader(new InputStreamReader(filestream));
            String block = buffRead.readLine();
            
            while (block != null) {
                charBlocks.add(block);
                block = buffRead.readLine();
            }
            buffRead.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Unable ro read file " + wordSearchFileName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        char[][] letterBlocks = new char[charBlocks.size()][];
        for (int i = 0; i < charBlocks.size(); ++i) {
            letterBlocks[i] = charBlocks.get(i).toCharArray();
        }
        
        return letterBlocks;
    }
    
    public static void testReadDictionary() {
        System.out.println("Starting readDictionary Test...");
        
        ArrayList<String> test1 = readDictionary("dictionary.txt");
        
        if (test1.get(0).contentEquals("Aardvark")) {
            System.out.println("readDictionary Test Passed!");
        }
        else {
            System.out.println("readDictionary Test Failed!");
        }
	}
	
    public static void testReadWordSearch() {
        System.out.println("Starting readWordSearch Test...");
        
        char[][] test1 = readWordSearch("WordSearch.txt");
        
        if (test1[0][0] == 'h') {
            System.out.println("readWordSearch Test Passed!");
        }
        else {
            System.out.println("readWordSearch Test Failed!");
        }
	}
	
    /**
     * Looks horizontally for the word in the word search, starting at
     * the given position. If the given position matches the first
     * character of word, look for the rest of the word characters
     * in the indexes to the right and left
     * 
     * @param word The word to look for.
     * @param wordSearch The grid of characters to search through
     * @param i The row to start searching at
     * @param j The column to start searching at
     * @return true if the word was found, false if not.
     */
    public static boolean searchHorizontal(String word, char[][] wordSearch, int i, int j) {
        try {

            for (int k = j; k < j + word.length(); ++k) {
                if (wordSearch[i][k] == word.charAt(k - j)) {
                    continue;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("i = " + i + " and/or j = " + j + " out of bounds.");
            return false;
        }
    }
    
    /**
     * Looks vertically for the word in the word search, starting at
     * the given position. If the given position matches the first character of
     * word, look for the rest of the word characters in the indexes above and below
     * 
     * @param word The word to look for.
     * @param wordSearch The grid of characters to search through
     * @param i The row to start searching at
     * @param j The column to start searching at
     * @return true if the word was found, false if not.
     */
    public static boolean searchVertical(String word, char[][] wordSearch, int i, int j) {
        try {
            
            for (int k = i; k < i + word.length(); ++k) {
                if (wordSearch[k][j] == word.charAt(k - i)) {
                    continue;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("i = " + i + " and/or j = " + j + " out of bounds.");
            return false;
        }
    }
    
    /**
     * Looks diagonally (up-left, up-right, down-left and down-right) for the word in the 
     * word search, starting at the given position. If the given position matches 
     * the first character of the word, look for the rest of the word
     * characters in the four diagonal directions.
     * 
     * @param word The word to look for.
     * @param wordSearch The grid of characters to search through
     * @param i The row to start searching at
     * @param j The column to start searching at
     * @return true if the word was found, false if not.
     */
    public static boolean searchDiagonal(String word, char[][] wordSearch, int i, int j) {
        String foundWord = "";
        char foundChar;
        
        try {

            for (int k = j; k < j + word.length(); ++k) {
                foundChar = wordSearch[i + k - j][k];
                foundWord += Character.toString(foundChar);
            }
            if (word.equals(foundWord)) {
                return true;
            }
        
            foundWord = "";
        
            for (int k = j; k < j + word.length(); ++k) {
                foundChar = wordSearch[i - k - j][k];
                foundWord += Character.toString(foundChar);
            }
            if (word.equals(foundWord)) {
                return true;
            }
        
            foundWord = "";
        
            for (int k = i; k < i + word.length(); ++k) {
                foundChar = wordSearch[k][j + k - i];
                foundWord += Character.toString(foundChar);
            }
            if (word.equals(foundWord)) {
                return true;
            }
        
            foundWord = "";
        
            for (int k = i; k < i + word.length(); ++k) {
                foundChar = wordSearch[k][j - k - i];
                foundWord += Character.toString(foundChar);
            }
            if (word.equals(foundWord)) {
                return true;
            }
        
            return false;
        }
        
        catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("i = " + i + " and/or j = " + j + " out of bounds.");
            return false;
        }
    }
    
    public static void testSearchHorizontal() {
        System.out.println("Starting searchHorizontal Test1 ...");
        
        if (searchHorizontal("lion", readWordSearch("SampleWordSearch.txt"), 1, 2)) {
            System.out.println("searchHorizontal Test1 Passed!");
        }
        else {
            System.out.println("searchHorizontal Test1 Failed!");
        }
    
        System.out.println("Starting searchHorizontal Test2 ...");
    
        if (searchHorizontal("slow", readWordSearch("SampleWordSearch.txt"), 4, 0)) {
            System.out.println("searchHorizontal Test2 Passed!");
        }
        else {
            System.out.println("searchHorizontal Test2 Failed!");
        }
    }

    public static void testSearchVertical() {
        System.out.println("Starting searchVertical Test1...");
        
        if (searchVertical("newt", readWordSearch("SampleWordSearch.txt"), 5, 4)) {
            System.out.println("searchVertical Test1 Passed!");
        }
        else {
            System.out.println("searchVertical Test1 Failed!");
        }
        
        System.out.println("Starting searchVertical Test2...");
        
        if (searchVertical("inn", readWordSearch("SampleWordSearch.txt"), 0, 5)) {
            System.out.println("searchVertical Test2 Passed!");
        }
        else {
            System.out.println("searchVertical Test2 Failed!");
        }
    }

    public static void testSearchDiagonal() {
        System.out.println("Starting searchDiagonal Test1...");
        
        if (searchDiagonal("prawn", readWordSearch("SampleWordSearch.txt"), 1, 0)) {
            System.out.println("searchDiagonal Test1 Passed!");
        }
        else {
            System.out.println("searchDiagonal Test1 Failed!");
        }
        
        System.out.println("Starting searchDiagonal Test2...");
        
        if (searchDiagonal("raw", readWordSearch("SampleWordSearch.txt"), 2, 1)) {
            System.out.println("searchDiagonal Test2 Passed!");
        }
        else {
            System.out.println("searchDiagonal Test2 Failed!");
        }
    }
    
    /**
     * Given the name of a dictionary file, and the name of the word search file, this
     * will solve the word search by finding all of the words listed in the dictionary file.
     * 
     * Duplicates should not be returned.
     * 
     * @param dictionaryName The dictionary file to read.
     * @param wordsearchName The file containing the word search.
     * @return  An ArrayList of words found.
     */
    public static ArrayList<String> searchForWords(String dictionaryFileName, String wordSearchFileName){
        ArrayList<String> words = readDictionary(dictionaryFileName);
        ArrayList<String> foundWords = new ArrayList<>();
        char[][] wordSearch = readWordSearch(wordSearchFileName);
        
        for (int i = 0; i < wordSearch.length; ++i) {
            for (int j = 0; j < wordSearch[i].length; ++j) {
                for (int k = 0; k < words.size(); ++k) {
                    if ( ( (searchHorizontal(words.get(k), wordSearch, i, j)) || (searchVertical(words.get(k), wordSearch, i, j)) || (searchDiagonal(words.get(k), wordSearch, i, j)) ) && (!foundWords.contains(words.get(k)))) {
                        foundWords.add(words.get(k));
                    }
                }
            }
        }
        return foundWords;
    }
    
    /** Given the name of a list, print the words in the list onto an output file
     *  named "wordsfound.txt"
     * 
     * @param foundWords an ArrayList of words found
     */
    public static void printWordsFound(ArrayList<String> foundWords){
        try {
            FileOutputStream fileStream = new FileOutputStream("wordsfound.txt");
            PrintWriter outFS = new PrintWriter(fileStream);
            for (int i = 0; i < foundWords.size(); ++i) {
                outFS.println(foundWords.get(i));
            }
            fileStream.close();
        }
            catch (FileNotFoundException e) {
                System.err.println("Unable to write to wordsfound.txt");
            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void testSearchForWords(String wordSearchFileName, String answersFileName){


        }

    public static void testPrintWordsFound(String wordsFoundFileName){
        /*int i = 0;
        ArrayList<String> foundWords = new ArrayList<>();
        File file = new File(wordsFoundFileName);
        Scanner scnr = null;
        try {
            scnr = new Scanner(file);
            while (scnr.hasNextLine()) {
                foundWords.add(i, scnr.nextLine());
                ++i;
            }
            Collections.sort(foundWords);
            for (String word : foundWords) {
                System.out.println(word);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (scnr != null) {
                scnr.close();
            }
        }*/
        ArrayList<String> words = searchForWords("dictionary.txt", wordsFoundFileName);
        for (String word : words) {
            System.out.println(word);
        
        }
    }
}


