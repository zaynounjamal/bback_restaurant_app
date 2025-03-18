<?php 
if(!empty($_POST['email']) && !empty($_POST['apiKey'])){
    $email = $_POST['email'];
    $apiKey = $_POST['apiKey'];
    $con = mysqli_connect("localhost","root","",database: "login_register");
    if($con){
        $query = "SELECT * FROM users WHERE email = '{$email}' and apiKey = '{$apiKey}'";
        $res=mysqli_query($con,$query);
        if(mysqli_num_rows($res) !=0){
            $row=mysqli_fetch_assoc(result: $res);
            $sqlUpdate ="UPDATE users set apiKey= '' WHERE email='{$email}'";
            if(mysqli_query($con, $sqlUpdate)){
                echo "success";
            }else echo "Logout Failed";

    }else echo"Unauthorised to access";
}else echo "Database Connection Failed";
}else echo "All Feilds are required";
?>