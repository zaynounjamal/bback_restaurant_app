<?php
header("Content-Type: application/json");
require 'config.php';

$response = ['status' => 'error', 'message' => ''];

try {
    // Validate required fields
    if (empty($_POST['id'])) throw new Exception("Missing item ID");
    
    $required = ['name', 'description', 'price', 'rating', 'timing', 'image_url', 'category'];
    foreach ($required as $field) {
        if (empty($_POST[$field])) {
            throw new Exception("Missing required field: $field");
        }
    }

    // Sanitize inputs
    $id = intval($_POST['id']);
    $name = mysqli_real_escape_string($conn, $_POST['name']);
    $description = mysqli_real_escape_string($conn, $_POST['description']);
    $price = floatval($_POST['price']);
    $rating = mysqli_real_escape_string($conn, $_POST['rating']);
    $timing = mysqli_real_escape_string($conn, $_POST['timing']);
    $image_url = mysqli_real_escape_string($conn, $_POST['image_url']);
    $category = mysqli_real_escape_string($conn, $_POST['category']);

    // Update database
    $query = "UPDATE menu_items SET 
                name = '$name',
                description = '$description',
                price = $price,
                rating = '$rating',
                timing = '$timing',
                image_url = '$image_url',
                category = '$category'
              WHERE id = $id";
    
    if (mysqli_query($conn, $query)) {
        if (mysqli_affected_rows($conn) > 0) {
            $response['status'] = 'success';
            $response['message'] = 'Item updated successfully';
        } else {
            throw new Exception("No changes made or item not found");
        }
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