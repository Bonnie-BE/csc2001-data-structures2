import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class AVLGUI extends JFrame {
    private JTextArea outputTextArea;
    private AVLTree avlBT;

    public AVLGUI() {
        super("GenericsKbAVLApp GUI");

        // Initialize AVL tree
        avlBT = new AVLTree();

        // Create components
        outputTextArea = new JTextArea(20, 50);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // Add text area to content pane
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(scrollPane, BorderLayout.CENTER);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Run experiments and output results
        runExperiments();
    }

    private void runExperiments() {
        // Define dataset sizes for experiments
        int[] datasetSizes = {3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 50000};

        // Define number of iterations
        int numIterations = 3; // For simplicity, using 3 iterations for each dataset size

        // Arrays to store operation counts for different dataset sizes
        int[][] insertCounts = new int[datasetSizes.length][numIterations];
        int[][] searchCounts = new int[datasetSizes.length][numIterations];

        // Experiment loop
        for (int sizeIndex = 0; sizeIndex < datasetSizes.length; sizeIndex++) {
            int size = datasetSizes[sizeIndex];

            // Run experiments for this dataset size
            for (int i = 0; i < numIterations; i++) {
                // Load random subset of data and insert into AVL tree
                loadDataSubset("GenericsKB.txt", size);

                // Reset operation counts
                //resetOpCounts();

                // Load query items from file and perform search operations
                try {
                    File queryFile = new File("GenericsKB-queries.txt");
                    Scanner queryScanner = new Scanner(queryFile);
                    while (queryScanner.hasNextLine()) {
                        String queryTerm = queryScanner.nextLine().trim();
                        avlBT.find(queryTerm); // Perform search operation
                    }
                    queryScanner.close();
                } catch (FileNotFoundException e) {
                    outputTextArea.append("Query file not found: GenericsKB-queries.txt\n");
                }

                // Store operation counts
                insertCounts[sizeIndex][i] = avlBT.getOpCountInsert();
                searchCounts[sizeIndex][i] = avlBT.getOpCountSearch();
            }
        }

        // Calculate min, max, and average operation counts
        int[] minInsertCounts = new int[datasetSizes.length];
        int[] maxInsertCounts = new int[datasetSizes.length];
        double[] avgInsertCounts = new double[datasetSizes.length];
        int[] minSearchCounts = new int[datasetSizes.length];
        int[] maxSearchCounts = new int[datasetSizes.length];
        double[] avgSearchCounts = new double[datasetSizes.length];

        for (int i = 0; i < datasetSizes.length; i++) {
            minInsertCounts[i] = Arrays.stream(insertCounts[i]).min().getAsInt();
            maxInsertCounts[i] = Arrays.stream(insertCounts[i]).max().getAsInt();
            avgInsertCounts[i] = Arrays.stream(insertCounts[i]).average().orElse(0);
            minSearchCounts[i] = Arrays.stream(searchCounts[i]).min().getAsInt();
            maxSearchCounts[i] = Arrays.stream(searchCounts[i]).max().getAsInt();
            avgSearchCounts[i] = Arrays.stream(searchCounts[i]).average().orElse(0);
        }

        // Output results to text area
        outputTextArea.append("\nInsert Operation Counts:\n");
        for (int i = 0; i < datasetSizes.length; i++) {
            outputTextArea.append("Dataset Size: " + datasetSizes[i] + "\n");
            outputTextArea.append("Minimum Insert Operation Count: " + minInsertCounts[i] + "\n");
            outputTextArea.append("Maximum Insert Operation Count: " + maxInsertCounts[i] + "\n");
            outputTextArea.append("Average Insert Operation Count: " + avgInsertCounts[i] + "\n");
        }
        outputTextArea.append("\nSearch Operation Counts:\n");
        for (int i = 0; i < datasetSizes.length; i++) {
            outputTextArea.append("Dataset Size: " + datasetSizes[i] + "\n");
            outputTextArea.append("Minimum Search Operation Count: " + minSearchCounts[i] + "\n");
            outputTextArea.append("Maximum Search Operation Count: " + maxSearchCounts[i] + "\n");
            outputTextArea.append("Average Search Operation Count: " + avgSearchCounts[i] + "\n");
        }
    }

    public void loadDataSubset(String filename, int subsetSize) {
        Random random = new Random();
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Read all lines from the file
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();

            // Shuffle the lines to randomize the dataset
            Collections.shuffle(lines, random);

            // Truncate the list to the subset size
            lines = lines.subList(0, Math.min(subsetSize, lines.size()));

            // Clear the AVL tree before loading the subset
            //avlBT.clear();

            // Insert each line from the subset into the AVL tree
            for (String line : lines) {
                String[] parts = line.split("\t");
                String term = parts[0];
                String sentence = parts[1];
                String cscore = parts[2];
                avlBT.insert(new Generic(term, sentence, Double.parseDouble(cscore)));
            }
        } catch (FileNotFoundException e) {
            outputTextArea.append("File not found: " + filename + "\n");
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AVLGUI::new);
    }
}
