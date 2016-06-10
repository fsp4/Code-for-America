import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Fabian Pisztora
 */

public class CodeForAmerica {
    private final ArrayList<String> categoryOfViolations = new ArrayList<String>();
    private final ArrayList<Integer> numOfViolations = new ArrayList<Integer>();
    private final ArrayList<Long> earliestViolation = new ArrayList<Long>();
    private final ArrayList<Long> latestViolation = new ArrayList<Long>();
    private final ArrayList<String> earliestViolationFormatted = new ArrayList<String>();
    private final ArrayList<String> latestViolationFormatted = new ArrayList<String>();
    
    public CodeForAmerica(String filename) throws IOException {
        ArrayList<String> lines = readFile(filename);
        parseLines(lines);
        printResults();
    }

    private void printResults() {
        System.out.printf("\n%-20s %10s %19s %19s", "Category", "Total", "Earliest", "Latest");
        for (int i = 0; i < categoryOfViolations.size(); i++) {
            System.out.printf("\n%-20.20s %10d %-19.19s %-19.19s", categoryOfViolations.get(i), numOfViolations.get(i), earliestViolationFormatted.get(i), latestViolationFormatted.get(i));
        }
    }
    
    private void parseLines(ArrayList<String> lines) {
        String[] splitLine;
        String violationCategory;
        String dateFormatted;
        long date;
        
        for (int i = 1; i < lines.size(); i++) {
            splitLine = lines.get(i).split(",");
            violationCategory = splitLine[2];
            date = Long.parseLong(splitLine[3].replaceAll("[^0-9]",""));
            dateFormatted = splitLine[3];
            
            if (categoryOfViolations.contains(violationCategory)) {
                int index = categoryOfViolations.indexOf(violationCategory);
                int number = numOfViolations.get(index) + 1;
                numOfViolations.set(index, number);
                
                if (date < earliestViolation.get(index)) {
                    earliestViolation.set(index, date);
                    earliestViolationFormatted.set(index, dateFormatted);
                }
                else if (date > latestViolation.get(index)) {
                    latestViolation.set(index, date);
                    latestViolationFormatted.set(index, dateFormatted);
                }
            }
            else {
                categoryOfViolations.add(violationCategory);
                numOfViolations.add(1);
                earliestViolation.add(date);
                latestViolation.add(date);
                earliestViolationFormatted.add(dateFormatted);
                latestViolationFormatted.add(dateFormatted);
            }
        }
    }
    
    private ArrayList<String> readFile(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> lines = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }
    
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
        System.out.print("Input file: ");
        String filename = in.next();
        
        CodeForAmerica cfa = new CodeForAmerica(filename);
    }
}
