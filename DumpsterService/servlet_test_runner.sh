#!/bin/bash

context_path="http://127.0.0.1:8080"
content_type="Content-Type: application/json"

function requestGET {
	resource_path="${1}"
	param="${2}"
	path="${context_path}${resource_path}"
	curl -X GET -H "${content_type}" "${path}${param}"
}

function requestPOST {
	resource_path="${1}"
	data="${2}"
	path="${context_path}${resource_path}"
	curl -X POST -H "${content_type}" "${path}" --data "${data}"
}

#requestPOST "/user/save" \
#"{\"id\":-1,\"name\":\"Mr Ingen Alls\",\"email\":\"ingen@alls.org\",\"password\":\"lkjkjhghgfgds\"}"
#requestPOST "/dumpster/save" \
#"{\"id\":-1,\"descr\":\"old records, books and unusual stamps in mint albums etc\",\"position\":\"xxx.yyy\",\"app_user_id\":3}"

requestGET "/user/load" "?identifier=erik@boardy.se"
requestGET "/dumpster/load" "?identifier=erik@boardy.se"
requestGET "/user/load" "?identifier=bengtsson@boardy.se"
requestGET "/user/load" "?identifier=ingen@alls.org"
requestGET "/dumpster/load" "?identifier=ingen@alls.org"
