<?php
require 'config.php';

$id = $_POST['id'];

$stmt = $conn->prepare("DELETE FROM menu_items WHERE id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();
echo "Item deleted successfully";
?>