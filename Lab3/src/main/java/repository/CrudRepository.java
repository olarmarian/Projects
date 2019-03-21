package repository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface CrudRepository<ID, E> {
        E findOne(ID id) throws Exception;
        Iterable<E> findAll() throws Exception;
        void save(E entity) throws Exception;
        void delete(ID id) throws IOException, SQLException, Exception;
        E update(E entity) throws Exception;
        long size() throws Exception;
        List<E> getPieceOfData(Integer start_idx,Integer length);
    }
