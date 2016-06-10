#!/bin/bash

# Start a webserver off the local directory, 
# this enables debugging javascript with cookies as needed for the cross page accordion state persistence

cd $NEXUS_DOCUMENTATION
python -m SimpleHTTPServer 9000 
