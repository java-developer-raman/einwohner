#!/bin/bash
source string-utils.sh

input="einwohner-application-basic.properties.tpl"
output=$(remove_substring_from_last $input .tpl)
rm -f "$output"
while IFS= read -r line
do
  property_key=$(echo $line | grep 'vault:' | cut -d '=' -f1)
  property_value=$(echo $line | grep 'vault:' | cut -d '=' -f2)

  if [[ $property_value = "vault:"* ]];
  then
   start_index_property_value=$(substring_index $line =)
   encrypted_text="${line:start_index_property_value}"
   body="{\"textToDecrypt\":\"$encrypted_text\",\"applicationName\":\"einwohner-startup\"}"
   decrypted_text=$(curl -u admin:admin -k -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data "$body" https://192.168.2.104:8888/decrypt-with-vault)
   echo "$property_key=$decrypted_text" >> "$output"
  else
   echo "$line" >> "$output"
  fi
done < "$input"