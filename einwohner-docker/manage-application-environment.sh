#!/bin/bash
source string-utils.sh
readonly app_ready_response='{"projectName":"einwohner-shared"'

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
    app_environment=$(ltrim $(grep "env" $output | cut -d '=' -f2))
    export env=$app_environment
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

remove_application_properties() {
    # For security reasons properties files used to startup app will be removed, as it contains sensitive data, and is no more required.
    echo "Removing startup files with sensitive information"
    for (( i = 0; i < 10; ++i )); do
        app_info=$(curl -k https://localhost:8443/einwohner/rest/anwendungs-info | grep $app_ready_response)
        echo $app_info
        if [[ -z "$app_info" ]]; then
            echo "Going to sleep for 2 seconds, As application is not ready yet"
            sleep 2
        else
            rm -f $CATALINA_HOME/conf/einwohner-application-basic.properties $CATALINA_HOME/conf/einwohner-application-basic.properties.tpl
            echo "Startup Files have been removed."
            break
        fi
    done
}

keep_only_environment_specific_keystores(){
    # Docker image has all keystores for all environments. But running container need keystores specific to environment, So removing all others
    cp $CATALINA_HOME/conf/$env/* $CATALINA_HOME/conf/
    rm -rf $CATALINA_HOME/conf/dev $CATALINA_HOME/conf/test
    echo "Keeping keystores for $env environment only, all others have been removed."
}