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

    public LoginPage(GymBros gymbros) {
        this.gymBros = gymbros;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(500, 70);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);


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

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // center align
        add(buttonPanel, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.CENTER);
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
