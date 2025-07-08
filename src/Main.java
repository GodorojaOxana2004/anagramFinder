import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<String>> anagrams = findAnagrams("sample.txt");
        printAnagrams(anagrams);
    }

    // читаем файл и находит группы анаграмм(похож слов)
    private static List<List<String>> findAnagrams(String fileName) {
        List<List<String>> result = new ArrayList<>();
        try {
            List<String> lines = readFile(fileName);
            List<String> sortedWords = sortWords(lines);
            groupAnagrams(lines, sortedWords, result);
            sortResult(result);
        } catch (IOException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
        return result;
    }

    // строки читаем
    private static List<String> readFile(String fileName) throws IOException {
        Path file = Path.of(fileName);
        return Files.readAllLines(file);
    }

    // делаем сортированный список
    private static List<String> sortWords(List<String> words) {
        List<String> sortedWords = new ArrayList<>();
        for (String word : words) {
            char[] letters = word.toCharArray();
            Arrays.sort(letters);
            sortedWords.add(new String(letters));
        }
        return sortedWords;
    }

    // группируем слова в соответсвии с анаграммами
    private static void groupAnagrams(List<String> originalWords, List<String> sortedWords, List<List<String>> result) {
        for (int i = 0; i < originalWords.size(); i++) {
            List<String> anagramGroup = new ArrayList<>();
            anagramGroup.add(originalWords.get(i));
            for (int j = i + 1; j < originalWords.size(); j++) {
                if (sortedWords.get(i).equals(sortedWords.get(j))) {
                    anagramGroup.add(originalWords.get(j));
                    i++;
                }
            }
            anagramGroup.sort(String::compareTo);
            result.add(anagramGroup);
        }
    }

    // сортируем по первому слову
    private static void sortResult(List<List<String>> result) {
        result.sort(Comparator.comparing(list -> list.get(0)));
    }

    // выводим
    private static void printAnagrams(List<List<String>> anagrams) {
        for (List<String> group : anagrams) {
            for (String word : group) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }
}