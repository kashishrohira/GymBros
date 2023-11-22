package ui;

import model.Exercise;
import model.GymBros;
import model.User;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.login.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GymBrosApp extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1200;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;

    // register/login commands
    public static final String LOGIN_COMMAND = "/login";
    public static final String REGISTER_COMMAND = "/register";
    public static final String LOGOUT_COMMAND = "/logout";
    public static final String SAVE_ACCOUNT = "/save";
    public static final String LOAD_ACCOUNT = "/load";
    // fitness-tracking commands
    public static final String VIEW_WORKOUT_LOG_COMMAND = "/viewlog";
    public static final String ADD_EXERCISE_COMMAND = "/exercise";
    public static final String TRACKER_COMMAND = "/track";
    public static final String HOME_COMMAND = "/home";

    // profile commands
    public static final String EDIT_PROFILE_COMMAND = "/edit";
    public static final String FOLLOW_USER_COMMAND = "/follow";
    public static final String SELF_PROFILE_COMMAND = "me";

    // other
    public static final String EXIT_COMMAND = "/exit";


    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;


    private Boolean loggedIn;
    private User currentlyLoggedInUser;
    private Scanner input;
    private HashMap<String, User> usernameUser;
    private HashMap<String, String> usernamePassword;
    private GymBros gymBros;
    private JsonWriter writer;
    private JsonReader reader;
    private String jsonUser = "./data/user1.json";

    // GUI
    private JLabel logoLabel;
    private JButton loadButton;
    private JButton newButton;
    private JFrame popUpFrame;
    private JLabel tapLabel;
    private JTextArea outputTextArea;

    // EFFECTS: creates a new instance of the GymBros application, with loggedIn being false,
    //          the usernameUser as empty hashmap, instantiates the Scanner to take in user input
    //          and runs the GymBros application
    public GymBrosApp() throws FileNotFoundException {
        loggedIn = false;
        usernameUser = new HashMap<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        currentlyLoggedInUser = null;
        gymBros = new GymBros();
        writer = new JsonWriter(jsonUser);
        reader = new JsonReader(jsonUser);
        runGymBros();
    }


    // EFFECTS: loads the first window
    private void runGymBros() {
        showPopUpLoad();
    }

    // MODIFIES: this
    // EFFECTS: shows pop up frame with load from file and new instance buttons
    public void showPopUpLoad() {
        initButtons();
        addActionListenerLoadButton();

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewInstance();
            }
        });

        popUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the pop-up window
        popUpFrame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());  // FlowLayout for logo and load button
        logoLabel = new JLabel();
        contentPanel.add(logoLabel, BorderLayout.CENTER);

        popUpFrame.add(contentPanel, BorderLayout.CENTER);

        initTapLabel();

        popUpFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayLoginMenu();

            }
        });

        setPopUpLayout();
    }

    // EFFECTS: initializes the JFrame and buttons
    public void initButtons() {
        popUpFrame = new JFrame("Pop-up Window");
        loadButton = new JButton("Load from file");
        newButton = new JButton("Create new GymBros");
    }

    // MODIFIES: this
    // EFFECTS: adds action listener to load button
    public void addActionListenerLoadButton() {
        // Add action listener for the Load button in the pop-up window
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadButton.setVisible(false);
                newButton.setVisible(false);
                showLogoDialog();
                loadAccount();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes the tap label and adds it to the frame
    public void initTapLabel() {
        // Initialize the tapLabel with "Tap anywhere to begin" text
        tapLabel = new JLabel("Tap anywhere to begin");
        tapLabel.setForeground(Color.WHITE);
        tapLabel.setBackground(Color.RED);
        tapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tapLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        tapLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tapLabel.setOpaque(true); // Make the label transparent
        tapLabel.setVisible(false);  // Initially set to invisible
        popUpFrame.add(tapLabel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: sets the layout of current page to pop up page
    public void setPopUpLayout() {
        JPanel loadButtonPanel = new JPanel();
        loadButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        loadButtonPanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));
        loadButtonPanel.add(loadButton);
        popUpFrame.add(loadButtonPanel, BorderLayout.NORTH);

        // adding new button
        JPanel newButtonPanel = new JPanel();
        newButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        newButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));
        newButtonPanel.add(newButton);
        popUpFrame.add(newButtonPanel, BorderLayout.SOUTH);

        popUpFrame.setSize(500, 500);
        popUpFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: shows the logo image
    public void showLogoDialog() {
        ImageIcon icon = new ImageIcon("gymbros-logo.jpeg");
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(popUpFrame.getWidth(), popUpFrame.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        logoLabel.setIcon(icon);

        // disable load button
        loadButton.setEnabled(false);

        // remove load button
        Container contentPane = popUpFrame.getContentPane();
        contentPane.remove(loadButton);
        contentPane.remove(newButton);
        popUpFrame.setLayout(null);

        // creating a new content panel with flow layout
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.add(logoLabel);
        contentPanel.add(tapLabel);

        popUpFrame.setContentPane(contentPanel);

        // show tap label
        tapLabel.setVisible(true);

        // repaint frame
        popUpFrame.revalidate();
        popUpFrame.repaint();

    }

    // MODIFIES: this
    // EFFECTS: creates a new instance of the app and shows logo dialog
    public void createNewInstance() {
        gymBros = new GymBros();
        loadButton.setVisible(false);
        newButton.setVisible(false);
        showLogoDialog();
    }

    // MODIFIES: this
    // EFFECTS: displays registration/login menu options to the user
    public void displayLoginMenu() {
        LoginPage loginPage = new LoginPage(gymBros, this);
        popUpFrame.setContentPane(loginPage);  // Set the content pane to the new loginPage
        popUpFrame.revalidate();
        popUpFrame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {

        try {
            gymBros = reader.read();
        } catch (IOException io) {
            outputTextArea.append("Unable to read from file: " + jsonUser + "\n");
        }
    }

    // EFFECTS: saves the account to file
    public void saveAccount() {
        try {
            writer.open();
            writer.writeGymBros(gymBros);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonUser);
        }
    }



}
