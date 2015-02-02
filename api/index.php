<?php 
include_once "simple_html_dom.php";
$html = file_get_html('http://roostereemland.nl/');


foreach ($elements->find('HR') as $child) {
    echo end($child);
}
?>