<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

$response = array(); // Array to hold response data

if (!empty($_POST['name']) && !empty($_POST['email']) && !empty($_POST['password'])) {
    $con = mysqli_connect("localhost", "root", "", "login_register");

    if ($con) {
        // Use prepared statement to check if the email already exists
        $checkStmt = $con->prepare("SELECT id FROM users WHERE email = ?");
        $checkStmt->bind_param("s", $email);

        $email = $_POST['email'];
        $checkStmt->execute();
        $checkStmt->store_result();

        if ($checkStmt->num_rows > 0) {
            $response = array("status" => "failed", "message" => "Email already registered");
        } else {
            // Insert user data
            $stmt = $con->prepare("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)");
            $stmt->bind_param("ssss", $name, $email, $password, $role);

            $name = $_POST['name'];
            $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
            $role = 'user'; // Default role

            if ($stmt->execute()) {
                $response = array("status" => "success", "message" => "Registration successful");
            } else {
                $response = array("status" => "failed", "message" => "Registration failed: " . $stmt->error);
            }
            $stmt->close();
        }
        $checkStmt->close();
    } else {
        $response = array("status" => "failed", "message" => "Database connection failed: " . mysqli_connect_error());
    }
} else {
    $response = array("status" => "failed", "message" => "All fields are required");
}

// Return response as JSON
header('Content-Type: application/json');
echo json_encode($response);
?>
