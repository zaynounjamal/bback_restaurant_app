<?php
if (!empty($_POST['email']) && !empty($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];
    $result = array();

    // Database connection
    $con = new mysqli("localhost", "root", "", "login_register");

    if ($con->connect_error) {
        $result = array("status" => "failed", "message" => "Database connection failed");
    } else {
        // Use prepared statements to prevent SQL injection
        $stmt = $con->prepare("SELECT * FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $res = $stmt->get_result();

        if ($res->num_rows > 0) {
            $row = $res->fetch_assoc();

            // Check if the password matches
            if (password_verify($password, $row['password'])) {
                try {
                    // Generate API key
                    $apiKey = bin2hex(random_bytes(23));
                } catch (Exception $e) {
                    $apiKey = bin2hex(uniqid($email, true));
                }

                // Update the user with the generated API key
                $stmtUpdate = $con->prepare("UPDATE users SET apiKey = ? WHERE email = ?");
                $stmtUpdate->bind_param("ss", $apiKey, $email);

                if ($stmtUpdate->execute()) {
                    // Include the role in the response
                    $result = array(
                        "status" => "success",
                        "message" => "Login Successful",
                        "name" => $row["name"],
                        "email" => $row["email"],
                        "apiKey" => $apiKey,
                        "role" => $row["role"] // Ensure role is included
                    );
                } else {
                    $result = array("status" => "failed", "message" => "Failed to update API key");
                }
            } else {
                $result = array("status" => "failed", "message" => "Incorrect email or password");
            }
        } else {
            $result = array("status" => "failed", "message" => "Email not found");
        }

        $stmt->close();
    }

    $con->close();
} else {
    $result = array("status" => "failed", "message" => "Email and password are required");
}

header('Content-Type: application/json');
echo json_encode($result, JSON_PRETTY_PRINT);
?>