<?php
    require_once('connection.php');

    $transaksi_id   = $_REQUEST['transaksi_id'];

    $query = mysqli_query($conn, "DELETE FROM tb_transaksi WHERE tb_transaksi.transaksi_id = '$transaksi_id'");

    if($query){
        echo json_encode(array('response' => 'success'));
    }else{
        echo json_encode(array('response' => 'failed'));
    }

?>