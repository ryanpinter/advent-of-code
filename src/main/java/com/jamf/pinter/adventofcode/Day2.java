package com.jamf.pinter.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by ryan.pinter -  12/2/24.
 */
public class Day2 {
	public static void main(String[] args) throws Exception {
		try(Stream<String> stringStream = Files.lines(Paths.get(Day1Puzzle1.class.getResource("/day2.txt").toURI()))) {
			final List<List<Integer>> streamStream = stringStream.map(t ->
					Arrays.stream(t.split("\\s+"))
							.map(Integer::parseInt)
							.toList()
			).toList();

			streamStream.stream().map(t -> check(t) ? 1 : 0).reduce(Integer::sum).ifPresent(t -> System.out.printf("Answer 1: %d%n", t));
			streamStream.stream().map(t -> checkDampener(t) ? 1 : 0).reduce(Integer::sum).ifPresent(t -> System.out.printf("Answer 2: %d%n", t));
		}
	}

	public static boolean checkDampener(List<Integer> nums) {
		if (check(nums)) {
			return true;
		}

		List<List<Integer>> damps = new ArrayList<>();
		for(int i = 0; i < nums.size(); i++) {
			List<Integer> copy = new ArrayList<>(nums);
			copy.remove(i);
			damps.add(copy);
		}

		for(List<Integer> damp : damps) {
			if(check(damp)) {
				return true;
			}
		}
		return false;
	}

	public static boolean check(List<Integer> nums) {
		boolean isAscending = true;
		boolean success = true;

		for(int i = 0; i < nums.size() - 1; i++) {
			int x = nums.get(i);
			int y = nums.get(i + 1);
			int diff = x - y;
			isAscending = i == 0 ? diff < 0 : isAscending;
			if(diff == 0 || Math.abs(diff) > 3 || isAscending == (diff > 0)) {
				success = false;
				break;
			}
		}

		//System.out.printf("Input: %s -> %s%n", nums, success ? "Success" : "Fail");
		return success;
	}
}
