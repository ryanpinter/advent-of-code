package com.jamf.pinter.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ryan.pinter -  12/4/24.
 */
public class Day4 {
	public static void main(String[] args) throws Exception {
		String[][] letterArray = Files.lines(Paths.get(Day3.class.getResource("/day4.txt").toURI()))
				.map(t -> t.split(""))
				.toArray(String[][]::new);
		int count = 0;
		for(int y = 0; y < letterArray.length; y++) {
			for(int x = 0; x < letterArray[y].length; x++) {
				String horiForward = get4Chars(letterArray[y], x);
				String vertDown = getVerticalChars(letterArray, y, x);
				String diagDownRight = getDiagonalRightChars(letterArray, y, x);
				String diagDownLeft = getDiagonalLeftChars(letterArray, y, x);

				count += (int) Stream.of(horiForward, reverse(horiForward),
								vertDown, reverse(vertDown),
								diagDownRight, reverse(diagDownRight),
								diagDownLeft, reverse(diagDownLeft)
						).filter(t -> t.equals("XMAS"))
						.count();
			}
		}
		System.out.println("Answer 1: " + count); //2536

		int countB = 0;
		for(int y = 1; y < letterArray.length - 1; y++) {
			for(int x = 1; x < letterArray[y].length - 1; x++) {
				String center = letterArray[y][x];
				String diagDownRight = letterArray[y - 1][x - 1] + center + letterArray[y + 1][x + 1];
				String diagUpRight = letterArray[y + 1][x - 1] + center + letterArray[y - 1][x + 1];

				if((diagDownRight.equals("MAS") || reverse(diagDownRight).equals("MAS")) &&
						(diagUpRight.equals("MAS") || reverse(diagUpRight).equals("MAS"))) {
					countB++;
				}
			}
		}
		System.out.println("Answer 2: " + countB); //1875
	}

	public static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	public static String getDiagonalRightChars(String[][] letterArray, int y, int x) {
		if(y + 3 >= letterArray.length || y < 0 || x + 3 >= letterArray[y].length || x < 0) {
			return "";
		}
		return letterArray[y][x] + letterArray[y + 1][x + 1] + letterArray[y + 2][x + 2] + letterArray[y + 3][x + 3];
	}

	public static String getDiagonalLeftChars(String[][] letterArray, int y, int x) {
		if(y + 3 >= letterArray.length || y < 0 || x - 3 < 0 || x >= letterArray[y].length) {
			return "";
		}
		return letterArray[y][x] + letterArray[y + 1][x - 1] + letterArray[y + 2][x - 2] + letterArray[y + 3][x - 3];
	}

	public static String getVerticalChars(String[][] letterArray, int y, int x) {
		if(y + 3 >= letterArray.length || y < 0) {
			return "";
		}
		return letterArray[y][x] + letterArray[y + 1][x] + letterArray[y + 2][x] + letterArray[y + 3][x];
	}

	public static String get4Chars(String[] letterArray, int pos) {
//		if(pos < 0 || pos + 4 > letterArray.length) {
//			return "";
//		}
		return Arrays.stream(letterArray).skip(Math.max(pos, 0)).limit(pos + 4 > letterArray.length ? 0 : 4).collect(Collectors.joining());
	}
}
