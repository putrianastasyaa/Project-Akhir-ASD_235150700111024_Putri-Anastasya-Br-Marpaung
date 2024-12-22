import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class SortingApp {
    public static void main(String[] args) {
        JFrame appFrame = new JFrame("Sorting Application");
        appFrame.setSize(800, 700);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setLocationRelativeTo(null);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(new Color(255, 200, 150)); // Red mixed with Yellow
        appFrame.add(containerPanel);

        // Input Panel
        JPanel inputContainer = new JPanel(new GridLayout(2, 2, 5, 5));
        inputContainer.setBorder(BorderFactory.createTitledBorder("Input Data"));
        inputContainer.setBackground(new Color(255, 220, 170));
        containerPanel.add(inputContainer, BorderLayout.NORTH);

        JLabel labelDataCount = new JLabel("Number of Data:");
        JTextField inputDataCount = new JTextField();
        JButton buttonGenerate = new JButton("Generate");

        inputContainer.add(labelDataCount);
        inputContainer.add(inputDataCount);
        inputContainer.add(new JLabel());
        inputContainer.add(buttonGenerate);

        // Sorting and Data Panel
        JPanel sortingDataPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        sortingDataPanel.setBorder(BorderFactory.createTitledBorder("Data and Sorting"));
        sortingDataPanel.setBackground(new Color(255, 200, 150));
        containerPanel.add(sortingDataPanel, BorderLayout.CENTER);

        JTextArea initialDataArea = new JTextArea(3, 30);
        initialDataArea.setEditable(false);
        JScrollPane initialDataScroll = new JScrollPane(initialDataArea);
        initialDataScroll.setBorder(BorderFactory.createTitledBorder("Initial Data"));
        sortingDataPanel.add(initialDataScroll);

        JPanel sortOptionsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        sortOptionsPanel.setBackground(new Color(255, 200, 150));

        JRadioButton radioBubbleSort = new JRadioButton("Bubble Sort", true);
        JRadioButton radioSelectionSort = new JRadioButton("Selection Sort");
        ButtonGroup sortMethodsGroup = new ButtonGroup();
        sortMethodsGroup.add(radioBubbleSort);
        sortMethodsGroup.add(radioSelectionSort);

        sortOptionsPanel.add(radioBubbleSort);
        sortOptionsPanel.add(radioSelectionSort);

        JButton buttonSort = new JButton("Sort");
        sortOptionsPanel.add(buttonSort);
        sortingDataPanel.add(sortOptionsPanel);

        JTextArea sortedDataArea = new JTextArea(3, 30);
        sortedDataArea.setEditable(false);
        JScrollPane sortedDataScroll = new JScrollPane(sortedDataArea);
        sortedDataScroll.setBorder(BorderFactory.createTitledBorder("Sorted Data"));
        sortingDataPanel.add(sortedDataScroll);

        // Simulation Panel
        JPanel simulationContainer = new JPanel(new BorderLayout());
        simulationContainer.setBorder(BorderFactory.createTitledBorder("Sorting Simulation"));
        simulationContainer.setBackground(new Color(255, 220, 170));
        containerPanel.add(simulationContainer, BorderLayout.SOUTH);

        JTextArea simulationArea = new JTextArea(10, 30);
        simulationArea.setEditable(false);
        JScrollPane simulationScroll = new JScrollPane(simulationArea);
        simulationContainer.add(simulationScroll, BorderLayout.CENTER);

        JButton buttonReset = new JButton("Reset");
        simulationContainer.add(buttonReset, BorderLayout.SOUTH);

        // Generate Action
        buttonGenerate.addActionListener(e -> {
            try {
                int count = Integer.parseInt(inputDataCount.getText());
                if (count <= 0) throw new NumberFormatException();

                Random random = new Random();
                int[] data = new int[count];
                for (int i = 0; i < count; i++) {
                    data[i] = random.nextInt(100);
                }

                initialDataArea.setText(Arrays.toString(data));
                sortedDataArea.setText("");
                simulationArea.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(appFrame, "Please enter a valid number of data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sort Action
        buttonSort.addActionListener(e -> {
            String dataText = initialDataArea.getText();
            if (dataText.isEmpty()) {
                JOptionPane.showMessageDialog(appFrame, "Please generate data first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] stringArray = dataText.replace("[", "").replace("]", "").split(", ");
            int[] data = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();

            simulationArea.setText("");

            if (radioBubbleSort.isSelected()) {
                performBubbleSort(data, simulationArea);
            } else if (radioSelectionSort.isSelected()) {
                performSelectionSort(data, simulationArea);
            }

            sortedDataArea.setText(Arrays.toString(data));
        });

        // Reset Action
        buttonReset.addActionListener(e -> {
            inputDataCount.setText("");
            initialDataArea.setText("");
            sortedDataArea.setText("");
            simulationArea.setText("");
            radioBubbleSort.setSelected(true);
        });

        appFrame.setVisible(true);
    }

    // Bubble Sort Implementation
    private static void performBubbleSort(int[] array, JTextArea simulationArea) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                simulationArea.append(Arrays.toString(array) + "\n");
            }
        }
    }

    // Selection Sort Implementation
    private static void performSelectionSort(int[] array, JTextArea simulationArea) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
            simulationArea.append(Arrays.toString(array) + "\n");
        }
    }
}
