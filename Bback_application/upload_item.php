<?php
header("Content-Type: application/json");
require 'config.php';

$response = ['status' => 'error', 'message' => ''];

try {
    // Validate required fields
    $required = ['name', 'description', 'price', 'rating', 'timing', 'image_url', 'category'];
    foreach ($required as $field) {
        if (empty($_POST[$field])) {
            throw new Exception("Missing required field: $field");
        }
    }

    // Sanitize inputs
    $name = mysqli_real_escape_string($conn, $_POST['name']);
    $description = mysqli_real_escape_string($conn, $_POST['description']);
    $price = floatval($_POST['price']);
    $rating = mysqli_real_escape_string($conn, $_POST['rating']);
    $timing = mysqli_real_escape_string($conn, $_POST['timing']);
    $image_url = mysqli_real_escape_string($conn, $_POST['image_url']);
    $category = mysqli_real_escape_string($conn, $_POST['category']);

    // Insert into database
    $query = "INSERT INTO menu_items (name, description, price, rating, timing, image_url, category) 
              VALUES ('$name', '$description', $price, '$rating', '$timing', '$image_url', '$category')";
    
    if (mysqli_query($conn, $query)) {
        $response['status'] = 'success';
        $response['message'] = 'Item added successfully';
    } else {
        throw new Exception("Database error: " . mysqli_error($conn));
    }

} catch (Exception $e) {
    $response['message'] = $e->getMessage();
} finally {
    mysqli_close($conn);
    echo json_encode($response);
}
?>