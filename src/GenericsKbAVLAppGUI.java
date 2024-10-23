/**
 * This class represents a GUI application for loading a knowledge base from a text file
 * and performing searches on it using an AVL tree data structure.
 * It displays the results of the searches in a text area.
 * The user interface prompts for a dataset size and loads a knowledge base accordingly.
 * After loading the knowledge base, it searches terms from a query file and displays the results.
 * If a term is found, it prints the term along with its associated statement.
 * If a term is not found, it prints a message indicating that the term was not found.
 *
 * @author Bonani Tshwane
 * @since 12/03/2024
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GenericsKbAVLAppGUI extends JFrame {
    private JTextArea outputTextArea;
    private AVLTree avlBT;

    /**
     * Constructs the GenericsKbAVLAppGUI class.
     * It initializes the AVL tree and sets up the GUI components.
     * It then loads the knowledge base from a text file and performs searches.
     * If any errors occur during the process, appropriate error messages are displayed.
     */
    public GenericsKbAVLAppGUI() {
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

        // Load knowledge base and perform searches
        loadKnowledgeBaseAndSearch("src/GenericsKB.txt", "src/GenericsKB-queries.txt");
    }

    /**
     * Loads the knowledge base from a text file and performs searches.
     * @param kbFilename The filename of the knowledge base text file.
     * @param queryFilename The filename of the query text file.
     */
    private void loadKnowledgeBaseAndSearch(String kbFilename, String queryFilename) {
        try {
            // Load knowledge base
            File kbFile = new File(kbFilename);
            BufferedReader kbReader = new BufferedReader(new FileReader(kbFile));
            String line;
            while ((line = kbReader.readLine()) != null) {
                String[] parts = line.split("\\t");
                String term = parts[0];
                String statement = parts[1];
                double cfScore = Double.parseDouble(parts[2]);
                avlBT.insert(new Generic(term, statement, cfScore));
            }
            kbReader.close();

            outputTextArea.append("Knowledge base loaded successfully.\n");

            // Perform searches
            File queryFile = new File(queryFilename);
            BufferedReader queryReader = new BufferedReader(new FileReader(queryFile));
            outputTextArea.append("Searching terms in the knowledge base...\n");
            while ((line = queryReader.readLine()) != null) {
                String query = line.trim();
                Generic output = avlBT.find(query);
                if (output != null) {
                    outputTextArea.append("Term found:" + query + ": " + output + "\n");
                } else {
                    outputTextArea.append("Term not found: " + query + "\n");
                }
            }
            queryReader.close();
        } catch (IOException e) {
            outputTextArea.append("Error loading knowledge base or queries: " + e.getMessage() + "\n");
        }
    }

    /**
     * The main method to launch the application.
     * It creates an instance of GenericsKbAVLAppGUI and schedules it to be run on the event dispatch thread.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GenericsKbAVLAppGUI());
    }
}