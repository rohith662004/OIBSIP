import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class DifferentReservationSystem extends JFrame {
    private JPanel loginPanel, passengerPanel, reservationPanel, cancellationPanel, managerPanel;
    private CardLayout cardLayout;
    private JTextField usernameField, passwordField, trainNumberField, pnrField, nameField, ageField, fromField, toField;
    private JComboBox<String> classTypeComboBox, genderComboBox;
    private Map<String, String> trainMap;
    private Random random;

    public DifferentReservationSystem() {
        super("Online Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        trainMap = loadTrainData();
        random = new Random();

        createLoginPanel();
        createPassengerPanel();
        createReservationPanel();
        createCancellationPanel();
        createManagerPanel();

        add(loginPanel, "Login");
        add(passengerPanel, "Passenger");
        add(reservationPanel, "Reservation");
        add(cancellationPanel, "Cancellation");
        add(managerPanel, "Manager");
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (username.equals("manager") && password.equals("password")) {
                    cardLayout.show(getContentPane(), "Manager");
                } else if (username.equals("passenger") && password.equals("password")) {
                    cardLayout.show(getContentPane(), "Passenger");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
    }

    private void createPassengerPanel() {
        passengerPanel = new JPanel(new GridLayout(2, 1));
        JButton reservationButton = new JButton("Reservation");
        JButton cancellationButton = new JButton("Cancellation");

        passengerPanel.add(reservationButton);
        passengerPanel.add(cancellationButton);

        reservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "Reservation");
            }
        });

        cancellationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(getContentPane(), "Cancellation");
            }
        });
    }

    private void createReservationPanel() {
        reservationPanel = new JPanel(new GridLayout(11, 2)); // Increased the row count to accommodate PNR
        JLabel trainNumberLabel = new JLabel("Train Number:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel genderLabel = new JLabel("Gender:");
        JLabel classTypeLabel = new JLabel("Class Type:");
        JLabel dateLabel = new JLabel("Date of Journey:");
        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");
        JLabel pnrLabel = new JLabel("PNR:");

        trainNumberField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        classTypeComboBox = new JComboBox<>(new String[]{"First Class", "Second Class", "Class 1 Tier", "Class 2 Tier", "Sleeper", "General"});
        JTextField dateField = new JTextField();
        fromField = new JTextField();
        toField = new JTextField();
        pnrField = new JTextField(); // Added field for displaying PNR
        pnrField.setEditable(false); // Ensure PNR is not editable
        JButton reserveButton = new JButton("Reserve");

        reservationPanel.add(nameLabel);
        reservationPanel.add(nameField);
        reservationPanel.add(ageLabel);
        reservationPanel.add(ageField);
        reservationPanel.add(genderLabel);
        reservationPanel.add(genderComboBox);
        reservationPanel.add(trainNumberLabel);
        reservationPanel.add(trainNumberField);
        reservationPanel.add(classTypeLabel);
        reservationPanel.add(classTypeComboBox);
        reservationPanel.add(dateLabel);
        reservationPanel.add(dateField);
        reservationPanel.add(fromLabel);
        reservationPanel.add(fromField);
        reservationPanel.add(toLabel);
        reservationPanel.add(toField);
        reservationPanel.add(pnrLabel);
        reservationPanel.add(pnrField); // Add PNR field
        reservationPanel.add(reserveButton);

        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String trainNumber = trainNumberField.getText();
                String trainName = getTrainName(trainNumber);
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String gender = genderComboBox.getSelectedItem().toString();
                String classType = classTypeComboBox.getSelectedItem().toString();
                String date = dateField.getText();
                String from = fromField.getText();
                String to = toField.getText();

                int pnr = random.nextInt(900000) + 100000; // Generating PNR
                pnrField.setText(String.valueOf(pnr)); // Displaying generated PNR

                StringBuilder reservationDetails = new StringBuilder();

                reservationDetails.append("Name: ").append(name).append("\n");
                reservationDetails.append("Age: ").append(age).append("\n");
                reservationDetails.append("Gender: ").append(gender).append("\n");
                reservationDetails.append("Train Number: ").append(trainNumber).append("\n");
                reservationDetails.append("Train Name: ").append(trainName).append("\n");
                reservationDetails.append("Class Type: ").append(classType).append("\n");
                reservationDetails.append("Date of Journey: ").append(date).append("\n");
                reservationDetails.append("From: ").append(from).append("\n");
                reservationDetails.append("To: ").append(to).append("\n");
                reservationDetails.append("PNR: ").append(pnr).append("\n"); // Include PNR in the details

                JOptionPane.showMessageDialog(null, "Reservation Successful!");
                JFrame frame = new JFrame("Reservation Details");
                JTextArea reservationDetailsArea = new JTextArea();
                reservationDetailsArea.setText(reservationDetails.toString());
                frame.add(new JScrollPane(reservationDetailsArea));
                frame.setSize(400, 400);
                frame.setVisible(true);
            }
        });
    }

    private String getTrainName(String trainNumber) {
        return trainMap.get(trainNumber);
    }

    private void createCancellationPanel() {
        cancellationPanel = new JPanel(new FlowLayout());
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrField = new JTextField(10);

        cancellationPanel.add(pnrLabel);
        cancellationPanel.add(pnrField);
    }

    private void createManagerPanel() {
        managerPanel = new JPanel(new GridLayout(4, 2));
        JLabel trainNumberLabel = new JLabel("Train Number:");
        JLabel trainNameLabel = new JLabel("Train Name:");
        trainNumberField = new JTextField();
        JTextField trainNameField = new JTextField(); // Added trainNameField
        /* Add any other components needed */
        managerPanel.add(trainNumberLabel);
        managerPanel.add(trainNumberField);
        managerPanel.add(trainNameLabel);
        managerPanel.add(trainNameField);
        /* Add any other components needed */
    }

    private Map<String, String> loadTrainData() {
        Map<String, String> trainMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("trains.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    trainMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainMap;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DifferentReservationSystem().setVisible(true));
    }
}
