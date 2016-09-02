def newline = System.getProperty("line.separator")

File index = new File('index.html')

parser=new XmlSlurper()
parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

doc = parser.parseText(index.text)

// create a new empty file (or overwrite existing with empty
new PrintWriter('nav.html').close()

// open the file for writing
File nav = new File('nav.html')

doc.depthFirst().collect { it }.findAll { it.name() == "span" && it.@class.text()=="chapter"}.each {
    nav.append("<div class=\"sidebar\"><a href=\"\${toindex}nexus-book/reference/${it.a.@href}\">${it}</a></div>${newline}")
}

// create a new empty file (or overwrite existing with empty
new PrintWriter('template.html').close()

def newTemplate = new File('template.html')
def templatePieces = ['header2nav.html' , 'nav.html' , 'nav2body.html', 'bodyContent.html', 'body2footer.html']
templatePieces.each{ newTemplate.append(new File(it).getText()) }

// create a new empty file (or overwrite existing with empty
new PrintWriter('search.html').close()

def newSearch = new File('search.html')
def searchPieces = ['header2nav.html' , 'nav.html' , 'nav2body.html', 'bodySearch.html', 'body2footer.html']
searchPieces.each{ newSearch.append(new File(it).getText()) }

// Need to add the deletion of the temporary nav.html file or ignore it in the template.py file.. otherwise it fails, maybe also ensure the other files dont leak into the output 

println "Done"
