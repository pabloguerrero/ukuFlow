#!/usr/bin/php -q

<?php
	// check if arguments supplied
	if (count($argv) == 1)  {
    		$input_file = $argv[1];
	} else {
    		print("Usage: $argv[0] <input_log_file> \n");
 	   	print("\n");
		exit(1);
	}

	$count_array = array();
	$fp_input_file = fopen($input_file, "r");

	while (!feof($fp_input_file)) {
		$input=trim(fgets($fp_input_file));

		if (strstr($input, "added scope")) {
			$input_tok = strtok($input, ":");
			$time = trim($input_tok);
			$node = trim(strtok(":"));
			$msg = trim(strtok(","));	
		}
	}

	
	

