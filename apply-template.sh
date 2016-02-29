#!/bin/bash

# fail if anything errors
set -e
# fail if a function call is missing an argument
set -u

dir=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
path=$1
title=$2
gsid=$3

echo "Applying documentation-wrapper template in ${dir}" 
echo "  path: ${path}"
echo "  title: ${title}"
echo "  gsid: ${gsid}"

# copy all content apart from the excluded stuff ;-) 
echo "  Copying template resources" 
rsync -a --exclude-from="$dir/rsync-excludes" $dir/* $path

echo "  Applying website template"
python "${dir}/template.py" -p "${path}" -t "${title}" -g "${gsid}"

#cp target/site/reference/index.html target/site/reference/public-book.html
