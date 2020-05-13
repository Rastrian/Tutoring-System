package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Cursos;

public class CursosDAO implements DAO<Cursos, Integer> {
	private String filename = "Cursos.bin";
	private File file = new File(filename);
	private static List<Cursos> lista;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public CursosDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		lista = new ArrayList<Cursos>();
		readFromFile();
	}

	@Override
	public Cursos get(Integer id) {
		readFromFile();
		for (Cursos usu : lista) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Cursos Cursos) {
		try { 
			if(!lista.contains(Cursos)) {
				lista.add(Cursos);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Cursos '" + Cursos.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Cursos Cursos) {
		int index = lista.indexOf(Cursos);
		if (index != -1) {
			lista.set(index, Cursos);
		}
		saveToFile();
	}

	@Override
	public void remove(Cursos Cursos) {
		int index = lista.indexOf(Cursos);
		if (index != -1) {
			lista.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Cursos Cursos : lista) {
				outputFile.writeObject(Cursos);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Cursos no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Cursos> getAll() {
		return lista;
	}

	private List<Cursos> readFromFile() {
		lista = new ArrayList<Cursos>();
		Cursos Cursos = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				Cursos = (Cursos) inputFile.readObject();
				lista.add(Cursos);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Cursos no disco!");
			e.printStackTrace();
		}
		return lista;
	}

	public int count() {
		return readFromFile().size();
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

}