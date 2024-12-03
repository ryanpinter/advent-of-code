package com.jamf.pinter.adventofcode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ryan.pinter -  12/2/24.
 */
public class Day1Puzzle1 {

	public static void main(String[] args) throws Exception {
		// Load resource day1puzzle1.txt
		try(Stream<String> stringStream = Files.lines(Paths.get(Day1Puzzle1.class.getResource("/day1puzzle1.txt").toURI()))) {
			AtomicInteger result = new AtomicInteger();
			Map<Boolean, List<Integer>> lists = stringStream.flatMap(t -> Arrays.stream(t.split("\\s+"))).map(Integer::parseInt).collect(
					Collectors.partitioningBy(t -> result.incrementAndGet() % 2 == 0));
			List<Integer> first = lists.get(false);
			List<Integer> second = lists.get(true);
			first.sort(Integer::compareTo);
			second.sort(Integer::compareTo);
			int total = 0;
			for(int i = 0; i < first.size(); i++) {
				total += Math.abs(first.get(i) - second.get(i));
			}
			System.out.println("Solution 1: " + total);

			final Map<Integer, List<Integer>> collect = second.stream().collect(Collectors.groupingBy(Function.identity()));
			final Integer answer = first.stream().map(t -> t * collect.getOrDefault(t, List.of()).size()).reduce(Integer::sum).orElse(0);
			System.out.println("Solution 2: " + answer);
		}
	}
}
