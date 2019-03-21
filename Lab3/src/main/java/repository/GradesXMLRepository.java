package repository;

import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.util.Pair;
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

public class GradesXMLRepository extends GradesRepository{
    public GradesXMLRepository(IValidation<Grade> validator, String fileName, String ob,StudentRepository sr, HomeworkRepository hr) throws Exception {
        super(validator, fileName, ob,sr,hr);
    }

    @Override
    public List<Grade> getPieceOfData(Integer start_idx, Integer final_idx) {
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
    public void save(Grade g) throws Exception {
        super.save(g);
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
                Element h= createFromGrade(k, doc);
                root.appendChild(h);
            });
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(filename));
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private Grade createFromElement(Element e){
        String att[] = e.getAttributeNode("student").getValue().split(" ");
        Student s = new Student(att[0],att[1],Integer.parseInt(att[2]),att[3],att[4]);
        att = e.getAttributeNode("homework").getValue().split(" ");
        Homework h = new Homework(Integer.parseInt(att[0]),att[1],Integer.parseInt(att[2]),Integer.parseInt(att[3]),Integer.parseInt(att[4]));
        String fb = e.getElementsByTagName("feedback").item(0).getTextContent();
        Float gr =Float.parseFloat(e.getElementsByTagName("value").item(0).getTextContent());
        String date = e.getElementsByTagName("date").item(0).getTextContent();
        return new Grade(s,h,gr,date,fb);
    }

    private Element createFromGrade(Grade g, Document doc){
        Element root = doc.getDocumentElement();
        Element e = doc.createElement("grade");
        e.setAttribute("id", g.getId().getKey()+" "+g.getId().getValue().toString());
        e.appendChild(createElement("student", doc, g.getId().getKey().getId()));
        e.appendChild(createElement("homework", doc, g.getId().getValue().toString()));
        e.appendChild(createElement("value", doc, g.getGrade().toString()));
        e.appendChild(createElement("date", doc, g.getDate().toString()));
        e.appendChild(createElement("feedback", doc, g.getFeedback()));
        return e;
    }

    private Element createElement(String tag, Document doc, String value){
        Element e = (Element) doc.createElement(tag);
        e.setTextContent(value);
        Element root = doc.getDocumentElement();
        return e;
    }
}
