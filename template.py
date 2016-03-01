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
parser.add_argument('-s','--searchdisplay',help='CSS display style value for the search box, defaults to none (=invisible)', required=False)
parser.add_argument('-v','--version',help='The version of Nexus the documents are for', required=True)
parser.add_argument('-i','--indexpath',help='Relative path reference to the index for navigation', required=True)
parser.add_argument('--product',help='Product', required=True)
parser.add_argument('--doctype',help='Doctype tag', required=False)


args = parser.parse_args()
path = args.path
bookTitle = args.title
searchdisplay = args.searchdisplay
version=args.version
toindex=args.indexpath
googleSearchToken = args.gsid
product = args.product
doctype = args.doctype

filename = inspect.getframeinfo(inspect.currentframe()).filename
wrapperpath = os.path.dirname(os.path.abspath(filename))

if doctype=="article":
   bodyTag='<body class="article">'
else:
   bodyTag='<body>'

if not searchdisplay:
  searchdisplay = "none"

print ("Applying template processing to ") + path
print ("  Wrapper path: ") + wrapperpath
print ("  Path to index set to: ") + toindex

for infile in glob.glob( os.path.join(path, '*.html') ):
  print "Reading File: " + infile
  body = open(infile, "r").read()
  if infile.endswith( 'search.html'):
    t = airspeed.Template(open( wrapperpath + "/search.html", "r").read())
    print( "  search.html replacements" )
  else:
    t = airspeed.Template(open( wrapperpath + "/template.html", "r").read())
    title = body[ body.index( "<title>" ) + 7 : body.rindex("</title>") ]
    body = body[ body.index( bodyTag) + len(bodyTag) : body.rindex("</body>") ]
      
  open(infile, "w").write( t.merge(locals()) );
