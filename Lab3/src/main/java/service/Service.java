package service;

import UI.Gui.*;
import UI.Gui.Event;
import UI.Gui.Observable;
import UI.Gui.Observer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import domain.*;
import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import repository.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Service implements Observable<Event>{
    private ArrayList<Observer<Event>> observers;

    private Integer sizeListStudents = 0,sizeListHomeworks = 0,sizeListGrades = 0;

    private CrudRepository<String,Student> repoStud = null;
    private CrudRepository<Integer,Homework> repoHW = null;
    private CrudRepository<Pair<Student,Homework>,Grade> repoGr = null;

    private LocalDate firstDay = LocalDate.of(2018, 10, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    private LocalDate now  = LocalDate.now();
    private long weeksofcollage ;

    public Service(CrudRepository repoStud, CrudRepository repoHomework, CrudRepository repoGrade) throws Exception {
        observers = new ArrayList<>();
        this.repoStud = repoStud;
        this.repoHW = repoHomework;
        this.repoGr = repoGrade;

        weeksofcollage = ChronoUnit.WEEKS.between(firstDay, now.plusDays(1))+1;
        this.sizeListGrades = this.allGrades().size();
        this.sizeListHomeworks = this.allHomeworks().size();
        this.sizeListStudents = this.allStudents().size();
    }

    public long collageWeek(){
        return weeksofcollage;
    }

    @Override
    public void addObserver(Observer<Event> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<Event> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event event) {
        observers.forEach(obs -> {
            try {
                obs.update(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addStudent(String id,String name,Integer group,String email,String teacher) throws Exception {
        Student s = new Student(id,name,group,email,teacher);
        this.repoStud.save(s);
        this.sizeListStudents++;
        notifyObservers(new StudentEvent(null,s, ChangeEventType.ADD));
    }

    public void updStudent(String id,String name,Integer group,String email,String teacher) throws Exception {
        Student oldS = this.repoStud.findOne(id);
        Student s = new Student(id,name,group,email,teacher);
        this.repoStud.update(s);
        notifyObservers(new StudentEvent(oldS,s,ChangeEventType.UPDATE));
    }

    public void remStudent(String id) throws Exception {
        this.removeGrade(null,id);
        Student s = this.repoStud.findOne(id);
        this.repoStud.delete(id);
        notifyObservers(new StudentEvent(s,null,ChangeEventType.DELETE));
        sizeListStudents--;
    }

    public Student findStudent(String id) throws Exception {
        return repoStud.findOne(id);
    }
    public Homework findHomework(Integer id) throws Exception {
        return repoHW.findOne(id);
    }

    public void addHomework(Integer id,String requirement,Integer deadline,Integer courseWeek,Integer homeworkWeek) throws Exception {
        Homework h = new Homework(id,requirement,deadline,courseWeek,homeworkWeek);
        this.repoHW.save(h);
        this.sizeListHomeworks++;
        notifyObservers(new HomeworkEvent(null,h,ChangeEventType.ADD));
    }

    public void updHomework(Integer id,String requirement,Integer deadline,Integer courseWeek,Integer homeworkWeek) throws Exception {
        Homework h = new Homework(id,requirement,deadline,courseWeek,homeworkWeek);
        Homework hOld = repoHW.findOne(id);
        this.repoHW.update(h);
        notifyObservers(new HomeworkEvent(hOld,h,ChangeEventType.ADD));
    }

    public void remHomework(Integer id) throws Exception {
        Homework h = repoHW.findOne(id);
        this.removeGrade(id,null);
        this.repoHW.delete(id);
        this.sizeListHomeworks--;
        notifyObservers(new HomeworkEvent(h,null,ChangeEventType.ADD));
    }

    public void updateDeadline(Integer idHomework) throws Exception {
        Homework h = this.repoHW.findOne(idHomework);
        Homework hOld = this.repoHW.findOne(idHomework);
        if(h.requestUpdateDeadline((int) weeksofcollage)){
            h.setDeadline(h.getDeadline()+1);
            this.repoHW.update(h);
            notifyObservers(new HomeworkEvent(hOld,h,ChangeEventType.ADD));
        }else{
            throw new ServiceException("The deadline can't be updated!");
        }
    }

    public ArrayList<Student> allStudents() throws Exception {
        Iterable<Student> it= this.repoStud.findAll();
        ArrayList<Student> arr = new ArrayList<>();
        it.forEach(arr::add);
        if(arr.size()==0){
            throw new ServiceException("The student list is empty!");
        }
        return arr;
    }

    public ArrayList<Homework> allHomeworks() throws Exception {
        Iterable<Homework> it= this.repoHW.findAll();
        ArrayList<Homework> arr = new ArrayList<>();
        it.forEach(arr::add);
        if(arr.size()==0){
            throw new ServiceException("The homeworks list is empty!");
        }
        return arr;
    }

    public ArrayList<Grade> allGrades() throws Exception {
        Iterable<Grade> it= this.repoGr.findAll();
        ArrayList<Grade> arr = new ArrayList<>();
        it.forEach(arr::add);
        return arr;
    }

    private double decrementValue(double oldValue, int noWeeks) {
        for (int i = 0; i < noWeeks; i++)
        {
            oldValue = oldValue - 2.5;
        }
        return oldValue;
    }
    
    public Student findByName(String name) throws Exception {
        var ref = new Object() {
            Student s = new Student();
        };
        this.repoStud.findAll().forEach(x->{
            if(x.getName().equals(name)){
                try {
                    ref.s = repoStud.findOne(x.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return ref.s;
    }

    public void addGrade(String idS, Integer idH, Float grade, String date, String feedback, Integer motivatedWeeks) throws Exception {
            Student s = this.repoStud.findOne(idS);
            Homework h = this.repoHW.findOne(idH);
            Grade g = new Grade(s,h,grade,date,feedback);
            g.setDate((g.getDate().plusDays(7*motivatedWeeks)).toString());
            int decWeeks = (int) (weeksofcollage - motivatedWeeks - h.getDeadline());
            if (decWeeks > 2) {
                throw new Exception("Already passed 2 or more weeks from deadline !");
            }
            if (decWeeks <= 2) {
                double newValue = decrementValue(g.getGrade(), decWeeks);
                if (newValue >= 5) {
                    g.setGrade((float) newValue);
                    repoGr.save(g);
                    notifyObservers(new GradeEvent(null,g,ChangeEventType.ADD));
                    this.sizeListGrades++;
                }
                else{
                    throw new Exception("The value of grade is less 5 after auto decrement");
                }
            }
            else{
                throw new ServiceException("You can't add this grade. The student with id: "+ idS + " has a grade for homework with id: "+idH);
            }
    }

    public void removeGrade(Integer idH, String idS) throws Exception {
        Homework homework = null;
        Student student = null;
        if( idH != null){
            homework = this.repoHW.findOne(idH);
        }
        if( idS != null){
            student = this.repoStud.findOne(idS);
        }
        Student finalStudent = student;
        Homework finalHomework = homework;
        this.repoGr.findAll().forEach(x->{
            if(x.getId().getKey().equals(finalStudent) || x.getId().getValue().equals(finalHomework) ){
                try {
                    this.repoGr.delete(x.getId());
                    this.sizeListGrades--;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<PairCustomized<Integer, String>> listOfHomeworks(){
        List<PairCustomized<Integer, String>> list = new ArrayList<>();
        try {
            repoHW.findAll().forEach(elem ->
            {
                PairCustomized<Integer,String> pair = new PairCustomized<>(elem.getId(),elem.getRequirement());
                list.add(pair);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> listIdHomeworks(){
        List<Integer> list = new ArrayList<>();
        try {
            repoHW.findAll().forEach(elem ->
            {
                list.add(elem.getId());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Integer> listGroups(){
        List<Integer> list = new ArrayList<>();
        try {
            repoStud.findAll().forEach(elem ->
            {
                list.add(elem.getGroup());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GradeDTO> gradeDTOList() throws Exception {
        return this.allGrades().stream().map(grade->{
            String studid = grade.getId().getKey().getId();
            String stud = grade.getId().getKey().getName();
            Integer homework = grade.getId().getValue().getId();
            Float value = grade.getGrade();
            LocalDate date = grade.getDate();
            return new GradeDTO(studid,stud,homework,value,date);
        }).collect(Collectors.toList());
    }

    public List<Pair<String,Double>> AverageForStudents() throws Exception {

        List<GradeDTO> list = this.gradeDTOList();
        Map<String, List<GradeDTO>> filtrare = list.stream()
                .collect(Collectors.groupingBy(GradeDTO::getStudentId, Collectors.toList()));

        List<Pair<String, Double>> raport = filtrare.entrySet().stream()
                .map((e) -> {
                    double s = e.getValue().stream().map(gradeDTO -> gradeDTO.getGrade()).
                            reduce(0D, (n1, n2) -> n1+n2);
                    String nume = null;
                    try {
                        nume = repoStud.findOne(e.getKey()).getName();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    double media = 0;
                    try {
                        media = s / repoHW.size();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    return new Pair<String, Double>(nume, media);
                }).sorted((o1, o2) -> (int)(Math.round((o2.getValue() - o1.getValue())*100))).collect(Collectors.toList());
        return raport;
    }

    private void addAntet(Document doc,String filename,String nameReport) throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(doc, new FileOutputStream(filename));
        doc.open();

        Paragraph title = new Paragraph("Raport Grades.",new Font(Font.FontFamily.HELVETICA,20));
        title.setIndentationLeft(50);
        Paragraph antet = new Paragraph("Grade application.\n"+nameReport,new Font(Font.FontFamily.HELVETICA,14));
        antet.setSpacingBefore(15);
        antet.setSpacingAfter(50);
        doc.add(title);
        doc.add(antet);
    }

    private void writeChartToPDF(Document document,JFreeChart chart, int width, int height, String fileName){
        PdfWriter writer = null;
        //Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
            document.open();

            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height,
                    new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                    height);

            CategoryPlot catplot = chart.getCategoryPlot();
            BarRenderer br = (BarRenderer) catplot.getRenderer();
            br.setMaximumBarWidth(.1);
            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        document.close();
    }

    private Integer noOfGrades(String req) {

        int rez = 0;
        ArrayList<Grade> list = null;
        try {
            list= this.allGrades();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Grade x : list){
            if(x.getId().getValue().getRequirement().equals(req)){
                rez++;
            }
        }
        return rez;
    }

    public List<Pair<String,Float>> raportList2(List<GradeDTO> list){
        Map<Integer,List<GradeDTO>> filtrare = list.stream().collect(Collectors.groupingBy(GradeDTO::getHomewordId, Collectors.toList()));
        return filtrare.entrySet().stream().map((e)->{
            Double s = e.getValue().stream().map(gradeDTO -> gradeDTO.getGrade()).reduce(0D,(n1, n2)->n1+n2);
            String req = null;
            try {
                req = repoHW.findOne(e.getKey()).getRequirement();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            int nr = noOfGrades(req);
            double medie = s/ nr;
            return new Pair<String,Float>(req, (float) medie);
        }).sorted((o1, o2) -> (int)(Math.round((o1.getValue() - o2.getValue())*100))).collect(Collectors.toList());
    }

    public void Raport1(String path) throws Exception {

        List<Pair<String, Double>> raport = AverageForStudents();
        String filenameRap = path;
        String nameRaport = "Average value labs for every student";
        File fileRap = new File(filenameRap);
        if(!fileRap.exists()) {
            Document document = new Document();

            addAntet(document, filenameRap,nameRaport);
            PdfPTable table = new PdfPTable(2);
            for(Pair<String, Double> pair : raport){
                Paragraph studParagraph = new Paragraph(pair.getKey());
                studParagraph.setIndentationLeft(20);
                PdfPCell studCell = new PdfPCell();
                studCell.addElement(studParagraph);

                Paragraph averageParagraph = new Paragraph(pair.getValue().toString());
                averageParagraph.setIndentationLeft(20);
                PdfPCell averageCell = new PdfPCell();
                averageCell.addElement(averageParagraph);

                table.addCell(studCell);
                table.addCell(averageCell);
            }
            document.add(table);
            document.close();

            System.out.println("PDF created");
        }else{
            fileRap.delete();
            this.Raport1(path);
            System.out.println("PDF modified");
        }

    }

    public void Raport2(String path) throws Exception {

        List<Pair<String,Float>> raport = this.raportList2(this.gradeDTOList());

        String filenameRap = path;
        String nameRaport = "Average value labs for every student";
        File fileRap = new File(filenameRap);
        if(!fileRap.exists()) {
            Document document = new Document();
            addAntet(document, filenameRap,nameRaport);
            Paragraph paragraph = new Paragraph("Hardest homework: "+raport.get(0).getKey()+" Value: "+ raport.get(0).getValue().toString());
            document.add(paragraph);
            document.close();

            System.out.println("PDF created");
        }else{
            fileRap.delete();
            this.Raport2(path);
            System.out.println("PDF modified");
        }
    }

    public void Raport3(String path) throws Exception {

        List<Pair<String, Double>> raport = AverageForStudents();
        String filenameRap = path;
        String nameRaport = "Average value labs for every student";
        File fileRap = new File(filenameRap);
        if(!fileRap.exists()) {
            Document document = new Document();
            addAntet(document, filenameRap,nameRaport);
            PdfPTable table = new PdfPTable(2);
            for(Pair<String, Double> pair : raport){
                Paragraph studParagraph = new Paragraph(pair.getKey());
                studParagraph.setIndentationLeft(20);
                PdfPCell studCell = new PdfPCell();
                studCell.addElement(studParagraph);

                Paragraph statusParagraph = new Paragraph("Rejected");
                if(pair.getValue()>=4){
                    statusParagraph = new Paragraph("Accepted");
                }
                statusParagraph.setIndentationLeft(20);
                PdfPCell statusCell = new PdfPCell();
                statusCell.addElement(statusParagraph);

                table.addCell(studCell);
                table.addCell(statusCell);
            }
            document.add(table);
            document.close();

            System.out.println("PDF created");
        }else{
            fileRap.delete();
            this.Raport3(path);
            System.out.println("PDF modified");
        }
    }

    private Boolean checkDate(List<GradeDTO> list){
        var ref = new Object() {Boolean condition = true;};
        list.forEach(x->{
            int deadline = 0;
            try {
                deadline = repoHW.findOne(x.getHomewordId()).getDeadline();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LocalDate grade_day = x.getDate();
            int noOfWeeks =(int)ChronoUnit.WEEKS.between(firstDay, grade_day)+1;
            if(noOfWeeks>deadline){
                ref.condition = false;
            }
        });
        return ref.condition;
    }

    public List<String> raport4List() throws Exception {
        List<GradeDTO> list = this.gradeDTOList();
        Map<String,List<GradeDTO>> filtrare = list.stream()
                .collect(Collectors.groupingBy(GradeDTO::getStudentId, Collectors.toList()));
        List<String> listStudents = filtrare.entrySet().stream()
                .map((e) -> {
                    if(checkDate(e.getValue())){
                        return e.getKey();
                    }
                    return "missing";
                }).collect(Collectors.toList());
        return listStudents.stream().filter(x->!x.equals("missing")).map((x)->{
            try {
                return repoStud.findOne(x).getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public List<Integer> getGroupList(){
        List<Integer> groups = new ArrayList<>();
        for(int i = 1;i<=7;i++){
            groups.add(210+i);
            groups.add(220+i);
            groups.add(230+i);

            groups.add(310+i);
            groups.add(320+i);
            groups.add(330+i);

            groups.add(910+i);
            groups.add(920+i);
            groups.add(930+i);

        }
        return groups;
    }

    public void Raport4(String path) throws Exception {
        List<String> raport = raport4List();

        String filenameRap = path;
        String nameRaport = "Students who give all homeworks at time";
        File fileRap = new File(filenameRap);
        Integer id = 0;
        if(!fileRap.exists()) {
            Document document = new Document();
            addAntet(document, filenameRap,nameRaport);
            PdfPTable table = new PdfPTable(2);
            for(String studentName: raport){
                Paragraph studParagraph = new Paragraph(studentName);
                studParagraph.setIndentationLeft(20);
                PdfPCell studCell = new PdfPCell();
                studCell.addElement(studParagraph);
                Paragraph idParagraph = new Paragraph(id.toString());
                idParagraph.setIndentationLeft(20);
                PdfPCell idCell = new PdfPCell();
                idCell.addElement(idParagraph);
                id++;

                table.addCell(idCell);
                table.addCell(studCell);
            }
            document.add(table);
            document.close();
            System.out.println("PDF created");
        }else{
            fileRap.delete();
            this.Raport4(path);
            System.out.println("PDF modified");
        }
    }

    public int allStudentsSize(){
        return sizeListStudents;
    }
    public int allHomeworksSize(){
        return sizeListHomeworks;
    }
    public int allGradesSize(){ return sizeListGrades; }

    public List<Homework> getPieceDataHomeworks(Integer start_idx,Integer length){
        return repoHW.getPieceOfData(start_idx,length);
    }
    public List<Student> getPieceDataStudents(Integer start_idx,Integer length){
        return repoStud.getPieceOfData(start_idx,length);
    }
    public List<Grade> getPieceDataGrades(Integer start_idx,Integer length){
        return repoGr.getPieceOfData(start_idx,length);
    }
}