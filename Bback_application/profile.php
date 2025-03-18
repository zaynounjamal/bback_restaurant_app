<?php 
if(!empty($_POST['email']) && !empty($_POST['apiKey'])){
    $email = $_POST['email'];
    $apiKey = $_POST['apiKey'];
    $result= array();
    $con = mysqli_connect("localhost","root","",database: "login_register");
    if($con){
        $query = "SELECT * FROM users WHERE email = '{$email}' and apiKey = '{$apiKey}'";
        $res=mysqli_query($con,$query);
        if(mysqli_num_rows($res) !=0){
               $row=mysqli_fetch_assoc(result: $res);
               $result=array("status"=>"success", "message"=>"Data fetched Successfully","name"=>$row["name"],"email"=>$row["email"],"apiKey"=>$row["apiKey"]);
            }else $result = array("status"=>"failed","message"=>"Unauthorised access");
        }else $result = array("status"=>"failed","message"=>"Database Connection Failed");
    }else $result = array("status"=>"failed","message"=>"All Feilds are required");
    echo json_encode($result,JSON_PRETTY_PRINT);
?>