package repository;

import domain.Homework;
import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import validation.IValidation;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class HomeworkXMLRepository extends HomeworkRepository{
    public HomeworkXMLRepository(IValidation<Homework> validator, String fileName, String ob) throws Exception {
        super(validator, fileName, ob);
    }

    @Override
    public List<Homework> getPieceOfData(Integer start_idx, Integer final_idx) {
        return null;
    }

    @Override
    protected void readData () {
        try {
            entities.clear();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);

            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes();
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if(node instanceof Element){
                    this.save(createFromElement((Element)node));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Homework h) throws Exception {
        super.save(h);
        writeData();
    }

    @Override
    protected void writeData(){
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            doc.appendChild(doc.createElement("entities"));
            Element root = doc.getDocumentElement();
            entities.forEach((v,k) ->
            {
                Element h= createFromHomework(k, doc);
                root.appendChild(h);
            });
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(filename));
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private Homework createFromElement(Element e){
        Integer idHomework = Integer.parseInt(e.getAttributeNode("id").getValue());
        String req = e.getElementsByTagName("requirement").item(0).getTextContent();
        Integer dl =Integer.parseInt(e.getElementsByTagName("deadline").item(0).getTextContent());
        Integer cw = Integer.parseInt(e.getElementsByTagName("courseweek").item(0).getTextContent());
        Integer hw= Integer.parseInt(e.getElementsByTagName("homeworkweek").item(0).getTextContent());
        return new Homework(idHomework,req,dl,cw,hw);
    }

    private Element createFromHomework(Homework h, Document doc){
        Element root = doc.getDocumentElement();
        Element e = doc.createElement("homework");
        e.setAttribute("id", h.getId().toString());
        e.appendChild(createElement("requirement", doc, h.getRequirement()));
        e.appendChild(createElement("deadline", doc, h.getDeadline().toString()));
        e.appendChild(createElement("courseweek", doc, h.getCourseWeek().toString()));
        e.appendChild(createElement("homeworkweek", doc, h.getHomeworkWeek().toString()));
        return e;
    }

    private Element createElement(String tag, Document doc, String value){
        Element e = (Element) doc.createElement(tag);
        e.setTextContent(value);
        Element root = doc.getDocumentElement();
        return e;
    }
}
