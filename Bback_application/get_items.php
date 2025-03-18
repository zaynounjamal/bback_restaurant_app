<?php
header("Content-Type: application/json");
require 'config.php';

$response = ['status' => 'error', 'message' => '', 'data' => []];

try {
    $category = isset($_GET['category']) ? mysqli_real_escape_string($conn, $_GET['category']) : 'all';
    
    $query = "SELECT * FROM menu_items";
    if ($category !== 'all') {
        $query .= " WHERE category = '$category'";
    }
    
    $result = mysqli_query($conn, $query);
    
    if (!$result) throw new Exception("Database error: " . mysqli_error($conn));
    
    $items = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $items[] = $row;
    }
    
    $response['status'] = 'success';
    $response['data'] = $items;

} catch (Exception $e) {
    $response['message'] = $e->getMessage();
} finally {
    mysqli_close($conn);
    echo json_encode($response);
}
?>