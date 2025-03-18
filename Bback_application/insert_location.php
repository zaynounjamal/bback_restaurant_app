<?php 
   $con = mysqli_connect("localhost", "root", "", "insert_location_order");

   if (!$con) {
       die("Connection failed: " . mysqli_connect_error());
   }

   $fullname = $_POST["FullName"];
   $email = $_POST["Email"];
   $location = $_POST["Location"];
   $phone = $_POST["Phone"];

   $sql = "INSERT INTO users_order (FullName, Email, Location, Phone) VALUES (?, ?, ?, ?)";
   $stmt = mysqli_prepare($con, $sql);

   if ($stmt === false) {
       error_log('Error preparing statement: ' . mysqli_error($con));
       die('Error preparing statement: ' . mysqli_error($con));
   }

   mysqli_stmt_bind_param($stmt, "ssss", $fullname, $email, $location, $phone);

   if (mysqli_stmt_execute($stmt)) {
       echo "Data inserted successfully";
   } else {
       error_log('Error executing statement: ' . mysqli_stmt_error($stmt));
       echo "Failed to insert data: " . mysqli_error($con);
   }

   mysqli_stmt_close($stmt);
   mysqli_close($con);
?>
