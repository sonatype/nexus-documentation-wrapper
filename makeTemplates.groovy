def newline = System.getProperty("line.separator")

def pre = "makeTemplate: "
println "$pre Starting"

wrapperDir = new File(getClass().protectionDomain.codeSource.location.path).parent + File.separator
println "$pre Running script in $wrapperDir"
//To get the script file path

//scriptFile = getClass().protectionDomain.codeSource.location.path

String product = args[0]
String webcontext
println "$pre Product $product"
if (product.contains("IQ")) {
  webcontext = "sonatype-clm-book"
} else {
  webcontext = "nexus-book"
}
println "$pre Webcontext $webcontext"

String path = args[1]
println "$pre Target path $path"
String context = webcontext + "/" + path.substring(path.lastIndexOf("site/")+5, path.length())
println "$pre Target context $context"
File index = new File(path + '/index.html')

parser=new XmlSlurper()
parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

doc = parser.parseText(index.text)

// create a new empty file (or overwrite existing with empty
new PrintWriter(wrapperDir + 'part-nav.html').close()

// open the file for writing
File nav = new File(wrapperDir + 'part-nav.html')

nav.append("<div class=\"sidebarTitle\" id=\"nxDocsSectNav\"><b>\${product} \${version}</b></div>")
nav.append("<div class=\"sidebarSection\">")


def linkCount = 0
doc.depthFirst().collect { it }.findAll { it.name() == "span" && it.@class.text()=="chapter"}.each {
    nav.append("<div class=\"sidebar\"><a href=\"\${toindex}${context}/${it.a.@href}\">${it}</a></div>${newline}")
    println "$pre added $it"
    linkCount++
}

println "$pre Added $linkCount links"

nav.append("</div>")
// nav.append("<br />")
// nav.append("</div>")
  
if (linkCount == 0) {
  new PrintWriter(wrapperDir + 'part-nav.html').close()
  println "$pre Flushed $nav since no links added."
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

println "$pre Assembled $newTemplate"

// create a new empty file (or overwrite existing with empty
new PrintWriter(wrapperDir + 'search.html').close()

def newSearch = new File(wrapperDir + 'search.html')
def searchPieces = [wrapperDir + 'part-header2nav.html', 
                    wrapperDir + 'part-nav.html', 
                    wrapperDir + 'part-nav2body.html', 
                    wrapperDir + 'part-bodySearch.html', 
                    wrapperDir + 'part-body2footer.html']
searchPieces.each{ newSearch.append(new File(it).getText()) }
println "$pre Assembled $newSearch"

// Need to add the deletion of the temporary nav.html file or ignore it in the template.py file.. otherwise it fails, maybe also ensure the other files dont leak into the output 

println "$pre Done"
