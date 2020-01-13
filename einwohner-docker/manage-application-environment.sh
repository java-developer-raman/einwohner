#!/bin/bash
source string-utils.sh
readonly app_ready_url="https://localhost:8443/einwohner/rest/anwendungs-info"
readonly app_ready_response='{"projectName":"einwohner-shared"'
readonly config_server_input="/usr/local/tomcat/app-conf/config-server.properties"
readonly app_properties_template_path="/usr/local/tomcat/conf/einwohner-application-basic.properties.tpl"
readonly app_properties_path="/usr/local/tomcat/conf/einwohner-application-basic.properties"
readonly host_properties_path="/usr/local/tomcat/conf/host/host.properties"
readonly server_xml_path="/usr/local/tomcat/conf/server.xml"

setup_container_environment_properties() {
    echo "Setting up default container properties build from host properties file $host_properties_path"
    app_environment=$(ltrim $(grep "env" $host_properties_path | cut -d '=' -f2))
    export env=$app_environment
}

build_app_properties() {
    echo "Building application properties"
    rm -f "$app_properties_path"
    cat $config_server_input > $app_properties_path
    printf "\n############ Other application properties ##########\n" >> $app_properties_path

    config_server_url=$(ltrim $(grep "config.server.url" $app_properties_path | cut -d '=' -f2))
    config_server_user=$(ltrim $(grep "config.server.user" $app_properties_path | cut -d '=' -f2))
    config_server_password=$(ltrim $(grep "config.server.password" $app_properties_path | cut -d '=' -f2))
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
            echo "$property_key=$decrypted_text" >> "$app_properties_path"
        else
            echo "$line" >> "$app_properties_path"
        fi
    done < "$app_properties_template_path"
    echo "Application properties were build into $app_properties_path"

    # replacing tls password token with actual password
    tls_keystore_password=$(ltrim $(grep "server.ssl.key-store-password" $app_properties_path | cut -d '=' -f2))
    sed -i -e "s/#tls_keystore_password#/$tls_keystore_password/g" $server_xml_path
}

remove_app_properties_after_app_is_ready() {
    # For security reasons properties files used to startup app will be removed, as it contains sensitive data, and is no more required.
    echo "Removing startup files with sensitive information"
    files_removed_flag=0
    for (( i = 0; i < 20; ++i )); do
        app_info=$(curl -k $app_ready_url | grep $app_ready_response)
        echo $app_info
        if [[ -z "$app_info" ]]; then
            echo "Going to sleep for 2 seconds, As application is not ready yet"
            sleep 2
        else
            rm -f $app_properties_path $app_properties_template_path
            echo "Startup Files have been removed."
            files_removed_flag=1
            break
        fi
    done
    if [[ $files_removed_flag -eq 0 ]]; then
        echo "Startup Files were not removed."
    fi
}

keep_only_environment_specific_keystores(){
    # Docker image has all keystores for all environments. But running container need keystores specific to environment, So removing all others
    cp $CATALINA_HOME/conf/$env/* $CATALINA_HOME/conf/
    rm -rf $CATALINA_HOME/conf/dev $CATALINA_HOME/conf/test
    echo "Keeping keystores for $env environment only, all others have been removed."
}