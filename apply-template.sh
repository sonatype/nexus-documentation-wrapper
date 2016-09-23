#!/bin/bash

# fail if anything errors
set -e
# fail if a function call is missing an argument
set -u

dir=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
echo "Applying nexus-documentation-wrapper template in ${dir}" 

path=$1
properties=$2
searchDisplay=$3
indexpath=$4
doctype=$5

echo "  path: ${path}"
echo "  searchDisplay: ${searchDisplay}"
echo "  indexpath: ${indexpath}"
echo "  doctype: ${doctype}"

echo "Reading $properties"
source $properties
 
echo "  product: ${product}"
echo "  bookTitle: ${bookTitle}"
echo "  googleSearchToken: ${googleSearchToken}"
echo "  version: ${version}"
echo "  searchUrl: ${searchUrl}" 

echo "Checking for/Setting up Groovy support"
if [ -x "$(command -v groovy)" ]; then
  echo 'groovy found on PATH.'
else
  echo 'On Bamboo/Zion Adding groovy to PATH.'
  export PATH=/opt/zion/bundles/tools/groovy/groovy-2.4.3/bin/:$PATH
fi
groovy -v

echo "Creating template files"
groovy "${dir}/makeTemplates.groovy" "$product" "$path"

# copy all content apart from the excluded stuff ;-) 
echo "  Copying template resources" 
rsync -a --exclude-from="$dir/rsync-excludes" $dir/* $path

echo "  Applying website template"
python "${dir}/template.py" -p "${path}" -t "${bookTitle}" -g "${googleSearchToken}" -s "${searchDisplay}" -v "${version}" -i "${indexpath}" --product "${product}" --doctype $doctype --searchurl "$searchUrl"
#cp target/site/reference/index.html target/site/reference/public-book.html