#!/bin/bash
source string-utils.sh
build_application_properties() {
 echo "Building application properties"
 config_server_input="/usr/local/tomcat/app-conf/config-server.properties"
 input="/usr/local/tomcat/conf/einwohner-application-basic.properties.tpl"
 output="$(remove_substring_from_last $input .tpl)"
 rm -f "$output"
 cat $config_server_input > $output

 config_server_url=$(ltrim $(grep "config.server.url" $output | cut -d '=' -f2))
 config_server_user=$(ltrim $(grep "config.server.user" $output | cut -d '=' -f2))
 config_server_password=$(ltrim $(grep "config.server.password" $output | cut -d '=' -f2))
 while IFS= read -r line
 do
  property_key=$(trim $(echo $line | cut -d '=' -f1))
  property_value=$(trim $(echo $line | cut -d '=' -f2))

  if [[ $property_value = "vault:"* ]];
  then
   start_index_property_value=$(substring_index $line =)
   encrypted_text=$(sub_string $line $start_index_property_value)
   body="{\"textToDecrypt\":\"$encrypted_text\",\"applicationName\":\"einwohner-startup\"}"
   decrypted_text=$(curl -u $config_server_user:$config_server_password -k -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data "$body" "$config_server_url/decrypt-with-vault")
   echo "$property_key=$decrypted_text" >> "$output"
  else
   echo "$line" >> "$output"
  fi
 done < "$input"
 echo "Application properties were build into $output"
}
