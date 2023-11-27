package ui.login;

import model.GymBros;
import ui.GymBrosApp;
import ui.HomeFeed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private GymBros gymBros;
    private GymBrosApp gymBrosApp;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;

    // REQUIRES: gymBros is not null
    // EFFECTS: constructs a loginPage with given gymBros
    public LoginPage(GymBros gymbros, GymBrosApp gymBrosApp) {
        this.gymBros = gymbros;
        this.gymBrosApp = gymBrosApp;

        init();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loginCard");
                showLoginDisplay(gymbros);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "registerCard");
                showRegisterDisplay(gymbros);
            }
        });

        addActionListenerExitButton();

        setLoginPageLayout();

    }

    // MODIFIES: this
    // EFFECTS: adds action listener for exit button
    public void addActionListenerExitButton() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes layout components
    public void init() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(500, 70);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
    }

    // MODIFIES: this
    // EFFECTS: sets the layout of the loginPage
    public void setLoginPageLayout() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//        setBackground(Color.BLACK);

        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(Box.createHorizontalStrut(120));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(80));
        buttonPanel.add(Box.createHorizontalStrut(120)); // add spacing between buttons
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createVerticalStrut(80)); // add spacing between buttons
        buttonPanel.add(Box.createHorizontalStrut(120));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue()); // add spacing at the bottom

        cardPanel.add(createLoginCard(), "loginCard");
        cardPanel.add(createRegisterCard(), "registerCard");

        add(buttonPanel, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.CENTER);

        setOpaque(true);
        buttonPanel.setOpaque(true);
        cardPanel.setOpaque(true);
    }

    // EFFECTS: creates a new JPanel for loginCard
    private JPanel createLoginCard() {
        JPanel loginCard = new JPanel();
        // Add components to the login card as needed
        return loginCard;
    }

    // EFFECTS: creates a new JPanel for RegisterCard
    private JPanel createRegisterCard() {
        JPanel registerCard = new JPanel();
        // Add components to the register card as needed
        return registerCard;
    }

    // MODIFIES: this
    // EFFECTS: creates new loginDisplay with given gymBros and this and displays it
    public void showLoginDisplay(GymBros gymBros) {
        LoginDisplay loginDisplay = new LoginDisplay(gymBros, this, gymBrosApp);

        removeAll();
        add(loginDisplay); // add loginDisplay page to the content pane

        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates new RegisterDisplay with given gymBros and this and displays it
    public void showRegisterDisplay(GymBros gymbros) {
        RegisterDisplay registerDisplay = new RegisterDisplay(gymBros, this);

        removeAll();
        add(registerDisplay); // add registerDisplay page to the content pane

        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: shows the loginCard in the cardPanel
    public void showLoginPage() {
        gymBrosApp.removeAll();
        cardLayout.show(cardPanel, "loginCard");
    }

    // MODIFIES: this
    // EFFECTS: handles exit for the page and asks user if they want to save file
    public void handleExit() {
        int option = JOptionPane.showConfirmDialog(LoginPage.this,
                "Do you want to save your data?", "Logout",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            gymBrosApp.saveAccount();

        } else if (option == JOptionPane.NO_OPTION) {
            // do nothing
        } else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            return;
        }
        gymBrosApp.printLog();
        System.exit(0);
    }
}
