<?php

ob_start();
session_start();

date_default_timezone_set('Asia/Dhaka');

define("DB_SERVER", "localhost"); // Server Name
define("DB_USER", "u706024379_agata"); // Database User
define("DB_PASS", "Agata001"); // Database Password
define("DB_NAME", "u706024379_agata"); // Database Name



define("UPLOADED_FOLDER", "front-end/uploads");    // Image/Video Upload Folder
define("UPLOADED_THUMB_FOLDER", "front-end/thumb");   // Thumb Image Upload Folder
define("API_PAGINATION", 8);    // 5 items in the api
define("BACKEND_PAGINATION", 16); // 5 items in the admin panel
define("MAX_IMAGE_SIZE", 1.5);    // Maximum Image Size 1 mb(Max Value of server 22mb(To change open .htaccess file))
define("MAX_FILE_COUNT", 10);   // Maximum File Count in One(Max Value of server 20(To change open .htaccess file))

define("DASHBOARD_ITEM_COUNT", 5);

define("DATE_FORMAT", "Y-m-d h:i:s");
define("DB_DATE_FORMAT", "Y-m-d");

define("DEFAULT_NEGATIVE", -9999);

define("EMAIL_USER", 1);
define("FACEBOOK_USER", 2);
define("GOOGLE_USER", 3);
define("NUMBER_USER", 4);
define("APPLE_USER", 5);

define("PENDING", 1);
define("DELIVERED",3);
define("CLEAR", 2);


define("ENCRYPTION_KEY", "fedcba9876543210");
define("ENCRYPTION_IV", "0123456789abcdef");

define("CURRENCY_APPEND", 1);
define("CURRENCY_PREPEND", 2);

$currency_types[CURRENCY_PREPEND] = "Prepend";
$currency_types[CURRENCY_APPEND] = "Append";

define("CURRENCY_TYPES", $currency_types);




define("PROFILE_DEFAULT", "profile_default.jpg");

define("PRIVATE_PATH", dirname(__FILE__));
define("PROJECT_PATH", dirname(PRIVATE_PATH));
define("PUBLIC_PATH", PROJECT_PATH );
define("UPLOAD_FOLDER", PUBLIC_PATH . DIRECTORY_SEPARATOR . UPLOADED_FOLDER . DIRECTORY_SEPARATOR);
define("UPLOAD_FOLDER_THUMB", PUBLIC_PATH  . DIRECTORY_SEPARATOR . UPLOADED_THUMB_FOLDER . DIRECTORY_SEPARATOR);
define("UPLOAD_LINK", getcwd() . DIRECTORY_SEPARATOR . UPLOADED_FOLDER . DIRECTORY_SEPARATOR);


require_once('models/lib/Database2.php');
require_once('models/lib/Helper.php');
require_once('models/lib/Session.php');
require_once('models/lib/Response.php');
require_once('models/lib/Errors.php');
require_once('models/lib/Message.php');
require_once('models/lib/Upload.php');
require_once('models/lib/Util.php');

require_once('models/Item_Image.php');






?>
