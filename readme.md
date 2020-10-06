script used for aem 6.2 to 6.5 content mig

drag content zip and the script will rebuild content with jcr:content nodes to be wrapped in root>responsivegrid nodes to allow it to render properly on 6.5
zipped content file size limit is 50mbs



JAVA VERSION - AEM_XML.java

put extracted directory in same path as java class
modify line 57 to the path you are trying to add the root/responsive grid to (as per the TODO instructions)
