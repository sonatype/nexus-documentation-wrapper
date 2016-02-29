import glob
import os
import argparse
import inspect
import sys

sys.path.insert(0, "airspeed")
import airspeed 

parser = argparse.ArgumentParser(description='Script to wrap produces html into template for site deployment')
parser.add_argument('-p','--path',help='Path where the replacement should be done', required=True)
parser.add_argument('-t','--title',help='Title of the documentation', required=True)
parser.add_argument('-g','--gsid',help='Google Search ID', required=True)
args = parser.parse_args()
path = args.path
bookTitle = args.title
gsid = args.gsid

filename = inspect.getframeinfo(inspect.currentframe()).filename
wrapperpath = os.path.dirname(os.path.abspath(filename))

print "wrapperpath" + wrapperpath

for infile in glob.glob( os.path.join(path, '*.html') ):
  print "Reading File: " + infile
  body = open(infile, "r").read()
  if infile.endswith( 'search.html'):
    t = airspeed.Template(open( wrapperpath + "/search.html", "r").read())
    print( "  search.html replacements" )
  else:
    t = airspeed.Template(open( wrapperpath + "/template.html", "r").read())
    title = body[ body.index( "<title>" ) + 7 : body.rindex("</title>") ]
    body = body[ body.index( "<body>") + 6 : body.rindex("</body>") ]
    if "index.html" in infile:
      print ("Replacing bookTitle  - replacing with ToC" )
      title = "Table of Contents"
      
  open(infile, "w").write( t.merge(locals()) );
