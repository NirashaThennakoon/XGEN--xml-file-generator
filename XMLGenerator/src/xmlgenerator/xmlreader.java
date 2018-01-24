/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlgenerator;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;





public class xmlreader extends DefaultHandler  {
    
    static ArrayList<String> element = new ArrayList<>();      //Defined Array for Getting the subscriber list from Text file
    static ArrayList<String> attName = new ArrayList<>();      //Defined Array for Getting the subscriber list from Text file
    static ArrayList<String> attValue = new ArrayList<>();      //Defined Array for Getting the subscriber list from Text file
    static ArrayList<String> elementcontent = new ArrayList<>();      //Defined Array for Getting the subscriber list from Text file
    static ArrayList<Integer> counter = new ArrayList<>();      //Defined Array for Getting the subscriber list from Text file
    static ArrayList<String> list = new ArrayList<>();
    static ArrayList<String> order = new ArrayList<>(); 
    static ArrayList<String> packetaddress1 = new ArrayList<>();         //Defined Array for assinging parent values
    static ArrayList<String> packetaddress2 = new ArrayList<>();         //Defined Array for assinging parent values
    static ArrayList<String> subid = new ArrayList<>();         //Defined Array for assinging parent values
    static ArrayList<String> authkey = new ArrayList<>();                //Defined Array for assinging parent values
    static int c=0;                 //attribute counter
    static int n=0;
    static int m=0;
    static int i =0;                //element
    static int q=0;
    static int r = 0;
    static int t =0;
    static int u = 0;
    
    public static String absolutePathToXml = "";
   public static String outputPathToXml = "";
    public static File path;
    static StreamResult  result;
    
   
    public xmlreader(){}
    
    
    
    public  void parseXml() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(absolutePathToXml, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfigurationException: ");
        } catch (SAXException e) {
            System.out.println("SAXException: ");
        } catch (IOException e) {
            System.out.println("IOException: ");
        }
    }
    
    @Override
    public void startElement(String s1, String s2, String elementName, Attributes attributes) 
            throws SAXException {
            element.add(elementName);
            order.add(elementName);
        
        System.out.println("element: " + elementName);
       
        counter.add(attributes.getLength());
        
        System.out.println("Attributes for element : "+attributes.getLength());
        //print all attributes for this element
        for(i = 0; i < attributes.getLength(); i++) {
            System.out.println("attribute Name: " + attributes.getLocalName(i));
            attName.add(attributes.getLocalName(i));
            System.out.println("attribute Value: " + attributes.getValue(i));
            attValue.add(attributes.getValue(i));
            c++;
        }
        
        if("fileHeader".equals(element.get(u)))
        {
            elementcontent.add(u,"");
    }
        if("fileFooter".equals(element.get(u)))
        {
            elementcontent.add(u,"");
    }
        u++;
    
        
        
        
       
        
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
           order.add(qName);
    }
    
    
    @Override
   	public void characters(char ch[], int start, int length) throws SAXException { 
            String tag = new String(ch, start, length);
            elementcontent.add(tag.concat(""));
                }
    


    
        static public void subs( String t) {
            
                    //Scanning and adding subscriber list values
            
		try {
                   
                    Scanner s = new Scanner(new File(t));
                    while (s.hasNext()){
                    list.add(s.next());
              }
		} catch (Exception e) {
		}
            
            
                  
                   
    }
         static public void packetaddress(String t) {
            
                    //Scanning and adding subscriber list values
            
            
		try {
                    Scanner s = new Scanner(new File(t));
                    while (s.hasNextLine()){
                        String curLine = s.nextLine();
                        String[] splitted = curLine.split(",");
                        String subscriber = splitted[0].trim();
                        String ip1 = splitted[1].trim();
                        String ip2 = splitted[2].trim();
                        list.add(subscriber);
                        packetaddress1.add(ip1);
                        packetaddress2.add(ip2);
                        
                       }
                   

		} catch (Exception e) {

			e.printStackTrace();

		}
         }
         
          static public void authenticate(String t) {
            
                    //Scanning and adding subscriber list values
            
            
		try {
                    Scanner s = new Scanner(new File(t));
                    while (s.hasNextLine()){
                        String curLine = s.nextLine();
                        String[] splitted = curLine.split(",");
                        String subsid = splitted[0].trim();
                        String auth = splitted[1].trim();
                        list.add(subsid);
                        subid.add(subsid);
                        authkey.add(auth);
                        
                       }
                    

		} catch (Exception e) {

			e.printStackTrace();

		}
          }
    
        public static StreamResult output(File path){
      
                 result = new StreamResult(path);
                outputPathToXml = path.getAbsolutePath();
               
                return result;
                        
              }
        
        
       static public void createxml() {

                try {
                i = 0; 
       
                c = 0;
                
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
          
         //Root Element...............................................................................................
                
                Element rootElement = doc.createElement(element.get(i));
                doc.appendChild(rootElement);  
                doc.createCDATASection(elementcontent.get(i));
                rootElement.setTextContent(elementcontent.get(i));
                        for(int j = 0; j < counter.get(i); j++) {
                        Attr att = doc.createAttribute(attName.get(j));
                        rootElement.setAttribute(attName.get(j),attValue.get(j));
                        c++;
                        }
                i++;  
                r++;
                
         //Root Element End.............................................................................

         //Header Element...........................................................................                
        
                Element HElement = doc.createElement(element.get(i));
                rootElement.appendChild(HElement);  
                doc.createCDATASection(elementcontent.get(i));
                HElement.setTextContent(elementcontent.get(i));
                        for(int j = 0; j < counter.get(i); j++) {
                        Attr att = doc.createAttribute(attName.get(c));
                        HElement.setAttribute(attName.get(c),attValue.get(c));
                        c++;
                        }
                i++;  
                r++;
                r++;
            
         //Header Element End...........................................................................                
                
         //Config Data Element............................
                
                Element CElement = doc.createElement(element.get(i));
                rootElement.appendChild(CElement);  
                doc.createCDATASection(elementcontent.get(i));
                CElement.setTextContent(elementcontent.get(i));
                        for(int j = 0; j < counter.get(i); j++) {
                        Attr att = doc.createAttribute(attName.get(c));
                        CElement.setAttribute(attName.get(c),attValue.get(c));
                        c++;
                        }
                i++;  
                r++;                
                
         //Config Data Element End............................                
                
         //Managed Element...................
                
                Element MElement = doc.createElement(element.get(i));
                CElement.appendChild(MElement);  
                doc.createCDATASection(elementcontent.get(i));
                MElement.setTextContent(elementcontent.get(i));
                        for(int j = 0; j < counter.get(i); j++) {
                        Attr att = doc.createAttribute(attName.get(c));
                        MElement.setAttribute(attName.get(c),attValue.get(c));
                        c++;
                        }
                i++;  
                r++;    
                
         //Managed Element End...................                
                
              m = c;                //saving i,c,r values of next element after Managed element
              n = i;
              t = r;  
              
              
              Element appendedElement = null;                   //child
              Element appendingElement = MElement;              //immediate parent
              Element app1 = MElement;                          //parent at next top level to appendingElement
              Element app2 = MElement;                          //parent at next top level to app1
              
              
                          for (int k = 0; k < list.size() ; k++){
                    i = n;                                      ////Reinitializing i,c,r values of next element after Managed element and
                    c = m;                                      //// for the re use for a next subscriber in list
                    r = t;
                    appendingElement = MElement;
                    
                    while (!"fileFooter".equals(element.get(i)))
                            {                              
                                appendedElement = doc.createElement(element.get(i));
                                if(order.get(r)!=order.get(r+1))
                                {
                                    appendingElement.appendChild(appendedElement);
                                    if(element.get(i)=="sn:packetMsAddressList")
                                    {
                                    doc.createCDATASection(packetaddress1.get(k)+"-"+packetaddress2.get(k));
                                    appendedElement.setTextContent(packetaddress1.get(k)+"-"+packetaddress2.get(k));                                    
                                    }
                                    else if(element.get(i)=="auc:authKey"){
                                    doc.createCDATASection(authkey.get(k));
                                    appendedElement.setTextContent(authkey.get(k)); 
                                    }
                                    else{
                                    doc.createCDATASection(elementcontent.get(r));
                                    appendedElement.setTextContent(elementcontent.get(r));
                                    }
                                    for(int j = 0; j < counter.get(i); j++) {
                                        Attr att = doc.createAttribute(attName.get(c));
                                        if(element.get(i)=="sn:SuMSubscriberProfile"&&attName.get(c)=="id")
                                            {
                                                appendedElement.setAttribute(attName.get(c),"94"+list.get(k));
                                       //         System.out.println("INSDIE");
                                            }
                                        else if(element.get(i)=="auc:MSubIdentificationNumber"&&attName.get(c)=="id"){
                                                appendedElement.setAttribute(attName.get(c),subid.get(k));
                                        }
                                        else{
                                                appendedElement.setAttribute(attName.get(c),attValue.get(c));
                                            }
                                        c++;
                                    }
                                    i++;  
                                    r++;
                                    app1 = appendingElement;
                                    appendingElement = appendedElement;

                                }
                                
                                else{
                                    if(order.get(r+2)==appendingElement.getTagName())
                                    {
                                appendingElement.appendChild(appendedElement);
                                if(element.get(i)=="sn:packetMsAddressList")
                                    {
                                    doc.createCDATASection(packetaddress1.get(k)+"-"+packetaddress2.get(k));
                                    appendedElement.setTextContent(packetaddress1.get(k)+"-"+packetaddress2.get(k));                                    
                                    }
                                else if(element.get(i)=="auc:MSubIdentificationNumber"&&attName.get(c)=="id"){
                                                appendedElement.setAttribute(attName.get(c),subid.get(k));
                                        }
                                else{
                                doc.createCDATASection(elementcontent.get(r+1)+elementcontent.get(r));
                                appendedElement.setTextContent(elementcontent.get(r+1)+elementcontent.get(r));
                                    }
                                    for(int j = 0; j < counter.get(i); j++) {
                                        Attr att = doc.createAttribute(attName.get(c));
                                        if(element.get(i)=="sn:SuMSubscriberProfile"&&attName.get(c)=="id")
                                            {
                                                appendedElement.setAttribute(attName.get(c),"94"+list.get(k));
                                       //         System.out.println("INSDIE");
                                            }
                                        else if(element.get(i)=="auc:MSubIdentificationNumber"&&attName.get(c)=="id"){
                                                appendedElement.setAttribute(attName.get(c),subid.get(k));
                                        }
                                        else{
                                                appendedElement.setAttribute(attName.get(c),attValue.get(c));
                                            }
                                        c++;
                                    }
                                i++;
                                r++;
                                r++;
                                r++;
                                appendingElement = app1;
                                    }
                                    
                                else{
                                                                   
                                appendingElement.appendChild(appendedElement);
                                if(element.get(i)=="sn:packetMsAddressList")
                                    {
                                    doc.createCDATASection(packetaddress1.get(k)+"-"+packetaddress2.get(k));
                                    appendedElement.setTextContent(packetaddress1.get(k)+"-"+packetaddress2.get(k));                                    
                                    }
                                else if(element.get(i)=="auc:MSubIdentificationNumber"&&attName.get(c)=="id"){
                                                appendedElement.setAttribute(attName.get(c),subid.get(k));
                                        }
                                else{
                                doc.createCDATASection(elementcontent.get(r+1)+elementcontent.get(r));
                                appendedElement.setTextContent(elementcontent.get(r+1)+elementcontent.get(r));
                                }
                                    for(int j = 0; j < counter.get(i); j++) {
                                        Attr att = doc.createAttribute(attName.get(c));
                                        if(element.get(i)=="sn:SuMSubscriberProfile"&&attName.get(c)=="id")
                                            {
                                                appendedElement.setAttribute(attName.get(c),"94"+list.get(k));
                                       //         System.out.println("INSDIE");
                                            }
                                        else if(element.get(i)=="auc:MSubIdentificationNumber"&&attName.get(c)=="id"){
                                                appendedElement.setAttribute(attName.get(c),subid.get(k));
                                        }
                                        else{
                                                appendedElement.setAttribute(attName.get(c),attValue.get(c));
                                            }
                                        c++;
                                    }
                                i++;
                                r++;
                                r++;

                                    }
                                }

                                
                                
                            }
 }
             
                Element FElement = doc.createElement(element.get(i));
                rootElement.appendChild(FElement);  
                doc.createCDATASection(elementcontent.get(i));
                FElement.setTextContent(elementcontent.get(i));
                        for(int j = 0; j < counter.get(i); j++) {
                        Attr att = doc.createAttribute(attName.get(c));
                        FElement.setAttribute(attName.get(c),attValue.get(c));
                        c++;
                        }                 
              
              
                
                        DOMSource source1 = new DOMSource(doc);
                        transformer.transform(source1,result);
                        System.out.println("File saved!");
                
    element.clear();     //Defined Array for Getting the subscriber list from Text file
    attName.clear();     //Defined Array for Getting the subscriber list from Text file
    attValue.clear();     //Defined Array for Getting the subscriber list from Text file
    elementcontent.clear();      //Defined Array for Getting the subscriber list from Text file
    counter.clear();     //Defined Array for Getting the subscriber list from Text file
    list.clear();
    order.clear();
    packetaddress1.clear();         //Defined Array for assinging parent values
    packetaddress2.clear();         //Defined Array for assinging parent values
    subid.clear();       //Defined Array for assinging parent values
    authkey.clear(); 
                        
     c=0;                 //attribute counter
    n=0;
    m=0;
    i =0;                //element
    q=0;
    r = 0;
    t =0;
    u = 0;                    
                        
                        
                        
                        
                        
              	} catch (ParserConfigurationException pce) {
		pce.printStackTrace();
                } catch (TransformerException tfe) {
		tfe.printStackTrace();
                }  
                
                
       }       
        
        
                    String s;
                    public static File xmlInput(File xml) {
                       absolutePathToXml= xml.getAbsolutePath();
                       return xml;
    }
        
      public static void main(String[] args) {
     
     
    
}

   
}