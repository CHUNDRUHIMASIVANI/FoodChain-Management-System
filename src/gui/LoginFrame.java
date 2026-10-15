package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import data.DatabaseConnection;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public LoginFrame() {

        setTitle("FoodChain - Login");

        setSize(650, 500);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // =========================================
        // MAIN PANEL
        // =========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        45,
                        30,
                        45));

        // =========================================
        // TITLE
        // =========================================

        JLabel title =
                new JLabel(
                        "FOODCHAIN",
                        SwingConstants.CENTER);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        36));

        JLabel subtitle =
                new JLabel(
                        "Food Supply & Distribution Platform",
                        SwingConstants.CENTER);

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16));

        JPanel titlePanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1));

        titlePanel.add(title);
        titlePanel.add(subtitle);

        // =========================================
        // LOGIN FORM
        // =========================================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                15,
                                20));

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel passwordLabel =
                new JLabel("Password:");

        JLabel roleLabel =
                new JLabel("Login As:");

        usernameField =
                new JTextField();

        passwordField =
                new JPasswordField();

        roleBox =
                new JComboBox<>(
                        new String[] {
                                "USER",
                                "ADMIN"
                        });

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(roleLabel);
        formPanel.add(roleBox);

        // =========================================
        // BUTTONS
        // =========================================

        JButton loginButton =
                new JButton("LOGIN");

        JButton registerButton =
                new JButton("REGISTER USER");

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // =========================================
        // ACTIONS
        // =========================================

        loginButton.addActionListener(
                e -> login());

        registerButton.addActionListener(
                e -> registerUser());

        // =========================================
        // ADD COMPONENTS
        // =========================================

        mainPanel.add(
                titlePanel,
                BorderLayout.NORTH);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);

        add(mainPanel);
    }

    // =====================================================
    // LOGIN
    // =====================================================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword());

        String selectedRole =
                roleBox
                        .getSelectedItem()
                        .toString();

        // =========================================
        // VALIDATION
        // =========================================

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        // =========================================
        // ADMIN LOGIN
        // =========================================

        if (selectedRole.equals("ADMIN")) {

            if (username.equals("admin")
                    && password.equals("admin123")) {

                openDashboard(
                        username,
                        "ADMIN");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Admin username or password.",
                        "Admin Login Failed",
                        JOptionPane.ERROR_MESSAGE);

                passwordField.setText("");
            }

            return;
        }

        // =========================================
        // USER LOGIN
        // =========================================

        String sql =
                "SELECT role "
                + "FROM users "
                + "WHERE username = ? "
                + "AND password = ? "
                + "AND role = 'USER'";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username);

            statement.setString(
                    2,
                    password);

            try (
                    ResultSet result =
                            statement.executeQuery()
            ) {

                if (result.next()) {

                    openDashboard(
                            username,
                            "USER");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid User username or password.",
                            "User Login Failed",
                            JOptionPane.ERROR_MESSAGE);

                    passwordField.setText("");
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection failed.\n\n"
                    + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // =====================================================
    // OPEN DASHBOARD
    // =====================================================

    private void openDashboard(
            String username,
            String role) {

        dispose();

        DashboardFrame dashboard =
                new DashboardFrame(
                        username,
                        role);

        dashboard.setVisible(true);
    }

    // =====================================================
    // REGISTER USER
    // =====================================================

private void registerUser() {

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    Object[] fields = {
        "Username:", usernameField,
        "Password:", passwordField
    };

    int option = JOptionPane.showConfirmDialog(
        this,
        fields,
        "FoodChain User Registration",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE
    );

    if (option != JOptionPane.OK_OPTION) {
        return;
    }

    String username = usernameField.getText().trim();
    String password =
        new String(passwordField.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(
            this,
            "Please enter both username and password.",
            "Registration Error",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (password.length() < 8) {
        JOptionPane.showMessageDialog(
            this,
            "Password must contain at least 8 characters.",
            "Registration Error",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    String sql =
        "INSERT INTO users (username, password, role) " +
        "VALUES (?, ?, ?)";

    try (
        java.sql.Connection con =
            data.DatabaseConnection.getConnection();

        java.sql.PreparedStatement ps =
            con.prepareStatement(sql)
    ) {
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, "USER");

        ps.executeUpdate();

        JOptionPane.showMessageDialog(
            this,
            "Registration successful! You can now log in.",
            "FoodChain Registration",
            JOptionPane.INFORMATION_MESSAGE
        );

    } catch (java.sql.SQLIntegrityConstraintViolationException ex) {

        JOptionPane.showMessageDialog(
            this,
            "That username already exists. Please choose another.",
            "Registration Error",
            JOptionPane.ERROR_MESSAGE
        );

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
            this,
            "Database error: " + ex.getMessage(),
            "FoodChain Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                () -> {

                    LoginFrame frame =
                            new LoginFrame();

                    frame.setVisible(true);
                });
    }
}