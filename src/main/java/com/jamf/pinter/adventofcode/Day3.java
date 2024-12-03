package com.jamf.pinter.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ryan.pinter -  12/3/24.
 */
public class Day3 {
	static Pattern pattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))");
	static Pattern mulPattern = Pattern.compile("(\\d{1,3}),(\\d{1,3})");
	static Pattern doPattern = Pattern.compile("do(?!n't)");
	static Pattern dontPattern = Pattern.compile("don't");

	public static void main(String[] args) throws Exception {
		String bigString = Files.lines(Paths.get(Day3.class.getResource("/day3.txt").toURI())).collect(Collectors.joining());

		Map<Integer, String> groupByStartIndex = pattern.matcher(bigString).results().collect(Collectors.toMap(MatchResult::start, MatchResult::group));
		final int answer1 = groupByStartIndex.values().stream().mapToInt(doTheMultiply()).sum();
		System.out.println("Answer 1: " + answer1); //182619815 is correct

		// Remove `muls` that are closer to a "don't" than a "do"
		List<Integer> doIndexes = doPattern.matcher(bigString).results().map(MatchResult::start).toList();
		List<Integer> dontIndexes = dontPattern.matcher(bigString).results().map(MatchResult::start).toList();
		final int answer2 = groupByStartIndex.entrySet().stream()
				.filter(t -> {
					int thisEntryAtPos = t.getKey();
					int closetDo = getClosestTo(doIndexes, thisEntryAtPos);
					int closetDont = getClosestTo(dontIndexes, thisEntryAtPos);
					return closetDo > closetDont;
				}).map(Map.Entry::getValue).mapToInt(doTheMultiply()).sum();
		System.out.println("Answer 2: " + answer2); // 80747545 is correct
	}

	private static ToIntFunction<String> doTheMultiply() {
		return t -> {
			final Matcher matcher2 = mulPattern.matcher(t);
			matcher2.find();
			int a = Integer.parseInt(matcher2.group(1));
			int b = Integer.parseInt(matcher2.group(2));
			return a * b;
		};
	}

	/**
	 * Finds the closest smaller number in the list to the given number
	 */
	public static int getClosestTo(List<Integer> list, int num) {
		int closest = Integer.MIN_VALUE;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) < num) {
				closest = list.get(i);
			}
		}
		return closest;
	}
}
