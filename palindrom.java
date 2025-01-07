package DevReg;
/*
 Given a string, return all the palindromes that can be obtained by rearranging its characters.

Example

For charactersSet = "ababb", the output should be
solution(charactersSet) = ["abbba", "babab"].

Input/Output

[execution time limit] 3 seconds (java)

[memory limit] 1 GB

[input] string charactersSet

A non-empty string of lowercase letters.

Guaranteed constraints:
1 ≤ charactersSet.length ≤ 15.

[output] array.string

Sorted array of palindromes.
 */

import java.util.*;

public class palindrom {

    public static List<String> solution(String charactersSet) {
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : charactersSet.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // Check if a palindrome is possible
        int oddCount = 0;
        char oddChar = 0;
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                oddCount++;
                oddChar = entry.getKey();
            }
        }

        if (oddCount > 1) {
            return new ArrayList<>(); // No palindromes possible
        }

        // Prepare half string (excluding the odd character if it exists)
        StringBuilder halfBuilder = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            for (int i = 0; i < entry.getValue() / 2; i++) {
                halfBuilder.append(entry.getKey());
            }
        }
        String half = halfBuilder.toString();

        // Generate all permutations of the half string
        Set<String> permutations = new HashSet<>();
        generatePermutations(half.toCharArray(), 0, permutations);

        // Construct full palindromes from permutations
        List<String> palindromes = new ArrayList<>();
        for (String perm : permutations) {
            StringBuilder palindrome = new StringBuilder(perm);
            if (oddCount == 1) {
                palindrome.append(oddChar);
            }
            palindrome.append(new StringBuilder(perm).reverse());
            palindromes.add(palindrome.toString());
        }

        Collections.sort(palindromes);
        return palindromes;
    }

    private static void generatePermutations(char[] chars, int index, Set<String> permutations) {
        if (index == chars.length) {
            permutations.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            generatePermutations(chars, index + 1, permutations);
            swap(chars, index, i); // backtrack
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    public static void main(String[] args) {
        String charactersSet = "ababb";
        System.out.println(solution(charactersSet)); // Output: [abbba, babab]
    }
}
