<?php
include 'db_connect.php';

$response = ['success' => false];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $userId = $_POST['user_id'] ?? '';
    $itemId = $_POST['item_id'] ?? '';
    $isFavorite = $_POST['is_favorite'] ?? '0';

    // Input validation
    if (!empty($userId) && !empty($itemId)) {
        try {
            if ($isFavorite === '1') {
                // Insert only if not exists
                $stmt = $conn->prepare("INSERT IGNORE INTO user_favorites (user_id, item_id) VALUES (?, ?)");
                $stmt->bind_param("ss", $userId, $itemId);
            } else {
                // Delete existing
                $stmt = $conn->prepare("DELETE FROM user_favorites WHERE user_id = ? AND item_id = ?");
                $stmt->bind_param("ss", $userId, $itemId);
            }
            
            if ($stmt->execute()) {
                $response['success'] = true;
            }
        } catch (mysqli_sql_exception $e) {
            $response['error'] = "Database error: " . $e->getMessage();
        }
    }
}

echo json_encode($response);
?>