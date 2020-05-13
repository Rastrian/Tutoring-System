package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Monitores;

public class MonitoresDAO implements DAO<Monitores, Integer> {
	private String filename = "Monitores.bin";
	private File file = new File(filename);
	private static List<Monitores> lista;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public MonitoresDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		lista = new ArrayList<Monitores>();
		readFromFile();
	}

	@Override
	public Monitores get(Integer id) {
		readFromFile();
		for (Monitores usu : lista) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Monitores Monitores) {
		try { 
			if(!lista.contains(Monitores)) {
				lista.add(Monitores);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Monitores '" + Monitores.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Monitores Monitores) {
		int index = lista.indexOf(Monitores);
		if (index != -1) {
			lista.set(index, Monitores);
		}
		saveToFile();
	}

	@Override
	public void remove(Monitores Monitores) {
		int index = lista.indexOf(Monitores);
		if (index != -1) {
			lista.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Monitores Monitores : lista) {
				outputFile.writeObject(Monitores);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Monitores no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Monitores> getAll() {
		return lista;
	}

	private List<Monitores> readFromFile() {
		lista = new ArrayList<Monitores>();
		Monitores Monitores = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				Monitores = (Monitores) inputFile.readObject();
				lista.add(Monitores);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Monitores no disco!");
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