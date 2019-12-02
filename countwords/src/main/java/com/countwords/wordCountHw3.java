package com.countwords;

import com.kennycason.kumo.*;

import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;

import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import java.io.*;

import org.apache.log4j.BasicConfigurator;

/* Christian Vargas-Polo EmpId: 23140563
 * HomeWork #3 Frequency Chart
 * Credit: KennyCason // jfree chart 
 * 
░░░░░░░░░▄██████████▄▄░░░░░░░░
░░░░░░▄█████████████████▄░░░░░
░░░░░██▀▀▀▀▀▀▀▀▀▀▀████████░░░░
░░░░██░░░░░░░░░░░░░░███████░░░
░░░██░░░░░░░░░░░░░░░████████░░
░░░█▀░░░░░░░░░░░░░░░▀███████░░
░░░█▄▄██▄░░░▀█████▄░░▀██████░░
░░░█▀███▄▀░░░▄██▄▄█▀░░░█████▄░
░░░█░░▀▀█░░░░░▀▀░░░▀░░░██░░▀▄█
░░░█░░░█░░░▄░░░░░░░░░░░░░██░██
░░░█░░█▄▄▄▄█▄▀▄░░░░░░░░░▄▄█▄█░
░░░█░░█▄▄▄▄▄▄░▀▄░░░░░░░░▄░▀█░░
░░░█░█▄████▀██▄▀░░░░░░░█░▀▀░░░
░░░░██▀░▄▄▄▄░░░▄▀░░░░▄▀█░░░░░░
░░░░░█▄▀░░░░▀█▀█▀░▄▄▀░▄▀░░░░░░
░░░░░▀▄░░░░░░░░▄▄▀░░░░█░░░░░░░
░░░░░▄██▀▀▀▀▀▀▀░░░░░░░█▄░░░░░░
░░▄▄▀░░░▀▄░░░░░░░░░░▄▀░▀▀▄░░░░
▄▀▀░░░░░░░█▄░░░░░░▄▀░░░░░░█▄░░
█░░░░░░░░░░░░░░░░░░░░░░░░░░▀█▄
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
█▄░░█ █▀▀█ ▀▀█▀▀░░█▀▀█ █▀▀█ █▀▀▄
█░█░█ █░░█ ░░█░░░░█▀▀▄ █▄▄█ █░░█
█░░▀█ █▄▄█ ░░█░░░░█▄▄█ █░░█ █▄▄▀
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░

 */

public class wordCountHw3 {

	public static void main(String[] args) {
		//initialize log4j
		BasicConfigurator.configure();
		// log.info("This is Logger Info");
		// read words method
		readWords();

	}

	public static void readWords() {
		try {
			/* Map<key:String,value:Integer> => HashMap*/
			Map<String, Integer> words = new HashMap<String, Integer>();
			
			// File to read from
			File myFile = new File("lyrics.txt");
			// File to print frequencyList into
			PrintWriter out = new PrintWriter("frequencyList.txt");

			// scanner object to read input
			Scanner input = new Scanner(myFile);

			// while input has another read value
			while (input.hasNextLine()) {
				// line read in is stored into temp var
				String temp = input.nextLine();
				// temp split into a String array at delim
				String[] str = temp.toLowerCase().split
							("[\\s,()!1234567890:\\[\\]*+/\". =-]");
				// for loop cycles 0 -> array length
				for (int i = 0; i < str.length; i++) {

					// if str[i] = null do nothing
					if (str[i].isEmpty()) {
					}

					// else if the word is not contained
					// add it to the has map and give it a value 1
					else if (!words.containsKey(str[i])) {
						words.put(str[i], 1);
						// else if the word exist(no doubles)
						//increment value +1
					} else {
						words.put(str[i], words.get(str[i]) + 1);
					}
				}
			}
			

			//close input
			input.close();
			//convert keys into an arrayList
			ArrayList<String> keyList = 
							new ArrayList<String>((words.keySet()));
			
			// sort alphabetically
			Collections.sort(keyList);

			// for loop cycles array
			for (String lyric : keyList) {
				
				// calls hashmap get() returns count + word
				out.println(words.get(lyric) + ": " + lyric);
				out.flush();

			}
			// total amount of words in the script
			out.println("Total words = " + words.size());

			out.close();
			
			
			

			// jchart frequency analyzer
			final FrequencyAnalyzer frequencyAnalyzer 
									= new FrequencyAnalyzer();

			// returns a total of 200 words max// recommend more for larger images
			frequencyAnalyzer.setWordFrequenciesToReturn(200);
			// min length of each word is 0 characters
			frequencyAnalyzer.setMinWordLength(0);

			// creates a list of word frequencies pulled from txt file
			final List<WordFrequency> wordFrequencies = 
							frequencyAnalyzer.load("lyrics.txt");

			// give the dimensions of the image to be drawn LxW
			final Dimension dimension = new Dimension(600, 800);
			final WordCloud wordCloud = new WordCloud
										(dimension, CollisionMode.PIXEL_PERFECT);

			// padding space between each word
			wordCloud.setPadding(2);
			
			// reads the image to be scaled and rendered with words
			//(MUST BE PNG//RENAME TO WHALE TO MAKE YOUR LIFE EASY)//
			wordCloud.setBackground(new PixelBoundryBackground("whale.png"));

			// pick a color for your words (google more colors to get fancyz)
			wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1),
					new Color(0x408DF1), new Color(0x40AAF1),
					new Color(0x40C5F1), new Color(0x40B3F1), new Color(0xFFFFFF)));

			//Font size for the letters(recc smaller for larger images)
			wordCloud.setFontScalar(new LinearFontScalar(9, 11));
			
			// builds array into word cloud
			wordCloud.build(wordFrequencies);
			
			//writes it out to a file labled test.png
			wordCloud.writeToFile("test.png");
			
	
		} catch (Exception e) {

			e.printStackTrace();
		}

	}		
		
	
	
}
