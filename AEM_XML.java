/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

 

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

 

/**
 *
 * @author VINCENT.LEUNG
 */
public class AEM_XML {

 

    static ArrayList<String> nodeName = new ArrayList();

 

    public static void main(String[] args) {
        File currentDir = new File(".");
        displayDirectoryFiles(currentDir);
    }

 

    public static void displayDirectoryFiles(File dir) {
        nodeName.add("jcr:content");
        /*

 

        nodeName.add("admission-requirements");
        nodeName.add("courses");
        nodeName.add("costs");
        nodeName.add("pathways");
        nodeName.add("contact-us");
        nodeName.add("apply-now");
        nodeName.add("program-learning-outcomes");
         */
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    // System.out.println("directory:" + file.getCanonicalPath());
                    displayDirectoryFiles(file);
                    // displayDirectoryContents(file);
                } else {
                    if (file.toString().endsWith(".content.xml") && file.toString().contains("contentTest6265")) {
                        // System.out.println("     file:" + file.getCanonicalPath());
                        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                        DocumentBuilder b = f.newDocumentBuilder();
                        org.w3c.dom.Document doc = b.parse(new ByteArrayInputStream(readTextFile(file.toString()).getBytes()));

 

                        Element root = doc.createElement("root");
                        root.setAttribute("jcr:primaryType", "nt:unstructured");
                        root.setAttribute("sling:resourceType", "wcm/foundation/components/responsivegrid");

 

                        Element responsiveGrid = doc.createElement("responsivegrid");
                        responsiveGrid.setAttribute("jcr:primaryType", "nt:unstructured");
                        responsiveGrid.setAttribute("sling:resourceType", "wcm/foundation/components/responsivegrid");
                        root.appendChild(responsiveGrid);
                        boolean added = false;
                        // iterate(doc.getDocumentElement(), root, responsiveGrid);
                        NodeList nList = doc.getElementsByTagName("jcr:content");
                        for (int temp = 0; temp < nList.getLength(); temp++) {

 

                            Node nNode = nList.item(temp);
                            if (nNode.getNodeName().equals("jcr:content")) {
                                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

 

                                //  responsiveGrid.appendChild(nNode);                           
                                // nNode.appendChild(root);
                                NodeList subList = nNode.getChildNodes();

 

                                for (int j = 0; j < subList.getLength(); j++) {
                                    if (subList.item(j).getNodeType() == Node.ELEMENT_NODE
                                            && !subList.item(j).getNodeName().equals("root")) {
                                        Node jNode = subList.item(j);
                                        added = true;
                                        //nNode.appendChild(jNode);
                                        responsiveGrid.appendChild(jNode);
                                        System.out.println("added parent node: " + jNode.getNodeName());

 

                                    }

 

                                }
                            }
                            if (added) {
                                System.out.println("file> " + file);
                                nNode.appendChild(root);

 

                            }

 

                            //nNode.getParentNode().appendChild(root);
                            //  doc.removeChild(nNode);

 

                            /* NodeList subList = nNode.getChildNodes();
                            for (int j = 0; j < subList.getLength(); j++) {

 

                                Node jNode = subList.item(j);
                                System.out.println("");
                                if (jNode.getNodeType() == Node.ELEMENT_NODE) {

 

                                    //System.out.println("jNode> " + jNode.getNodeName());
                                    if (nodeName.contains(jNode.getNodeName())) {
                                        root.appendChild(responsiveGrid);
                                        responsiveGrid.appendChild(jNode);
                                        nNode.appendChild(root);
                                        System.out.println("added parent node: " + jNode.getNodeName());
                                    }
                                }
                            }*/
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);

 

                            StreamResult result = new StreamResult(file);
                            transformer.transform(source, result);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 

    public static String readTextFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content;
    }

 

    public static List<String> readTextFileByLines(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        return lines;
    }

 

    public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
    }
}
