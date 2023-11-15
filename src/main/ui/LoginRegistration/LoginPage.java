package ui.LoginRegistration;

import model.GymBros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private GymBros gymBros;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;

    public LoginPage(GymBros gymbros) {
        this.gymBros = gymbros;

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

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLoginPageLayout();

    }

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

    private JPanel createLoginCard() {
        JPanel loginCard = new JPanel();
        // Add components to the login card as needed
        return loginCard;
    }

    private JPanel createRegisterCard() {
        JPanel registerCard = new JPanel();
        // Add components to the register card as needed
        return registerCard;
    }

    public void showLoginDisplay(GymBros gymBros) {
        LoginDisplay loginDisplay = new LoginDisplay(gymBros, this);

        removeAll();
        add(loginDisplay); // add loginDisplay page to the content pane

        revalidate();
        repaint();
    }

    public void showRegisterDisplay(GymBros gymbros) {
        RegisterDisplay registerDisplay = new RegisterDisplay(gymBros, this);

        removeAll();
        add(registerDisplay); // add registerDisplay page to the content pane

        revalidate();
        repaint();
    }

    public void showLoginPage() {
        cardLayout.show(cardPanel, "loginCard");
    }
}
