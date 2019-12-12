<?php

    define('HOST','localhost');
    define('USER','root');
    define('PASS','1234');
    define('DB','db_uangkas');

    $conn = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to connect');

    date_default_timezone_set("Asia/Jakarta");
    

?>