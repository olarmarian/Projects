package repository;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import validation.*;

import javax.naming.ldap.PagedResultsControl;


@SuppressWarnings("ALL")
public abstract class AbstractCrudRepository<ID, E extends HasId<ID>> implements CrudRepository<ID, E> {
    protected Map<ID, E> entities;
    protected IValidation<E> validator;
    protected String filename;
    protected String objtype;

    protected void writeData() throws IOException, SQLException {
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(filename));
            entities.values().forEach(obj->{
                try {
                    writer.write(obj.toString());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();

        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void readData() {
        entities.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null){
                Class<?> itemClass = Class.forName(objtype);
                Object item = itemClass.newInstance();
                Method method = itemClass.getDeclaredMethod("buildObject", String.class);
                E obj = (E)method.invoke(item, line);
                entities.put(obj.getId(),obj);
            }
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected AbstractCrudRepository(){}

    protected AbstractCrudRepository(IValidation<E> validator,String filename,String objtype) throws Exception {
        this.objtype = objtype;
        this.entities = new HashMap<>();
        this.validator = validator;
        this.filename = filename;
        readData();
    }

    @Override
    public E findOne(ID id) throws Exception {
        try {
            readData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.entities.get(id);
    }

    @Override
    public Iterable<E> findAll(){
        readData();
        return this.entities.values();
    }

    @Override
    public void save(E entity) throws Exception {
        this.validator.validate(entity);
        if (this.entities.putIfAbsent(entity.getId(),entity)!=null) {
            throw new Exception("Duplicate entity");
        }
        writeData();
    }

    @Override
    public void delete(ID id) throws IOException, SQLException, Exception {
        this.entities.remove(id);
        writeData();
    }

    @Override
    public E update(E entity) throws Exception {
        try {
            this.validator.validate(entity);
            if(findOne(entity.getId()) == null){
                return null;
            }
            else {
                this.entities.replace(entity.getId(), entity);
                writeData();
                return entity;
            }
        }
        catch (ValidationException exc){
            System.out.println("Invalid new entity.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long size() throws Exception {
        readData();
        return this.entities.size();
    }
}
