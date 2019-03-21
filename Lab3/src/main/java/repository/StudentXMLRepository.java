package repository;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import validation.IValidation;
import validation.ValidationException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class StudentXMLRepository extends StudentRepository{
    public StudentXMLRepository(IValidation<Student> validator, String fileName, String ob) throws Exception {
        super(validator, fileName, ob);
    }

    @Override
    public List<Student> getPieceOfData(Integer start_idx, Integer final_idx) {
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
    public void save(Student s) throws Exception {
        super.save(s);
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
                Element stud = createFromStudent(k, doc);
                root.appendChild(stud);
            });
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(filename));
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private Student createFromElement(Element e){
        String idStudent = e.getAttributeNode("id").getValue();
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        String email = e.getElementsByTagName("email").item(0).getTextContent();
        Integer group = Integer.parseInt(e.getElementsByTagName("group").item(0).getTextContent());
        String teacher = e.getElementsByTagName("teacher").item(0).getTextContent();
        return new Student(idStudent,name,group,email,teacher);
    }

    private Element createFromStudent(Student st, Document doc){
        Element root = doc.getDocumentElement();
        Element e = doc.createElement("student");
        e.setAttribute("id", st.getId());
        e.appendChild(createElement("name", doc, st.getName()));
        e.appendChild(createElement("email", doc, st.getEmail()));
        e.appendChild(createElement("group", doc, st.getGroup().toString()));
        e.appendChild(createElement("teacher", doc, st.getTeacher()));
        return e;
    }

    private Element createElement(String tag, Document doc, String value){
        Element e = (Element) doc.createElement(tag);
        e.setTextContent(value);
        Element root = doc.getDocumentElement();
        return e;
    }
}
