<?php
require_once('connection.php');

$query = mysqli_query($conn, "SELECT * FROM tb_transaksi ORDER BY tb_transaksi.transaksi_id DESC");

$result = array();

while($row = mysqli_fetch_array($query)){
    array_push($result, array(

        'transaksi_id'  => $row['transaksi_id'],
        'status'        => $row['status'],
        'jumlah'        => $row['jumlah'],
        'keterangan'    => $row['keterangan'],
        'tanggal'       => date('d/m/Y', strtotime($row['tanggal'])),
        'tanggal2'      => $row['tanggal']

    ));
}


    $query = mysqli_query($conn, "SELECT (SELECT SUM(tb_transaksi.jumlah) FROM tb_transaksi WHERE tb_transaksi.status = 'MASUK') AS MASUK, (SELECT SUM(tb_transaksi.jumlah) FROM tb_transaksi WHERE tb_transaksi.status = 'KELUAR') AS KELUAR");

    while($row = mysqli_fetch_assoc($query)){

        $masuk  = $row['MASUK'];
        $keluar = $row['KELUAR'];

    }

    echo json_encode(array(

        'masuk'     => $masuk,
        'keluar'    => $keluar,
        'saldo'     => ($masuk - $keluar),
        'result'    => $result

    ));

    mysqli_close($conn);


?>