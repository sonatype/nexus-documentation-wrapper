def newline = System.getProperty("line.separator")

wrapperDir = new File(getClass().protectionDomain.codeSource.location.path).parent + File.separator
println "Running script in $wrapperDir"
//To get the script file path

//scriptFile = getClass().protectionDomain.codeSource.location.path

String path = args[0]
File index = new File(path + '/index.html')

parser=new XmlSlurper()
parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

doc = parser.parseText(index.text)

// create a new empty file (or overwrite existing with empty
new PrintWriter(wrapperDir + 'part-nav.html').close()

// open the file for writing
File nav = new File(wrapperDir + 'part-nav.html')

doc.depthFirst().collect { it }.findAll { it.name() == "span" && it.@class.text()=="chapter"}.each {
    nav.append("<div class=\"sidebar\"><a href=\"\${toindex}nexus-book/reference/${it.a.@href}\">${it}</a></div>${newline}")
}

// create a new empty file (or overwrite existing with empty
new PrintWriter(wrapperDir + 'template.html').close()

def newTemplate = new File(wrapperDir + 'template.html')
def templatePieces = [wrapperDir + 'part-header2nav.html', 
                      wrapperDir + 'part-nav.html', 
                      wrapperDir + 'part-nav2body.html', 
                      wrapperDir + 'part-bodyContent.html', 
                      wrapperDir + 'part-body2footer.html']
templatePieces.each{ newTemplate.append(new File(it).getText()) }

// create a new empty file (or overwrite existing with empty
new PrintWriter(wrapperDir + 'search.html').close()

def newSearch = new File(wrapperDir + 'search.html')
def searchPieces = [wrapperDir + 'part-header2nav.html', 
                    wrapperDir + 'part-nav.html', 
                    wrapperDir + 'part-nav2body.html', 
                    wrapperDir + 'part-bodySearch.html', 
                    wrapperDir + 'part-body2footer.html']
searchPieces.each{ newSearch.append(new File(it).getText()) }

// Need to add the deletion of the temporary nav.html file or ignore it in the template.py file.. otherwise it fails, maybe also ensure the other files dont leak into the output 

println "Done"
