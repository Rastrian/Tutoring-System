package dao;
import java.util.List;

public interface Dao<T, K> {
	public T get(K chave);
	public void add(T p);
	public void update(T p);
	public void remove(T p);
	public List<T> getAll();
}
